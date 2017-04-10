package cn.ilanhai.kem.domain.contacts.group;

import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * 查询群组联系人请求
 * 
 * @author dgj
 *
 */
public class SearchGroupContactsRequest extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8718341964084774506L;

	private String groupId;
	private String keyword;
	private Integer startCount;
	private Integer pageSize;
	private String[] groupIds;

	public String[] getGroupIds() {
		return groupIds;
	}

	public void setGroupIds(String[] groupIds) {
		this.groupIds = groupIds;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
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
