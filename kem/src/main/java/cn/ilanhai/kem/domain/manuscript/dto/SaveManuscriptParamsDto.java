package cn.ilanhai.kem.domain.manuscript.dto;

import java.util.List;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class SaveManuscriptParamsDto extends AbstractEntity {
	private static final long serialVersionUID = 6362626228850408584L;

	private String manuscriptId;
	private List<String> params;
	private Integer paramType;

	public String getManuscriptId() {
		return manuscriptId;
	}

	public void setManuscriptId(String manuscriptId) {
		this.manuscriptId = manuscriptId;
	}

	public List<String> getParams() {
		return params;
	}

	public void setParams(List<String> params) {
		this.params = params;
	}

	public Integer getParamType() {
		return paramType;
	}

	public void setParamType(Integer paramType) {
		this.paramType = paramType;
	}
}
