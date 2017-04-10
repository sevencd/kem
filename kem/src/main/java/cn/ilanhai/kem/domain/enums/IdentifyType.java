package cn.ilanhai.kem.domain.enums;

import cn.ilanhai.kem.common.IntegerEnum;

public enum IdentifyType implements IntegerEnum {
	PHONENO(0);
	private int value;

	IdentifyType(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public IdentifyType valueOf(Integer value) {
		if (value == null)
			return null;
		switch (value) {
		case 0:
			return PHONENO;
		default:
			return null;
		}
	}
}
