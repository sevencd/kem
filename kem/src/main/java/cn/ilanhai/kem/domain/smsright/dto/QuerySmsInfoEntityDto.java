package cn.ilanhai.kem.domain.smsright.dto;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class QuerySmsInfoEntityDto extends AbstractEntity {
	public QuerySmsInfoEntityDto() {

	}
	
	public String getSmsId() {
		return smsId;
	}
	public void setSmsId(String smsId) {
		this.smsId = smsId;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String smsId;

}
