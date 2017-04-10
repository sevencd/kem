package cn.ilanhai.kem.domain.paymentservice.QueryCondition;

import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * 根据id查询套餐内容
 * @author Nature
 *
 */
public class GetPackageServiceByIdQCondition extends AbstractEntity{

	private static final long serialVersionUID = 4555199035015729033L;
	//服务套餐ID
	private int packageServiceId;

	public int getPackageServiceId() {
		return packageServiceId;
	}

	public void setPackageServiceId(int packageServiceId) {
		this.packageServiceId = packageServiceId;
	}
	
}
