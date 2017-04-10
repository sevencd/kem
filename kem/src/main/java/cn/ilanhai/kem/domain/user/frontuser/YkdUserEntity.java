package cn.ilanhai.kem.domain.user.frontuser;

import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * 用户
 * 
 * @author hy
 *
 */
public class YkdUserEntity extends AbstractEntity {
	private static final long serialVersionUID = -6274621638426467194L;
	/**
	 * 用户名称
	 */
	private String userName;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
}
