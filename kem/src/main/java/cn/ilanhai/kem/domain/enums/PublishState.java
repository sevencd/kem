package cn.ilanhai.kem.domain.enums;

public enum PublishState {
	/**
	 * 不可发布
	 */
	CANNOTPUBLISH(0),
	/**
	 * 可发布
	 */
	CANPUBLISH(1);
	private Integer value;

	public Integer getValue() {
		return value;
	}

	private PublishState(Integer value) {
		this.value = value;
	}

}
