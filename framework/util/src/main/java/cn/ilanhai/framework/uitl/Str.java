package cn.ilanhai.framework.uitl;

/**
 * 字符串助手
 * 
 * @author he
 *
 */
public final class Str {

	/**
	 * 字符中是否为null或empty
	 * 
	 * @param str
	 * @return
	 */
	public static final boolean isNullOrEmpty(String str) {
		return str == null || "".equals(str);
	}
}
