package cn.ilanhai.kem.domain.enums;

public enum UserImgOrderType {
	/**
	 * 最近
	 */
	DESC("DESC", 1),
	/**
	 * 最远
	 */
	ASC("ASC", 2);
	private String name;
	private Integer value;

	private UserImgOrderType(String name, Integer value) {
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public Integer getValue() {
		return value;
	}
	
	public static String valueOf(Integer value){
		String result = ASC.getName();
		for (UserImgOrderType orderType : values()) {
			if(orderType.getValue().equals(value)){
				return orderType.getName();
			}
		}
		return result;
	}
}
