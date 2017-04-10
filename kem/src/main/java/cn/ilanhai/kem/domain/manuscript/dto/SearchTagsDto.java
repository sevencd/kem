package cn.ilanhai.kem.domain.manuscript.dto;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class SearchTagsDto extends AbstractEntity {
	private static final long serialVersionUID = 6250151820854346127L;
	private Integer terminalType;
	private Integer parameterType;
	private Integer manuscriptType;
	private Integer tagType;
	private String userId;

	public Integer getTerminalType() {
		return terminalType;
	}

	public void setTerminalType(Integer terminalType) {
		this.terminalType = terminalType;
	}

	public Integer getParameterType() {
		return parameterType;
	}

	public void setParameterType(Integer parameterType) {
		this.parameterType = parameterType;
	}

	public Integer getManuscriptType() {
		return manuscriptType;
	}

	public void setManuscriptType(Integer manuscriptType) {
		this.manuscriptType = manuscriptType;
	}

	public Integer getTagType() {
		return tagType;
	}

	public void setTagType(Integer tagType) {
		this.tagType = tagType;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
