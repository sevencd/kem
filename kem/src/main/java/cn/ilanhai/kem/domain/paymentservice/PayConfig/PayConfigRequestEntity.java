package cn.ilanhai.kem.domain.paymentservice.PayConfig;

import java.util.List;

import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * 保存支付配置请求实体
 * @author dgj
 *
 */
public class PayConfigRequestEntity extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6158685745855752827L;

	private List<PayConfigDetailEntity> info;
	
	public List<PayConfigDetailEntity> getInfo() {
		return info;
	}
	public void setInfo(List<PayConfigDetailEntity> info) {
		this.info = info;
	}
}
