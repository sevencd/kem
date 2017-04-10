package cn.ilanhai.kem.domain.email.dto;

import java.util.Iterator;

import cn.ilanhai.framework.app.domain.AbstractEntity;
import cn.ilanhai.framework.app.domain.Entity;

public class QueryEmailResponseDto extends AbstractEntity {
	private static final long serialVersionUID = -2797040197455336937L;
	private Iterator<Entity> list;
	private Integer pageSize;
	private Integer startCount;
	private Integer totalCount;
	public Iterator<Entity> getList() {
		return list;
	}
	public void setList(Iterator<Entity> list) {
		this.list = list;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getStartCount() {
		return startCount;
	}
	public void setStartCount(Integer startCount) {
		this.startCount = startCount;
	}
	public Integer getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
}
