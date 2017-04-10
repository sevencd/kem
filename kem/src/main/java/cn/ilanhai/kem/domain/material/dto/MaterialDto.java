package cn.ilanhai.kem.domain.material.dto;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class MaterialDto extends AbstractEntity {
	private static final long serialVersionUID = 2597223490710324237L;

	// 分类名称
	private String materialName;
	// 分类状态
	private Integer materialState;
	// 终端类型
	private Integer terminalType;
	public String getMaterialName() {
		return materialName;
	}
	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}
	public Integer getMaterialState() {
		return materialState;
	}
	public void setMaterialState(Integer materialState) {
		this.materialState = materialState;
	}
	public Integer getTerminalType() {
		return terminalType;
	}
	public void setTerminalType(Integer terminalType) {
		this.terminalType = terminalType;
	}
}
