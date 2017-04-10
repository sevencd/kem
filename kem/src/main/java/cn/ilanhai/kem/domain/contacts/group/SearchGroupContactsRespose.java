package cn.ilanhai.kem.domain.contacts.group;

import java.util.List;

import cn.ilanhai.framework.app.domain.AbstractEntity;
import cn.ilanhai.framework.app.domain.Entity;

/**
 * 查询群组联系人返回信息
 * 
 * @author dgj
 *
 */
public class SearchGroupContactsRespose extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6828503237085900817L;

	private Integer startCount;
	private Integer pageSize;
	private Integer totalCount;
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

	public void setList(List<Entity> list2) {
		this.list = list2;
	}

}
