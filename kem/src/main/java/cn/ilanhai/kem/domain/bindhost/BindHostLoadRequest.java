package cn.ilanhai.kem.domain.bindhost;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class BindHostLoadRequest extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5950012225158289703L;
	private String userId;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
}
