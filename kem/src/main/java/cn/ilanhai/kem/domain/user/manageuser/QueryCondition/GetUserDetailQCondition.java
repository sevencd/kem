package cn.ilanhai.kem.domain.user.manageuser.QueryCondition;

import cn.ilanhai.framework.app.domain.AbstractEntity;
import cn.ilanhai.kem.domain.enums.MemberType;
import cn.ilanhai.kem.domain.enums.UserInfoType;

/**
 * 查询用户详情数据查询条件
 * @author Nature
 *
 */
public class GetUserDetailQCondition extends AbstractEntity{

	private static final long serialVersionUID = 2675702134590828412L;

	/**
	 * 用户ID
	 */
	private String userId;
	/**
	 * 用户名称 info_type
	 */
	private Integer company=UserInfoType.COMPANY.getValue();
	/**
	 * 用户电话info_type
	 */
	private Integer userPhone=UserInfoType.PHONE.getValue();
	/**
	 * 联系人info_type
	 */
	private Integer contact=UserInfoType.CONTACT.getValue();
	/**
	 * 职位info_type
	 */
	private Integer job=UserInfoType.JOB.getValue();
	/**
	 * 联系人电话info_type
	 */
	private Integer contactPhoneNo=UserInfoType.CONTACTPHONENO.getValue();
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
}
