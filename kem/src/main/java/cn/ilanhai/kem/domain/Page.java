package cn.ilanhai.kem.domain;

import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * 分页基类
 * 
 * @author he
 *
 */
public abstract class Page extends AbstractEntity {

	private static final long serialVersionUID = -7614822487807484214L;
	
	//起始条数
	private Integer startCount;
	//获取条数
	private Integer pageSize;
	//排序
	private Order order;
	//总数
	private Integer recordCount;

	public Page() {
		
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

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Integer getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(Integer recordCount) {
		this.recordCount = recordCount;
	}

}
