package cn.ilanhai.kem.domain.bindhost;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class BindHostResponseEntity extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6513226584095975093L;

	private Integer id;
	private String userId;
	private String host;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
}
