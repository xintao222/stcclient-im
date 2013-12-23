package stc.foundation.endpoint;

import stc.foundation.codec.bean.AbstractCommonBean;


/**
 * 
 * @author key
 *
 */
public interface SsipEndpoint{
	
	/**
	 * endpoint状态定义
	 * @author key
	 *
	 */
	public enum EP_STAT{
		DISCONNECTED,		//未连接
		CONNECTED			//已连接
		//READY,				//准备好
		//STOP				//停止状态
	};
	
	/**
	 * 
	 * @author key
	 *
	 */
	public enum EP_REASON{
		ENCODE_ERROR,		//编码失败
		RESP_TIMEOUT,		//响应超时
		NET_ERROR			//网络异常
	};
	
	/**
	 * 初始化EP，但需要start才能开启；后续可能需要注册参数
	 * @param receiver		//消息或者状态更改回调
	 * @param host			//服务器地址 ip or domainname
	 * @param port			//端口
	 */
	public void init(SsipReceiver receiver, String host, int port);
	
	/**
	 * 停止运行，清理数据。不可再启动，需要重新new对象
	 */
	public void quit();
	
	/**
	 * 获取EP当前运行状态
	 * @return
	 */
	public EP_STAT status();
	
	/**
	 * 发送消息
	 * @param msg
	 */
	public void send(AbstractCommonBean msg);
}
