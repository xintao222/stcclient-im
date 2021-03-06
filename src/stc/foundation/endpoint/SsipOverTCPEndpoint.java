package stc.foundation.endpoint;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.HashMap;
import java.util.UUID;

import android.util.Log;

import stc.foundation.codec.CodecHelper;
import stc.foundation.codec.bean.AbstractCommonBean;
import stc.foundation.codec.bean.xip.SsipHeader;
import stc.foundation.util.ByteUtils;
import stc.foundation.util.SingleTaskHandler;
import stc.foundation.util.TaskRunnable;

/**
 * 建立一条TCP连接，维持这条链接，并负责Ssip消息的编解码和收发
 * 
 * @author key
 * 
 */
public class SsipOverTCPEndpoint implements SsipEndpoint {

	private final static String TAG = "SsipOverTCPEndpoint";

	private SsipReceiver receiver = null;
	private String host = null;
	private int port = 0;
	private Socket socket = null;
	private DataInputStream input = null;
	private DataOutputStream output = null;
	private int MSGSEND_TIMEOUT_MILLIS = 30000;
	private int CONNECT_TIMEOUT_MILLIS = 10000;
	private EP_STAT status = EP_STAT.DISCONNECTED;
	/**
	 * 主要的工作将由master任务队列完成，由于new Socket/write方法都是阻塞的，所以可能造成排队的情况。一种结果就是request的超时检测时间将被拉长
	 */
	private SingleTaskHandler masterHandler = new SingleTaskHandler(
			"ssip_master");

	/**
	 * 接收线程，仅负责收据的收
	 */
	private ReadThread readThread = null;

	/**
	 * 用于保存所有还未处理的ssip消息
	 */
	private HashMap<UUID, AbstractCommonBean> messages = new HashMap<UUID, AbstractCommonBean>();

	/**
	 * 记录当前重连的时间
	 */
	private int reconnDelayedMills = 0;

	@Override
	public void init(SsipReceiver receiver, String host, int port) {
		this.receiver = receiver;
		this.host = host;
		this.port = port;

		// 立即进行连接
		masterHandler.post(new Runnable() {

			@Override
			public void run() {
				connect();
			}
		});
	}

	@Override
	public EP_STAT status() {
		return this.status;
	}

