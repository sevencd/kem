package cn.ilanhai.kem.domain.payment;

/**
 * 支付信息dto
 * 
 * @author he
 *
 */
public class PaymentInfoDto {
	/**
	 * 定单编号
	 */
	private String orderId;
	/**
	 * 支付方式
	 */
	private int paymentWay;

	/**
	 * 前端返回url
	 */
	private String returnUrl;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public int getPaymentWay() {
		return paymentWay;
	}

	public void setPaymentWay(int paymentWay) {
		this.paymentWay = paymentWay;
	}

	public String getReturnUrl() {
		return returnUrl;
	}

	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}

}
