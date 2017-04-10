package cn.ilanhai.kem.domain.smsright.dto;

import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * 加载请求实体
 * 
 * @author he
 *
 */
public class SmsSendDtoResponse extends AbstractEntity {
	public SmsSendDtoResponse() {

	}

	public String getSmsId() {
		return smsId;
	}

	public void setSmsId(String smsId) {
		this.smsId = smsId;
	}

	private String smsId;
}
