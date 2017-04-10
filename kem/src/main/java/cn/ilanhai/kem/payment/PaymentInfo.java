package cn.ilanhai.kem.payment;

/**
 * 支付信息
 * 
 * @author he
 *
 */
public class PaymentInfo {
	/**
	 * 定单编号
	 */
	private String orderId;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 描述
	 */
	private String description;
	/**
	 * 金额
	 */
	private String totalFee;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
	}
}