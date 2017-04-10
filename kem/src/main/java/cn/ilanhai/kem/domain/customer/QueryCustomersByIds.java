package cn.ilanhai.kem.domain.customer;

import java.util.List;

import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * 通过ids查询多个客户信息
 * 
 * @author dgj
 *
 */
public class QueryCustomersByIds extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5377719311686085346L;

	private List<String> customerIds;

	public List<String> getCustomerIds() {
		return customerIds;
	}

	public void setCustomerIds(List<String> customerIds) {
		this.customerIds = customerIds;
	}

}
