package cn.ilanhai.kem.domain.paymentservice.PayInfo;

import java.util.List;
import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * 保存付费配置入参
 * 
 * @author dgj
 *
 */
public class PayInfoRequestEntity extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7861532060159744719L;
	/**
	 * 1.5修改
	 */
//	private String name;
//	private double price;// 单价
//	private Integer type;// 套餐类型
	private List<PayInfoServiceEntity> packageService;// 套餐

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

	public List<PayInfoServiceEntity> getPackageService() {
		return packageService;
	}

	public void setPackageService(List<PayInfoServiceEntity> packageService) {
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

}
