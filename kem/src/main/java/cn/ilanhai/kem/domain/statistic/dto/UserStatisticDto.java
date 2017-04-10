package cn.ilanhai.kem.domain.statistic.dto;

import java.sql.Timestamp;

import cn.ilanhai.kem.domain.user.frontuser.UserInfoDtoResponse;

/**
 * 会员用户统计明细信息
 * 
 * @author csz
 *
 */
public class UserStatisticDto extends UserInfoDtoResponse{
	private static final long serialVersionUID = 1L;
	/**
	 * 开通时间
	 */
	private Timestamp createTime;
	/**
	 * 开通时长
	 */
	private String duration;
	/**
	 * 支付方式
	 */
	private String payWay;
	/**
	 * 支付金额
	 */
	private String cash;
	/**
	 * 购买数量
	 */
	private long buyNum;
	
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getPayWay() {
		return payWay;
	}
	public void setPayWay(String payWay) {
		this.payWay = payWay;
	}
	public String getCash() {
		return cash;
	}
	public void setCash(String cash) {
		this.cash = cash;
	}
	public long getBuyNum() {
		return buyNum;
	}
	public void setBuyNum(long buyNum) {
		this.buyNum = buyNum;
	}
}
