package cn.ilanhai.kem.domain.email;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.ilanhai.framework.uitl.Str;

/**
 * 邮件保存请求
 * 
 * @author he
 *
 */
public class MailSaveRequest {

	public MailSaveRequest() {
		this.operates = new ArrayList<MailInfoItemDto>();
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public List<MailInfoItemDto> getOperates() {
		return operates;
	}

	public void setOperates(List<MailInfoItemDto> operates) {
		this.operates = operates;
	}

	public boolean checkIsContainsOtherKey() {
		if (this.operates == null || this.operates.size() <= 0)
			return false;
		List<String> keys = Arrays.asList(MailInfoEntity.KEYS);
		for (int i = 0; i < this.operates.size(); i++) {
			String key = this.operates.get(i).getKey();
			if (Str.isNullOrEmpty(key))
				return true;
			if (!keys.contains(key))
				return true;

		}
		return false;
	}

	public MailInfoItemDto getOperate(String key) {
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

	/**
	 * 邮件ID，可为空，为空时为新建
	 */
	private String emailId;

	/**
	 * 邮件ID，可为空，为空时为新建
	 */
	private List<MailInfoItemDto> operates;
}
