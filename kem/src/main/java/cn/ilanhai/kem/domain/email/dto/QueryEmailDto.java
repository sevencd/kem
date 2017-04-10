package cn.ilanhai.kem.domain.email.dto;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class QueryEmailDto extends AbstractEntity {
	private static final long serialVersionUID = -3036826718637113312L;
	private String keyword;
	private Integer status;
	private Integer startCount;
	private Integer pageSize;
	private String userId;

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getStartCount() {
		return startCount;
	}

	public void setStartCount(Integer startCount) {
		this.startCount = startCount;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
