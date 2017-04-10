package cn.ilanhai.kem.domain.extension;

import java.util.Iterator;

import cn.ilanhai.framework.app.domain.AbstractEntity;
import cn.ilanhai.framework.app.domain.Entity;

public class SearExtensionResponseDto extends AbstractEntity {
	private static final long serialVersionUID = -4590737413877559069L;
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
	/**
	 * 数据集合
	 */
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

	public void setList(Iterator<Entity> list) {
		this.list = list;
	}
}
