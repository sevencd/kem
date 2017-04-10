package cn.ilanhai.kem.domain.enums;

/**
 * 验证状态
 * 
 * @author he
 *
 */
public enum ValidateStatus {
	/**
	 * 未验证
	 */
	NOT_VALIDATE(0),
	/**
	 * 已验证
	 */
	VERIFIED(1);
	private final int value;

	private ValidateStatus(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public static ValidateStatus valueOf(Integer value) {
		if (value == null)
			return null;
		switch (value) {
		case 0:
			return NOT_VALIDATE;
		case 1:
			return VERIFIED;
		default:
			return null;
		}
	}
}
