package stc.foundation.codec;

import java.util.HashMap;
import java.util.Map;

import stc.foundation.codec.bean.tlv.encode.DefaultEncodeContextFactory;
import stc.foundation.codec.bean.tlv.encode.DefaultEncoderRepository;
import stc.foundation.codec.bean.tlv.encode.TLVEncoder;
import stc.foundation.codec.bean.tlv.encode.encoders.BeanTLVEncoder;
import stc.foundation.codec.bean.tlv.encode.encoders.ByteArrayTLVEncoder;
import stc.foundation.codec.bean.tlv.encode.encoders.ByteTLVEncoder;
import stc.foundation.codec.bean.tlv.encode.encoders.IntTLVEncoder;
import stc.foundation.codec.bean.tlv.encode.encoders.LongTLVEncoder;
import stc.foundation.codec.bean.tlv.encode.encoders.ShortTLVEncoder;
import stc.foundation.codec.bean.tlv.encode.encoders.StringTLVEncoder;
import stc.foundation.util.DefaultNumberCodecs;


/**
 *
 * 
 * */
public class EncodeContextFactory {

	private static DefaultEncodeContextFactory instance = null;

	private EncodeContextFactory() {
	}

	public synchronized static DefaultEncodeContextFactory getInstance() {
		if (instance == null) {
			instance = new DefaultEncodeContextFactory();
			DefaultEncoderRepository repo = new DefaultEncoderRepository();
			repo.setEncoders(getEncoders());
			instance.setEncoderRepository(repo);
			instance.setNumberCodec(DefaultNumberCodecs
					.getBigEndianNumberCodec());
		}
		return instance;
	}

	private static Map<Class<?>, TLVEncoder> getEncoders() {
		Map<Class<?>, TLVEncoder> encoders = new HashMap<Class<?>, TLVEncoder>();
		encoders.put(String.class, new StringTLVEncoder());
		encoders.put(Integer.class, new IntTLVEncoder());
		encoders.put(int.class, new IntTLVEncoder());
		encoders.put(Byte.class, new ByteTLVEncoder());
		encoders.put(byte.class, new ByteTLVEncoder());
		encoders.put(byte[].class, new ByteArrayTLVEncoder());
		encoders.put(Short.class, new ShortTLVEncoder());
		encoders.put(short.class, new ShortTLVEncoder());
		encoders.put(Object.class, new BeanTLVEncoder());
		encoders.put(Long.class, new LongTLVEncoder());
		encoders.put(long.class, new LongTLVEncoder());
		return encoders;
	}

}
