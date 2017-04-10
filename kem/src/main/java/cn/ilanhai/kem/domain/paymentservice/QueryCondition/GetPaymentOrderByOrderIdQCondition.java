package cn.ilanhai.kem.domain.paymentservice.QueryCondition;

import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * 根据ID查询订单
 * @author Nature
 *
 */
public class GetPaymentOrderByOrderIdQCondition extends AbstractEntity{

	private static final long serialVersionUID = -5291945749207169265L;
	
	//订单ID
	private String orderId;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	

}
