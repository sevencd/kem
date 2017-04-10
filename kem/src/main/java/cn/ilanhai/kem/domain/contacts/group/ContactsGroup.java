package cn.ilanhai.kem.domain.contacts.group;

import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * 查询群组
 * 
 * @author dgj
 *
 */
public class ContactsGroup extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6244415245863049413L;

	private String groupId;
	private String groupName;
	private String contactsCount;

	public String getContactsCount() {
		return contactsCount;
	}

	public void setContactsCount(String contactsCount) {
		this.contactsCount = contactsCount;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

}
