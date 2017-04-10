package cn.ilanhai.kem.domain.manuscript;

import java.util.Date;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class ManuscriptSettingEntity extends AbstractEntity {
	private static final long serialVersionUID = 4397951365419882400L;
	private Integer settingId;
	private String manuscriptId;
	private String manuscriptName;
	private String manuscriptImg;
	private String manuscriptMainColor;
	private String manuscriptSummary;
	private Date createTime;
	private Integer enableState;

	public Integer getSettingId() {
		return settingId;
	}

	public void setSettingId(Integer settingId) {
		this.settingId = settingId;
	}

	public String getManuscriptId() {
		return manuscriptId;
	}

	public void setManuscriptId(String manuscriptId) {
		this.manuscriptId = manuscriptId;
	}

	public String getManuscriptName() {
		return manuscriptName;
	}

	public void setManuscriptName(String manuscriptName) {
		this.manuscriptName = manuscriptName;
	}

	public String getManuscriptImg() {
		return manuscriptImg;
	}

	public void setManuscriptImg(String manuscriptImg) {
		this.manuscriptImg = manuscriptImg;
	}

	public String getManuscriptMainColor() {
		return manuscriptMainColor;
	}

	public void setManuscriptMainColor(String manuscriptMainColor) {
		this.manuscriptMainColor = manuscriptMainColor;
	}

	public String getManuscriptSummary() {
		return manuscriptSummary;
	}

	public void setManuscriptSummary(String manuscriptSummary) {
		this.manuscriptSummary = manuscriptSummary;
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
