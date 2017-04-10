package cn.ilanhai.kem.domain.customer.tag;

import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * 加载客户信息dto
 * 
 * @author dgj
 *
 */
public class QueryCustomerDto extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2560968498759912280L;
	/**
	 * 客户id
	 */
	private String customerId;
	/**
	 * 用户id
	 */
	private String userId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

}
