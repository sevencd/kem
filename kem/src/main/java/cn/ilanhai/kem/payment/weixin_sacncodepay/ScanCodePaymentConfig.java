package cn.ilanhai.kem.payment.weixin_sacncodepay;

import cn.ilanhai.kem.payment.PaymentConfig;

/**
 * @author he
 *
 */
public class ScanCodePaymentConfig extends PaymentConfig {
	/**
	 * app key
	 */
	private String appKey;
	/**
	 * app id
	 */
	private String appId;
	/**
	 * mch id
	 */
	private String appMchId;
	/**
	 * 回调
	 */
	private String notifyUrl;

	/**
	 * key
	 */
	private String key;

	public ScanCodePaymentConfig() {

	}

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppMchId() {
		return appMchId;
	}

	public void setAppMchId(String appMchId) {
		this.appMchId = appMchId;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
}
