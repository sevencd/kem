package cn.ilanhai.kem.domain.member.QueryCondition;

import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * 根据用户ID查询会员信息
 * @author Nature
 *
 */
public class GetMemberInfoByUserIdQCondition extends AbstractEntity{

	private static final long serialVersionUID = -6072820844006745197L;
	
	private String userId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
}
