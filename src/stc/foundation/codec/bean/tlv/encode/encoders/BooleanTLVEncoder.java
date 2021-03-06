package stc.foundation.codec.bean.tlv.encode.encoders;

import java.util.Arrays;
import java.util.List;

import stc.foundation.codec.bean.tlv.encode.TLVEncodeContext;
import stc.foundation.codec.bean.tlv.encode.TLVEncoder;


public class BooleanTLVEncoder implements TLVEncoder {

	public List<byte[]> encode(Object src, TLVEncodeContext ctx) {
		if (src instanceof Boolean) {
			return Arrays.asList(new byte[] { (byte) ((Boolean) src ? 1 : 0) });
		} else {
			throw new RuntimeException(
					"BooleanTLVEncoder: wrong source type. [" + src.getClass()
							+ "]");
		}
	}
}