	@Override
	public void send(AbstractCommonBean msg) {

		// 使用异步的方式
		masterHandler.post(new TaskRunnable("", "", msg, "send ssip") {

			@Override
			public void trun() {
				AbstractCommonBean msg = (AbstractCommonBean) obj;
				// 开始编码
				byte[] buf = CodecHelper.encodeSsip(msg);
				if (buf == null) {
					// 编码失败
					receiver.messageFailed(msg, EP_REASON.ENCODE_ERROR);
					return;
				}

				if (output == null) {
					// 网络异常
					receiver.messageFailed(msg, EP_REASON.NET_ERROR);
					return;
				}

				try {
					output.write(buf);

					// 发送成功
					receiver.messageSent(msg);

					// 等待响应
					messages.put(msg.getIdentification(), msg);
					
					// 新增一个响应超时检测的任务
					masterHandler.postDelayedWithToken(new TaskRunnable("", "", msg,
							"check ssip timeout") {

						@Override
						public void trun() {
							// 超时时间到
							AbstractCommonBean msg = (AbstractCommonBean) obj;
							AbstractCommonBean request = messages.get(msg
									.getIdentification());
							if (request != null) {
								// 响应超时了
								receiver.messageFailed(msg, EP_REASON.RESP_TIMEOUT);
								messages.remove(msg.getIdentification());
							}
						}
					}, MSGSEND_TIMEOUT_MILLIS, msg.getIdentification().toString());
					
				} catch (Exception e) {
					e.printStackTrace();
					// 网络异常
					receiver.messageFailed(msg, EP_REASON.NET_ERROR);
					//发送失败不需要触发重连，该工作会由接收线程负责监测
					try {
						//把socket关闭掉，这样可以更快触发接收线程检测到异常
						socket.close();
						socket = null;
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		});		
	}

		
	/**
	 * 断开重连的逻辑在这里，读和写、第一次连接失败、心跳失败等都会触发这个操作
	 * 
	 */
	private void connect() {
		TaskRunnable task = new TaskRunnable("reconnect") {
			@Override
			public void trun() {
				try {
					// 先清理socket相关资源
					readThread = null;

					if (socket != null) {
						try {
							socket.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
						socket = null;
					}
					if (input != null) {
						try {
							input.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
						input = null;
					}
					if (output != null) {
						try {
							output.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
						output = null;
					}
					if (status != EP_STAT.DISCONNECTED) {
						//状态变更
						status = EP_STAT.DISCONNECTED;
						receiver.statusChanged(status);
					}					
					
					// 发起连接,设置超时时间CONNECT_TIMEOUT_MILLIS
					socket = new Socket();
					SocketAddress address = new InetSocketAddress(host, port);
					socket.connect(address, CONNECT_TIMEOUT_MILLIS);
					input = new DataInputStream(socket.getInputStream());
					output = new DataOutputStream(socket.getOutputStream());
					readThread = new ReadThread(SsipOverTCPEndpoint.this, input);
					readThread.start();
					// 重置重连时间
					reconnDelayedMills = 0;
					status = EP_STAT.CONNECTED;
					receiver.statusChanged(status);

				} catch (Exception e) {
					e.printStackTrace();
					connect();

				}
			}
		};
		
		//如果是第一次重连那么需要插队执行
		if (this.reconnDelayedMills==0){
			masterHandler.postAtFront(task);
		} else {
			masterHandler.postDelayed(task, this.getReconnectDelayedMills());
		}

	}

	/**
	 * 获取下一次重连的时间
	 * 
	 * @return
	 */
	private int getReconnectDelayedMills() {
		if (reconnDelayedMills < 5000) {
			reconnDelayedMills += 1000;
		} else if (reconnDelayedMills < 60000) {
			reconnDelayedMills += 5000;
		}
		return reconnDelayedMills;
	}

	/**
	 * 接收到完整的ssip报文，被ReadThread回调，需要异步处理
	 * 
	 * @param ssip
	 * @return
	 */
	private void onReceivedSsip(byte[] ssip) {
		// 使用异步的方式
		masterHandler.post(new TaskRunnable("", "", ssip, "receive ssip") {

			@Override
			public void trun() {
				byte[] buf = (byte[]) obj;
				// 开始编码
				AbstractCommonBean response = CodecHelper.decodeSsip(buf);
				if (response == null) {
					// 解码失败
					Log.e(TAG,
							"decode failed:"
									+ ByteUtils.bytesAsHexString(buf, 256));
				} else {
					AbstractCommonBean request = messages.get(response
							.getIdentification());
					if (request != null) {
						// 已收到响应，删除
						messages.remove(response.getIdentification());
						masterHandler.clearPendingTask(response.getIdentification().toString());
					}

					receiver.messageReceived(response);
				}
			}
		});
	}

	/**
	 * ReadThread读失败，需要重新建立连接，需要异步处理
	 */
	private void onReadError() {
		//触发重连		
		this.connect();
	}

	/**
	 * 该接收线程负责从socket/inputstream读取完整的ssip协议数据，并交给master负责后续处理
	 * 
	 * @author key
	 * 
	 */
	private class ReadThread extends Thread {

		// 用于接收socket数据的stream
		DataInputStream input = null;
		SsipOverTCPEndpoint endpoint = null;

		// 用于接收SSIP头的缓存
		byte[] header = new byte[SsipHeader.SSIP_HEADER_LENGTH];

		public ReadThread(SsipOverTCPEndpoint endpoint, DataInputStream input) {
			super();
			this.endpoint = endpoint;
			this.input = input;
		}

		@Override
		public void run() {
			int ret = -1;
			if (input != null) {
				try {

					do {
						Log.i(TAG, "start receive data...");
						// 读取SSIP头数据
						ret = input.read(header, 0, header.length);
						if (ret != header.length) {
							// 读取失败
							throw (new IOException("socket read header error:"
									+ ret));
						}
						// 获取整个SSIP报文长度
						ret = CodecHelper.getSsipLength(header);
						byte[] message = new byte[ret];
						System.arraycopy(header, 0, message, 0, header.length);
						if (ret > header.length) {
							// 继续读取body部分
							ret = input.read(message, header.length, ret
									- header.length);
							if (ret != (message.length - header.length)) {
								// 读取失败
								throw (new IOException(
										"socket read body error:" + ret));
							}

						}
						// 读取到完整的ssip报文
						endpoint.onReceivedSsip(message);
					} while (true);
				} catch (Exception e) {
					e.printStackTrace();
					// 读异常,向endpoint通知,停止工作
					endpoint.onReadError();
				}
			} else {
				throw (new RuntimeException("socket input is null"));
			}
			// 停止该线程
			this.endpoint = null;
			this.input = null;
			this.header = null;
		}
	}

}
