package cn.ilanhai.kem.keyfac;

public enum Key {
	KEY_TEMPLATE("1001", 12), // 模版
	KEY_SPECIAL("1002", 12), // 专题
	KEY_EXTENSION("1003", 12), // 推广
	KEY_USER("user", 12), // 用户
	KEY_MANUSCRIPT("1004", 12), // 优秀案例
	KEY_DET("1005", 12), // 未登录稿件
	KEY_ORDER("odr", 12), // 支付订单
	KEY_MEMBER("member", 12), // 会员
	KEY_SVG("svg", 12), // svg
	KEY_CONTACT("contact", 12), // 联系人
	KEY_MATERIAL("mtr", 12), // 素材分类
	KEY_COMPOSITEMATERIAL("cmtr", 12), // 组合素材
	KEY_RESOURCE_IMG("img", 12), // 素材
	KEY_EMAIL("email", 12), // 邮件
	KEY_SMS("sms", 12), // 短信
	KEY_CONTACTS_GROUP("ctg", 12), // 群组
	KEY_CUSTOMER("customer", 12), // 客户
	KEY_COMPANYCODE("companyCode", 2),// 公司编码
	;
	private String key;
	private int length;

	private Key(String key, int length) {
		this.key = key;
		this.length = length;
	}

	public String getKey() {
		return key;
	}

	public int getLength() {
		return length;
	}

	public static Key getEnum(String key) {
		for (Key keyType : values()) {
			if (keyType.getKey().equals(key))
				return keyType;
		}
		return null;
	}
}
