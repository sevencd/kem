package cn.ilanhai.kem.domain.paymentservice.dto;

import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * 下单返回值
 * @author Nature
 *
 */
public class PlaceOrderResponseDto extends AbstractEntity{

	private static final long serialVersionUID = -3121623681125922847L;

	//订单ID
	private String orderId;
	//应付金额
	private String totalFee;
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getTotalFee() {
		return totalFee;
	}
	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
	}
	
	
}
