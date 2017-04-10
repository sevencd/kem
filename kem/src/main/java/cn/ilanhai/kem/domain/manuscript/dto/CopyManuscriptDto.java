package cn.ilanhai.kem.domain.manuscript.dto;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class CopyManuscriptDto extends AbstractEntity {
	private static final long serialVersionUID = -1811113794792337614L;
	private String manuscriptId;
	private String manuscriptName;

	public CopyManuscriptDto() {

	}

	public String getManuscriptId() {
		return manuscriptId;
	}

	public void setManuscriptId(String manuscriptId) {
		this.manuscriptId = manuscriptId;
	}

	public String getManuscriptName() {
		return manuscriptName;
	}

	public void setManuscriptName(String manuscriptName) {
		this.manuscriptName = manuscriptName;
	}
}
