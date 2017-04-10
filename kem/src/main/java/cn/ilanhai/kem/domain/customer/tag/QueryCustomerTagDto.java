package cn.ilanhai.kem.domain.customer.tag;

import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * 查询客户标签Dto
 * 
 * @author dgj
 *
 */
public class QueryCustomerTagDto extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1181496553047673843L;
	/**
	 * 客户id
	 */
	private String customerId;

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

}
