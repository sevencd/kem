package cn.ilanhai.kem.domain.paymentservice.PayInfo;

import java.util.Date;

import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * 套餐详情返回
 * 
 * @author dgj
 *
 */
public class payInfoServiceInfoResponseEitity extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4345142092889773755L;

	private Integer id;// 编号
	private Date addTime;
	private Date updateTime;
	private Integer type;// 0 版权 1 会员(域名与外连)',
	private Integer quantity;// 数量
	private Integer unit;// 单位 0 月数 1次数
	private Integer packageServiceId;// 对应套餐id
	private Integer serviceId;// 对应的服务id
	private Integer timeMode;// 服务支付方式，会员永远是0，1表示按月，2表示按年

	public Integer getTimeMode() {
		return timeMode;
	}

	public void setTimeMode(Integer timeMode) {
		this.timeMode = timeMode;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Integer getUnit() {
		return unit;
	}

	public void setUnit(Integer unit) {
		this.unit = unit;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Integer getPackageServiceId() {
		return packageServiceId;
	}

	public void setPackageServiceId(Integer packageServiceId) {
		this.packageServiceId = packageServiceId;
	}

	public Integer getServiceId() {
		return serviceId;
	}

	public void setServiceId(Integer serviceId) {
		this.serviceId = serviceId;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}
