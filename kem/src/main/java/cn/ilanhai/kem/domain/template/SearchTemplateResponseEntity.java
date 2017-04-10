package cn.ilanhai.kem.domain.template;

import java.util.List;

import cn.ilanhai.framework.app.domain.AbstractEntity;
import cn.ilanhai.framework.app.domain.Entity;

public class SearchTemplateResponseEntity extends AbstractEntity {
	private static final long serialVersionUID = -4687846201582844469L;
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
