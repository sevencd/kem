package cn.ilanhai.kem.domain.user.trafficuser;

import java.util.Iterator;

import cn.ilanhai.framework.app.domain.AbstractEntity;
import cn.ilanhai.framework.app.domain.Entity;

public class QueryTrafficUserResponseDto extends AbstractEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5701720448717075648L;

	/**
	 * 查询信息
	 */
	private String searchInfo;
	/**
	 * 推广id
	 */
	private String extensionId;

	/**
	 * 数据集
	 */
	private Iterator<Entity> list;
	/**
	 * 开始条数，不可空，正整数
	 */
	private Integer startCount;
	/**
	 * 取用条数，不可空，正整数
	 */
	private Integer pageSize;
	/**
	 * 总数
	 */
	private Integer totalCount;

	public String getSearchInfo() {
		return searchInfo;
	}

	public void setSearchInfo(String searchInfo) {
		this.searchInfo = searchInfo;
	}

	public String getExtensionId() {
		return extensionId;
	}

	public void setExtensionId(String extensionId) {
		this.extensionId = extensionId;
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

	public Iterator<Entity> getList() {
		return list;
	}

	public void setList(Iterator<Entity> list) {
		this.list = list;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
}
