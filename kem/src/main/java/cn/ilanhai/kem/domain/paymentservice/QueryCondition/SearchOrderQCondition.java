package cn.ilanhai.kem.domain.paymentservice.QueryCondition;

import java.util.Date;
import java.util.List;

import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * 查询订单条件实体
 * 
 * @author dgj
 *
 */
public class SearchOrderQCondition extends AbstractEntity {

	private static final long serialVersionUID = 4196030415679891065L;

	// 用户ID，不可为空
	private List<String> userId;
	// 下单时间，起始时间,可为空
	private Date createtimeStart;
	// 下单时间，结束时间，可空
	private Date createtimeEnd;
	// 根据消费内容做模糊筛选，可空
	private String keyword;
	// 0 支付宝 1 微信 2 未知，可空，空时为全部
	private List<String> payway;
	// 支付状态：0待支付，1已支付
	private Integer payStatus;
	// 起始条数
	private Integer startCount;
	// 获取条数
	private Integer pageSize;

	public List<String> getUserId() {
		return userId;
	}

	public void setUserId(List<String> userId) {
		this.userId = userId;
	}

	public Integer getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}

	public Date getCreatetimeStart() {
		return createtimeStart;
	}

	public void setCreatetimeStart(Date createtimeStart) {
		this.createtimeStart = createtimeStart;
	}

	public Date getCreatetimeEnd() {
		return createtimeEnd;
	}

	public void setCreatetimeEnd(Date createtimeEnd) {
		this.createtimeEnd = createtimeEnd;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public Integer getStartCount() {
		return startCount;
	}

	public List<String> getPayway() {
		return payway;
	}

	public void setPayway(List<String> payway) {
		this.payway = payway;
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

}
