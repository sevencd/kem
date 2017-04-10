package cn.ilanhai.kem.domain.plugin.activeplugin.dto;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class QueryDrawPrizeRecordByRecordIdDto extends AbstractEntity{

	private static final long serialVersionUID = 4767674480758763683L;

	private Integer recordId;

	public Integer getRecordId() {
		return recordId;
	}

	public void setRecordId(Integer recordId) {
		this.recordId = recordId;
	}
	
	
}
