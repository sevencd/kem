package cn.ilanhai.kem.domain.collectionType.dto;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class LoadAllCollectionTypeDto extends AbstractEntity {
	private static final long serialVersionUID = -2609663571236706158L;
	private Integer loadType;
	/**
	 * 开始条数，不可空，正整数
	 */
	private int startCount;
	/**
	 * 取用条数，不可空，正整数
	 */
	private int pageSize;


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

	public Integer getLoadType() {
		return loadType;
	}

	public void setLoadType(Integer loadType) {
		this.loadType = loadType;
	}
}
