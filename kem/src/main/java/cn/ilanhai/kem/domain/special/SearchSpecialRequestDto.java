package cn.ilanhai.kem.domain.special;

import java.util.Date;

import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * 查询专题请求Dto
 * 
 * @author hy
 *
 */
public class SearchSpecialRequestDto extends AbstractEntity {
	private static final long serialVersionUID = 1345591773431720654L;
	/**
	 * 标签名称，可空，空代表无此项限制，完全匹配，不超过5个字
	 */
	private String tagName;
	/**
	 * 专题名称 模糊查询
	 */
	private String specialName;
	/**
	 * 可空，为空则查询所有模板类型，模板类型：1：PC端；2：移动端
	 */
	private Integer specialType;
	/**
	 * 可空，为空则查询所有状态，1:已保存，2:已发布，3:已禁用
	 */
	private Integer specialState;
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

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public String getSpecialName() {
		return specialName;
	}

	public void setSpecialName(String specialName) {
		this.specialName = specialName;
	}

	public Integer getSpecialType() {
		return specialType;
	}

	public void setSpecialType(Integer specialType) {
		this.specialType = specialType;
	}

	public Integer getSpecialState() {
		return specialState;
	}

	public void setSpecialState(Integer specialState) {
		this.specialState = specialState;
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
}
