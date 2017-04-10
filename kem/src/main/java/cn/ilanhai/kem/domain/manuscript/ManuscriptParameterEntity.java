package cn.ilanhai.kem.domain.manuscript;

import java.util.Date;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class ManuscriptParameterEntity extends AbstractEntity {
	private static final long serialVersionUID = 6543962229014288133L;
	private Integer parameterId;
	private String manuscriptId;
	private Integer parameterType;
	private Date createTime;
	private String parameter;
	private Integer enableState;

	public Integer getParameterId() {
		return parameterId;
	}

	public void setParameterId(Integer parameterId) {
		this.parameterId = parameterId;
	}

	public String getManuscriptId() {
		return manuscriptId;
	}

	public void setManuscriptId(String manuscriptId) {
		this.manuscriptId = manuscriptId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public Integer getParameterType() {
		return parameterType;
	}

	public void setParameterType(Integer parameterType) {
		this.parameterType = parameterType;
	}

	public Integer getEnableState() {
		return enableState;
	}

	public void setEnableState(Integer enableState) {
		this.enableState = enableState;
	}
}
