package cn.ilanhai.kem.domain.paymentservice.pricerange;

import java.util.List;

import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * 保存价格区间请求
 * 
 * @author dgj
 *
 */
public class PriveRangeRequest extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8379290467449839133L;

	private List<PriceRangeEntity> list;
	private Integer type;

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public List<PriceRangeEntity> getList() {
		return list;
	}

	public void setList(List<PriceRangeEntity> list) {
		this.list = list;
	}

}
