package cn.ilanhai.kem.domain.enums;

public enum UserRelationType {
	// 主账户0 子账户1
	MAINUSER(0), SUBUSER(1);
	private int value;

	UserRelationType(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public UserRelationType getEnum(int value) {
		for (UserRelationType type : values()) {
			if (type.getValue() == value) {
				return type;
			}
		}
		return null;
	}
}
