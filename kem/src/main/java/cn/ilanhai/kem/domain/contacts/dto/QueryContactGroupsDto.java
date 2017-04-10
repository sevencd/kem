package cn.ilanhai.kem.domain.contacts.dto;

import java.util.List;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class QueryContactGroupsDto extends AbstractEntity {
	private static final long serialVersionUID = -3036826718637113312L;
	private List<String> groupIds;
	private String userId;

	public List<String> getGroupIds() {
		return groupIds;
	}

	public void setGroupIds(List<String> groupIds) {
		this.groupIds = groupIds;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
