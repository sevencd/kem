package cn.ilanhai.kem.domain.paymentservice.pricerange;

import java.util.Date;

import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * 价格区间实体
 * 
 * @author dgj
 *
 */
public class PriceRangeEntity extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4728794207580091077L;

	private Integer packageServiceId;
	// 区间价，以后用，现在设置为最大值，或者null
	private Integer max;
	private double price;
	private Date createTime;
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getMax() {
		return max;
	}

	public void setMax(Integer max) {
		this.max = max;
	}

	public Integer getPackageServiceId() {
		return packageServiceId;
	}

	public void setPackageServiceId(Integer packageServiceId) {
		this.packageServiceId = packageServiceId;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

}
