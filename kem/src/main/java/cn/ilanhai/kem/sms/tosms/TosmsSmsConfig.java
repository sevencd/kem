package cn.ilanhai.kem.sms.tosms;

import java.util.Date;

import cn.ilanhai.kem.sms.SmsConfig;

/**
 * 传信网配置参数
 * 
 * @author he
 *
 */
public class TosmsSmsConfig extends SmsConfig {
	public TosmsSmsConfig() {

	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * 用户名* 登录用户名
	 */
	private String userName;
	/**
	 * 密码* 需要MD5加密(32位字符串)
	 */
	private String password;

}
