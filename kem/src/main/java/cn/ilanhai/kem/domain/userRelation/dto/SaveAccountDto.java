package cn.ilanhai.kem.domain.userRelation.dto;

import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * 保存子账户DTO
 * 
 * @author hy
 *
 */
public class SaveAccountDto extends AbstractEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4898138884981285184L;
	private String phone;
	private String pwd;
	private String userId;
	private Integer state;
	private String oldPwd;

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getOldPwd() {
		return oldPwd;
	}

	public void setOldPwd(String oldPwd) {
		this.oldPwd = oldPwd;
	}

}
