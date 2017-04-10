package cn.ilanhai.kem.domain.user.backuser;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class CurrentUserResponseEntity extends AbstractEntity {
	private static final long serialVersionUID = -7695659100187815749L;

	private String userName;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public CurrentUserResponseEntity(String userName) {
		this.userName = userName;
	}
}
