package cn.ilanhai.kem.domain.member.QueryCondition;

import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * 根据会员ID编号查询会员信息
 * @author Nature
 *
 */
public class GetMemberInfoByMemberIdQCondition extends AbstractEntity{

	private static final long serialVersionUID = -2227323425513963058L;
	
	private String memberId;

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
}
	
