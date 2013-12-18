package stc.foundation.codec.bean.tlv.encode.encoders;

import java.lang.reflect.Field;

import stc.foundation.codec.bean.tlv.annotation.TLVAttribute;
import stc.foundation.codec.bean.tlv.encode.TLVEncodeContext;


public class AbstractNumberTLVEncoder {
	static protected int getAnnotationByteSize(TLVEncodeContext ctx) {
		Field field = ctx.getValueField();
		if (null != field) {
			TLVAttribute attr = field.getAnnotation(TLVAttribute.class);
			if (null != attr) {
				return attr.bytes();
			}
		}

		return -1;
	}
}
