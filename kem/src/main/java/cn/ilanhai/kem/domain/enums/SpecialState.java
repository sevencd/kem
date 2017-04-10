package cn.ilanhai.kem.domain.enums;

public enum SpecialState {
	/**
	 * 已保存
	 */
	HASCREATE(0, "已创建"),
	/**
	 * 已保存
	 */
	HASSAVE(1, "已保存"),
	/**
	 * 已发布
	 */
	HASPUBLISH(2, "已发布"),
	/**
	 * 已禁用
	 */
	HASDISABLE(3, "已禁用");

	private Integer value;
	private String name;

	public Integer getValue() {
		return value;
	}

	public String getName() {
		return name;
	}

	SpecialState(Integer value, String name) {
		this.value = value;
		this.name = name;
	}
}
