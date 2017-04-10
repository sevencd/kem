package cn.ilanhai.kem.domain.smsright.dto;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class SearchSmsDto extends AbstractEntity {
	private static final long serialVersionUID = -5032454467489494709L;
	private String keyword;
	private Integer startCount;
	private Integer pageSize;

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
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
}
