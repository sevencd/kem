package cn.ilanhai.kem.domain.manuscript.dto;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class GetManuscriptParameterDto extends AbstractEntity{

	private static final long serialVersionUID = 6362626228850408584L;
	
	private String id;
	private Integer parameterType;
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getParameterType() {
		return parameterType;
	}

	public void setParameterType(Integer parameterType) {
		this.parameterType = parameterType;
	}
}
