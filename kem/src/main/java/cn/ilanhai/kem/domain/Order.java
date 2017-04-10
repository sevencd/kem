package cn.ilanhai.kem.domain;

/**
 * 排序枚举
 * @author Nature
 *
 */
public enum Order {
	ASC("ASC"), DESC("DESC");
	private String val;

	private Order(String v) {
		this.val = v;

	}
	 
	@Override
	public String toString() {
		return this.val;
	}

	public static Order valueOf1(String v) {
		if (v == null)
			return null;
		if (v.equalsIgnoreCase(ASC.toString()))
			return ASC;
		else if (v.equalsIgnoreCase(DESC.toString()))
			return DESC;
		else
			return null;
	}

}
