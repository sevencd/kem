package cn.ilanhai.kem.domain.user.frontuser;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class UpdateUserLoginPwdDto extends AbstractEntity {

	private String oldLoginpwd;
	private String newLoginpwd;

	public UpdateUserLoginPwdDto() {

	}

	public String getOldLoginpwd() {
		return oldLoginpwd;
	}

	public void setOldLoginpwd(String oldLoginpwd) {
		this.oldLoginpwd = oldLoginpwd;
	}

	public String getNewLoginpwd() {
		return newLoginpwd;
	}

	public void setNewLoginpwd(String newLoginpwd) {
		this.newLoginpwd = newLoginpwd;
	}

}
