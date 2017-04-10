package cn.ilanhai.kem.domain.smsright.dto;

import java.util.ArrayList;
import java.util.List;

import cn.ilanhai.framework.app.domain.AbstractEntity;
import cn.ilanhai.kem.domain.email.MailInfoItemDto;

/**
 * 加载请求实体
 * 
 * @author he
 *
 */
public class SmsLoadDtoResponse extends AbstractEntity {
	public SmsLoadDtoResponse() {
		info = new ArrayList<SmsInfoDto>();
	}

	public List<SmsInfoDto> getInfo() {
		return info;
	}

	public void setInfo(List<SmsInfoDto> info) {
		this.info = info;
	}

	private List<SmsInfoDto> info;
}
