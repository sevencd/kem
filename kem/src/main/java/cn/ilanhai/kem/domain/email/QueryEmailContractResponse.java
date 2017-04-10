package cn.ilanhai.kem.domain.email;

import java.util.List;

import cn.ilanhai.framework.app.domain.AbstractEntity;
import cn.ilanhai.framework.app.domain.Entity;

/**
 * 查询联系人的返回
 * @author dgj
 *
 */
public class QueryEmailContractResponse extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1933803025409460069L;

	private List<Entity> List;
	private Integer startCount;
	private Integer totalCount;
	private Integer pageSize;

	public List<Entity> getList() {
		return List;
	}

	public void setList(List<Entity> list) {
		List = list;
	}

	public Integer getStartCount() {
		return startCount;
	}

	public void setStartCount(Integer startCount) {
		this.startCount = startCount;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
}
