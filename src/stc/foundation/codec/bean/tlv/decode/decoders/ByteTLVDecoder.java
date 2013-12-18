/**
 * 
 */
package stc.foundation.codec.bean.tlv.decode.decoders;

import stc.foundation.codec.bean.tlv.decode.TLVDecodeContext;
import stc.foundation.codec.bean.tlv.decode.TLVDecoder;

/**
 * @author hp
 * 
 */
public class ByteTLVDecoder implements TLVDecoder {

	// @SuppressWarnings("unused")
	// private static final Logger logger =
	// LoggerFactory.getLogger(ByteTLVDecoder.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skymobi.bean.tlv.TLVDecoder#decode(int, byte[],
	 * com.skymobi.bean.tlv.TLVDecodeContext)
	 */
	public Object decode(int tlvLength, byte[] tlvValue, TLVDecodeContext ctx) {
		return tlvValue[0];
	}

}