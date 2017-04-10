package cn.ilanhai.kem.domain.enums;

/**
 * 模块类型
 * 
 * @author hy
 *
 */
public enum ModelType {
	/**
	 * 专题
	 */
	SPECIAL(1, "专题"),
	/**
	 * 推广
	 */
	EXTENSION(2, "推广"),
	/**
	 * 模版
	 */
	TEMPLATE(3, "模版");

	private Integer value;
	private String name;

	public Integer getValue() {
		return value;
	}

	public String getName() {
		return name;
	}

	ModelType(Integer value, String name) {
		this.value = value;
		this.name = name;
	}
}
