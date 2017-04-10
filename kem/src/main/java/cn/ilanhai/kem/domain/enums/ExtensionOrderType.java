package cn.ilanhai.kem.domain.enums;

public enum ExtensionOrderType {
	DATEASC(1, "date-asc"), DATEDESC(2, "date-desc"), CLICKASC(3, "click-asc"), CLICKDESC(4, "click-desc");
	private Integer value;
	private String name;

	public Integer getValue() {
		return value;
	}

	public String getName() {
		return name;
	}

	public static ExtensionOrderType getEnum(Integer value) {
		switch (value) {
		case 1:
			return DATEASC;
		case 2:
			return DATEDESC;
		case 3:
			return CLICKASC;
		case 4:
			return CLICKDESC;
		}
		return null;
	}

	public static ExtensionOrderType getEnum(String name) {
		switch (name) {
		case "date-asc":
			return DATEASC;
		case "date-desc":
			return DATEDESC;
		case "click-asc":
			return CLICKASC;
		case "click-desc":
			return CLICKDESC;
		}
		return null;
	}

	ExtensionOrderType(Integer value, String name) {
		this.value = value;
		this.name = name;
	}
}
