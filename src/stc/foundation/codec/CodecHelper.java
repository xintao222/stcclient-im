package stc.foundation.codec;

import java.util.ArrayList;

import android.util.Log;
import stc.foundation.codec.bean.AbstractCommonBean;
import stc.foundation.codec.bean.tlv.annotation.TLVAttribute;
import stc.foundation.codec.bean.tlv.decode.decoders.BeanTLVDecoder;
import stc.foundation.codec.bean.tlv.encode.TLVEncodeContext;
import stc.foundation.codec.bean.tlv.encode.encoders.BeanTLVEncoder;
import stc.foundation.codec.bean.util.meta.Int2TypeMetainfo;
import stc.foundation.codec.bean.xip.SsipHeader;
import stc.foundation.codec.bean.xip.XipUtils;
import stc.foundation.util.ByteUtils;

public class CodecHelper {
	
	/**
	 * 在这里制定所有需要编解码的ssip类
	 */
	private static ArrayList<Class<?>> ssipClasses = new ArrayList<Class<?>>(); 
	private static Int2TypeMetainfo ssipMetaInfo = null;
	static {
		ssipClasses.add(stc.bean.AuthRequest.class);
		ssipClasses.add(stc.bean.AuthResponse.class);
		ssipMetaInfo = XipUtils.createSsipTypeMetainfo(ssipClasses);
	}

	/**
	 * 编码TLV结构的对象，不包含任何头信息
	 * @param request
	 * @return
	 */
	static public byte[] encode(Object tlvBean) {

		BeanTLVEncoder beanEncoder = new BeanTLVEncoder();
		TLVEncodeContext encode = beanEncoder.getEncodeContextFactory()
				.createEncodeContext(tlvBean.getClass(), null);
		return ByteUtils.union(beanEncoder.encode(tlvBean, encode));
	}

	/**
	 * 解码TLV结构的数据，不包含任何头信息
	 * @param content
	 * @param resptype
	 * @return
	 */
	static public Object decode(byte[] content, Class<?> resptype) {

		BeanTLVDecoder beanDecoder = new BeanTLVDecoder();
		return beanDecoder.decode(content.length, content, beanDecoder
				.getDecodeContextFactory().createDecodeContext(resptype, null));
	}
	
	/**
	 * 编码带ssip头的TLV结构对象,之所以采用AbstractCommonBean类型，是因为我们需要去取uuid作为header中的uuid
	 * @param ssipBean
	 * @return
	 */
	static public byte[] encodeSsip(AbstractCommonBean ssipBean) {
		TLVAttribute attr = ssipBean.getClass().getAnnotation(TLVAttribute.class);
		
		//先编码协议体
		BeanTLVEncoder beanEncoder = new BeanTLVEncoder();
		TLVEncodeContext encode = beanEncoder.getEncodeContextFactory()
				.createEncodeContext(ssipBean.getClass(), null);
		byte[] body = ByteUtils.union(beanEncoder.encode(ssipBean, encode));
		
		//编码协议头
		//使用几个关键参数构造协议头对象
		SsipHeader ssip = XipUtils.createSsipHeader(ssipBean.getIdentification(), attr.tag(), body.length);		
		
		return  XipUtils.encodeSsip(ssip, body);
	}
	
	/**
	 * 解码带ssip头的TLV结构数据,之所以采用AbstractCommonBean类型，是因为我们需要去设置uuid
	 * @param content
	 * @return
	 */
	static public AbstractCommonBean decodeSsip(byte[] content) {
		
		SsipHeader ssip = XipUtils.decodeSsipHeader(content);
		Class<?> ssipType = ssipMetaInfo.find(ssip.getMessageCode());
		
		byte[] body = new byte[ssip.getMessageLength()];
		System.arraycopy(content, content.length-ssip.getMessageLength(), body, 0, ssip.getMessageLength());
		BeanTLVDecoder beanDecoder = new BeanTLVDecoder();
		AbstractCommonBean bean = (AbstractCommonBean)beanDecoder.decode(body.length, body, beanDecoder
				.getDecodeContextFactory().createDecodeContext(ssipType, null));
		bean.setIdentification(ssip.getTransactionAsUUID());
		return bean;
	}
	
	/**
	 * 从byte[]读取ssip中的length字段，返回整个协议包的大小
	 * @param buf
	 * @return
	 */
	static public int getSsipLength(byte[] buf) {
		return XipUtils.getSsipLength(buf);
	}
	
}
