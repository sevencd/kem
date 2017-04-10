package cn.ilanhai.kem.domain.contacts.group;

import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * 通过名字查询群组是否存在
 * 
 * @author dgj
 *
 */
public class QueryContactsGroupIsExits extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1648752517847923147L;

	private String groupName;
	private String userId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

}
