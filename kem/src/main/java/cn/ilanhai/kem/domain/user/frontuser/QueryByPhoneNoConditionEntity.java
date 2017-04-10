package cn.ilanhai.kem.domain.user.frontuser;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class QueryByPhoneNoConditionEntity extends AbstractEntity{

	private static final long serialVersionUID = 8666382655824953013L;
	
	private String phoneNo;

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	
}
