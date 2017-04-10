package cn.ilanhai.kem.util;

import java.util.List;
import java.util.regex.Pattern;

import cn.ilanhai.framework.common.exception.BlAppException;
import cn.ilanhai.framework.uitl.Str;
import cn.ilanhai.kem.bl.BLContextUtil;
import cn.ilanhai.kem.common.CodeTable;

/**
 * 字符串校验
 * 
 * @author hy
 *
 */
public final class StringVerifyUtil {
	/**
	 * 校验后台用户登录用户名合法性
	 * 
	 * @param backUserName
	 * @return
	 * @throws BlAppException
	 */
	public static void backUserNameVerify(String backUserName) throws BlAppException {
		// 中文[\u4e00-\u9fa5]
		if (Str.isNullOrEmpty(backUserName)) {
			throw new BlAppException(CodeTable.BL_BACKTUSER_LOGINNAME_ERROR.getValue(),
					CodeTable.BL_BACKTUSER_LOGINNAME_ERROR.getDesc());
		} else {
			if (backUserName.length() > 20) {
				throw new BlAppException(CodeTable.BL_BACKTUSER_LOGINNAME_ERROR.getValue(),
						CodeTable.BL_BACKTUSER_LOGINNAME_ERROR.getDesc());
			}
		}
	}

	/**
	 * 校验后台用户登录密码的合法性
	 * 
	 * @param backUserPwd
	 * @return
	 * @throws BlAppException
	 */
	public static void pwdVerify(String backUserPwd) throws BlAppException {
		// (?!^[_#@]+$)
		if (Str.isNullOrEmpty(backUserPwd)) {
			throw new BlAppException(CodeTable.BL_BACKTUSER_LOGINPWD_ERROR.getValue(),
					CodeTable.BL_BACKTUSER_LOGINPWD_ERROR.getDesc());
		}
		String regEx = "^[A-Za-z0-9]{6,20}$";
		if (!Pattern.compile(regEx).matcher(backUserPwd).find()) {
			throw new BlAppException(CodeTable.BL_BACKTUSER_LOGINPWD_ERROR.getValue(),
					CodeTable.BL_BACKTUSER_LOGINPWD_ERROR.getDesc());
		}
	}

	/**
	 * 校验模版名合法性
	 * 
	 * @param backUserName
	 * @return
	 * @throws BlAppException
	 */
	public static void templateNameVerify(String templateName) throws BlAppException {
		if (Str.isNullOrEmpty(templateName)) {
			throw new BlAppException(CodeTable.BL_TEMPLATE_NAME_ERROR.getValue(),
					CodeTable.BL_TEMPLATE_NAME_ERROR.getDesc());
		} else {
			if (templateName.length() > 20) {
				throw new BlAppException(CodeTable.BL_TEMPLATE_NAME_ERROR.getValue(),
						CodeTable.BL_TEMPLATE_NAME_ERROR.getDesc());
			}
		}
	}

	public static void tagNameVerify(String tagName) throws BlAppException {
		String regEx = "^[A-Za-z0-9_\u4e00-\u9fa5]{1,5}$";
		if (!Pattern.compile(regEx).matcher(tagName).find()) {
			String tmp = String.format(CodeTable.BL_TAG_ADDNAME_ERROR.getDesc(), tagName);
			throw new BlAppException(CodeTable.BL_TAG_ADDNAME_ERROR.getValue(), tmp);
		}
	}

	public static String unHttpAndPost(String str) {
		String http = "http://";
		if (str.contains(http)) {
			str = str.substring(7, str.length());
		}
		if (str.contains("/_1")) {
			str = str.substring(0, str.indexOf("/_1"));
		}
		if (str.contains(":")) {
			str = str.substring(0, str.length() - 5);
		}
		return str;
	}

	/**
	 * 将数组转换为字符串
	 * 
	 * @param str
	 * @return
	 */
	public static String arrayToString(List<String> str) {
		if (str == null) {
			return "";
		}
		return str.toString().replace("[", "").replace("]", "");
	}
}
