package cn.ilanhai.kem.domain.enums;

public enum PayWay {
	/**
	 * 等待支付
	 */
	ZHIFUBAO(0),

	/**
	 * 已支付
	 */
	WEIXIN(1),
	/**
	 * 已支付
	 */
	OTHER(2);
	
	private final int value;

	private PayWay(int value) {
		this.value = value;
	}
	
	public Integer getValue(){
		return value;
	}
	
	public static PayWay getEnum(Integer value){
		switch(value){
		case 0:return ZHIFUBAO;
		case 1:return WEIXIN;
		case 2:return OTHER;
		}
		return null;
	}
}
