package cn.ilanhai.kem.domain.tag;

import java.util.List;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class OrderTagRequestEntity extends AbstractEntity {
	private static final long serialVersionUID = -8823612768224096974L;
	private List<OrderDataEntity> data;
	public List<OrderDataEntity> getData() {
		return data;
	}
	public void setData(List<OrderDataEntity> data) {
		this.data = data;
	}
}
