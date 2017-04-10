package cn.ilanhai.framework.uitl;

import java.util.regex.Pattern;

public final class ExpressionMatchUtil {
	private final static String EMAIL_REGULAR = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
	private final static String PHONE_REGULAR = "^((13[0-9])|(14[57])|(15[^4,\\D])|(18[0-9])|(17[0-9]))\\d{8}$";
	private final static String QQ_REGULAR = "^\\d{3,15}$";
	private final static String NAME_REGULAR = "^[a-zA-Z0-9_\u4e00-\u9fa5]{1,20}$";

	/**
	 * 判断是否是手机号
	 * 
	 * @param phoneNo
	 * @return
	 */
	public static boolean isPhoneNo(String phoneNo) {
		if (phoneNo == null)
			return false;
		return Pattern.matches(PHONE_REGULAR, phoneNo);
	}

	public static boolean isEmailAddress(String emailAddress) {
		if (Str.isNullOrEmpty(emailAddress))
			return true;
		return Pattern.matches(EMAIL_REGULAR, emailAddress);
	}

	public static boolean isQQ(String QQ) {
		if (Str.isNullOrEmpty(QQ))
			return true;
		return Pattern.matches(QQ_REGULAR, QQ);
	}

	public static boolean isName(String name) {
		if (name == null)
			return false;
		return Pattern.matches(NAME_REGULAR, name);
	}

//	public static void main(String[] args) {
//		System.out.println(isName("14788888888"));
//		System.out.println(isName("147888888"));
//		System.out.println(isName("慌"));
//		System.out.println(isName("1"));
//		System.out.println(isName("11111111111111111111111111111111"));
//	}
}
