package cn.ilanhai.kem.domain.enums;

/**
 * 模板状态
 * 
 * @author he
 *
 */
public enum TemplateState {
	/**
	 * 未提交
	 */
	UNSUBMITTED(1),

	/**
	 * 审核中
	 */
	VERIFY(2),

	/**
	 * 已上架
	 */
	ADDED(3),

	/**
	 * 已退回
	 */
	BOUNCED(4),

	/**
	 * 已下架
	 */
	SHELF(5);
	private final Integer value;

	private TemplateState(int value) {
		this.value = value;
	}

	public static TemplateState valueOf(Integer value) {
		if (value == null)
			return null;
		switch (value) {
		case 1:
			return UNSUBMITTED;
		case 2:
			return VERIFY;
		case 3:
			return ADDED;
		case 4:
			return BOUNCED;
		case 5:
			return SHELF;
		default:
			return null;
		}
	}

	public Integer getValue() {
		return value;
	}

}
