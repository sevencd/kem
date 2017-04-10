package cn.ilanhai.kem.domain.enums;

public enum ManuscriptType {
	/**
	 * 模版
	 */
	TEMPLATE(1),
	/**
	 * 专题
	 */
	SPECIAL(2),
	/**
	 * 推广
	 */
	EXTENSION(3),
	/**
	 * 案例
	 */
	EXCELLENTCASE(4),
	/**
	 * 未登录 稿件
	 */
	DEF(5);
	private Integer value;

	ManuscriptType(Integer value) {
		this.value = value;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public static ManuscriptType getEnum(Integer value) {
		switch (value) {
		case 1:
			return TEMPLATE;
		case 2:
			return SPECIAL;
		case 3:
			return EXTENSION;
		case 4:
			return EXCELLENTCASE;
		}
		return null;
	}
}
