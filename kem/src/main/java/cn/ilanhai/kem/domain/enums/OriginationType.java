package cn.ilanhai.kem.domain.enums;

/**
 * 客户来源
 * @author dgj
 *
 */
public enum OriginationType {
	/**
	 * 快捷表单
	 */
	SHORTCUTFROM("0"),
	/**
	 * 九宫格
	 */
	NINEGRID("1"),

	/**
	 * 刮刮乐
	 */
	SCRATCH("2"),
	/**
	 * 大转盘
	 */
	WHELL("3"),
	/**
	 * 砸金蛋
	 */
	GOLDEGG("4"),

	/**
	 * 拆红包
	 */
	REDENVELOPE("5"),
	/**
	 * 手机号
	 */
	PHONENO("phoneNo"),
	/**
	 * 邮箱
	 */
	EMAIL("email"),
	/**
	 * QQ号
	 */
	QQNUMBER("qq"),
	/**
	 * 微信号
	 */
	WECHAT("wechat")

	;
	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	OriginationType(String value) {
		this.value = value;
	}

}
