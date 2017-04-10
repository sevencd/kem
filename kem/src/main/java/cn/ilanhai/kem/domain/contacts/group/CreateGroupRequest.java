package cn.ilanhai.kem.domain.contacts.group;

import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * 保存群组的请求
 * 
 * @author dgj
 *
 */
public class CreateGroupRequest extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2870426368087751609L;

	private String groupId;
	private String groupName;
	private Integer type;

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

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
}
