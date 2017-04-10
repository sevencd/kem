package cn.ilanhai.kem.domain.smsright.dto;

/**
 * 加载请求实体
 * 
 * @author he
 *
 */
public class SmsLoadDtoRequest {
	public SmsLoadDtoRequest() {

	}

	public String getSmsId() {
		return smsId;
	}

	public void setSmsId(String smsId) {
		this.smsId = smsId;
	}

	private String smsId;
}
