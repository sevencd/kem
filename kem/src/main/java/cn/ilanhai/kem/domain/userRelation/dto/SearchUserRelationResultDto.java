package cn.ilanhai.kem.domain.userRelation.dto;

import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * 查询用户关系配置
 * 
 * @author hy
 *
 */
public class SearchUserRelationResultDto extends AbstractEntity {
	private static final long serialVersionUID = 8125338106010105595L;

	public SearchUserRelationResultDto() {
		super();
	}

	/**
	 * 用户ID
	 */
	private String userId;
	/**
	 * 用户主主账户id
	 */
	private String fatherUserId;
	/**
	 * 状态 0:启用1:停用
	 */
	private Integer state;

	private String userPhone;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getFatherUserId() {
		return fatherUserId;
	}

	public void setFatherUserId(String fatherUserId) {
		this.fatherUserId = fatherUserId;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}
}
