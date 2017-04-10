package cn.ilanhai.kem.domain.contacts.dto;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class ContactsKeyValue extends AbstractEntity {
	private static final long serialVersionUID = 2986453285569290817L;
	private Integer key;
	private String content;

	public Integer getKey() {
		return key;
	}

	public void setKey(Integer key) {
		this.key = key;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
