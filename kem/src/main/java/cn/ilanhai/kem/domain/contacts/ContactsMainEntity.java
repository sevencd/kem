package cn.ilanhai.kem.domain.contacts;

import java.util.Date;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class ContactsMainEntity extends AbstractEntity {
	private static final long serialVersionUID = 432010022968707933L;
	/**
	 * 联系人编号
	 */
	private String contactsId;
	/**
	 * 是否删除 1删除，0没有
	 */
	private Integer isDelete;
	/**
	 * 创建时间
	 */
	private Date createtime;
	/**
	 * 更新时间
	 */
	private Date updatetime;
	/**
	 * 联系人类型，1手机，2邮件
	 */
	private Integer contactsType;

	private String userId;

	public String getContactsId() {
		return contactsId;
	}

	public void setContactsId(String contactsId) {
		this.contactsId = contactsId;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

	public Integer getContactsType() {
		return contactsType;
	}

	public void setContactsType(Integer contactsType) {
		this.contactsType = contactsType;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
