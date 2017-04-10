package cn.ilanhai.kem.domain.user.frontuser;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class BackuserManageFromtUserInfoEntity extends AbstractEntity {

	private static final long serialVersionUID = -6664618374466924884L;
	
	/**
	 * 用户id
	 */
	private String user_id;
	/**
	 * 状态
	 */
	private Integer status;

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * @return the user_id
	 */
	public String getUser_id() {
		return user_id;
	}

	/**
	 * @param user_id the user_id to set
	 */
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
}
