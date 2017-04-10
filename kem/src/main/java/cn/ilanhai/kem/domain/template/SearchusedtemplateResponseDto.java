package cn.ilanhai.kem.domain.template;

import java.util.List;

import cn.ilanhai.framework.app.domain.AbstractEntity;
import cn.ilanhai.framework.app.domain.Entity;

public class SearchusedtemplateResponseDto extends AbstractEntity {
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
	private List<Entity> list;

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

	public List<Entity> getList() {
		return list;
	}

	public void setList(List<Entity> list) {
		this.list = list;
	}

	public SearchusedtemplateResponseDto(List<Entity> list) {
		this.list = list;
	}
}
