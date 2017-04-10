package cn.ilanhai.kem.domain.member.QueryCondition;

import java.util.Date;

import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * 查询新过期的会员
 * @author Nature
 *
 */
public class QueryNewExpiredMemberQCondition extends AbstractEntity{

	private static final long serialVersionUID = 8383319650767676622L;
	/**
	 * 截止时间
	 */
	private Date cutoffTime;

	public Date getCutoffTime() {
		return cutoffTime;
	}

	public void setCutoffTime(Date cutoffTime) {
		this.cutoffTime = cutoffTime;
	}
	
}
