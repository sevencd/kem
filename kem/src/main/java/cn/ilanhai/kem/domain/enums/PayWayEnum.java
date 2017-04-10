package cn.ilanhai.kem.domain.enums;

public enum PayWayEnum {
	/**
	 * 支付宝
	 */
	ZHIFUBAO(0,"支付宝"),

	/**
	 * 微信
	 */
	WEIXIN(1,"微信"),
	/**
	 *其他
	 */
	OTHER(2,"其他");
	
	private final int code;
	private final String name;
	private PayWayEnum(int code,String value){
		this.code=code;
		this.name=value;
	}
	public int getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public static PayWayEnum getEnum(int code){
		switch(code){
		case 0:return ZHIFUBAO;
		case 1:return WEIXIN;
		case 2:return OTHER;
		}
		return null;
	}
	public static String  getName(int code){
		switch(code){
		case 0:return ZHIFUBAO.getName();
		case 1:return WEIXIN.getName();
		case 2:return OTHER.getName();
		}
		return null;
	}
}
