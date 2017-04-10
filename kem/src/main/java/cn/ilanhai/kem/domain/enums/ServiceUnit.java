package cn.ilanhai.kem.domain.enums;

/**
 * 服务单位
 * @author Nature
 *
 */
public enum ServiceUnit {

	/**
	 * 月
	 */
	MONTH(0),

	/**
	 * 次数
	 */
	TIMES(1);
	
	private final int value;

	private ServiceUnit(int value) {
		this.value = value;
	}
	
	public Integer getValue(){
		return value;
	}
	
	public static ServiceUnit getEnum(Integer value){
		switch(value){
		case 0:return MONTH;
		case 1:return TIMES;
		}
		return null;
	}
}
