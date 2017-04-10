package cn.ilanhai.kem.domain.tag;

import java.util.Date;

import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * 标签
 * 
 * @author hy
 *
 */
public class ResponseTagEntity extends AbstractEntity {
	private static final long serialVersionUID = 1907849965714404812L;
	/**
	 * 标签id
	 */
	private Integer tagId;

	/**
	 * 标签名称
	 */
	private String tagName;

	/**
	 * 
	 */
	private Integer orderNum;
	/**
	 * 引用次数
	 */
	private Integer quoteNum;
	/**
	 * 是否加精
	 */
	private Boolean isSelection;

	private Date createtime;

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Integer getTagId() {
		return tagId;
	}

	public void setTagId(Integer tagId) {
		this.tagId = tagId;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public Integer getQuoteNum() {
		return quoteNum;
	}

	public void setQuoteNum(Integer quoteNum) {
		this.quoteNum = quoteNum;
	}

	public Boolean getIsSelection() {
		return isSelection;
	}

	public void setIsSelection(Boolean isSelection) {
		this.isSelection = isSelection;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
}
