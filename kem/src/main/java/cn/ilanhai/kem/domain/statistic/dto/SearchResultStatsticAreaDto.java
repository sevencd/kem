package cn.ilanhai.kem.domain.statistic.dto;

import java.util.Iterator;

import cn.ilanhai.framework.app.domain.AbstractEntity;
import cn.ilanhai.framework.app.domain.Entity;

public class SearchResultStatsticAreaDto extends AbstractEntity {
	private static final long serialVersionUID = -5743924443594365547L;
	private String manuscriptId;
	private Integer maxQuantity;
	/**
	 * 开始条数，不可空，正整数
	 */
	private Integer startCount;
	/**
	 * 取用条数，不可空，正整数
	 */
	private Integer pageSize;
	
	private Iterator<Entity> list;

	public String getManuscriptId() {
		return manuscriptId;
	}

	public void setManuscriptId(String manuscriptId) {
		this.manuscriptId = manuscriptId;
	}

	public Integer getMaxQuantity() {
		return maxQuantity;
	}

	public void setMaxQuantity(Integer maxQuantity) {
		this.maxQuantity = maxQuantity;
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

	public Iterator<Entity> getList() {
		return list;
	}

	public void setList(Iterator<Entity> list) {
		this.list = list;
	}
}
