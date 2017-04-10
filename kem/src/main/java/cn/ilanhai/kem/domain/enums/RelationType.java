package cn.ilanhai.kem.domain.enums;

import cn.ilanhai.kem.common.IntegerEnum;

public enum RelationType implements IntegerEnum{
	/**
	 * 
	 */
	TEMPLATE(1),

	SPECIAL(2),
	
	EXTENSION(3);
	
	private final int value;

	private RelationType(int value) {
		this.value = value;
	}
	
	public Integer getValue(){
		return value;
	}
	
	public static RelationType getEnum(Integer value){
		switch(value){
		case 1:
			return TEMPLATE;
		case 2:
			return SPECIAL;
		case 3:
			return EXTENSION;
		}
		return null;
	}
	
	public RelationType valueOf(Integer value){
		return getEnum(value);
	}
}
