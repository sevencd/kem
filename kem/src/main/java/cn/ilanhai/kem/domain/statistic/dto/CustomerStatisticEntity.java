package cn.ilanhai.kem.domain.statistic.dto;

import cn.ilanhai.framework.app.domain.AbstractEntity;
/**
 * 
 * 客户统计
 * @author csz
 *@date 2017-03-30
 */
public class CustomerStatisticEntity extends AbstractEntity {
	private static final long serialVersionUID = 1L;
	/**
	 * 客户类别
	 */
	private Integer type;
	/**
	 * 天数
	 */
	private Integer  days;
	/**
	 * 数量
	 */
	private Long count;
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getDays() {
		return days;
	}
	public void setDays(Integer days) {
		this.days = days;
	}
	public Long getCount() {
		return count;
	}
	public void setCount(Long count) {
		this.count = count;
	}
	
}
