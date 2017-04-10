package cn.ilanhai.kem.domain.user.frontuser.dto;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class SaveUserCodeDto extends AbstractEntity {
	private static final long serialVersionUID = -3149572019354020155L;
	private String code;
	private String userId;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
