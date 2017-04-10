package cn.ilanhai.kem.domain.enums;

public enum ExtensionState {
	/**
	 * 已保存
	 */
	HASLOOKFOR(1, "已查看"),
	/**
	 * 已发布
	 */
	HASPUBLISH(0, "已发布"),
	/**
	 * 已禁用
	 */
	HASDISABLE(2, "已禁用");

	private Integer value;
	private String name;

	public Integer getValue() {
		return value;
	}

	public String getName() {
		return name;
	}

	ExtensionState(Integer value, String name) {
		this.value = value;
		this.name = name;
	}
}
