package cn.ilanhai.kem.domain.email;

import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * 根据用户id查询邮件联系人
 * @author dgj
 *
 */
public class SearchEmailQuantityByUser extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5600067358260065575L;

	private String userId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
}
