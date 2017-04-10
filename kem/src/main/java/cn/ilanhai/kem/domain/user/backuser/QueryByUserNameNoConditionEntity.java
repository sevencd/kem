package cn.ilanhai.kem.domain.user.backuser;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class QueryByUserNameNoConditionEntity extends AbstractEntity {
	private static final long serialVersionUID = 1199598029954380367L;
	private String userName;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
