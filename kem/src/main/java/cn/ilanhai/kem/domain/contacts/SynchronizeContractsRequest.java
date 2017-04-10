package cn.ilanhai.kem.domain.contacts;

import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * 同步联系人请求
 * 
 * @author dgj
 *
 */
public class SynchronizeContractsRequest extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8227691650333094146L;

	private Integer type;
	private String userId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

}
