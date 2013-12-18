/**
 * 
 */
package stc.foundation.codec.bean.tlv.encode;

import java.lang.reflect.Field;

import stc.foundation.codec.bean.tlv.meta.TLVFieldMetainfo;
import stc.foundation.codec.bean.util.meta.Int2TypeMetainfo;
import stc.foundation.util.NumberCodec;


/**
 * @author hp
 * 
 */
public interface TLVEncodeContext {
	public Class<?> getValueType();

	public Field getValueField();

	public Int2TypeMetainfo getTypeMetainfo();

	public TLVFieldMetainfo getFieldMetainfo();

	public NumberCodec getNumberCodec();

	public TLVEncoderRepository getEncoderRepository();
}
