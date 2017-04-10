package cn.ilanhai.kem.domain.auth;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class AuthDto extends AbstractEntity {
	private String code;
	private int state = -1;
	private int source = -1;

	public AuthDto() {

	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getSource() {
		return source;
	}

	public void setSource(int source) {
		this.source = source;
	}
	
}
