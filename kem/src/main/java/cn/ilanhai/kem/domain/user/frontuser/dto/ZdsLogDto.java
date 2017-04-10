package cn.ilanhai.kem.domain.user.frontuser.dto;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class ZdsLogDto extends AbstractEntity {
	private static final long serialVersionUID = -3149572019354020155L;
	private String userId;
	private String param;
	private String result;
	private String exception;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getException() {
		return exception;
	}

	public void setException(String exception) {
		this.exception = exception;
	}
}
