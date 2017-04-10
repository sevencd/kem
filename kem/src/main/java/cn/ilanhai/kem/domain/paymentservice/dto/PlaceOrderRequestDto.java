package cn.ilanhai.kem.domain.paymentservice.dto;

import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * 创建订单请求
 * @author Nature
 *
 */
public class PlaceOrderRequestDto extends AbstractEntity{

	private static final long serialVersionUID = 7701310752019696970L;

	//套餐ID
	private Integer packageServiceId;
	//订单类型,0 线下订单 1线上订单
	private Integer type;
	//实际支付金额
	private Double payAmount;
	//用户ID
	private String userId;
	//支付方式
	private Integer payWay;
	//数量
	private Integer amount;
	//服务类型，0 会员；1 去版权
	private Integer serviceType;
	
	public Integer getPackageServiceId() {
		return packageServiceId;
	}
	public void setPackageServiceId(Integer packageServiceId) {
		this.packageServiceId = packageServiceId;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Double getPayAmount() {
		return payAmount;
	}
	public void setPayAmount(Double payAmount) {
		this.payAmount = payAmount;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Integer getPayWay() {
		return payWay;
	}
	public void setPayWay(Integer payWay) {
		this.payWay = payWay;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public Integer getServiceType() {
		return serviceType;
	}
	public void setServiceType(Integer serviceType) {
		this.serviceType = serviceType;
	}
	
}
