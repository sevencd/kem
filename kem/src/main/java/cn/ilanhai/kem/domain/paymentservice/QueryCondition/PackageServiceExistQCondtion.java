package cn.ilanhai.kem.domain.paymentservice.QueryCondition;

import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * 判断服务套餐是否存在的请求条件
 * @author Nature
 *
 */
public class PackageServiceExistQCondtion extends AbstractEntity{

	private static final long serialVersionUID = -4640763600433633277L;

	private int packageServiceId;

	public int getPackageServiceId() {
		return packageServiceId;
	}

	public void setPackageServiceId(int packageServiceId) {
		this.packageServiceId = packageServiceId;
	}
	
}
