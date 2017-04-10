package cn.ilanhai.kem.domain.contacts;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class ContactsInfoEntity extends AbstractEntity {
	private static final long serialVersionUID = 2160031229608775549L;
	/**
	 * info编号
	 */
	private Integer infoId;
	/**
	 * 联系人编号
	 */
	private String contactsId;
	/**
	 * info key值
	 */
	private Integer infoKey;
	/**
	 * info value值
	 */
	private String infoValue;
	/**
	 * 状态
	 */
	private Integer enable;
	
	private String userId;
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getInfoId() {
		return infoId;
	}

	public void setInfoId(Integer infoId) {
		this.infoId = infoId;
	}

	public String getContactsId() {
		return contactsId;
	}

	public void setContactsId(String contactsId) {
		this.contactsId = contactsId;
	}

	public Integer getInfoKey() {
		return infoKey;
	}

	public void setInfoKey(Integer infoKey) {
		this.infoKey = infoKey;
	}

	public String getInfoValue() {
		return infoValue;
	}

	public void setInfoValue(String infoValue) {
		this.infoValue = infoValue;
	}

	public Integer getEnable() {
		return enable;
	}

	public void setEnable(Integer enable) {
		this.enable = enable;
	}
}
