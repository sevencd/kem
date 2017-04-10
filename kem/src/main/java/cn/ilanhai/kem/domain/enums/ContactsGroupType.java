package cn.ilanhai.kem.domain.enums;

public enum ContactsGroupType {
	phoneGroup(1), emailGroup(2);
	private Integer value;

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	ContactsGroupType(Integer value) {
		this.value = value;
	}

	public static ContactsGroupType getEnum(Integer value) {
		for (ContactsGroupType groupType : values()) {
			if (groupType.getValue().equals(value)) {
				return groupType;
			}
		}
		return null;
	}
}
