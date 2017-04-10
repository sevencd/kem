package cn.ilanhai.kem.domain.member;

import java.util.Date;

import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * 会员信息
 * 
 * @author Nature
 *
 */
public class MemberEntity extends AbstractEntity {

	public static final int ENABLE = 1;
	public static final int DISABLE = 0;

	private static final long serialVersionUID = -3390130179969055349L;

	// 会员ID
	private String memberId;
	// 用户ID
	private String userId;
	// 开始时间
	private Date starttime;
	// 结束时间
	private Date endtime;
	// 套餐id
	private Integer packageServiceId;
	// 会员状态
	private int status;
	// 购买套餐时的价格
	private double packageServicePrice;
	
	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getStarttime() {
		return starttime;
	}

	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}

	public Date getEndtime() {
		return endtime;
	}

	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Integer getPackageServiceId() {
		return packageServiceId;
	}

	public void setPackageServiceId(Integer packageServiceId) {
		this.packageServiceId = packageServiceId;
	}

	public double getPackageServicePrice() {
		return packageServicePrice;
	}

	public void setPackageServicePrice(double packageServicePrice) {
		this.packageServicePrice = packageServicePrice;
	}
}
