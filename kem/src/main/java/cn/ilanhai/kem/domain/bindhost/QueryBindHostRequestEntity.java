package cn.ilanhai.kem.domain.bindhost;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class QueryBindHostRequestEntity extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 877466079070957116L;
	
	private String userId;

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

}
