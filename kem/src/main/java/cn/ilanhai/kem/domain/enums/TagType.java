package cn.ilanhai.kem.domain.enums;

public enum TagType {

	/**
	 * 表单插件
	 */
	SYS(1),

	/**
	 * 活动插件
	 */
	USER(0);

	private final int value;

	private TagType(int value) {
		this.value = value;
	}

	public Integer getValue() {
		return value;
	}

	public static TagType getEnum(Integer value) {
		switch (value) {
		case 0:
			return USER;
		case 1:
			return SYS;
		}
		return null;
	}
}
