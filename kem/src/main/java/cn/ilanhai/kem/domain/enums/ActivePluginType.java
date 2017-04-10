package cn.ilanhai.kem.domain.enums;

import cn.ilanhai.kem.common.IntegerEnum;

public enum ActivePluginType implements IntegerEnum {
	/**
	 * 九宫格
	 */
	NINEGRID(1),

	/**
	 * 刮刮乐
	 */
	SCRATCH(2),
	/**
	 * 大转盘
	 */
	WHELL(3),
	/**
	 * 砸金蛋
	 */
	GOLDEGG(4),

	/**
	 * 拆红包
	 */
	REDENVELOPE(5);

	private final int value;

	private ActivePluginType(int value) {
		this.value = value;
	}

	public Integer getValue() {
		return value;
	}

	public static ActivePluginType getEnum(Integer value) {
		if (value == null)
			return null;
		switch (value) {
		case 1:
			return NINEGRID;
		case 2:
			return SCRATCH;
		case 3:
			return WHELL;
		case 4:
			return GOLDEGG;
		case 5:
			return REDENVELOPE;
		}
		return null;
	}

	public static String getName(Integer value) {
		if (value == null)
			return null;
		switch (value) {
		case 1:
			return "九宫格";
		case 2:
			return "刮刮卡";
		case 3:
			return "大转盘";
		case 4:
			return "砸金蛋";
		case 5:
			return "拆红包";
		}
		return null;
	}

	public ActivePluginType valueOf(Integer value) {
		return getEnum(value);
	}
}
