package cn.ilanhai.kem.domain.midway;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class PullPublishedResponseDto extends AbstractEntity{

	private static final long serialVersionUID = 3219921988098648263L;
	
	private String kid;
	private String main_domain;
	private String api_host;
	private String mobile_host;
	
	public String getKid() {
		return kid;
	}
	public void setKid(String kid) {
		this.kid = kid;
	}
	public String getMain_domain() {
		return main_domain;
	}
	public void setMain_domain(String main_domain) {
		this.main_domain = main_domain;
	}
	public String getApi_host() {
		return api_host;
	}
	public void setApi_host(String api_host) {
		this.api_host = api_host;
	}
	public String getMobile_host() {
		return mobile_host;
	}
	public void setMobile_host(String mobile_host) {
		this.mobile_host = mobile_host;
	}
	
	
}
