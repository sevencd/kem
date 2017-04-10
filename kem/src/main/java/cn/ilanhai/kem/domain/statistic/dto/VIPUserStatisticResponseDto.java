package cn.ilanhai.kem.domain.statistic.dto;

import java.util.List;

import cn.ilanhai.framework.app.domain.AbstractEntity;
import cn.ilanhai.framework.app.domain.Entity;
/**
 * 
 * 会员统计数据总览数据明细数据,客户端响应数据
 * @author csz
 *
 */
public class VIPUserStatisticResponseDto extends AbstractEntity {
	private static final long serialVersionUID = 1L;
	/**
	 * 开通人数
	 */
	private long vipAmount;
	/**
	 * 注册人数
	 */
	private long registerAmount;
	/**
	 * 开通率
	 */
	private String rate;
	/**
	 * 用户信息明细
	 */
	private List<Entity> detail;
	private Integer startCount;//开始条数
	private Integer pageSize;//页面大小
	private Integer totalCount;//总记录数
	public long getVipAmount() {
		return vipAmount;
	}
	public void setVipAmount(long vipAmount) {
		this.vipAmount = vipAmount;
	}
	public long getRegisterAmount() {
		return registerAmount;
	}
	public void setRegisterAmount(long registerAmount) {
		this.registerAmount = registerAmount;
	}
	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
	public Integer getStartCount() {
		return startCount;
	}
	public void setStartCount(Integer startCount) {
		this.startCount = startCount;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
	public List<Entity> getDetail() {
		return detail;
	}
	public void setDetail(List<Entity> detail) {
		this.detail = detail;
	}

}
