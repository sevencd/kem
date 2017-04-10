package cn.ilanhai.kem.domain.statistic.dto;

import cn.ilanhai.framework.app.domain.AbstractEntity;
/**
 * 
 * 客户统计
 * @author csz
 *@date 2017-03-22
 */
public class CustomerStatisticDto extends AbstractEntity {
	private static final long serialVersionUID = 1L;
	/**
	 * 新增潜客数量
	 */
	private Long newComeNum;
	/**
	 * 新增有意向客户数量
	 */
	private Long  intentNum;
	/**
	 * 成交客户数量
	 */
	private Long DealNum;
	/**
	 * 客户成交率
	 */
	//private String rate;
	/**
	 *账号手机号
	 */
	private String phoneNo;
	
	
	public Long getNewComeNum() {
		return newComeNum;
	}
	public void setNewComeNum(Long newComeNum) {
		this.newComeNum = newComeNum;
	}
	public Long getIntentNum() {
		return intentNum;
	}
	public void setIntentNum(Long intentNum) {
		this.intentNum = intentNum;
	}
	public Long getDealNum() {
		return DealNum;
	}
	public void setDealNum(Long dealNum) {
		DealNum = dealNum;
	}
	/*public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}*/
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	
}
