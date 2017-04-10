package cn.ilanhai.kem.domain.enums;

public enum EnableState {
	enable(0), unenable(1);
	private Integer value;

	EnableState(Integer value) {
		this.value = value;
	}
	
	public static EnableState getEnum(Integer value) {
		switch (value) {
		case 0:
			return enable;
		case 1:
			return unenable;
		}
		return null;
	}
	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}
}
