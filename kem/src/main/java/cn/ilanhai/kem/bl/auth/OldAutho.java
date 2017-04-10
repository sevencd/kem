package cn.ilanhai.kem.bl.auth;

import java.util.HashMap;
import java.util.Map;


import cn.ilanhai.framework.uitl.Str;
import cn.ilanhai.kem.util.HttpHelper;

/**
 * 
 * @author he
 *
 */
public class OldAutho {
	// "http://openapi.baidu.com/social/oauth/2.0/authorize?media_type=sinaweibo&amp;client_id=gwPm8DkexswclYHVjuKeUtGg&amp;state=0&amp;response_type=code&amp;redirect_uri=http%3A%2F%2F4105ec76.nat123.net%3A30078%2Fhttpserver%2Fkem%2Ffrontuser%2Fauth&amp;display=page&amp;client_type=web";
	// "http://openapi.baidu.com/social/oauth/2.0/authorize?media_type=qqdenglu&amp;client_id=gwPm8DkexswclYHVjuKeUtGg&amp;state=1&amp;response_type=code&amp;redirect_uri=http%3A%2F%2F4105ec76.nat123.net%3A30078%2Fhttpserver%2Fkem%2Ffrontuser%2Fauth&amp;display=page&amp;client_type=web";
	// "http://openapi.baidu.com/social/oauth/2.0/authorize?media_type=baidu&amp;client_id=gwPm8DkexswclYHVjuKeUtGg&amp;state=2&amp;response_type=code&amp;redirect_uri=http%3A%2F%2F4105ec76.nat123.net%3A30078%2Fhttpserver%2Fkem%2Ffrontuser%2Fauth&amp;display=page&amp;client_type=web";
	// "http://openapi.baidu.com/social/oauth/2.0/authorize?media_type=qqweibo&amp;client_id=gwPm8DkexswclYHVjuKeUtGg&amp;state=3&amp;response_type=code&amp;redirect_uri=http%3A%2F%2F4105ec76.nat123.net%3A30078%2Fhttpserver%2Fkem%2Ffrontuser%2Fauth&amp;display=page&amp;client_type=web";
	// "http://openapi.baidu.com/social/oauth/2.0/authorize?media_type=kaixin&amp;client_id=gwPm8DkexswclYHVjuKeUtGg&amp;state=4&amp;response_type=code&amp;redirect_uri=http%3A%2F%2F4105ec76.nat123.net%3A30078%2Fhttpserver%2Fkem%2Ffrontuser%2Fauth&amp;display=page&amp;client_type=web";
	// "http://openapi.baidu.com/social/oauth/2.0/authorize?media_type=renren&amp;client_id=gwPm8DkexswclYHVjuKeUtGg&amp;state=5&amp;response_type=code&amp;redirect_uri=http%3A%2F%2F4105ec76.nat123.net%3A30078%2Fhttpserver%2Fkem%2Ffrontuser%2Fauth&amp;display=page&amp;client_type=web";
	private final String API_KEY = "gwPm8DkexswclYHVjuKeUtGg";
	private final String SECRET_KEY = "xyEeTLCczzUfzHb6EtQ7hwykFILBjYrM";
	private final String REDIRECT_URI = "http://4105ec76.nat123.net:30078/httpserver/kem/frontuser/auth";
	private final String API_AUTHORIZE = "http://openapi.baidu.com/social/oauth/2.0/authorize?media_type=%s&amp;client_id=%s&amp;state=%s&amp;response_type=code&amp;redirect_uri=%s&amp;display=page&amp;client_type=web";
	private final String API_ACCESS_TOKEN = "https://openapi.baidu.com/social/oauth/2.0/token";

	private enum MediaType {
		sinaweibo(0), qqdenglu(1), baidu(2), qqweibo(3), kaixin(4), renren(5);
		private int value;

		private MediaType(int value) {
			this.value = value;
		}

		public static MediaType valueOf(int value) {
			switch (value) {
			case 0:
				return sinaweibo;
			case 1:
				return qqdenglu;
			case 2:
				return baidu;
			case 3:
				return qqweibo;
			case 4:
				return kaixin;
			case 5:
				return renren;
			default:
				return null;
			}
		}

		public int getValue() {
			return this.value;
		}
	}

	public OldAutho() {

	}

	public String accessToken(String code, int state, String redirectUri) {
		Map<String, String> para = null;
		MediaType mediaType = null;
		try {
			if (Str.isNullOrEmpty(code))
				return null;
			mediaType = MediaType.valueOf(state);
			if (mediaType == null)
				return null;
			para = new HashMap<String, String>();
			para.put("grant_type", "authorization_code");
			para.put("code", code);
			para.put("client_id", this.API_KEY);
			para.put("client_secret", this.SECRET_KEY);
			para.put("redirect_uri", redirectUri);
			return HttpHelper.sendPost(API_ACCESS_TOKEN, para, "utf-8");
		} catch (Exception e) {
			return null;
		}

	}
}
