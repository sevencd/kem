package cn.ilanhai.kem.domain.bindhost;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class UpdateUserHostStatus extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6302284687857422948L;

	private Integer status;
	private String userId;
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
}
