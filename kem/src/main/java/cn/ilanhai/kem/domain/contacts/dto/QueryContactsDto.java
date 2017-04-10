package cn.ilanhai.kem.domain.contacts.dto;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class QueryContactsDto extends AbstractEntity {
	private static final long serialVersionUID = -3036826718637113312L;
	private String keyword;
	private Integer type;
	private Integer startCount;
	private Integer pageSize;
	private String userId;

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
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
