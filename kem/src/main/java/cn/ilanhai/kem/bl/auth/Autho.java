package cn.ilanhai.kem.bl.auth;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

public abstract class Autho {
	protected MediaType mediaType;
	protected String appId;
	protected String appSecret;
	protected String redirectUri;
	protected String code;
	protected Source source;

	public enum Source {
		pc(0), wap(1);
		private int value;

		private Source(int value) {
			this.value = value;
		}

		public static Source valueOf(int value) {
			switch (value) {
			case 0:
				return pc;
			case 1:
				return wap;
			default:
				return null;
			}
		}

		public int getValue() {
			return this.value;
		}
	}

	public enum MediaType {
		sinaweibo(0), qq(1), wx(2);
		private int value;

		private MediaType(int value) {
			this.value = value;
		}

		public static MediaType valueOf(int value) {
			switch (value) {
			case 0:
				return sinaweibo;
			case 1:
				return qq;
			case 2:
				return wx;
			default:
				return null;
			}
		}

		public int getValue() {
			return this.value;
		}
	}

	protected Autho() {

	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	public MediaType getMediaType() {
		return mediaType;
	}

	public String getRedirectUri() {
		return redirectUri;
	}

	public void setRedirectUri(String redirectUri) {
		this.redirectUri = redirectUri;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Source getSource() {
		return source;
	}

	public void setSource(Source source) {
		this.source = source;
	}

	public static Autho createAuth(MediaType mediaType) {

		if (mediaType == null)
			return null;
		if (mediaType.getValue() == MediaType.sinaweibo.getValue())
			return new SinaWeiBoAutho();
		else if (mediaType.getValue() == MediaType.qq.getValue())
			return new QQAutho();
		else if (mediaType.getValue() == MediaType.wx.getValue())
			return new WXAutho();
		else
			return null;
	}

	public abstract JSONObject getAccessToken() throws Exception;

	public abstract JSONObject getUserInfo(String accessToken, String openId) throws Exception;

	protected Map<String, String> parserParamters(String data) {
		Map<String, String> paramter = null;
		String[] tmp = null;
		String[] kv = null;
		String s = null;
		if (data == null || data.length() <= 0)
			return null;
		tmp = data.split("&");
		if (tmp == null || tmp.length <= 0)
			return null;
		paramter = new HashMap<String, String>();
		for (int i = 0; i < tmp.length; i++) {
			s = tmp[i];
			if (s == null || s.length() <= 0)
				continue;
			kv = s.split("=");
			if (kv == null || kv.length < 2)
				continue;
			if (paramter.containsKey(kv[0]))
				continue;
			paramter.put(kv[0], kv[1]);

		}
		return paramter;
	}

	protected JSONObject json(int code, String msg) {
		return this.json(code, msg, null);
	}

	protected JSONObject json(int code, String msg, JSONObject data) {
		JSONObject jsonObject = null;
		jsonObject = new JSONObject();
		jsonObject.put("code", code);
		jsonObject.put("msg", msg);
		jsonObject.put("data", data);

		return jsonObject;
	}
}
