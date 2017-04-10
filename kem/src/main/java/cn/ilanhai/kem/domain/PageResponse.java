package cn.ilanhai.kem.domain;

import java.util.Iterator;

import cn.ilanhai.framework.app.domain.AbstractEntity;
import cn.ilanhai.framework.app.domain.Entity;

public class PageResponse extends AbstractEntity {

	private static final long serialVersionUID = 2018920091745076741L;
	
	private Integer totalCount;
	private Integer startCount;// 开始条数
	private Integer pageSize;// 取用条数
	private Iterator<Entity> list;

	public PageResponse() {

	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
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
