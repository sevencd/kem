package cn.ilanhai.kem.domain.enums;

public enum ManuscriptState {
	hasSave(0),
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

	private Integer value;

	ManuscriptState(Integer value) {
		this.value = value;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public static ManuscriptState getEnum(Integer value) {
		for (ManuscriptState state : values()) {
			if (state.getValue().equals(value)) {
				return state;
			}
		}
		return null;
	}
}
