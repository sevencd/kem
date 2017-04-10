package cn.ilanhai.kem.domain.manuscript.dto;

import java.util.Date;

import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * 查询自己的稿件dto
 * 
 * @author hy
 *
 */
public class SearchFrontManuscriptDto extends AbstractEntity {
	private static final long serialVersionUID = 6362626228850408584L;

	private String userId;
	private String manuscriptName;
	private String tagName;
	private Date startTime;
	private Date endTime;
	private Integer manuscriptType;
	private Integer terminalType;
	private Integer startCount;
	private Integer pageSize;
	private int switchUser;
	private int byrand; 
	private int used;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getManuscriptName() {
		return manuscriptName;
	}

	public void setManuscriptName(String manuscriptName) {
		this.manuscriptName = manuscriptName;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Integer getManuscriptType() {
		return manuscriptType;
	}

	public void setManuscriptType(Integer manuscriptType) {
		this.manuscriptType = manuscriptType;
	}

	public Integer getTerminalType() {
		return terminalType;
	}

	public void setTerminalType(Integer terminalType) {
		this.terminalType = terminalType;
	}

	public int getSwitchUser() {
		return switchUser;
	}

	public void setSwitchUser(int switchUser) {
		this.switchUser = switchUser;
	}

	public Integer getStartCount() {
		return startCount;
	}

	public void setStartCount(Integer startCount) {
		this.startCount = startCount;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public int getByrand() {
		return byrand;
	}

	public void setByrand(int byrand) {
		this.byrand = byrand;
	}

	public int getUsed() {
		return used;
	}

	public void setUsed(int used) {
		this.used = used;
	}
}
