package cn.ilanhai.kem.domain.customer.dto;

import java.util.Iterator;

import cn.ilanhai.framework.app.domain.AbstractEntity;
import cn.ilanhai.framework.app.domain.Entity;

public class SearchResultCustomerDto extends AbstractEntity {
	private static final long serialVersionUID = 1405006279198511375L;
	private Integer startCount;
	private Integer pageSize;
	private Integer totalCount;
	private Iterator<Entity> list;

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

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public Iterator<Entity> getList() {
		return list;
	}

	public void setList(Iterator<Entity> iterator) {
		this.list = iterator;
	}
}
