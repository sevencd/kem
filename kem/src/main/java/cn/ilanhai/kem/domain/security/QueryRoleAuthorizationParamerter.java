package cn.ilanhai.kem.domain.security;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class QueryRoleAuthorizationParamerter extends AbstractEntity {

	private static final long serialVersionUID = 1L;
	private int groupId;
	private String uri;

	public QueryRoleAuthorizationParamerter() {

	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}
}
