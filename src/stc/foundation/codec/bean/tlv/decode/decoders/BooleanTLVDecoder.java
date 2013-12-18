package stc.foundation.codec.bean.tlv.decode.decoders;

import stc.foundation.codec.bean.tlv.decode.TLVDecodeContext;
import stc.foundation.codec.bean.tlv.decode.TLVDecoder;

public class BooleanTLVDecoder implements TLVDecoder {

	public Object decode(int tlvLength, byte[] tlvValue, TLVDecodeContext ctx) {
		return new Boolean(0 != tlvValue[0]);
	}
}
