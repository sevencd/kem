package cn.ilanhai.kem.domain.plugin.activeplugin.dto;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class ExchangePrizeRequestDto extends AbstractEntity{

	private static final long serialVersionUID = -8173127491836132034L;

	private Integer recordId;

	public Integer getRecordId() {
		return recordId;
	}

	public void setRecordId(Integer recordId) {
		this.recordId = recordId;
	}
	
}
