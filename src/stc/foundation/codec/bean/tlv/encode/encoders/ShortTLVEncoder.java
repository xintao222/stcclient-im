/**
 * 
 */
package stc.foundation.codec.bean.tlv.encode.encoders;

import java.util.Arrays;
import java.util.List;

import stc.foundation.codec.bean.tlv.encode.TLVEncodeContext;
import stc.foundation.codec.bean.tlv.encode.TLVEncoder;


/**
 * @author hp
 * 
 */
public class ShortTLVEncoder extends AbstractNumberTLVEncoder implements
		TLVEncoder {

	// @SuppressWarnings("unused")
	// private static final Logger logger =
	// LoggerFactory.getLogger(ShortTLVEncoder.class);
	private static final int DEFAULT_BYTE_SIZE = 2;

	public List<byte[]> encode(Object from, TLVEncodeContext ctx) {
		byte[] ret = null;

		int byteSize = getAnnotationByteSize(ctx);
		if (-1 == byteSize) {
			byteSize = DEFAULT_BYTE_SIZE;
		}

		if (from instanceof Short) {
			ret = ctx.getNumberCodec().short2Bytes(((Short) from).shortValue(),
					byteSize);
		} else if (from instanceof Integer) {
			ret = ctx.getNumberCodec().int2Bytes(((Integer) from).intValue(),
					byteSize);
		} else if (from instanceof Long) {
			ret = ctx.getNumberCodec().long2Bytes(((Long) from).longValue(),
					byteSize);
		} else {
			throw new RuntimeException("ShortTLVEncoder: wrong source type. ["
					+ from.getClass() + "]");
		}

		return Arrays.asList(ret);
	}

}
