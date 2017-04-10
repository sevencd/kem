package cn.ilanhai.kem.domain.manuscript.dto;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class SaveManuscriptNameDto extends AbstractEntity {
	private static final long serialVersionUID = 1184659505843486941L;
	private String manuscriptId;
	private String name;
	private Integer terminalType;

	public String getManuscriptId() {
		return manuscriptId;
	}

	public void setManuscriptId(String manuscriptId) {
		this.manuscriptId = manuscriptId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getTerminalType() {
		return terminalType;
	}

	public void setTerminalType(Integer terminalType) {
		this.terminalType = terminalType;
	}
}
