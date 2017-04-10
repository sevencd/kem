package cn.ilanhai.kem.domain.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户类型
 * 
 * @author he
 *
 */
public enum UserType {
	/**
	 * 后台用户
	 */
	BACK_USER(0),
	/**
	 * 普通用户
	 */
	GENERAL_USER(1),
	/**
	 * 设计师
	 */
	DESIGNERS(2),
	/**
	 * 匿名用户
	 */
	ANONYMOUS_USER(3),
	/**
	 * 流量用户
	 */
	USER_TRAFFIC(4);
	
	private final int value;
	private static Map<Integer, UserType> datas = new HashMap<Integer, UserType>();
	static {
		for (UserType value : values()) {
			datas.put(value.getValue(), value);
		}
	}

	public static UserType getUserType(Integer value) {
		if (value == null)
			return null;
		if (datas.containsKey(value)) {
			return datas.get(value);
		} else {
			return null;
		}
	}

	private UserType(int value) {
		this.value = value;
	}

	public static UserType valueOf(Integer value) {
		if (value == null)
			return null;
		switch (value) {
		case 1:
			return GENERAL_USER;
		case 2:
			return DESIGNERS;
		case 3:
			return ANONYMOUS_USER;
		case 4:
			return USER_TRAFFIC;
		default:
			return null;
		}
	}

	/**
	 * 获取该类型的类型名称
	 * 
	 * @param userType
	 * @return
	 */
	public static String valueOf(UserType userType) {
		if (userType == null)
			return null;
		switch (userType) {
		case GENERAL_USER:
			return "普通用户";
		case DESIGNERS:
			return "设计师";
		case ANONYMOUS_USER:
			return "匿名用户";
		case USER_TRAFFIC:
			return "流量用户";
		default:
			return null;
		}
	}

	public int getValue() {
		return value;
	}
}
