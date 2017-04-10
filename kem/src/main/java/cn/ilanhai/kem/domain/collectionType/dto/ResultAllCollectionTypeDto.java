package cn.ilanhai.kem.domain.collectionType.dto;

import java.util.List;

import cn.ilanhai.framework.app.domain.AbstractEntity;
import cn.ilanhai.framework.app.domain.Entity;

public class ResultAllCollectionTypeDto extends AbstractEntity {
	private static final long serialVersionUID = -2609663571236706158L;
	private List<Entity> types;
	/**
	 * 开始条数，不可空，正整数
	 */
	private int startCount;
	/**
	 * 取用条数，不可空，正整数
	 */
	private int pageSize;

	private Integer totalCount;

	public int getStartCount() {
		return startCount;
	}

	public void setStartCount(int startCount) {
		this.startCount = startCount;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public List<Entity> getTypes() {
		return types;
	}

	public void setTypes(List<Entity> types) {
		this.types = types;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
}
