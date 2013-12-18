package stc.foundation.codec;

import stc.foundation.codec.bean.tlv.decode.decoders.BeanTLVDecoder;
import stc.foundation.codec.bean.tlv.encode.TLVEncodeContext;
import stc.foundation.codec.bean.tlv.encode.encoders.BeanTLVEncoder;
import stc.foundation.util.ByteUtils;

public class CodecManager {

	static public byte[] encode(Object request) {

		BeanTLVEncoder beanEncoder = new BeanTLVEncoder();
		TLVEncodeContext encode = beanEncoder.getEncodeContextFactory()
				.createEncodeContext(request.getClass(), null);
		return ByteUtils.union(beanEncoder.encode(request, encode));
	}

	static public Object decode(byte[] content, Class<?> resptype) {

		BeanTLVDecoder beanDecoder = new BeanTLVDecoder();
		return beanDecoder.decode(content.length, content, beanDecoder
				.getDecodeContextFactory().createDecodeContext(resptype, null));
	}
}
