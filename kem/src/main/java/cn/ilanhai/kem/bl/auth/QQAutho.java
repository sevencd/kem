package cn.ilanhai.kem.bl.auth;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import cn.ilanhai.kem.util.HttpHelper;
import cn.ilanhai.kem.util.HttpsHelper;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * @author he
 *
 */
public class QQAutho extends Autho {
	private final Logger logger = Logger.getLogger(QQAutho.class);
	// 详情:http://wiki.connect.qq.com/%E4%BD%BF%E7%94%A8authorization_code%E8%8E%B7%E5%8F%96access_token
	private static final String AUTHORIZE = "response_type=code&client_id=%s&redirect_uri=%s&state=%s";
	// 详情:http://wiki.connect.qq.com/%E4%BD%BF%E7%94%A8authorization_code%E8%8E%B7%E5%8F%96access_token
	public static final String AUTHORIZE_PC_WAP = String.format(
			"https://graph.qq.com/oauth2.0/authorize?%s", AUTHORIZE);

	// 详情:http://wiki.connect.qq.com/%E4%BD%BF%E7%94%A8authorization_code%E8%8E%B7%E5%8F%96access_token
	private final String ACCESS_TOKEN = "grant_type=authorization_code&client_id=%s&client_secret=%s&code=%s&redirect_uri=%s";
	private final String ACCESS_TOKEN_PC = String.format(
			"https://graph.qq.com/oauth2.0/token?%s", ACCESS_TOKEN);
	// 详情:http://wiki.connect.qq.com/%E4%BD%BF%E7%94%A8authorization_code%E8%8E%B7%E5%8F%96access_token
	private final String ACCESS_TOKEN_WAP = String.format(
			"https://graph.z.qq.com/moc2/token?%s", ACCESS_TOKEN);
	private final String ACCESS_TOKEN_ROLLOVER = "grant_type=refresh_token&client_id=%s&client_secret=%s&refresh_token=%s";
	private final String ACCESS_TOKEN__ROLLOVER_PC = String.format(
			"https://graph.qq.com/oauth2.0/token?%s", ACCESS_TOKEN_ROLLOVER);
	// 详情:http://wiki.connect.qq.com/%E4%BD%BF%E7%94%A8authorization_code%E8%8E%B7%E5%8F%96access_token
	private final String ACCESS_TOKEN__ROLLOVER_WAP = String.format(
			"https://graph.z.qq.com/moc2/token?%s", ACCESS_TOKEN_ROLLOVER);
	private final String OPEN_ID = "access_token=%s";
	private final String OPEN_ID_PC = String.format(
			"https://graph.qq.com/oauth2.0/me?%s", OPEN_ID);
	// 详情:http://wiki.connect.qq.com/%E4%BD%BF%E7%94%A8authorization_code%E8%8E%B7%E5%8F%96access_token
	private final String OPEN_ID_WAP = String.format(
			"https://graph.z.qq.com/moc2/me?%s", OPEN_ID);
	private final String API = "%s?access_token=%s&oauth_consumer_key=%s&openid=%s";
	private final String API_PC_AND_WAP = String.format(
			"https://graph.qq.com/user/%s", API);

	public QQAutho() {
		this.mediaType = MediaType.qq;
		this.appId = "101346803";
		this.appSecret = "268efed6279d070672a585257410db5e";
		// APP ID：101346803
		// APP Key：268efed6279d070672a585257410db5e
	}

