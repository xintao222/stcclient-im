/**
 * 
 */
package stc.foundation.util;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * @author isdom
 * 
 */
public class StringUtils {
	static public List<String> line2list(String line) {
		List<String> ret = new ArrayList<String>();
		StringTokenizer st = new StringTokenizer(line, ",", false);
		while (st.hasMoreTokens()) {
			ret.add(st.nextToken().trim());
		}

		return ret;
	}

	static public String[] line2array(String line) {
		return line2list(line).toArray(new String[0]);
	}

	public static boolean isBlank(String str) {
		if (str == null || str.trim().length() == 0)
			return true;
		return false;
	}

	/**
	 * 通过字符串获取字符串数组
	 * 
	 * @param content
	 * @param prefix
	 * @return
	 */
	public static String[] getData(String content, String prefix) {
		if (content == null || content.isEmpty()
				|| content.indexOf(prefix) == 0) {
			return null;
		}
		return content.split(prefix);
	}

	/**
	 * 获取字符串中icon或�?是bgcolor的�?
	 * 
	 * @param txt
	 * @param prefix
	 * @param indexString
	 * @return
	 */
	public static String getIcon(String txt, String prefix, String indexString) {
		String url = "";
		// icon=http://cdn.joloplay.cn/image/pic/icon/warn.png;text=腾讯手机管家扫描:未�?�?
		if (txt != null && !txt.isEmpty() && txt.contains(prefix)) {
			String[] safes = txt.split(prefix);
			url = safes[0];
			if (url != null && !url.isEmpty() && url.contains(indexString)) {
				url = url.substring(5, url.length());
			} else {
				url = "";
			}
		}
		return url;
	}
	/**
	 * 获取字符串中的text�?
	 * 
	 * @param txt
	 * @param prefix
	 * @param indexString
	 * @return
	 */
	public static String getText(String txt, String prefix) {
		String safe = "";
		// icon=http://cdn.joloplay.cn/image/pic/icon/warn.png;text=腾讯手机管家扫描:未�?�?
		if (txt != null && !txt.isEmpty() && txt.contains(prefix)) {
			String[] safes = txt.split(prefix);
			safe = safes[1];
			if (safe != null && !safe.isEmpty() && safe.contains("text=")) {
				safe = safe.substring(5, safe.length());
			} else {
				safe = "";
			}
		}
		return safe;
	}

}
