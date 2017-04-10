package cn.ilanhai.kem.bl.auth;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import cn.ilanhai.kem.util.HttpHelper;
import cn.ilanhai.kem.util.HttpsHelper;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class SinaWeiBoAutho extends Autho {
	private final Logger logger = Logger.getLogger(SinaWeiBoAutho.class);
	// 详情:http://open.weibo.com/wiki/Oauth2/authorize
	private static final String AUTHORIZE = "client_id=%s&redirect_uri=%s&scope=&state=%s&display=default&forcelogin=true&language=zh_CN";
	// 详情:http://open.weibo.com/wiki/Oauth2/authorize
	public static final String AUTHORIZE_URL = String.format(
			"https://api.weibo.com/oauth2/authorize?%s", AUTHORIZE);
	// 详情:http://open.weibo.com/wiki/Oauth2/access_token
	// private final String ACCESS_TOKEN =
	// "client_id=%s&client_secret=%s&code=%s&grant_type=authorization_code&redirect_uri=%s";
	// 详情:http://open.weibo.com/wiki/Oauth2/access_token
	private final String ACCESS_TOKEN_URL = "https://api.weibo.com/oauth2/access_token";
	private final String ACCESS_TOKEN_ROLLOVER = "";
	private final String ACCESS_TOKEN__ROLLOVER_URL = "";
	// 详情:http://open.weibo.com/wiki/2/users/show
	private final String API_USER_INFO = "access_token=%s&uid=%s";
	// 详情:http://open.weibo.com/wiki/2/users/show
	private final String API_USER_INFO_URL = String.format(
			"https://api.weibo.com/2/users/show.json?%s", API_USER_INFO);

	public SinaWeiBoAutho() {
		this.mediaType = MediaType.sinaweibo;
		this.appId = "3823533855";
		this.appSecret = "75ba1eac140afcdda2ea176606eb784d";
		// App Key：3823533855
		// App Secret：75ba1eac140afcdda2ea176606eb784d
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
			logger.info("ReqAccessToken Result:" + res);
			if (res == null || res.containsKey("error_code")) {
				msg = "获取access token数据出错";
				if (res != null)
					msg = String
							.format("获取access token出错。 原因：错误码:%s 消息:%s",
									res.get("error_code"),
									res.get("error_description"));
				return this.json(-1, msg);

			}
			res.put("openid", res.get("uid"));
			res.put("accessToken", res.getString("access_token"));
			// accessToken = res.getString("access_token");
			// openId = res.getString("uid");
			// res = this.getUserInfo(accessToken, openId);
			// logger.info("ReqUserInfo Result:" + res);
			// if (res == null || res.containsKey("error_code")) {
			// msg = String.format("获取用户信息失败 原因：错误码:%s 消息:%s",
			// res.get("error_code"), res.get("error_description"));
			// return this.json(-1, msg);
			// }

			return json(0, "ok", res);
		} catch (Exception e) {
			return this.json(-1, e.getMessage());
		}

	}

	/**
	 * 获取access_token<br>
	 * 注：授权有效期 测试（1天） 普通（7天）<br>
	 * 
	 * @return 成功：（json格式） <br>
	 *         access_token string 用户授权的唯一票据，用于调用微博的开放接口，同时也是第三方应用验证微博用户登录的唯一票据，
	 *         第三方应用应该用该票据和自己应用内的用户建立唯一影射关系，来识别登录状态，不能使用本返回值里的UID字段来做登录识别。 <br>
	 *         expires_in string access_token的生命周期，单位是秒数。 <br>
	 *         remind_in string access_token的生命周期（该参数即将废弃，开发者请使用expires_in）。 <br>
	 *         uid string 授权用户的UID，本字段只是为了方便开发者
	 *         ，减少一次user/show接口调用而返回的，第三方应用不能用此字段作为用户登录状态的识别
	 *         ，只有access_token才是用户授权的唯一票据。 <br>
	 *         失败：（json格式） <br>
	 *         error 错误<br>
	 *         error_code 错误码 <br>
	 *         error_description 错误描述
	 */
	private JSONObject reqAccessToken() throws Exception {
		Map<String, String> para = null;
		String url = null;
		String doc = null;
		try {
			url = ACCESS_TOKEN_URL;
			/**
			 * client_id true string 申请应用时分配的AppKey。 <br>
			 * client_secret true string 申请应用时分配的AppSecret。 <br>
			 * grant_type true string 请求的类型，填写authorization_code <br>
			 * 注：grant_type为authorization_code时 <br>
			 * code true string 调用authorize获得的code值。 <br>
			 * redirect_uri true string 回调地址，需需与注册应用里的回调地址一致。 <br>
			 */
			para = new HashMap<String, String>();
			para.put("client_id", this.appId);
			para.put("client_secret", this.appSecret);
			para.put("grant_type", "authorization_code");
			para.put("code", this.code);
			para.put("redirect_uri", this.redirectUri);
			doc = HttpHelper.sendPost(url, para, "utf-8");

			// doc = HttpsHelper.post(url, para);
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
	 *         id int64 用户UID<br>
	 *         idstr string 字符串型的用户UID<br>
	 *         screen_name string 用户昵称<br>
	 *         name string 友好显示名称<br>
	 *         province int 用户所在省级ID<br>
	 *         city int 用户所在城市ID<br>
	 *         location string 用户所在地<br>
	 *         description string 用户个人描述<br>
	 *         url string 用户博客地址<br>
	 *         profile_image_url string 用户头像地址（中图），50×50像素<br>
	 *         profile_url string 用户的微博统一URL地址<br>
	 *         domain string 用户的个性化域名<br>
	 *         weihao string 用户的微号<br>
	 *         gender string 性别，m：男、f：女、n：未知<br>
	 *         followers_count int 粉丝数<br>
	 *         friends_count int 关注数<br>
	 *         statuses_count int 微博数<br>
	 *         favourites_count int 收藏数<br>
	 *         created_at string 用户创建（注册）时间<br>
	 *         following boolean 暂未支持<br>
	 *         allow_all_act_msg boolean 是否允许所有人给我发私信，true：是，false：否<br>
	 *         geo_enabled boolean 是否允许标识用户的地理位置，true：是，false：否<br>
	 *         verified boolean 是否是微博认证用户，即加V用户，true：是，false：否<br>
	 *         verified_type int 暂未支持<br>
	 *         remark string 用户备注信息，只有在查询用户关系时才返回此字段<br>
	 *         status object 用户的最近一条微博信息字段 详细<br>
	 *         allow_all_comment boolean 是否允许所有人对我的微博进行评论，true：是，false：否<br>
	 *         avatar_large string 用户头像地址（大图），180×180像素<br>
	 *         avatar_hd string 用户头像地址（高清），高清头像原图<br>
	 *         verified_reason string 认证原因<br>
	 *         follow_me boolean 该用户是否关注当前登录用户，true：是，false：否<br>
	 *         online_status int 用户的在线状态，0：不在线、1：在线<br>
	 *         bi_followers_count int 用户的互粉数<br>
	 *         lang string 用户当前的语言版本，zh-cn：简体中文，zh-tw：繁体中文，en：英语<br>
	 *         失败：（json格式） <br>
	 *         error 错误<br>
	 *         error_code 错误码 <br>
	 *         error_description 错误描述
	 */
	public JSONObject getUserInfo(String accessToken, String uid)
			throws Exception {
		String url = null;
		String doc = null;
		JSONObject res = null;
		String msg = null;
		try {
			url = API_USER_INFO_URL;
			url = String.format(url, accessToken, uid);
			logger.info("GetUserInfo Params");
			logger.info("access_token=" + accessToken);
			logger.info("openId=" + uid);
			doc = HttpHelper.sendGet(url, "utf-8");
			// doc = HttpsHelper.get(url);
			logger.info("GetUserInfo Result:" + doc);
			if (doc == null || doc.length() <= 0)
				return this.json(-1, "获取用户信息失败");
			res = JSON.parseObject(doc);
			if (res == null || res.containsKey("error_code")) {
				msg = String.format("获取用户信息失败 原因：错误码:%s 消息:%s",
						res.get("error_code"), res.get("error_description"));
				return this.json(-1, msg);
			}
			res.put("nickname", res.get("screen_name"));
			return json(0, "ok", res);

		} catch (Exception e) {
			throw e;
		}
	}
}
