package cn.ilanhai.kem.domain.enums;

import cn.ilanhai.kem.common.IntegerEnum;

public enum UserInfoType implements IntegerEnum {
	PHONE(0), NAME(1), EMAIL(2),
	/**
	 * 有客到
	 */
	YKDUSER(3),
	/**
	 * 公司
	 */
	COMPANY(4),
	/**
	 * 职业
	 */
	JOB(5),
	/**
	 * 联系人
	 */
	CONTACT(6),
	/**
	 * 联系电话
	 */
	CONTACTPHONENO(7),
	/**
	 * 公司地址
	 */
	COMPANYADDRESS(8),
	/**
	 * 公司电话
	 */
	COMPANYPHONENO(9),
	/**
	 * 邮编
	 */
	ZIPCODE(10),
	/**
	 * 舟大师登录名称
	 */
	ZDSUSERNAME(11),
	/**
	 * 舟大师登录密码
	 */
	ZDSUSERPWD(12),
	/**
	 * 舟大师打码次数
	 */
	ZDSUSECODE(13);
	private Integer value;

	UserInfoType(Integer value) {
		this.value = value;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public UserInfoType valueOf(Integer value) {
		if (value == null)
			return null;
		switch (value) {
		case 0:
			return PHONE;
		case 1:
			return NAME;
		case 2:
			return EMAIL;
		case 3:
			return YKDUSER;
		default:
			return null;
		}
	}

	/**
	 * 获取该类型的类型名称
	 * 
	 * @param userType
	 * @return
	 */
	public static String valueOf(UserInfoType userInfoType) {
		if (userInfoType == null)
			return null;
		switch (userInfoType) {
		case PHONE:
			return "电话";
		case NAME:
			return "姓名";
		case EMAIL:
			return "邮件";
		case YKDUSER:
			return "有客到用户";
		default:
			return null;
		}
	}
}
