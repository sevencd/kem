package cn.ilanhai.kem.domain.paymentservice.PayInfo;

import java.util.List;

import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * 查询付费配置返回参数
 * 
 * @author dgj
 *
 */
public class PayInfoResponseEntity extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 308676559681032244L;

//	private double price;// 单价
//	private Integer type;
//	private Integer unit;// 单位
//	/**
//	 * 1.5新增名字修改
//	 */
//	private String name;
	private List<payInfoServiceResponseEntity> packageService;// 套餐

//	public String getName() {
//		return name;
//	}
//
//	public void setName(String name) {
//		this.name = name;
//	}
//
//	public double getPrice() {
//		return price;
//	}
//
//	public void setPrice(double price) {
//		this.price = price;
//	}

	public List<payInfoServiceResponseEntity> getPackageService() {
		return packageService;
	}

	public void setPackageService(List<payInfoServiceResponseEntity> packageService) {
		this.packageService = packageService;
	}

//	/**
//	 * @return the type
//	 */
//	public Integer getType() {
//		return type;
//	}
//
//	/**
//	 * @param type
//	 *            the type to set
//	 */
//	public void setType(Integer type) {
//		this.type = type;
//	}
//
//	/**
//	 * @return the unit
//	 */
//	public Integer getUnit() {
//		return unit;
//	}
//
//	/**
//	 * @param unit
//	 *            the unit to set
//	 */
//	public void setUnit(Integer unit) {
//		this.unit = unit;
//	}

}