	@Override
	public JSONObject getAccessToken() throws Exception {
		JSONObject res = null;
		Map<String, String> tmp = null;
		String accessToken = null;
		String openId = null;
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
			tmp = this.reqAccessToken();
			logger.info("ReqAccessToken Result:" + tmp);
			if (tmp == null || tmp.containsKey("code")) {
				msg = "获取access token数据出错";
				if (tmp != null)
					msg = String.format("获取access token出错。 原因：错误码:%s 消息:%s",
							tmp.get("code"), tmp.get("msg"));
				return this.json(-1, msg);
			}
			accessToken = tmp.get("access_token");
			tmp = this.reqOpenId(accessToken);
			logger.info("ReqOpenId Result:" + tmp);
			if (tmp == null || tmp.containsKey("code")) {
				msg = "获取open id数据出错";
				if (tmp != null)
					msg = String.format("获取open id出错。 原因：错误码:%s 消息:%s",
							tmp.get("code"), tmp.get("msg"));
				return this.json(-1, msg);
			}
			res = new JSONObject();
			openId = tmp.get("openid");
			res.put("openid", openId);
			res.put("accessToken", accessToken);
			// res = this.getUserInfo(accessToken, openId);
			// logger.info("GetUserInfo Result:" + tmp);
			// if (res == null || !res.containsKey("ret"))
			// return this.json(-1, "获取用户信息失败");
			// if (res.getInteger("ret") != 0)
			// return this.json(-1, "获取用户信息失败");
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
	 * 注：Access_Token的有效期默认是3个月
	 * 
	 * @return 成功： <br>
	 *         access_token 授权令牌，Access_Token。<br>
	 *         expires_in 该access token的有效期，单位为秒。 <br>
	 *         refresh_token 在授权自动续期步骤中，获取新的Access_Token时需要提供的参数。 <br>
	 *         失败： <br>
	 *         code 错误码 <br>
	 *         msg 错误消息
	 */
	private Map<String, String> reqAccessToken() throws Exception {
		Map<String, String> res = null;
		String url = null;
		String doc = null;
		try {
			url = ACCESS_TOKEN_PC;
			if (source == Source.wap)
				url = ACCESS_TOKEN_WAP;
			url = String.format(url, this.appId, this.appSecret, this.code,
					this.redirectUri);
			// doc = HttpHelper.sendGet(url, "utf-8");
			doc = HttpsHelper.get(url);

			res = this.parserParamters(doc);
			return res;
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 获取openId
	 * 
	 * @param access_token
	 * @return 成功： <br>
	 *         client_id appId<br>
	 *         openid openId<br>
	 *         失败： <br>
	 *         code 错误码 <br>
	 *         msg 错误消息
	 */
	private Map<String, String> reqOpenId(String access_token) throws Exception {
		Map<String, String> res = null;
		String url = null;
		String doc = null;
		JSONObject tmp = null;
		try {
			url = OPEN_ID_PC;
			if (source == Source.wap)
				url = OPEN_ID_WAP;
			url = String.format(url, access_token);
			// doc = HttpHelper.sendGet(url, "utf-8");
			doc = HttpsHelper.get(url);

			if (source == Source.pc) {
				// callback(
				// {"client_id":"101346803","openid":"F7B9B27BEF992D3626EDAE9A120BE2A8"}
				// )
				doc = doc.replace("callback(", "");
				doc = doc.replace(")", "");
				doc = doc.replace(";", "").trim();
				tmp = JSON.parseObject(doc);
				res = new HashMap<String, String>();
				res.put("client_id", tmp.getString("client_id"));
				res.put("openid", tmp.getString("openid"));
			} else
				res = this.parserParamters(doc);
			return res;
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 获取用户信息
	 * 
	 * @param access_token
	 * @param openId
	 * @return 结果：<br>
	 *         ret 返回码 <br>
	 *         msg 如果ret<0，会有相应的错误信息提示，返回数据全部用UTF-8编码。 <br>
	 *         nickname 用户在QQ空间的昵称。 <br>
	 *         figureurl 大小为30×30像素的QQ空间头像URL。 <br>
	 *         figureurl_1 大小为50×50像素的QQ空间头像URL。 <br>
	 *         figureurl_2 大小为100×100像素的QQ空间头像URL。 <br>
	 *         figureurl_qq_1 大小为40×40像素的QQ头像URL。 <br>
	 *         figureurl_qq_2 大小为100×100像素的QQ头像URL。需要注意，不是所有的用户都拥有QQ的100x100的头像，
	 *         但40x40像素则是一定会有。 <br>
	 *         gender 性别。 如果获取不到则默认返回"男" <br>
	 *         is_yellow_vip 标识用户是否为黄钻用户（0：不是；1：是）。 <br>
	 *         vip 标识用户是否为黄钻用户（0：不是；1：是） <br>
	 *         yellow_vip_level 黄钻等级 <br>
	 *         level 黄钻等级 <br>
	 *         is_yellow_year_vip 标识是否为年费黄钻用户（0：不是； 1：是） <br>
	 */
	public JSONObject getUserInfo(String access_token, String openId)
			throws Exception {
		String url = null;
		String doc = null;
		JSONObject res = null;
		try {
			url = API_PC_AND_WAP;
			url = String.format(url, "get_user_info", access_token,
					this.getAppId(), openId);
			logger.info("GetUserInfo Params");
			logger.info("access_token=" + access_token);
			logger.info("openId=" + openId);
			// doc = HttpHelper.sendGet(url, "utf-8");
			doc = HttpsHelper.get(url);
			logger.info("GetUserInfo Result:" + doc);
			if (doc == null || doc.length() <= 0)
				return this.json(-1, "获取用户信息失败");
			res = JSON.parseObject(doc);
			if (res == null || !res.containsKey("ret"))
				return this.json(-1, "获取用户信息失败");
			if (res.getInteger("ret") != 0)
				return this.json(-1, "获取用户信息失败");
			return json(0, "ok", res);
		} catch (Exception e) {
			throw e;
		}
	}
}
