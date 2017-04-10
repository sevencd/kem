package cn.ilanhai.kem.domain.enums;

public enum ContactType {
	phone(1), email(2);
	private Integer value;

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	ContactType(Integer value) {
		this.value = value;
	}

	public static ContactType getEnum(Integer value) {
		if (value == null)
			return null;
		for (ContactType type : values()) {
			if (type.getValue().equals(value)) {
				return type;
			}
		}
		return null;
	}
}
