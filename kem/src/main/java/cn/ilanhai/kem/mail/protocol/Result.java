package cn.ilanhai.kem.mail.protocol;

import cn.ilanhai.framework.uitl.FastJson;
import cn.ilanhai.framework.uitl.Str;

/**
 * 反回值
 * 
 * @author he
 *
 */
public abstract class Result {

	public static <T> T jsonToObject(String jsonString, Class<T> c) {
		if (Str.isNullOrEmpty(jsonString))
			return null;
		if (c == null)
			return null;
		return FastJson.json2Bean(jsonString, c);
	}
}
