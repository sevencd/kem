package cn.ilanhai.kem.domain.enums;

public enum MaterialInfoType {
	// 素材类型 0:SVG源文件 1:SVG模版
	materialresouse(0), materialtemplate(1),

	materialtype(2),
	// 素材保存路径
	materialurl(3),
	// 素材关键字
	materialkeyword(4),
	// 素材状态
	materialstate(5),
	// 素材id
	materialid(6),
	// 素材名称
	materialname(7),
	// 稿件id
	manuscriptid(8),
	// 素材模版保存路径
	materialtemplateurl(9),
	//备注
	materialremark(10),;
	private Integer value;

	MaterialInfoType(Integer value) {
		this.value = value;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public static MaterialInfoType getEnum(Integer value) {
		for (MaterialInfoType InfoType : values()) {
			if (InfoType.getValue().equals(value)) {
				return InfoType;
			}
		}
		return null;
	}
}
