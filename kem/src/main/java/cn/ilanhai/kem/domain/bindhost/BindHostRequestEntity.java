package cn.ilanhai.kem.domain.bindhost;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class BindHostRequestEntity  extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6465565504014734531L;
	
	private Integer id;
	private String userId;//用户id
	private String host;//绑定的域名
	private Integer status;//绑定域名的状态，0禁用，1正常
	
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
	/**
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
}
