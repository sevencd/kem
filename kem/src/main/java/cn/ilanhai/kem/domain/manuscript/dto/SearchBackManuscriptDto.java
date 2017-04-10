package cn.ilanhai.kem.domain.manuscript.dto;

import java.util.Date;

import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * 后台查询稿件dto
 * 
 * @author hy
 *
 */
public class SearchBackManuscriptDto extends AbstractEntity {
	private static final long serialVersionUID = 6362626228850408584L;

	private String manuscriptName;
	private String tagName;
	private String orderType;
	private Date startTime;
	private Date endTime;
	private Integer manuscriptType;
	private Integer terminalType;
	private Integer startCount;
	private Integer pageSize;
	private int manuscriptState;

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

	public int getManuscriptState() {
		return manuscriptState;
	}

	public void setManuscriptState(int manuscriptState) {
		this.manuscriptState = manuscriptState;
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

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
}
