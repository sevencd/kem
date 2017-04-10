package cn.ilanhai.framework.common.session.enums;

/**
 * 
 * @author he
 *
 */
public enum SessionType {
	MEMORY_SESSION(0), USER_MEMORY_SESSION(1), REDIS_SESSION(2);
	private int value;

	private SessionType(int value) {
		this.value = value;
	}

	public int getValue() {
		return this.value;
	}

	public static SessionType valueOf(int value) {
		switch (value) {
		case 0:
			return MEMORY_SESSION;
		case 1:
			return USER_MEMORY_SESSION;
		case 2:
			return REDIS_SESSION;
		default:
			return null;
		}
	}
}
