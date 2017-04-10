package cn.ilanhai.kem.domain.contacts;

import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * 同步联系人返回
 * 
 * @author dgj
 *
 */
public class SynchronizeContractsResponse extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -720985648460747183L;

	/**
	 * 同步的数量
	 */
	private Integer synCount;
	/**
	 * 去重的数量
	 */
	private Integer removalCount;

	public Integer getSynCount() {
		return synCount;
	}

	public void setSynCount(Integer synCount) {
		this.synCount = synCount;
	}

	public Integer getRemovalCount() {
		return removalCount;
	}

	public void setRemovalCount(Integer removalCount) {
		this.removalCount = removalCount;
	}

}
