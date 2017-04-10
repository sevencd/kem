package cn.ilanhai.kem.domain.special;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class SaveSpecialNameDto extends AbstractEntity {
	private static final long serialVersionUID = 1184659505843486941L;
	private String specialId;
	private String name;
	private Integer terminalType;

	public String getSpecialId() {
		return specialId;
	}

	public void setSpecialId(String specialId) {
		this.specialId = specialId;
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
