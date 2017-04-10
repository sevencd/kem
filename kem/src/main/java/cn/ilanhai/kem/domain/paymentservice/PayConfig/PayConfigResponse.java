package cn.ilanhai.kem.domain.paymentservice.PayConfig;

import java.util.List;
import cn.ilanhai.framework.app.domain.AbstractEntity;
import cn.ilanhai.framework.app.domain.Entity;

/**
 * 支付配置查询返回
 * @author dgj
 *
 */
public class PayConfigResponse extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3939919219444329033L;
	private List<Entity> info;
	/**
	 * @return the info
	 */
	public List<Entity> getInfo() {
		return info;
	}
	/**
	 * @param info the info to set
	 */
	public void setInfo(List<Entity> info) {
		this.info = info;
	}


}
