package cn.ilanhai.kem.domain.material.dto;

import java.util.Iterator;

import cn.ilanhai.framework.app.domain.AbstractEntity;
import cn.ilanhai.framework.app.domain.Entity;

public class SearchMateralResponseDto extends AbstractEntity{
	private static final long serialVersionUID = 5147954032634441987L;

	/**
	 * 数据集合
	 */
	private Iterator<Entity> list;
	
	/**
	 * 开始条数
	 */
	private Integer startCount;
	/**
	 * 页面个数
	 */
	private Integer pageSize;
	/**
	 * 总数
	 */
	private Integer totalCount;
	public Iterator<Entity> getList() {
		return list;
	}
	public void setList(Iterator<Entity> list) {
		this.list = list;
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
	public Integer getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
}
