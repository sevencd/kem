package cn.ilanhai.kem.domain.enums;

public enum ResourcesType {
	/**
	 * 短信
	 */
	SMS("sms"),
	/**
	 * 邮件
	 */
	EMAIL("email"),
	/**
	 * 子账户
	 */
	SUBUSER("subuser"),
	/**
	 * B2B
	 */
	B2B("b2b"),
	/**
	 * 客户采集
	 */
	CUSTOMER("customer"),
	/**
	 * 发布次数
	 */
	PUBLISH("publish"),
	/**
	 * 客户导入
	 */
	CUSTOMERIMP("customerImp");
	private String value;

	ResourcesType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
