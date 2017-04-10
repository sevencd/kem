package cn.ilanhai.kem.domain.statistic.dto;

import java.util.Date;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class QueryStatsticAreaDto extends AbstractEntity {
	private static final long serialVersionUID = -5743924443594365547L;
	private String visitUrl;
	private String areaName;
	/**
	 * 城市
	 */
	private String areaCity;
	private Date areaAddTime;
	
	private Date startTime;
	private Date endTime;
	
	
	/**
	 * 开始条数，不可空，正整数
	 */
	private Integer startCount;
	/**
	 * 取用条数，不可空，正整数
	 */
	private Integer pageSize;
	

	public String getVisitUrl() {
		return visitUrl;
	}

	public void setVisitUrl(String visitUrl) {
		this.visitUrl = visitUrl;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getAreaCity() {
		return areaCity;
	}

	public void setAreaCity(String areaCity) {
		this.areaCity = areaCity;
	}

	public Date getAreaAddTime() {
		return areaAddTime;
	}

	public void setAreaAddTime(Date areaAddTime) {
		this.areaAddTime = areaAddTime;
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
}
