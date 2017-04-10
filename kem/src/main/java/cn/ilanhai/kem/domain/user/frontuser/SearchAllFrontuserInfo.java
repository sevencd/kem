package cn.ilanhai.kem.domain.user.frontuser;

import cn.ilanhai.framework.app.domain.AbstractEntity;

//后台用户获取所有会员的信息
public class SearchAllFrontuserInfo extends AbstractEntity {

	private static final long serialVersionUID = 4865705583612461159L;
	
	private String user_name;
	private String user_phone;
	private String user_email;
	private String status;
	private Integer count;
	/**
	 * @return the user_name
	 */
	public String getUser_name() {
		return user_name;
	}
	/**
	 * @param user_name the user_name to set
	 */
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	/**
	 * @return the user_phone
	 */
	public String getUser_phone() {
		return user_phone;
	}
	/**
	 * @param user_phone the user_phone to set
	 */
	public void setUser_phone(String user_phone) {
		this.user_phone = user_phone;
	}
	/**
	 * @return the user_email
	 */
	public String getUser_email() {
		return user_email;
	}
	/**
	 * @param user_email the user_email to set
	 */
	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the count
	 */
	public Integer getCount() {
		return count;
	}
	/**
	 * @param count the count to set
	 */
	public void setCount(Integer count) {
		this.count = count;
	}

}
