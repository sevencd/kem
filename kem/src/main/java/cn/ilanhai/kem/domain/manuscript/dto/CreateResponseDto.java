package cn.ilanhai.kem.domain.manuscript.dto;

import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * 新建模版返回数据
 * 
 * @author hy
 *
 */
public class CreateResponseDto extends AbstractEntity {
	private static final long serialVersionUID = 3044496274582093561L;
	private String manuscriptId;
	private Integer terminalType;

	public CreateResponseDto() {

	}

	public String getManuscriptId() {
		return manuscriptId;
	}

	public void setManuscriptId(String manuscriptId) {
		this.manuscriptId = manuscriptId;
	}

	public Integer getTerminalType() {
		return terminalType;
	}

	public void setTerminalType(Integer terminalType) {
		this.terminalType = terminalType;
	}
}
