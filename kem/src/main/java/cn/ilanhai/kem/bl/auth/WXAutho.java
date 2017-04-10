package cn.ilanhai.kem.bl.auth;

import java.util.Map;

import org.apache.log4j.Logger;

import cn.ilanhai.kem.util.HttpHelper;
import cn.ilanhai.kem.util.HttpsHelper;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class WXAutho extends Autho {
	private final Logger logger = Logger.getLogger(WXAutho.class);
	// 详情:https://open.weixin.qq.com/cgi-bin/showdocument?action=dir_list&t=resource/res_list&verify=1&id=open1419316505&token=&lang=zh_CN
	private static final String AUTHORIZE = "appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_login&state=%s#wechat_redirect";
	// 详情:https://open.weixin.qq.com/cgi-bin/showdocument?action=dir_list&t=resource/res_list&verify=1&id=open1419316505&token=&lang=zh_CN
	public static final String AUTHORIZE_URL = String.format(
			"https://open.weixin.qq.com/connect/qrconnect?%s", AUTHORIZE);

	// 详情:https://open.weixin.qq.com/cgi-bin/showdocument?action=dir_list&t=resource/res_list&verify=1&id=open1419316505&token=&lang=zh_CN
	private final String ACCESS_TOKEN = "appid=%s&secret=%s&code=%s&grant_type=authorization_code";
	// 详情:https://open.weixin.qq.com/cgi-bin/showdocument?action=dir_list&t=resource/res_list&verify=1&id=open1419316505&token=&lang=zh_CN
	private final String ACCESS_TOKEN_URL = String.format(
			"https://api.weixin.qq.com/sns/oauth2/access_token?%s",
			ACCESS_TOKEN);
	// 详情:https://open.weixin.qq.com/cgi-bin/showdocument?action=dir_list&t=resource/res_list&verify=1&id=open1419316505&token=&lang=zh_CN
	private final String ACCESS_TOKEN_ROLLOVER = "appid=%s&grant_type=refresh_token&refresh_token=%s";
	// 详情:https://open.weixin.qq.com/cgi-bin/showdocument?action=dir_list&t=resource/res_list&verify=1&id=open1419316505&token=&lang=zh_CN
	private final String ACCESS_TOKEN__ROLLOVER_URL = String.format(
			"https://api.weixin.qq.com/sns/oauth2/refresh_token?%s",
			ACCESS_TOKEN_ROLLOVER);
	// 详情:https://open.weixin.qq.com/cgi-bin/showdocument?action=dir_list&t=resource/res_list&verify=1&id=open1419316505&token=&lang=zh_CN
	private final String API_USER_INFO = "access_token=%s&openid=%s";
	// 详情:https://open.weixin.qq.com/cgi-bin/showdocument?action=dir_list&t=resource/res_list&verify=1&id=open1419316505&token=&lang=zh_CN
	private final String API_USER_INFO_URL = String.format(
			"https://api.weixin.qq.com/sns/userinfo?%s", API_USER_INFO);

	public WXAutho() {
		this.mediaType = MediaType.wx;
		this.appId = "wx4fd7cc4236957f9d";
		this.appSecret = "a11e64ef1803bcbe457f524d80d395d8";
		// AppID:wx4fd7cc4236957f9d
		// AppSecret:a11e64ef1803bcbe457f524d80d395d8
	}

	@Override
	public JSONObject getAccessToken() throws Exception {
		JSONObject res = null;
		// String accessToken = null;
		// String openId = null;
		String msg = null;
		try {
			if (this.appId == null || this.appId.length() <= 0)
				return this.json(-1, "appId错误");
			if (this.appSecret == null || this.appSecret.length() <= 0)
				return this.json(-1, "appSecret错误");
			if (this.redirectUri == null || this.redirectUri.length() <= 0)
				return this.json(-1, "redirectUri错误");
			if (this.code == null || this.code.length() <= 0)
				return this.json(-1, "code错误");
			if (this.source == null)
				return this.json(-1, "source错误");
			logger.info("GetAccessToken Params");
			logger.info("appId=" + this.appId);
			logger.info("appSecret=" + this.appId);
			logger.info("redirectUri=" + this.appId);
			logger.info("code=" + this.appId);
			logger.info("source=" + this.source);
			res = this.reqAccessToken();
			logger.info("ReqAccess Result:" + res);
			if (res == null || res.containsKey("errcode")) {
				msg = "获取access token数据出错";
				if (res != null)
					msg = String.format("获取access token出错。 原因：错误码:%s 消息:%s",
							res.get("errcode"), res.get("errmsg"));
				return this.json(-1, msg);
			}
			res.put("accessToken", res.getString("access_token"));
			// accessToken = res.getString("access_token");
			// openId = res.getString("openid");
			// res = this.getUserInfo(accessToken, openId);
			// logger.info("ReqUserInfo Result:" + res);
			// if (res == null || res.containsKey("errcode")) {
			// msg = String.format("获取用户信息失败 原因：错误码:%s 消息:%s",
			// res.get("errcode"), res.get("errmsg"));
			// return this.json(-1, msg);
			// }
			return json(0, "ok", res);
		} catch (Exception e) {
			return this.json(-1, e.getMessage());
		}

	}

	/**
	 * 获取code（暂有前端发起)<br>
	 * code的有效期默认10分钟
	 * 
	 * @return
	 */
	private String reqCode() {
		return null;
	}

	/**
	 * 获取access_token<br>
	 * 注：Access_Token的有效期默认是2小时
	 * 
	 * @return 成功：（json格式） <br>
	 *         access_token 接口调用凭证 <br>
	 *         expires_in access_token接口调用凭证超时时间，单位（秒） <br>
	 *         refresh_token 用户刷新access_token <br>
	 *         openid 授权用户唯一标识 scope 用户授权的作用域，使用逗号（,）分隔 <br>
	 *         unionid 当且仅当该网站应用已获得该用户的userinfo授权时，才会出现该字段 <br>
	 *         失败：（json格式） <br>
	 *         errcode 错误码 <br>
	 *         errmsg 错误消息
	 */
	private JSONObject reqAccessToken() throws Exception {
		Map<String, String> res = null;
		String url = null;
		String doc = null;
		try {
			url = ACCESS_TOKEN_URL;
			url = String.format(url, this.appId, this.appSecret, this.code,
					this.redirectUri);
			// doc = HttpHelper.sendGet(url, "utf-8");
			doc = HttpsHelper.get(url);
			return JSON.parseObject(doc);
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 获取用户信息
	 * 
	 * @param access_token
	 * @param openId
	 * @return 结果：（json格式）<br>
	 *         openid 普通用户的标识，对当前开发者帐号唯一 <br>
	 *         nickname 普通用户昵称 sex 普通用户性别，1为男性，2为女性<br>
	 *         province 普通用户个人资料填写的省份 <br>
	 *         city 普通用户个人资料填写的城市 <br>
	 *         country 国家，如中国为CN <br>
	 *         headimgurl
	 *         用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像
	 *         ），用户没有头像时该项为空 <br>
	 *         privilege 用户特权信息，json数组，如微信沃卡用户为（chinaunicom）<br>
	 *         unionid 用户统一标识。针对一个微信开放平台帐号下的应用，同一用户的unionid是唯一的。<br>
	 *         失败：（json格式） <br>
	 *         errcode 错误码 <br>
	 *         errmsg 错误消息*
	 */
	public JSONObject getUserInfo(String access_token, String openId)
			throws Exception {
		String url = null;
		String doc = null;
		JSONObject res = null;
		String msg = null;
		try {
			url = API_USER_INFO_URL;
			url = String.format(url, access_token, this.getAppId(), openId);
			logger.info("GetUserInfo Params");
			logger.info("access_token=" + access_token);
			logger.info("openId=" + openId);
			// doc = HttpHelper.sendGet(url, "utf-8");
			doc = HttpsHelper.get(url);
			logger.info("GetUserInfo Result:" + doc);
			if (doc == null || doc.length() <= 0)
				return this.json(-1, "获取用户信息失败");
			res = JSON.parseObject(doc);
			if (res == null || res.containsKey("errcode")) {
				msg = String.format("获取用户信息失败 原因：错误码:%s 消息:%s",
						res.get("errcode"), res.get("errmsg"));
				return this.json(-1, msg);
			}
			return json(0, "ok", res);
		} catch (Exception e) {
			throw e;
		}
	}
}
