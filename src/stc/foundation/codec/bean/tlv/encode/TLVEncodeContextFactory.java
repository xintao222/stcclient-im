/**
 * 
 */
package stc.foundation.codec.bean.tlv.encode;

import java.lang.reflect.Field;

/**
 * @author hp
 * 
 */
public interface TLVEncodeContextFactory {
	public TLVEncodeContext createEncodeContext(Class<?> type, Field field);
}
