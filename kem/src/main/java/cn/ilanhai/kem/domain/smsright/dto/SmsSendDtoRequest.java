package cn.ilanhai.kem.domain.smsright.dto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.ilanhai.framework.uitl.ExpressionMatchUtil;
import cn.ilanhai.framework.uitl.Str;
import cn.ilanhai.kem.domain.smsright.SmsInfoEntity;

/**
 * 加载请求实体
 * 
 * @author he
 *
 */
public class SmsSendDtoRequest {
	public SmsSendDtoRequest() {
		this.operates = new ArrayList<SmsInfoDto>();
	}

	public List<SmsInfoDto> getOperates() {
		return operates;
	}

	public void setOperates(List<SmsInfoDto> operates) {
		this.operates = operates;
	}

	public boolean checkIsContainsOtherKey() {
		if (this.operates == null || this.operates.size() <= 0)
			return false;
		List<String> keys = Arrays.asList(SmsInfoEntity.KEYS);
		for (int i = 0; i < this.operates.size(); i++) {
			String key = this.operates.get(i).getKey();
			if (Str.isNullOrEmpty(key))
				return true;
			if (!keys.contains(key))
				return true;

		}
		return false;
	}

	public SmsInfoDto getOperate(String key) {
		if (Str.isNullOrEmpty(key))
			return null;
		if (this.operates == null || this.operates.size() <= 0)
			return null;
		for (int i = 0; i < this.operates.size(); i++) {
			String k = this.operates.get(i).getKey();
			if (Str.isNullOrEmpty(k))
				continue;
			if (k.equals(key))
				return this.operates.get(i);
		}
		return null;
	}

	public boolean checkContracts(String contracts) {
		if (Str.isNullOrEmpty(contracts))
			return false;
		String[] tmp = contracts.split(",");
		if (tmp == null || tmp.length <= 0)
			return false;
		return true;
	}

	private List<SmsInfoDto> operates;
}
