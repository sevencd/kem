package cn.ilanhai.kem.domain.member;

import java.util.Date;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class UserResourcesEntity extends AbstractEntity {
	private static final long serialVersionUID = -5599410608993138506L;

	private String userId;
	/**
	 * 服务id
	 */
	private int serviceId;
	/**
	 * 套餐中该服务的次数
	 */
	private int packageServiceTimes;
	/**
	 * 套餐中该服务的总次数
	 */
	private int packageServiceTotalTimes;
	/**
	 * 购买的该服务次数
	 */
	private int serviceTimes;
	/**
	 * 购买的该服务总次数
	 */
	private int serviceTotalTimes;
	/**
	 * 购买的该服务的剩余次数
	 */
	private int remainingNum;
	// 开始时间 默认当前时间
	private Date starttime;
	// 结束时间 默认当前时间
	private Date endtime;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getServiceId() {
		return serviceId;
	}

	public void setServiceId(int serviceId) {
		this.serviceId = serviceId;
	}

	public int getPackageServiceTimes() {
		return packageServiceTimes;
	}

	public void setPackageServiceTimes(int packageServiceTimes) {
		this.packageServiceTimes = packageServiceTimes;
	}

	public int getPackageServiceTotalTimes() {
		return packageServiceTotalTimes;
	}

	public void setPackageServiceTotalTimes(int packageServiceTotalTimes) {
		this.packageServiceTotalTimes = packageServiceTotalTimes;
	}

	public int getServiceTimes() {
		return serviceTimes;
	}

	public void setServiceTimes(int serviceTimes) {
		this.serviceTimes = serviceTimes;
	}

	public int getServiceTotalTimes() {
		return serviceTotalTimes;
	}

	public void setServiceTotalTimes(int serviceTotalTimes) {
		this.serviceTotalTimes = serviceTotalTimes;
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

	public int getRemainingNum() {
		return remainingNum;
	}

	public void setRemainingNum(int remainingNum) {
		this.remainingNum = remainingNum;
	}

}
