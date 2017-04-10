package cn.ilanhai.kem.domain.statistic.dto;

import java.util.List;

import cn.ilanhai.framework.app.domain.AbstractEntity;
import cn.ilanhai.framework.app.domain.Entity;
/**
 * 
 * 客户统计
 * @author csz
 *@date 2017-03-22
 */
public class CustomerBoardResponseDto extends AbstractEntity{
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
	 * 是否主账号是否子账号
	 * {@link}UserRelationType
	 */
	private Integer relationType;
	/**
	 * 按1天统计当前账号数据
	 */
	private CustomerStatisticDto customerForDay1; 
	/**
	 * 按7天统计当前账号数据
	 */
	private CustomerStatisticDto customerForDay7; 
	/**
	 * 按30天统计当前账号数据
	 */
	private CustomerStatisticDto customerForDay30; 

	/**
	 * 子账号营销数据
	 */
	private List<Entity> list;
	public List<Entity> getList() {
		return list;
	}
	public void setList(List<Entity> list) {
		this.list = list;
	}
	public Integer getRelationType() {
		return relationType;
	}
	public void setRelationType(Integer relationType) {
		this.relationType = relationType;
	}
	public CustomerStatisticDto getCustomerForDay1() {
		return customerForDay1;
	}
	public void setCustomerForDay1(CustomerStatisticDto customerForDay1) {
		this.customerForDay1 = customerForDay1;
	}
	public CustomerStatisticDto getCustomerForDay7() {
		return customerForDay7;
	}
	public void setCustomerForDay7(CustomerStatisticDto customerForDay7) {
		this.customerForDay7 = customerForDay7;
	}
	public CustomerStatisticDto getCustomerForDay30() {
		return customerForDay30;
	}
	public void setCustomerForDay30(CustomerStatisticDto customerForDay30) {
		this.customerForDay30 = customerForDay30;
	}
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
	
}
