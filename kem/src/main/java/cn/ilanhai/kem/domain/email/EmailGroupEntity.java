package cn.ilanhai.kem.domain.email;

import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * 群组
 * 
 * @author hy
 *
 */
public class EmailGroupEntity extends AbstractEntity {

	private static final long serialVersionUID = -3819134273843866362L;
	private Integer emailGroupId;
	private String emailId;
	private String groupId;
	private String groupName;
	private int contactsCount;

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
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

	public Integer getEmailGroupId() {
		return emailGroupId;
	}
	
	public int getContactsCount() {
		return contactsCount;
	}

	public void setContactsCount(int contactsCount) {
		this.contactsCount = contactsCount;
	}

	public void setEmailGroupId(Integer emailGroupId) {
		this.emailGroupId = emailGroupId;
	}
}
