package cn.ilanhai.kem.domain.enums;

import cn.ilanhai.kem.common.IntegerEnum;

public enum VoucherType implements IntegerEnum {
	PASSWORD(0);
	private int value;

	VoucherType(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public VoucherType valueOf(Integer value) {
		if (value == null)
			return null;
		switch (value) {
		case 0:
			return PASSWORD;
		default:
			return null;
		}
	}
}
