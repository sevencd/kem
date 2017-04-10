package cn.ilanhai.kem.domain.customer;

import cn.ilanhai.kem.common.IntegerEnum;

public enum CustomerGroupingEnum implements IntegerEnum {
	/**
	 * 潜在客户
	 */
	POTENTIAL(1),
	/**
	 * 有意向客户
	 */
	INTERESTED(2),
	/**
	 * 成交客户
	 */
	TURNOVER(3),
	/**
	 * 无意向客户
	 */
	NOINTENTION(4);

	private final int value;

	private CustomerGroupingEnum(int value) {
		this.value = value;
	}

	public Integer getValue() {
		return value;
	}

	public static CustomerGroupingEnum getEmun(Integer value) {
		if (value == null) {
			return null;
		}
		switch (value) {
		case 1:
			return POTENTIAL;
		case 2:
			return INTERESTED;
		case 3:
			return TURNOVER;
		case 4:
			return NOINTENTION;
		}
		return null;
	}
	
	public static String getName(Integer value) {
		if (value == null) {
			return null;
		}
		switch(value) {
		case 1:
			return "潜在客户";
		case 2:
			return "有意向客户";
		case 3:
			return "成交客户";
		case 4:
			return "无意向客户";
		}
		return null;
	}

	@Override
	public CustomerGroupingEnum valueOf(Integer value) {

		return getEmun(value);
	}

}
