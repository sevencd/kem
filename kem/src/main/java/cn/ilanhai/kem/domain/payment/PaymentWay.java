package cn.ilanhai.kem.domain.payment;

/**
 * 支付方式
 * 
 * @author he
 *
 */
public enum PaymentWay {
	Alipay, WeiXin;
	public static PaymentWay valueOf(int v) {
		switch (v) {
		case 0:
			return PaymentWay.Alipay;
		case 1:
			return PaymentWay.WeiXin;
		default:
			return PaymentWay.Alipay;
		}
	}
}
