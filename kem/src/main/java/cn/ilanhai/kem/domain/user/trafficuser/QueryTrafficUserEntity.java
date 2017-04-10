package cn.ilanhai.kem.domain.user.trafficuser;

import cn.ilanhai.framework.app.domain.AbstractEntity;
import cn.ilanhai.kem.domain.enums.TrafficuserType;

public class QueryTrafficUserEntity extends AbstractEntity {
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
	 * 客户来源
	 */
	private Integer trafficuserType;

	/**
	 * 开始条数，不可空，正整数
	 */
	private Integer startCount;
	/**
	 * 取用条数，不可空，正整数
	 */
	private Integer pageSize;

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

	public Integer getTrafficuserType() {
		return trafficuserType;
	}

	public void setTrafficuserType(Integer trafficuserType) {
		this.trafficuserType = trafficuserType;
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
}
