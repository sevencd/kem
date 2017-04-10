package cn.ilanhai.framework.testNg;

public class StringUtil {
	public static String removeSpaces(String str) {
		str = putOffCurrentTime(str);
		str = putOffSessionId(str);
		return str.replaceAll("[\\s]+", "");
	}

	private static String putOffCurrentTime(String value) {
		String s = "\"currentTimeMillis\": 1471365253459,";
		int a = value.indexOf("\"currentTimeMillis\"");
		if (a >= 0) {
			String b = value.substring(a, a + s.length());
			value = value.replaceAll(b, "");
		}
		return value;

	}

	private static String putOffSessionId(String value) {
		String s = "\"sessionId\":\"49014dfe-98e8-40f9-8ab7-801ab160788e\"";
		int a = value.indexOf("\"sessionId\"");
		if (a >= 0) {
			String b = value.substring(a, a + s.length());
			value = value.replaceAll(b, "");
		}
		return value;

	}

}
