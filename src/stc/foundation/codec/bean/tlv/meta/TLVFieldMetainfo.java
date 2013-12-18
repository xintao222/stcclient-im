/**
 * 
 */
package stc.foundation.codec.bean.tlv.meta;

import java.lang.reflect.Field;

/**
 * @author hp
 * 
 */
public interface TLVFieldMetainfo {
	public Field get(int tag);
}
