package cn.ilanhai.kem.domain.smsright.dto;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class QueryOneSmsInfoEntityDto extends AbstractEntity {
	public QueryOneSmsInfoEntityDto() {

	}

	public String getSmsId() {
		return smsId;
	}

	public void setSmsId(String smsId) {
		this.smsId = smsId;
	}

	public String getInfoKey() {
		return infoKey;
	}

	public void setInfoKey(String infoKey) {
		this.infoKey = infoKey;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String smsId;
	private String infoKey;

}
