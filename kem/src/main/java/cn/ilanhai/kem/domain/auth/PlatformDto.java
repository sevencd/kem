package cn.ilanhai.kem.domain.auth;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class PlatformDto extends AbstractEntity {
	private Integer type;

	private Boolean enable;

	private String authUri;

	public PlatformDto() {
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	

	public Boolean getEnable() {
		return enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}

	public String getAuthUri() {
		return authUri;
	}

	public void setAuthUri(String authUri) {
		this.authUri = authUri;
	}

}
