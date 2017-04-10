package cn.ilanhai.kem.payment.alipay_directpay;

import cn.ilanhai.kem.payment.PaymentConfig;

/**
 * @author he
 *
 */
public class DirectPayPaymentConfig extends PaymentConfig {

	/**
	 * 合作身份者ID，签约账号，以2088开头由16位纯数字组成的字符串，查看地址：https://b.alipay.com/order/
	 * pidAndKey.htm
	 */
	private String partner;
	/**
	 * 收款支付宝账号，以2088开头由16位纯数字组成的字符串，一般情况下收款账号就是签约账号
	 */
	private String sellerId;
	/**
	 * 商户的私钥,需要PKCS8格式，RSA公私钥生成：https://doc.open.alipay.com/doc2/detail.htm?spm=
	 * a219a.7629140.0.0.nBDxfy&treeId=58&articleId=103242&docType=1
	 */
	private String privateKey;
	/**
	 * 支付宝的公钥,查看地址：https://b.alipay.com/order/pidAndKey.htm
	 */
	private String publicKey;
	/**
	 * 服务器异步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	 */
	private String notifyUrl;

	private String returnUrl;

	public String getPartner() {
		return partner;
	}

	public void setPartner(String partner) {
		this.partner = partner;
	}

	public String getSellerId() {
		return sellerId;
	}

	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}

	public String getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}

	public String getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public String getReturnUrl() {
		return returnUrl;
	}

	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}

}
