package cn.ilanhai.kem.domain.user.frontuser;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class LoadUserInfoByType extends AbstractEntity {

	private static final long serialVersionUID = -4387252467855093641L;

	/*
	 * 用户类型
	 */
	private Integer userType;
	/*
	 * 开始数
	 */
	private Integer startCount;
	/*
	 * 查看条数
	 */
	private Integer pageSize;
	
	private String searchString;
	
	private Integer memberStatus;
	
	
	public Integer getMemberStatus() {
		return memberStatus;
	}

	public void setMemberStatus(Integer memberStatus) {
		this.memberStatus = memberStatus;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	/**
	 * @return the startCount
	 */
	public Integer getStartCount() {
		return startCount;
	}

	/**
	 * @param startCount the startCount to set
	 */
	public void setStartCount(Integer startCount) {
		this.startCount = startCount;
	}

	/**
	 * @return the pageSize
	 */
	public Integer getPageSize() {
		return pageSize;
	}

	/**
	 * @param pageSize the pageSize to set
	 */
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * @return the searchString
	 */
	public String getSearchString() {
		return searchString;
	}

	/**
	 * @param searchString the searchString to set
	 */
	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}

}
