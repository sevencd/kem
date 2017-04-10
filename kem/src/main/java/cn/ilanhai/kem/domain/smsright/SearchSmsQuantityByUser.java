package cn.ilanhai.kem.domain.smsright;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class SearchSmsQuantityByUser extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1884868166423641810L;
	private String userId;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
}
