package stc.foundation.codec;

import java.util.HashMap;
import java.util.Map;

import stc.foundation.codec.bean.tlv.decode.DefaultDecodeContextFactory;
import stc.foundation.codec.bean.tlv.decode.DefaultDecoderRepository;
import stc.foundation.codec.bean.tlv.decode.TLVDecoder;
import stc.foundation.codec.bean.tlv.decode.decoders.BeanTLVDecoder;
import stc.foundation.codec.bean.tlv.decode.decoders.ByteArrayTLVDecoder;
import stc.foundation.codec.bean.tlv.decode.decoders.ByteTLVDecoder;
import stc.foundation.codec.bean.tlv.decode.decoders.IntTLVDecoder;
import stc.foundation.codec.bean.tlv.decode.decoders.LongTLVDecoder;
import stc.foundation.codec.bean.tlv.decode.decoders.ShortTLVDecoder;
import stc.foundation.codec.bean.tlv.decode.decoders.StringTLVDecoder;
import stc.foundation.util.DefaultNumberCodecs;


public class DecodeContextFactory {

	private static DefaultDecodeContextFactory instance = null;

	private DecodeContextFactory() {
	}

	public synchronized static DefaultDecodeContextFactory getInstance() {
		if (instance == null) {
			instance = new DefaultDecodeContextFactory();
			DefaultDecoderRepository repo = new DefaultDecoderRepository();
			repo.setDecoders(getDecoders());
			instance.setDecoderRepository(repo);
			instance.setNumberCodec(DefaultNumberCodecs
					.getBigEndianNumberCodec());
		}
		return instance;
	}

	private static Map<Class<?>, TLVDecoder> getDecoders() {
		Map<Class<?>, TLVDecoder> decoders = new HashMap<Class<?>, TLVDecoder>();
		decoders.put(String.class, new StringTLVDecoder());
		decoders.put(Integer.class, new IntTLVDecoder());
		decoders.put(int.class, (TLVDecoder) new IntTLVDecoder());
		decoders.put(Byte.class, new ByteTLVDecoder());
		decoders.put(byte.class, new ByteTLVDecoder());
		decoders.put(byte[].class, new ByteArrayTLVDecoder());
		decoders.put(Short.class, new ShortTLVDecoder());
		decoders.put(short.class, new ShortTLVDecoder());
		decoders.put(Object.class, new BeanTLVDecoder());
		decoders.put(Long.class, new LongTLVDecoder());
		decoders.put(long.class, new LongTLVDecoder());
		return decoders;
	}

}
