package cn.ilanhai.kem.domain.special;

import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * 查询使用过的模版记录
 * 
 * @author hy
 *
 */
public class SearchCollectionEntity extends AbstractEntity {
	private static final long serialVersionUID = -4590737413877559069L;
	private Integer startCount;
	private Integer pageSize;
	/**
	 * 使用者ID
	 */
	private Integer userId;

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

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
}
