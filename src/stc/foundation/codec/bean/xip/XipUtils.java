package stc.foundation.codec.bean.xip;

import java.util.Collection;
import java.util.UUID;

import stc.foundation.util.Transformer;
import stc.foundation.util.DefaultNumberCodecs;
import stc.foundation.codec.bean.tlv.annotation.TLVAttribute;
import stc.foundation.codec.bean.util.meta.BeanMetainfoUtils;
import stc.foundation.codec.bean.util.meta.Int2TypeMetainfo;

public class XipUtils {
	
	
	/**
	 * 构造SSIP协议头对象
	 * @param transaction	uuid
	 * @param messageCode	协议编码
	 * @param messageLen	协议体编码后的长度
	 * @return
	 */
	public static SsipHeader createSsipHeader(UUID transaction, int messageCode, int messageLen) {
		SsipHeader ssip = new SsipHeader();
		ssip.setLength(SsipHeader.SSIP_HEADER_LENGTH+messageLen);
		ssip.setMessageCode(messageCode);
		ssip.setTransaction(transaction);
		ssip.setMessageLength(messageLen);
		return ssip;
	}
	
	/**
	 * 编码一个ssip的协议对象
	 * @param messageCode
	 * @param messageLen
	 * @param body
	 * @return
	 */
	public static byte[] encodeSsip(SsipHeader ssip, byte[] body) {
		byte[] buf = new byte[ssip.getLength()];
		
		//0,ver
		byte[] temp = DefaultNumberCodecs.getBigEndianNumberCodec().int2Bytes(ssip.getBasicVer(), 1);
		buf[0] = temp[0];
		
		//1-3,total len
		temp = DefaultNumberCodecs.getBigEndianNumberCodec().int2Bytes(ssip.getLength(), 3);
		buf[1] = temp[0];
		buf[2] = temp[1];
		buf[3] = temp[2];
		
		//4-19,UUID
		temp = DefaultNumberCodecs.getBigEndianNumberCodec().long2Bytes(ssip.getFirstTransaction(), 8);
		System.arraycopy(temp, 0, buf, 4, 8);
		temp = DefaultNumberCodecs.getBigEndianNumberCodec().long2Bytes(ssip.getSecondTransaction(), 8);
		System.arraycopy(temp, 0, buf, 12, 8);
		
		//20-23,0x00
		//24-27,code
		temp = DefaultNumberCodecs.getBigEndianNumberCodec().int2Bytes(ssip.getMessageCode(), 4);
		buf[24] = temp[0];
		buf[25] = temp[1];
		buf[26] = temp[2];
		buf[27] = temp[3];
		
		//28-31,data len
		temp = DefaultNumberCodecs.getBigEndianNumberCodec().int2Bytes(ssip.getMessageLength(), 4);
		buf[28] = temp[0];
		buf[29] = temp[1];
		buf[30] = temp[2];
		buf[31] = temp[3];
		
		//把body接上
		System.arraycopy(body, 0, buf, 32, body.length);
		
		return buf;
	}
	
	public static SsipHeader decodeSsipHeader(byte[] content) {
		
		SsipHeader ssip = new SsipHeader();
		
		//0,ver ignore
		//ssip.setBasicVer(content[0]);
		
		byte[] buf = new byte[8];
		
		//1-3,total len
		System.arraycopy(content, 1, buf, 0, 3);
		ssip.setLength(DefaultNumberCodecs.getBigEndianNumberCodec().bytes2Int(buf, 3));
		
		//4-19,UUID
		System.arraycopy(content, 4, buf, 0, 8);
		ssip.setFirstTransaction(DefaultNumberCodecs.getBigEndianNumberCodec().bytes2Long(buf, 8));
		
		System.arraycopy(content, 12, buf, 0, 8);
		ssip.setSecondTransaction(DefaultNumberCodecs.getBigEndianNumberCodec().bytes2Long(buf, 8));
		
		//20-23,0x00
		//24-27,code
		System.arraycopy(content, 24, buf, 0, 4);
		ssip.setMessageCode(DefaultNumberCodecs.getBigEndianNumberCodec().bytes2Int(buf, 4));
		
		//28-31,data len
		System.arraycopy(content, 28, buf, 0, 4);
		ssip.setMessageLength(DefaultNumberCodecs.getBigEndianNumberCodec().bytes2Int(buf, 4));
		
		return ssip;
	}
	
	/**
	 * 注册所有的SSIP class定义
	 */
	private static final Transformer<?, ?> SSIP_CLS2INT = new Transformer<Object, Object>() {

		public Object transform(Object input) {
			Class<?> cls = (Class<?>) input;
			TLVAttribute attr = cls.getAnnotation(TLVAttribute.class);
			return null != attr ? attr.tag() : null;
		}
	};

	static public Int2TypeMetainfo createSsipTypeMetainfo(
			Collection<Class<?>> classes) {

		return BeanMetainfoUtils.createTypeMetainfoByClasses(classes, SSIP_CLS2INT);
	}
	
	/**
	 * 获取该ssip报文长度
	 * @param header
	 */
	public static int getSsipLength(byte[] header) {
		byte[] buf = new byte[3];		
		//1-3,total len
		System.arraycopy(header, 1, buf, 0, 3);
		return DefaultNumberCodecs.getBigEndianNumberCodec().bytes2Int(buf, 3);
	}

}
