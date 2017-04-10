package cn.ilanhai.kem.domain.plugin.activeplugin.dto;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class DrawPrizeRequestDto extends AbstractEntity{

	private static final long serialVersionUID = -4578661708141556981L;
	
	private String relationId;
	private String name;
	private String phone;
	
	public String getRelationId() {
		return relationId;
	}
	public void setRelationId(String relationId) {
		this.relationId = relationId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	
}
