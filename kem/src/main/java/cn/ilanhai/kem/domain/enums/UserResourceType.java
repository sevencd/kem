package cn.ilanhai.kem.domain.enums;

public enum UserResourceType {
	IMG("img"), SVG("svg"), CONTROLS("thumbnail_group_controls");
	private String value;

	UserResourceType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public static UserResourceType getEnum(String value) {
		UserResourceType result = IMG;
		for (UserResourceType userResourceType : values()) {
			if (userResourceType.getValue().equals(value)) {
				return userResourceType;
			}
		}
		return result;
	}
}
