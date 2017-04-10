package cn.ilanhai.kem.domain.statistic.dto;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class StatisticRequestDto extends AbstractEntity {
	private static final long serialVersionUID = 1L;
	private Integer startCount;//开始条数
	private Integer pageSize;//页面大小
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
