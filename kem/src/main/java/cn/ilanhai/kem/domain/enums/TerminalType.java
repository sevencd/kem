package cn.ilanhai.kem.domain.enums;

public enum TerminalType {
	PC(1), WAP(2);
	private Integer value;

	TerminalType(Integer value) {
		this.value = value;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public static TerminalType getEnum(Integer value) {
		switch (value) {
		case 1:
			return PC;
		case 2:
			return WAP;
		}
		return null;
	}
}
