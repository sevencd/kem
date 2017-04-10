package cn.ilanhai.kem.domain.special;

import java.util.Iterator;

import cn.ilanhai.framework.app.domain.AbstractEntity;
import cn.ilanhai.framework.app.domain.Entity;

/**
 * 查询专题响应Dto
 * 
 * @author hy
 *
 */
public class SearchSpecialResponseDto extends AbstractEntity {
	private static final long serialVersionUID = -4941444373770952039L;
	private Integer totalCount;
	private Integer startCount;
	private Integer pageSize;
	/**
	 * 返回集合
	 */
	private Iterator<Entity> list;

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
