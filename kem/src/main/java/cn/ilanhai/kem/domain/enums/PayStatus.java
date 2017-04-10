package cn.ilanhai.kem.domain.enums;

/**
 * 支付状态
 * @author Nature
 *
 */
public enum PayStatus {
	/**
	 * 等待支付
	 */
	WAITPAY(0),

	/**
	 * 已支付
	 */
	PAYED(1);
	
	private final int value;

	private PayStatus(int value) {
		this.value = value;
	}
	
	public Integer getValue(){
		return value;
	}
	
	public static PayStatus getEnum(Integer value){
		switch(value){
		case 0:return WAITPAY;
		case 1:return PAYED;
		}
		return null;
	}
}
