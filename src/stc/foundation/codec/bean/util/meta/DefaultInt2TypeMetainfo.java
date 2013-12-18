/**
 * 
 */
package stc.foundation.codec.bean.util.meta;

import java.util.HashMap;
import java.util.Map;

/**
 * @author hp
 * 
 */
public class DefaultInt2TypeMetainfo implements Int2TypeMetainfo {

	private Map<Integer, Class<?>> codes = new HashMap<Integer, Class<?>>();

	public void add(int tag, Class<?> type) {
		codes.put(tag, type);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.skymobi.bean.tlv.TagRepository#find(int)
	 */
	public Class<?> find(int value) {
		return codes.get(value);
	}

	public Map<Integer, String> getAllMetainfo() {
		Map<Integer, String> ret = new HashMap<Integer, String>();
		for (Map.Entry<Integer, Class<?>> entry : this.codes.entrySet()) {
			ret.put(entry.getKey(), entry.getValue().toString());
		}

		return ret;
	}

}
