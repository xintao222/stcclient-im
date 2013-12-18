/**
 * 
 */
package stc.foundation.util;

import java.util.UUID;

/**
 * @author hp
 * 
 */
public interface MutableIdentifyable extends Identifyable {
	public void setIdentification(UUID id);
}
