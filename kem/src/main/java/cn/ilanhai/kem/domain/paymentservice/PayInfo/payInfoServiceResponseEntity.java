package cn.ilanhai.kem.domain.paymentservice.PayInfo;

import java.util.Date;
import java.util.List;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class payInfoServiceResponseEntity extends AbstractEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1339318432447261273L;

	private Integer id;// 套餐编号
	private String name;// 套餐名称
	private Date addTime;
	private Date updateTime;
	private Integer type;// 类型 0 会员套餐 1 版权套餐
	private double price;// 价格
	private String description;// 描述
	private Integer timeMode;// 服务支付方式，会员永远是0，1表示按月，2表示按年
	private List<payInfoServiceInfoResponseEitity> info;// 套餐信息

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public List<payInfoServiceInfoResponseEitity> getInfo() {
		return info;
	}

	public void setInfo(List<payInfoServiceInfoResponseEitity> info) {
		this.info = info;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
