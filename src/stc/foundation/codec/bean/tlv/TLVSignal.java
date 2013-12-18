/**
 * 
 */
package stc.foundation.codec.bean.tlv;

import stc.foundation.util.MutableIdentifyable;

/**
 * @author hp
 * 
 */
public interface TLVSignal extends MutableIdentifyable {

	public void setSourceId(short id);

	public short getSourceId();
}
