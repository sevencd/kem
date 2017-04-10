package cn.ilanhai.kem.domain.enums;

import cn.ilanhai.kem.common.IBaseEnum;
/**
 * 
 * @Description 是否的构造
 * @TypeName BooleanEnum
 * @time 2017年3月2日 上午10:05:21
 * @author csz
 */
public enum BooleanEnum implements IBaseEnum {
	TRUE(1, "是"), FALSE(0, "否");
	private int code;
	private String name;
	private BooleanEnum(int code, String name) {
		this.code = code;
		this.name = name;
	}

	@Override
	public Integer getCode() {
		return code;
	}

	@Override
	public String getName() {
		return name;
	}

}
