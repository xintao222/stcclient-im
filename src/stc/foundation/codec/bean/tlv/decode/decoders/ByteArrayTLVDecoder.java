/**
 * 
 */
package stc.foundation.codec.bean.tlv.decode.decoders;

import stc.foundation.codec.bean.tlv.decode.TLVDecodeContext;
import stc.foundation.codec.bean.tlv.decode.TLVDecoder;
import stc.foundation.util.ArrayUtils;

/**
 * @author hp
 * 
 */
public class ByteArrayTLVDecoder implements TLVDecoder {

	// @SuppressWarnings("unused")
	// private static final Logger logger =
	// LoggerFactory.getLogger(ByteArrayTLVDecoder.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skymobi.bean.tlv.TLVDecoder#decode(int, byte[],
	 * com.skymobi.bean.tlv.TLVDecodeContext)
	 */
	public Object decode(int tlvLength, byte[] tlvValue, TLVDecodeContext ctx) {
		if (tlvLength == tlvValue.length) {
			return tlvValue;
		} else {
			return ArrayUtils.subarray(tlvValue, 0, tlvLength);
		}
	}

}
