package cn.ilanhai.kem.domain.tag;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class OrderDataEntity extends AbstractEntity {
	private static final long serialVersionUID = 7524940677880681698L;
	private Integer tageId;
	private Integer orderNum;

	public Integer getTageId() {
		return tageId;
	}

	public void setTageId(Integer tageId) {
		this.tageId = tageId;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

}
