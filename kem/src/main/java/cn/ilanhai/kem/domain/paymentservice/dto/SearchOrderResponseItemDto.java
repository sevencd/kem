package cn.ilanhai.kem.domain.paymentservice.dto;

import java.util.Date;

import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * 查询用户订单信息条目
 * @author Nature
 *
 */
public class SearchOrderResponseItemDto extends AbstractEntity{

	private static final long serialVersionUID = -2034947411037896014L;

	//交易号
	private String orderId;
	//用户ID
	private String userId;
	//消费内容
	private String orderContent;
	//下单时间
	private Date createTime;
	//应付金额，两位小数
	private Double amountPayable;
	//实付金额，两位小数
	private Double payAmount;
	//支付方式：0 支付宝 1 微信 2 未知
	private Integer payway;
	//支付状态：0 等待支付 1 已支付
	private Integer payStatus;
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getOrderContent() {
		return orderContent;
	}
	public void setOrderContent(String orderContent) {
		this.orderContent = orderContent;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Double getAmountPayable() {
		return amountPayable;
	}
	public void setAmountPayable(Double amountPayable) {
		this.amountPayable = amountPayable;
	}
	public Double getPayAmount() {
		return payAmount;
	}
	public void setPayAmount(Double payAmount) {
		this.payAmount = payAmount;
	}
	public Integer getPayway() {
		return payway;
	}
	public void setPayway(Integer payway) {
		this.payway = payway;
	}
	public Integer getPayStatus() {
		return payStatus;
	}
	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}
	
}
