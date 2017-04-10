package cn.ilanhai.kem.bl.integrate.ykd;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.ilanhai.framework.app.RequestContext;
import cn.ilanhai.framework.app.dao.Dao;
import cn.ilanhai.framework.common.exception.AppException;
import cn.ilanhai.framework.common.exception.BlAppException;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.framework.uitl.Str;
import cn.ilanhai.kem.bl.BLContextUtil;
import cn.ilanhai.kem.dao.user.frontuser.FrontuserDao;
import cn.ilanhai.kem.domain.enums.UserInfoType;
import cn.ilanhai.kem.domain.enums.UserStatus;
import cn.ilanhai.kem.domain.user.frontuser.FrontUserEntity;
import cn.ilanhai.kem.domain.user.frontuser.FrontUserInfoEntity;
import cn.ilanhai.kem.util.HttpHelper;
import cn.ilanhai.kem.util.MD5Util;

public class YkdIntegrateManager {
	private Dao dao;
	private String app_key = "4a12ffb4a9980a2e30c0e89c630e8733";
	private String app_secret = "a06bc630f2c9b8a50e79f9f459537b03";
	private String app_api_register = "http://new.testsz.youkedao.com/api/createuser/";
	private String app_api_login = "http://new.testsz.youkedao.com/";

	public void init(RequestContext context) throws DaoAppException, BlAppException {
		this.dao = BLContextUtil.getDao(context, FrontuserDao.class);
		this.app_key = BLContextUtil.getValue(context, "app_key");
		this.app_secret = BLContextUtil.getValue(context, "app_secret");
		this.app_api_register = BLContextUtil.getValue(context, "app_api_register");
		this.app_api_login = BLContextUtil.getValue(context, "app_api_login");
	}

	public JSONObject register(FrontUserEntity user) throws AppException {
		long ts = System.currentTimeMillis();
		Map<String, String> param = new HashMap<String, String>();
		param.put("username", user.getPhoneNo());
		param.put("shortname", Str.isNullOrEmpty(user.getUserName()) ? user.getUserName() : user.getPhoneNo());
		param.put("logo", user.getUserImg());
		param.put("phone", user.getPhoneNo());
		param.put("token", md5(user.getPhoneNo(), ts));
		param.put("ts", ts + "");
		param.put("app_key", app_key);
		return buildResult(param, 0);
	}

	private JSONObject buildResult(Map<String, String> param, int i) throws AppException {
		String result = null;
		JSONObject jsonResult = null;
		try {
			result = HttpHelper.sendPost(app_api_register, param, "UTF-8");
			jsonResult = JSONObject.parseObject(result);
		} catch (Exception e) {
			throw new BlAppException(-5, "有客到连接异常");
		}
		if (jsonResult == null) {
			throw new BlAppException(-6, "有客到服务异常");
		} else if (!new Integer(0).equals(jsonResult.getInteger("code"))) {
			throw new BlAppException(jsonResult.getInteger("code"), jsonResult.getString("message"));
		} else {
			JSONArray rows = jsonResult.getJSONArray("rows");
			if (rows.size() < 1) {
				if (i > 3) {
					throw new BlAppException(-3, "用户异常无法登陆微信拓客");
				}
				try {
					result = HttpHelper.sendPost(app_api_register, param, "UTF-8");
				} catch (Exception e) {
					throw new BlAppException(-5, "有客到连接异常");
				}
				buildResult(param, ++i);
			} else {
				JSONObject jsonUser = rows.getJSONObject(0);
				String ykdUser = jsonUser.getString("ykd_user");
				String ykdUserid = jsonUser.getString("userid");
				if (Str.isNullOrEmpty(ykdUser) || Str.isNullOrEmpty(ykdUserid)) {
					throw new BlAppException(-4, "用户异常无法登陆微信拓客");
				}
				// String ykdUserFlag = jsonUser.getString("user_flag");
				// String ykdUsername = jsonUser.getString("username");
				return jsonUser;
			}
		}
		return null;
	}

	public String ykdLogin(FrontUserEntity user) throws AppException {
		JSONObject ykdUser = register(user);
		String user_id = ykdUser.getString("userid");
		String user_name = ykdUser.getString("ykd_user");
		FrontUserInfoEntity info = new FrontUserInfoEntity();
		info.setUserID(user.getUserID());
		info.setInfo(user_id);
		info.setInfoType(UserInfoType.YKDUSER.getValue());
		info.setInfoState(UserStatus.ENABLE.getValue());
		dao.save(info);
		// BLContextUtil.valiSaveDomain(val, "有客到信息");
		long ts = System.currentTimeMillis();
		String url = String.format("%s?app_key=%s&user=%s&ts=%s&token=%s", app_api_login, app_key, user_name, ts,
				md5(user_name, ts));
		return url;
	}

	private String md5(String user, long timestamp) {
		String as = String.format("%s#%s#%s#%s", app_key, user, timestamp, app_secret);
		String token = MD5Util.MD5(as);
		return token;
	}

	public String getUserNameByYkdUser(String ykdUser) throws DaoAppException {
		FrontUserInfoEntity info = new FrontUserInfoEntity();
		info.setInfo(ykdUser);
		info.setInfoType(UserInfoType.YKDUSER.getValue());
		FrontUserEntity frontUserEntity = (FrontUserEntity) dao.query(info, false);
		if (frontUserEntity == null) {
			return null;
		}
		return frontUserEntity.getUserName();
	}
}
