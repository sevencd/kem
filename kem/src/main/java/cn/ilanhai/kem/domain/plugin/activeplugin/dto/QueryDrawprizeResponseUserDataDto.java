package cn.ilanhai.kem.domain.plugin.activeplugin.dto;

import java.util.Iterator;

import cn.ilanhai.framework.app.domain.AbstractEntity;
import cn.ilanhai.framework.app.domain.Entity;

public class QueryDrawprizeResponseUserDataDto extends AbstractEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8459875270885039670L;
	private String prizeNo;

	private Iterator<Entity> list;
	/**
	 * 开始条数，不可空，正整数
	 */
	private Integer startCount;
	/**
	 * 取用条数，不可空，正整数
	 */
	private Integer pageSize;

	private Integer totalCount;

	public String getPrizeNo() {
		return prizeNo;
	}

	public void setPrizeNo(String prizeNo) {
		this.prizeNo = prizeNo;
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

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
}
