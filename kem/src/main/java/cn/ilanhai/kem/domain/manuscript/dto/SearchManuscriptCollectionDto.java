package cn.ilanhai.kem.domain.manuscript.dto;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class SearchManuscriptCollectionDto extends AbstractEntity {
	private static final long serialVersionUID = -2609663571236706158L;
	
	private Integer startCount;
	private Integer pageSize;
	/**
	 * 可空，为空则查询所有模板类型，模板类型：1：PC端；2：移动端
	 */
	private Integer terminalType;
	private String manuscriptName;
	
	private String userId;
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
	public Integer getTerminalType() {
		return terminalType;
	}
	public void setTerminalType(Integer terminalType) {
		this.terminalType = terminalType;
	}
	public String getManuscriptName() {
		return manuscriptName;
	}
	public void setManuscriptName(String manuscriptName) {
		this.manuscriptName = manuscriptName;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
}
