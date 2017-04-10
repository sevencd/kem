package cn.ilanhai.kem.domain.enums;

import java.util.HashMap;
import java.util.Map;

public enum UserStatus {
	/**
	 * 禁用
	 */
	DISABLE(0),
	/**
	 * 启用
	 */
	ENABLE(1),
	/** 
	* 试用
	 */
	Trial(2),;
	private final int value;

	private UserStatus(int value) {
		this.value = value;
	}

	private static Map<Integer, UserStatus> datas = new HashMap<Integer, UserStatus>();
	static {
		for (UserStatus value : values()) {
			datas.put(value.getValue(), value);
		}
	}

	public static UserStatus getUserStatus(Integer value) {
		if (value == null)
			return null;
		if (datas.containsKey(value)) {
			return datas.get(value);
		} else {
			return null;
		}
	}

	public int getValue() {
		return value;
	}
}
