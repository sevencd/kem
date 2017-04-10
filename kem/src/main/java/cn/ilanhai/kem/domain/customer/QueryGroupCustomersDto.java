package cn.ilanhai.kem.domain.customer;

import java.util.List;

import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * 通过群组id查询客户信息
 * 
 * @author dgj
 *
 */
public class QueryGroupCustomersDto extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -521994212216901965L;
	private List<String> groupIds;
	private String userId;

	public List<String> getGroupIds() {
		return groupIds;
	}

	public void setGroupIds(List<String> groupIds) {
		this.groupIds = groupIds;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
