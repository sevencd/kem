package cn.ilanhai.kem.domain.enums;

public enum TrafficuserType {
	/**
	 * 表单插件
	 */
	FORMPLUGINUSER(1, "表单"),

	/**
	 * 活动插件
	 */
	ACTIVEPLUGINUSER(2, "活动插件");

	private final int value;
	private final String name;

	private TrafficuserType(int value, String name) {
		this.value = value;
		this.name = name;
	}

	public Integer getValue() {
		return value;
	}

	public String getName() {
		return name;
	}

	public static TrafficuserType getEnum(Integer value) {
		switch (value) {
		case 1:
			return FORMPLUGINUSER;
		case 2:
			return ACTIVEPLUGINUSER;
		}
		return null;
	}
}
