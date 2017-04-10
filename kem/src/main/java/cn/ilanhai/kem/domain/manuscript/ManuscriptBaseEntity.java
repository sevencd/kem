package cn.ilanhai.kem.domain.manuscript;

import java.util.Date;
import java.util.List;

import cn.ilanhai.framework.app.domain.AbstractEntity;
import cn.ilanhai.kem.domain.enums.ManuscriptParameterType;

public class ManuscriptBaseEntity extends AbstractEntity {
	private static final long serialVersionUID = -548692365539567302L;
	protected String manuscriptId;
	protected Date createTime;
	protected String userId;
	protected Integer manuscriptType;
	protected Integer manuscriptState;
	protected Integer enableState;
	protected Integer terminalType;
	protected List<ManuscriptParameterEntity> manuscriptParameters;
	protected ManuscriptContentEntity manuscriptContent;
	protected ManuscriptSettingEntity manuscriptSetting;

	public String getManuscriptId() {
		return manuscriptId;
	}

	public void setManuscriptId(String manuscriptId) {
		this.manuscriptId = manuscriptId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public List<ManuscriptParameterEntity> getManuscriptParameters() {
		return manuscriptParameters;
	}

	public void setManuscriptParameters(List<ManuscriptParameterEntity> manuscriptParameters) {
		this.manuscriptParameters = manuscriptParameters;
	}
	
	public String getManuscriptParameterByType(ManuscriptParameterType type){
		for(ManuscriptParameterEntity value :this.manuscriptParameters){
			if(value.getParameterType().equals(type.getValue())){
				return value.getParameter();
			}
		}
		return null;
	}

	public ManuscriptContentEntity getManuscriptContent() {
		return manuscriptContent;
	}

	public void setManuscriptContent(ManuscriptContentEntity manuscriptContent) {
		this.manuscriptContent = manuscriptContent;
	}

	public ManuscriptSettingEntity getManuscriptSetting() {
		return manuscriptSetting;
	}

	public void setManuscriptSetting(ManuscriptSettingEntity manuscriptSetting) {
		this.manuscriptSetting = manuscriptSetting;
	}

	public Integer getManuscriptType() {
		return manuscriptType;
	}

	public void setManuscriptType(Integer manuscriptType) {
		this.manuscriptType = manuscriptType;
	}

	public Integer getManuscriptState() {
		return manuscriptState;
	}

	public void setManuscriptState(Integer manuscriptState) {
		this.manuscriptState = manuscriptState;
	}

	public Integer getEnableState() {
		return enableState;
	}

	public void setEnableState(Integer enableState) {
		this.enableState = enableState;
	}

	public Integer getTerminalType() {
		return terminalType;
	}

	public void setTerminalType(Integer terminalType) {
		this.terminalType = terminalType;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
