package cn.ilanhai.kem.domain.paymentservice.PayConfig;

import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * 加载支付配置的请求实体
 * @author dgj
 *
 */
public class PayConfigloadRequestEntity extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8550971768265486250L;
	private Integer type;//支付类型 0支付宝，1微信
	/**
	 * @return the type
	 */
	public Integer getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(Integer type) {
		this.type = type;
	}
	

}
