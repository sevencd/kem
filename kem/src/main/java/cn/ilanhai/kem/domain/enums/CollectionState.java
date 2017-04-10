package cn.ilanhai.kem.domain.enums;

public enum CollectionState {
	/**
	 * 收藏
	 */
	COLLECTION(1, "收藏"),
	/**
	 * 不收藏
	 */
	NOCOLLECTION(0, "不收藏");

	private Integer value;
	private String name;

	public Integer getValue() {
		return value;
	}

	public String getName() {
		return name;
	}

	CollectionState(Integer value, String name) {
		this.value = value;
		this.name = name;
	}
}
