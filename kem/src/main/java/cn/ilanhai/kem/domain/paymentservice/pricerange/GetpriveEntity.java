package cn.ilanhai.kem.domain.paymentservice.pricerange;

import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * 获取服务类型对应的价格
 * 
 * @author dgj
 *
 */
public class GetpriveEntity extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9048801800684103523L;
	private Integer packageServiceId;
	private Integer type;
	private Integer number;

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

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

}
