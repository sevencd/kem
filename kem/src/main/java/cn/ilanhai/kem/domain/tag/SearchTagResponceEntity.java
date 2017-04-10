package cn.ilanhai.kem.domain.tag;

import java.util.List;

import cn.ilanhai.framework.app.domain.AbstractEntity;
import cn.ilanhai.framework.app.domain.Entity;

/**
 * 查询标签响应对象
 * 
 * @author hy
 *
 */
public class SearchTagResponceEntity extends AbstractEntity {
	private static final long serialVersionUID = 775049931312784051L;
	private List<Entity> list;
	private Integer totalCount;
	private Integer startCount;
	private Integer pageSize;

	public List<Entity> getList() {
		return list;
	}

	public void setList(List<Entity> list) {
		this.list = list;
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

}
