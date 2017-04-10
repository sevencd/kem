package cn.ilanhai.kem.domain.paymentservice;

import java.util.Date;

import cn.ilanhai.framework.app.domain.AbstractEntity;
import cn.ilanhai.kem.domain.enums.ServiceUnit;

/**
 * 订单详情实体
 * 
 * @author Nature
 *
 */
public class PaymentOrderInfoEntity extends AbstractEntity {

	private static final long serialVersionUID = -8982521227524310937L;

	// ID
	private int id;
	// 订单ID
	private String orderId;
	// 创建时间
	private Date createTime;
	// 修改时间
	private Date updateTime;
	// 数量
	private int quantity;
	// 单位
	private int unit;
	// 服务ID
	private int serviceId;
	// 服务套餐ID
	private Integer packageServiceId;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public int getUnit() {
		return unit;
	}
	public void setUnit(int unit) {
		this.unit = unit;
	}
	public int getServiceId() {
		return serviceId;
	}
	public void setServiceId(int serviceId) {
		this.serviceId = serviceId;
	}
	public Integer getPackageServiceId() {
		return packageServiceId;
	}
	public void setPackageServiceId(Integer packageServiceId) {
		this.packageServiceId = packageServiceId;
	}

}
