package cn.ilanhai.kem.domain.contacts.dto;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class SearchContactsResponseDto extends AbstractEntity {
	private static final long serialVersionUID = -2797040197455336937L;
	private String contactId;
	private String value;
	private String name;

	public String getContactId() {
		return contactId;
	}

	public void setContactId(String contactId) {
		this.contactId = contactId;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
