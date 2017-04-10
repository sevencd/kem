package cn.ilanhai.kem.domain.customer.tag;

import java.util.Date;

import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * 客户标签实体
 * 
 * @author dgj
 *
 */
public class CustomerTagEntity extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5089336585473259040L;

	/**
	 * 客户标签id ,保存或修改都不用填
	 */
	private Integer tagId;
	/**
	 * 客户id ，必填
	 */
	private String customerId;
	/**
	 * 客户从专题标签带过来的 ，可以为空
	 */
	private String SpecialTag;
	/**
	 * 客户自定义标签 ，可以为空
	 */
	private String customerTag;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 修改时间
	 */
	private Date updateTime;

	public Integer getTagId() {
		return tagId;
	}

	public void setTagId(Integer tagId) {
		this.tagId = tagId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getSpecialTag() {
		return SpecialTag;
	}

	public void setSpecialTag(String specialTag) {
		SpecialTag = specialTag;
	}

	public String getCustomerTag() {
		return customerTag;
	}

	public void setCustomerTag(String customerTag) {
		this.customerTag = customerTag;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}
