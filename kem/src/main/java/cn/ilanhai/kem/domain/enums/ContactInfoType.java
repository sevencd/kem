package cn.ilanhai.kem.domain.enums;

public enum ContactInfoType {
	name(3), context(2);
	private Integer value;

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	ContactInfoType(Integer value) {
		this.value = value;
	}
}
