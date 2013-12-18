/**
 * 
 */
package stc.foundation.util;

/**
 * @author hp
 * 
 */
public interface Transformer<FROM, TO> {
	public TO transform(FROM from);
}
