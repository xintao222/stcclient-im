/**
 * 
 */
package stc.foundation.codec.bean.tlv.encode;

/**
 * @author hp
 * 
 */
public interface TLVEncoderRepository {
	public TLVEncoder getEncoderOf(Class<?> cls);
}
