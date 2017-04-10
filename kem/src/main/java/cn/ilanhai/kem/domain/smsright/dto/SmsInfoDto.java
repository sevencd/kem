package cn.ilanhai.kem.domain.smsright.dto;

import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * 
 * @author he
 *
 */
public class SmsInfoDto extends AbstractEntity {
	public SmsInfoDto() {

	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 不可为空，指定操作的内容
	 */
	private String key; //

	/**
	 * 依据具体内容决定是否可为空
	 */
	private String content;//
}
