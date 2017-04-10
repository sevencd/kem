package cn.ilanhai.kem.domain.contacts;

import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * 通过id查询联系人信息
 * @author dgj
 *
 */
public class SearchContactsById extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5508664474333673670L;

	private String contactsId;

	public String getContactsId() {
		return contactsId;
	}

	public void setContactsId(String contactsId) {
		this.contactsId = contactsId;
	}

}
