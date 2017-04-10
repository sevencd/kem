package cn.ilanhai.kem.domain.bindhost;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class QueryExtensionByuserRequest extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3542720693080199994L;
	
	private String userId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
