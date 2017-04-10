package cn.ilanhai.kem.domain.paymentservice.dto;

import java.util.Date;
import java.util.List;

import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * 订单查询请求
 * 
 * @author Nature
 *
 */
public class SearchOrderRequestDto extends AbstractEntity {

	private static final long serialVersionUID = 303738648165972779L;

	// 用户id
	private String userId;
	// 下单时间，起始时间
	private Date createtimeStart;
	// 下单时间，截止时间
	private Date createtimeEnd;
	// 关键词
	private String keyword;
	// 支付方式
	private String payway;
	// 起始条数
	private Integer startCount;
	// 获取条数
	private Integer pageSize;

	private Integer payStatus;

	public Integer getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

	public String getPayway() {
		return payway;
	}

	public void setPayway(String payway) {
		this.payway = payway;
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

}
