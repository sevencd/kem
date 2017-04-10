package cn.ilanhai.kem.domain.statistic.dto;

import java.util.List;

import cn.ilanhai.framework.app.domain.AbstractEntity;
import cn.ilanhai.kem.domain.customer.CustomerGroupingEnum;
import cn.ilanhai.kem.domain.customer.CustomerInfoEntity;
import cn.ilanhai.kem.domain.enums.UserStatus;

/**
 * 客户统计
 * 
 * @author csz
 * @date 2017-03-23
 */
public class CustomerStatisticRequestDto extends AbstractEntity {
	private static final long serialVersionUID = 1L;
	/**
	 * 客户类型的key
	 */
	private String customerKey = CustomerInfoEntity.KEY_TYPE;
	/**
	 * 客户类别
	 * 
	 * @see CustomerGroupingEnum
	 */
	private String type;
	/**
	 * 是否主账号是否子账号
	 * {@link}UserRelationType
	 */
	private Integer relationType;
	/**
	 * 当前账号的fatherUserId
	 */
	private String fatherUserId;
	/**
	 * 用户id
	 */
	private String userId;
	/**
	 * 统计天数
	 */
	private Integer days;
	/**
	 * 记录状态
	 * @return
	 */
	private Integer state=UserStatus.ENABLE.getValue();
	/**
	 * 手机号
	 */
	private String phoneNo;
	public String getCustomerKey() {
		return customerKey;
	}

	public void setCustomerKey(String customerKey) {
		this.customerKey = customerKey;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getDays() {
		return days;
	}

	public void setDays(Integer days) {
		this.days = days;
	}

	public Integer getRelationType() {
		return relationType;
	}

	public void setRelationType(Integer relationType) {
		this.relationType = relationType;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getFatherUserId() {
		return fatherUserId;
	}

	public void setFatherUserId(String fatherUserId) {
		this.fatherUserId = fatherUserId;
	}


}
