package cn.ilanhai.kem.domain.manuscript.dto;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class SearchManuscriptDto extends AbstractEntity {
	private static final long serialVersionUID = -2609663571236706158L;
	
	private String userId;
	
	private  String searchstr;
	/**
	 * 标签
	 */
	private String tag;
	/**
	 * 终端类型 1pc 2wap
	 */
	private Integer terminalType;
	/**
	 * 开始条数，不可空，正整数
	 */
	private Integer startCount;
	/**
	 * 取用条数，不可空，正整数
	 */
	private Integer pageSize;

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public Integer getTerminalType() {
		return terminalType;
	}

	public void setTerminalType(Integer terminalType) {
		this.terminalType = terminalType;
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

	public String getSearchstr() {
		return searchstr;
	}

	public void setSearchstr(String searchstr) {
		this.searchstr = searchstr;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
