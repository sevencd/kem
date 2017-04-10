package cn.ilanhai.kem.domain.extension;

import java.util.Date;

import cn.ilanhai.framework.app.domain.AbstractEntity;
import cn.ilanhai.kem.domain.enums.ExtensionOrderType;

/**
 * 查询推广请求entity
 * 
 * @author hy
 *
 */
public class SearchExtensionRequestEntity extends AbstractEntity {
	private static final long serialVersionUID = 1345591773431720654L;

	/**
	 * 专题名称 模糊查询
	 */
	private String extensionName;
	/**
	 * 推广状态 0:已发布，1：已查看 2：已禁用 如果为空 则为当前用户所有推广 如果为后台用户则不能为空
	 */
	private Integer extensionState;
	/**
	 * 推广类型 1：PC端；2：移动端
	 */
	private Integer extensionType;
	/**
	 * 可空，不为空时则筛选结果的创建时间将会大于等于该值
	 */
	private Date timeStart;
	/**
	 * 可空，不为空时则筛选结果的创建时间将会小于该值
	 */
	private Date timeEnd;
	/**
	 * 开始条数，不可空，正整数
	 */
	private Integer startCount;
	/**
	 * 取用条数，不可空，正整数
	 */
	private Integer pageSize;
	/**
	 * 作者
	 */
	private String userId;
	/**
	 * 推广ID
	 */
	private String extensionId;

	private ExtensionOrderType orderType;

	private String orderType_new;

	public ExtensionOrderType getOrderType() {
		return orderType;
	}

	public void setOrderType(ExtensionOrderType orderType) {
		this.orderType = orderType;
	}

	public String getExtensionName() {
		return extensionName;
	}

	public void setExtensionName(String extensionName) {
		this.extensionName = extensionName;
	}

	public Integer getExtensionState() {
		return extensionState;
	}

	public void setExtensionState(Integer extensionState) {
		this.extensionState = extensionState;
	}

	public Integer getExtensionType() {
		return extensionType;
	}

	public void setExtensionType(Integer extensionType) {
		this.extensionType = extensionType;
	}

	public Date getTimeStart() {
		return timeStart;
	}

	public void setTimeStart(Date timeStart) {
		this.timeStart = timeStart;
	}

	public Date getTimeEnd() {
		return timeEnd;
	}

	public void setTimeEnd(Date timeEnd) {
		this.timeEnd = timeEnd;
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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getExtensionId() {
		return extensionId;
	}

	public void setExtensionId(String extensionId) {
		this.extensionId = extensionId;
	}

	public void setOrderType(String orderType) {
		this.orderType = ExtensionOrderType.getEnum(orderType);
	}

	public String getOrderType_new() {
		return orderType_new;
	}

	public void setOrderType_new(String orderType_new) {
		this.orderType_new = orderType_new;
	}

}
