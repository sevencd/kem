package cn.ilanhai.kem.payment.protocol.weixin_scancodepay;

import java.security.MessageDigest;
import java.util.Random;

//微信统一订单结构   详见:https://pay.weixin.qq.com/wiki/doc/api/app.php?chapter=9_1
public abstract class AbstractUnifiedOrder {
	// public static String appid = "";
	// 公众账号ID appid 是 String(32) wxd678efh567hg6787
	// 微信分配的公众账号ID（企业号corpid即为此appId）
	private String appId = "";
	// 商户号 mch_id 是 String(32) 1230000109 微信支付分配的商户号
	private String mchId = "";
	// 设备号 device_info 否 String(32) 013467007045764
	// 终端设备号(门店号或收银设备ID)，注意：PC网页或公众号内支付请传"WEB"
	private String deviceInfo = "";
	// 随机字符串 nonce_str 是 String(32) 5K8264ILTKCH16CQ2502SI8ZNMTM67VS
	// 随机字符串，不长于32位。推荐随机数生成算法
	private String nonceStr = "";
	// 签名 sign 是 String(32) C380BEC2BFD727A4B6845133519F3AD6 签名，详见签名生成算法
	private String sign = "";

	public AbstractUnifiedOrder() {

	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
		// AbstractUnifiedOrder.appid = appId;
	}

	public String getMchId() {
		return mchId;
	}

	public void setMchId(String mchId) {
		this.mchId = mchId;
	}

	public String getDeviceInfo() {
		return deviceInfo;
	}

	public void setDeviceInfo(String deviceInfo) {
		this.deviceInfo = deviceInfo;
	}

	public String getNonceStr() {
		return AbstractUnifiedOrder.getRandom();
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public abstract String toXmlString();

	public enum TradeType {
		JSAPI, NATIVE, APP
	}

	public final static String getRandom() {
		Random random = null;
		random = new Random();
		return getMessageDigest(String.valueOf(random.nextInt(10000))
				.getBytes());
	}

	public final static long genTimeStamp() {
		return System.currentTimeMillis() / 1000;
	}

	public final static String getMessageDigest(byte[] buffer) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(buffer);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			return null;
		}
	}
}
