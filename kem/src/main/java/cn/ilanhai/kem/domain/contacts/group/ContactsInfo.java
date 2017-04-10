package cn.ilanhai.kem.domain.contacts.group;

import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * 联系人信息
 * 
 * @author dgj
 *
 */
public class ContactsInfo extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4743452037656115911L;

	private String contactId;
	private String name;
	private String value;

	public String getContactId() {
		return contactId;
	}

	public void setContactId(String contactId) {
		this.contactId = contactId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
