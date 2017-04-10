package cn.ilanhai.kem.domain.contacts.dto;

import cn.ilanhai.framework.app.domain.AbstractEntity;
import cn.ilanhai.kem.domain.enums.ContactType;

public class ContactMsgDto extends AbstractEntity {
	private static final long serialVersionUID = 3545463047037551978L;
	private String userId;
	private String name;
	private String context;
	private ContactType contactType;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public ContactType getContactType() {
		return contactType;
	}

	public void setContactType(ContactType contactType) {
		this.contactType = contactType;
	}
}
