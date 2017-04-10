package cn.ilanhai.kem.domain.paymentservice.pricerange;

import java.util.List;

import cn.ilanhai.framework.app.domain.AbstractEntity;
import cn.ilanhai.framework.app.domain.Entity;

/**
 * 加载付费配置
 * 
 * @author dgj
 *
 */
public class PriceRangeResponse extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6839567431367533743L;
	private List<Entity> list;
	private Integer type;

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public List<Entity> getList() {
		return list;
	}

	public void setList(List<Entity> list) {
		this.list = list;
	}

}
