package cn.ilanhai.kem.domain.member.QueryCondition;

import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * 查询用户状态条件对象
 * @author Nature
 *
 */
public class MemberStatusQCondition extends AbstractEntity{

	private static final long serialVersionUID = 195925312100163083L;
	
	private String userId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
}
