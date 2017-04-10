package cn.ilanhai.kem.domain.manuscript.dto;

import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * 创建稿件Dto
 * 
 * @author hy
 *
 */
public class CreateManuscriptResponseDto extends AbstractEntity {
	private static final long serialVersionUID = 2932530049066042174L;
	/**
	 * id
	 */
	private String ManuscriptId;
	/**
	 * 终端类型
	 */
	private Integer terminalType;

	public String getManuscriptId() {
		return ManuscriptId;
	}

	public void setManuscriptId(String manuscriptId) {
		ManuscriptId = manuscriptId;
	}

	public Integer getTerminalType() {
		return terminalType;
	}

	public void setTerminalType(Integer terminalType) {
		this.terminalType = terminalType;
	}
}
