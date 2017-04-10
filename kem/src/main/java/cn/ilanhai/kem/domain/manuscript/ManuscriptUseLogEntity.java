package cn.ilanhai.kem.domain.manuscript;

import java.util.Date;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class ManuscriptUseLogEntity extends AbstractEntity {
	private static final long serialVersionUID = 2982556023035529529L;
	private Integer logId;
	private String manuscriptId;
	private String log;
	private Date createTime;
	private Integer enableState;

	public Integer getLogId() {
		return logId;
	}

	public void setLogId(Integer logId) {
		this.logId = logId;
	}

	public String getManuscriptId() {
		return manuscriptId;
	}

	public void setManuscriptId(String manuscriptId) {
		this.manuscriptId = manuscriptId;
	}

	public String getLog() {
		return log;
	}

	public void setLog(String log) {
		this.log = log;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getEnableState() {
		return enableState;
	}

	public void setEnableState(Integer enableState) {
		this.enableState = enableState;
	}
}
