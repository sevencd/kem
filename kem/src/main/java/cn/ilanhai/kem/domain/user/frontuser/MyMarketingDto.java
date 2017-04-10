package cn.ilanhai.kem.domain.user.frontuser;

import cn.ilanhai.framework.app.domain.AbstractEntity;
import cn.ilanhai.kem.domain.enums.MemberType;
/**
 * 会员列表请求dto，我的内容营销
 * @author csz
 * @time 2017-03-22 10:09
 */
import cn.ilanhai.kem.domain.member.UserResourcesEntity;
public class MyMarketingDto extends AbstractEntity {

	private static final long serialVersionUID = -4387252467855093641L;
	/**
	 * 账号等级
	 * {@link MemberType}
	 */
	private Integer memberType;
	/**
	 * 我的邮件营销
	 */
	private UserResourcesEntity emailResouce;
	/**
	 * 我的短信营销
	 */
	private UserResourcesEntity smsResouce;
	/**
	 * 我的微信营销
	 */
	private UserResourcesEntity weChatResouce;
	/**
	 * 我的b2b营销
	 */
	private UserResourcesEntity b2bResouce;
	/**
	 * 我的采集数据营销
	 */
	private UserResourcesEntity cralwerResouce;
	/**
	 * 我的营销内容发布
	 */
	private UserResourcesEntity publishResouce;
	/**
	 * 子账号
	 */
	private UserResourcesEntity subAccount;
	/**
	 * 构造函数
	 * @param emailResouce
	 * @param smsResouce
	 * @param weChatResouce
	 * @param b2bResouce
	 * @param cralwerResouce
	 * @param publishResouce
	 * @param subAccount
	 */
	public MyMarketingDto(UserResourcesEntity emailResouce, UserResourcesEntity smsResouce,
			UserResourcesEntity weChatResouce, UserResourcesEntity b2bResouce, UserResourcesEntity cralwerResouce,
			UserResourcesEntity publishResouce, UserResourcesEntity subAccount) {
		super();
		this.emailResouce = emailResouce;
		this.smsResouce = smsResouce;
		this.weChatResouce = weChatResouce;
		this.b2bResouce = b2bResouce;
		this.cralwerResouce = cralwerResouce;
		this.publishResouce = publishResouce;
		this.subAccount = subAccount;
	}
	public UserResourcesEntity getEmailResouce() {
		return emailResouce;
	}
	public void setEmailResouce(UserResourcesEntity emailResouce) {
		this.emailResouce = emailResouce;
	}
	public UserResourcesEntity getSmsResouce() {
		return smsResouce;
	}
	public void setSmsResouce(UserResourcesEntity smsResouce) {
		this.smsResouce = smsResouce;
	}
	public UserResourcesEntity getWeChatResouce() {
		return weChatResouce;
	}
	public void setWeChatResouce(UserResourcesEntity weChatResouce) {
		this.weChatResouce = weChatResouce;
	}
	public UserResourcesEntity getCralwerResouce() {
		return cralwerResouce;
	}
	public void setCralwerResouce(UserResourcesEntity cralwerResouce) {
		this.cralwerResouce = cralwerResouce;
	}
	public UserResourcesEntity getB2bResouce() {
		return b2bResouce;
	}
	public void setB2bResouce(UserResourcesEntity b2bResouce) {
		this.b2bResouce = b2bResouce;
	}
	public UserResourcesEntity getPublishResouce() {
		return publishResouce;
	}
	public void setPublishResouce(UserResourcesEntity publishResouce) {
		this.publishResouce = publishResouce;
	}
	public UserResourcesEntity getSubAccount() {
		return subAccount;
	}
	public void setSubAccount(UserResourcesEntity subAccount) {
		this.subAccount = subAccount;
	}
	public Integer getMemberType() {
		return memberType;
	}
	public void setMemberType(Integer memberType) {
		this.memberType = memberType;
	}
}
