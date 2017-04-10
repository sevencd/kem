package cn.ilanhai.kem.mail.sohu;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.ilanhai.framework.uitl.FastJson;
import cn.ilanhai.framework.uitl.Str;
import cn.ilanhai.kem.mail.MailConfig;

public class SohuMailConfig extends MailConfig {

	public SohuMailConfig() {

	}

	public String getApiUser() {
		return apiUser;
	}

	public void setApiUser(String apiUser) {
		this.apiUser = apiUser;
	}

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public Map<String, String> toMap() {
		Map<String, String> map = null;
		String tmp = null;
		map = new HashMap<String, String>();
		tmp = this.apiUser;
		if (!Str.isNullOrEmpty(tmp))
			map.put("apiUser", this.encode(tmp));
		tmp = this.apiKey;
		if (!Str.isNullOrEmpty(tmp))
			map.put("apiKey", this.encode(tmp));
		return map;
	}

	protected String listToString(List<String> list, char split) {
		if (list == null || list.size() <= 0)
			return null;
		StringBuilder builder = null;
		builder = new StringBuilder();
		int offset = list.size() - 1;
		for (int i = 0; i < offset; i++)
			builder.append(list.get(i)).append(split);
		builder.append(list.get(offset));
		return builder.toString();
	}

	protected String mapToJsonString(Map<String, String> map) {
		if (map == null || map.size() <= 0)
			return null;
		return FastJson.bean2Json(map);
	}

	/**
	 * apiUser string 是 API_USER
	 */
	private String apiUser;
	/**
	 * apiKey string 是 API_KEY
	 */
	private String apiKey;
}
