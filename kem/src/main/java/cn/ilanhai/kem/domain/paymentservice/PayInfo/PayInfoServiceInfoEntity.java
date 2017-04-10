package cn.ilanhai.kem.domain.paymentservice.PayInfo;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class PayInfoServiceInfoEntity extends AbstractEntity {

	private static final long serialVersionUID = 30207452762299735L;

	public static int getServiceIdByType(int type) {
		return type + 1;
	}

	public static int getTypeByServiceId(int serviceId) {
		return serviceId - 1;
	}

	public static final int UNRIGHTS = 1;
	public static final int MEMBER = 0;
	public static final int EMAIL = 2;
	public static final int SMS = 3;
	public static final int CUSTOMERCLUE = 4;// 客户线索
	public static final int B2B = 5;// 打码
	public static final int SUBACCOUNT = 6;// 子账号
	public static final int PUBLISHNUM = 7;// 发布次数
	public static final int BASEVER = 8; // 基础版本
	public static final int STANDARDVER = 9;// 标准版
	public static final int HIGHVER = 10;// 高级版
	
	private Integer id;// 编号
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
}
