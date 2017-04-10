package cn.ilanhai.kem.domain.customer.dto;

import java.util.List;

import cn.ilanhai.framework.app.domain.AbstractEntity;
import cn.ilanhai.kem.domain.customer.CustomerInfoEntity;
import cn.ilanhai.kem.domain.customer.CustomerMainEntity;

public class ImplCustomerDto extends AbstractEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -146527335988126653L;
	private List<CustomerMainEntity> mains;
	private List<CustomerInfoEntity> infos;
	public List<CustomerMainEntity> getMains() {
		return mains;
	}

	public void setMains(List<CustomerMainEntity> mains) {
		this.mains = mains;
	}

	public List<CustomerInfoEntity> getInfos() {
		return infos;
	}

	public void setInfos(List<CustomerInfoEntity> infos) {
		this.infos = infos;
	}
}
