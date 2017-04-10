package cn.ilanhai.kem.bl.integrate.zds;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.ilanhai.framework.app.RequestContext;
import cn.ilanhai.framework.app.dao.Dao;
import cn.ilanhai.framework.common.exception.AppException;
import cn.ilanhai.framework.common.exception.BlAppException;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.framework.common.exception.SessionContainerException;
import cn.ilanhai.framework.uitl.Str;
import cn.ilanhai.kem.bl.BLContextUtil;
import cn.ilanhai.kem.bl.member.MemberManager;
import cn.ilanhai.kem.bl.user.frontuser.FrontuserManger;
import cn.ilanhai.kem.bl.user.frontuser.UserRelationManger;
import cn.ilanhai.kem.dao.user.frontuser.FrontuserDao;
import cn.ilanhai.kem.domain.enums.UserInfoType;
import cn.ilanhai.kem.domain.member.MemberEntity;
import cn.ilanhai.kem.domain.paymentservice.PayInfo.PayInfoServiceInfoEntity;
import cn.ilanhai.kem.domain.user.frontuser.FrontUserEntity;
import cn.ilanhai.kem.domain.user.frontuser.FrontUserInfoEntity;
import cn.ilanhai.kem.util.DecriptUtil;
import cn.ilanhai.kem.util.HttpHelper;

public class ZdsIntegrateManager {
	private Logger logger = Logger.getLogger(ZdsIntegrateManager.class);
	private Dao dao;
	private RequestContext context;
	private String app_host = "http://api.sumszw.com";
	private String app_key = "5c85eaf50eca11e7bcbf1a5d847de508";
	private String app_id = "438255";
	private String accoount_type = "2";
	public String b2b = "44";
	public String b2btimes = "22";
	private String unit = "3";// 1年 2月 3天

	// 注册
	private String app_api_register = app_host + "/api/AgentUser/Register";
	// 一键登录
	private String app_api_login = app_host + "/api/AgentUser/GetMasterZhouAccessUrl";
	// 开通续费
	private String app_api_openRenewal = app_host + "/api/AgentMasterZhouOrder/OpenOrRenewal";
	// 续费时间
	private String app_api_renewal = app_host + "/api/AgentMasterZhouOrder/OpenOrRenewalByDay";
	// 获取已开通的套餐
	private String app_api_getServices = app_host + "/api/AgentMasterZhouOrder/GetMasterZhouServices";
	// 获取周大师套餐列表
	private String app_api_getPackageList = app_host + "api/AgentOrder/GetAgentMasterZhouPackageList";

	public void init(RequestContext context) throws DaoAppException, BlAppException {
		this.dao = BLContextUtil.getDao(context, FrontuserDao.class);
		this.context = context;
		app_host = BLContextUtil.getValue(context, "zds_app_host");
		app_key = BLContextUtil.getValue(context, "zds_app_key");
		app_id = BLContextUtil.getValue(context, "zds_app_id");
		accoount_type = BLContextUtil.getValue(context, "zds_accoount_type");
		b2b = BLContextUtil.getValue(context, "zds_b2b_coverage");
		b2btimes = BLContextUtil.getValue(context, "zds_b2b_code");
		unit = BLContextUtil.getValue(context, "zds_b2b_unit");
	}

	/**
	 * 舟大师注册
	 * 
	 * @param user
	 * @throws AppException
	 * @throws SessionContainerException
	 * @throws ParseException
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	public boolean register(FrontUserEntity user) throws DaoAppException, BlAppException {
		if (user == null) {
			return false;
		}
		String userId = user.getUserID();
		if (isRegisterd(userId)) {
			return false;
		}
		String timeStamp = null;
		try {
			timeStamp = getTimestep();
		} catch (ParseException e) {
			UserRelationManger.zdsLog(context, userId, timeStamp, timeStamp, e.getMessage());
		}
		String key = app_key;
		Random random = new Random();
		int seed = random.nextInt(100);
		// 加密
		String signature = signature(timeStamp, key, seed);
		// 封装入参
		Map<String, String> param = buildParamRegister(user, timeStamp, seed, signature);
		// 注册请求
		JSONObject data = doPost(this.app_api_register, param, userId);
		if (data != null) {
			String szUserName = data.getString("SzUserName");
			String szPassword = data.getString("SzPassword");
			if (Str.isNullOrEmpty(szPassword) || Str.isNullOrEmpty(szUserName)) {
				UserRelationManger.zdsLog(context, userId, JSONObject.toJSONString(param), data.toJSONString(),
						"Str.isNullOrEmpty(szPassword) || Str.isNullOrEmpty(szUserName)");
				return false;
			}
			FrontuserManger.saveUserInfoByType(context, UserInfoType.ZDSUSERNAME, szUserName, userId);
			FrontuserManger.saveUserInfoByType(context, UserInfoType.ZDSUSERPWD, szPassword, userId);
			return true;
		}
		return false;
	}

	/**
	 * 舟大师post 请求
	 * 
	 * @param api
	 *            请求接口
	 * @param param
	 *            请求参数
	 * @param user
	 *            请求用户
	 * @return
	 */
	private JSONObject doPost(String api, Map<String, String> param, String user) {
		JSONObject data = null;
		JSONObject jsonResult = null;
		String result = null;
		try {
			result = HttpHelper.sendPost(api, param, "UTF-8");
			jsonResult = JSONObject.parseObject(result);
			logger.info("result:" + result);
			if (jsonResult != null && jsonResult.getJSONObject("ExtensionData") != null
					&& new Integer(1).equals(jsonResult.getJSONObject("ExtensionData").getInteger("CallResult"))) {
				data = jsonResult.getJSONObject("Data");
				if (data == null) {
					UserRelationManger.zdsLog(context, user, JSONObject.toJSONString(param), result, "data == null");
				}
				return data;
			} else {
				UserRelationManger.zdsLog(context, user, JSONObject.toJSONString(param), result,
						"jsonResult != null && jsonResult.getJSONObject('ExtensionData') != null"
								+ "&& new Integer(1).equals(jsonResult.getJSONObject('ExtensionData').getInteger('CallResult'))");
			}
		} catch (Exception e) {
			UserRelationManger.zdsLog(context, user, JSONObject.toJSONString(param), result, e.getMessage());
		}
		return data;
	}

	/**
	 * 开通全网覆盖
	 * 
	 * @param userId
	 * @param payInfo
	 */
	public void openZdsCoverage(String userId, int count) {
		JSONObject data = null;
		Map<String, String> param = new HashMap<String, String>();
		try {
			String szUserName = FrontuserManger.getUserInfoByType(context, UserInfoType.ZDSUSERNAME, userId);
			if (!Str.isNullOrEmpty(szUserName)) {
				String timeStamp = getTimestep();
				String key = app_key;
				Random random = new Random();
				int seed = random.nextInt(100);
				// 加密
				String signature = signature(timeStamp, key, seed);
				param.put("Signature", signature);
				param.put("TimeStamp", timeStamp);
				param.put("Seed", seed + "");
				param.put("PartnerId", app_id);
				param.put("SzUserName", szUserName);
				param.put("PackageId", this.b2b);
				param.put("AccoountType", accoount_type);
				// param.put("Count", count + ""); 先屏蔽购买数量测试为1天
				param.put("Count", "1");
				if (!isOpenService(szUserName, this.b2b)) {
					param.put("OperationType", "1");
					param.put("ModeType", "1");
					param.put("Unit", unit);
					data = doPost(app_api_openRenewal, param, userId);
				} else {
					param.put("OperationType", "2");
					data = doPost(app_api_renewal, param, userId);
				}
			}
		} catch (Exception e) {
			UserRelationManger.zdsLog(context, userId, JSONObject.toJSONString(param),
					data == null ? "data==null" : data.toJSONString(), e.getMessage());
		}
	}

	/**
	 * 开通大师打码
	 * 
	 * @param userId
	 * @param payInfo
	 */
	public void openZdsCode(String userId, int count) {
		JSONObject data = null;
		Map<String, String> param = new HashMap<String, String>();
		try {
			String szUserName = FrontuserManger.getUserInfoByType(context, UserInfoType.ZDSUSERNAME, userId);
			if (!Str.isNullOrEmpty(szUserName)) {
				String timeStamp = getTimestep();
				String key = app_key;
				Random random = new Random();
				int seed = random.nextInt(100);
				// 加密
				String signature = signature(timeStamp, key, seed);
				param.put("Signature", signature);
				param.put("TimeStamp", timeStamp);
				param.put("Seed", seed + "");
				param.put("PartnerId", app_id);
				param.put("SzUserName", szUserName);
				param.put("PackageId", this.b2btimes);
				param.put("AccoountType", accoount_type);
				String codeTimes = FrontuserManger.getUserInfoByType(context, UserInfoType.ZDSUSECODE, userId);
				if (Str.isNullOrEmpty(codeTimes)) {
					codeTimes = "0";
				}
				int code = Integer.parseInt(codeTimes);
				if (code < count) {
					FrontuserManger.saveUserInfoByType(context, UserInfoType.ZDSUSECODE, count + "", userId);
					count -= code;
				}
				while (count-- > 0) {
					if (!isOpenService(szUserName, this.b2btimes)) {
						param.put("OperationType", "1");
						// data = doPost(app_api_openRenewal, param, userId);
						// 先屏蔽购买打码
					} else {
						param.put("OperationType", "2");
						// data = doPost(app_api_openRenewal, param, userId);
					}
				}
			}
		} catch (Exception e) {
			UserRelationManger.zdsLog(context, userId, JSONObject.toJSONString(param),
					data == null ? "data==null" : data.toJSONString(), e.getMessage());
		}
	}

	// {
	// if (map == null) {
	// PayInfoServiceEntity payInfoServiceEntity =
	// OrderManager.getServiceEntity(context, userId);
	// if (payInfoServiceEntity != null && payInfoServiceEntity.getInfo() !=
	// null) {
	// for (PayInfoServiceInfoEntity payInfoServiceInfoEntity :
	// payInfoServiceEntity.getInfo()) {
	// // 购买全网覆盖
	// if
	// (payInfoServiceInfoEntity.getType().equals(PayInfoServiceInfoEntity.MEMBER))
	// {
	// openB2b(szUserName, payInfoServiceInfoEntity, b2b);
	// }
	// // 购买打码
	// if
	// (payInfoServiceInfoEntity.getType().equals(PayInfoServiceInfoEntity.B2B))
	// {
	// openB2b(szUserName, payInfoServiceInfoEntity, b2btimes);
	// }
	// }
	// }
	// } else {
	// openB2b(szUserName, map.get(PayInfoServiceInfoEntity.B2B), b2b);
	// }
	// }

	private boolean isOpenService(String szUserName, String serviceId) {
		if (Str.isNullOrEmpty(szUserName) || Str.isNullOrEmpty(serviceId)) {
			return false;
		}
		String timeStamp = null;
		try {
			timeStamp = getTimestep();
		} catch (ParseException e) {
			UserRelationManger.zdsLog(context, szUserName, "timeStamp", timeStamp, e.getMessage());
			return false;
		}
		String key = app_key;
		Random random = new Random();
		int seed = random.nextInt(100);
		String signature = signature(timeStamp, key, seed);
		Map<String, String> param = new HashMap<String, String>();
		param.put("Signature", signature);
		param.put("TimeStamp", timeStamp);
		param.put("Seed", seed + "");
		param.put("PartnerId", app_id);
		param.put("SzUserName", szUserName);

		JSONObject data = doPost(app_api_getServices, param, szUserName);
		if (data != null) {
			JSONArray list = data.getJSONArray("List");
			if (list.size() <= 0) {
				return false;
			}
			JSONObject service = null;
			for (int i = 0; i < list.size(); i++) {
				service = list.getJSONObject(i);
				if (service != null && serviceId.equals(service.getString("PackageId"))) {
					return true;
				}
			}

		}
		return false;
	}

	// private void openB2b(String szUserName, PayInfoServiceInfoEntity
	// payInfoServiceInfoEntity, String B2btype)
	// throws ParseException, DaoAppException, BlAppException {
	// if (payInfoServiceInfoEntity == null || Str.isNullOrEmpty(szUserName) ||
	// Str.isNullOrEmpty(B2btype)) {
	// return;
	// }
	// if (B2btype.equals(b2b)) {
	// int count = payInfoServiceInfoEntity.getQuantity() / 12; // 年数
	// openOrRenewal(count, "1", szUserName, B2btype, "1");
	// count = payInfoServiceInfoEntity.getQuantity() % 12;// 剩余月数
	// if (count > 0) {
	// count = count / 6;
	// openOrRenewal(count, "2", szUserName, B2btype, "2");
	// }
	// } else if (B2btype.equals(b2btimes)) {// 打码
	// // openOrRenewal(payInfoServiceInfoEntity.getQuantity(), "1",
	// // szUserName, B2btype, "1");
	// }
	// }

	public String zdsLogin(FrontUserEntity user) throws ParseException, DaoAppException, BlAppException {
		if (user == null) {
			return "";
		}
		String userId = user.getUserID();
		if (Str.isNullOrEmpty(userId)) {
			return "";
		}
		if (!isRegisterd(userId)) {
			throw new BlAppException(-4, "请先开通B2B服务");
		}

		MemberEntity memberEntity = MemberManager.getMemberInfo(context, userId);
		if (memberEntity == null || memberEntity.getStatus() == 0) {
			throw new BlAppException(-3, "请先续费B2B服务");
		}
		String szUserName = FrontuserManger.getUserInfoByType(context, UserInfoType.ZDSUSERNAME, userId);
		// String szPassword = FrontuserManger.getUserInfoByType(context,
		// UserInfoType.ZDSUSERPWD, userId);

		String timeStamp = getTimestep();
		String key = app_key;
		Random random = new Random();
		int seed = random.nextInt(100);
		// 加密
		String signature = signature(timeStamp, key, seed);

		// 封装入参
		StringBuffer param = buildParamLogin(szUserName, userId, timeStamp, seed, signature);

		String result = null;
		JSONObject jsonResult = null;
		try {
			// 注册api请求
			result = HttpHelper.sendGet(app_api_login + param.toString(), "UTF-8");
			jsonResult = JSONObject.parseObject(result);
		} catch (Exception e) {
			throw new BlAppException(-5, "舟大师连接异常");
		}
		logger.info("result:" + result);
		if (jsonResult != null && jsonResult.getJSONObject("ExtensionData") != null
				&& new Integer(1).equals(jsonResult.getJSONObject("ExtensionData").getInteger("CallResult"))) {
			JSONObject data = jsonResult.getJSONObject("Data");
			if (data == null) {
				throw new BlAppException(-10, "舟大师登录数据异常");
			}
			String url = data.getString("Url");
			if (Str.isNullOrEmpty(url)) {
				throw new BlAppException(-10, "舟大师登录数据异常");
			}
			return url;
		} else {
			throw new BlAppException(-11, "舟大师登录失败");
		}
	}

	/**
	 * 开通或续费
	 * 
	 * @param dayTimes
	 * @return
	 * @throws ParseException
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	// public boolean openOrRenewal(int count, String unit, String zdsuser,
	// String packageId, String operationType)
	// throws ParseException, DaoAppException, BlAppException {
	// if (count == 0) {
	// return false;
	// }
	// if (Str.isNullOrEmpty(unit) || Str.isNullOrEmpty(zdsuser) ||
	// Str.isNullOrEmpty(packageId)
	// || Str.isNullOrEmpty(operationType)) {
	// return false;
	// }
	// //
	// String timeStamp = getTimestep();
	// String key = app_key;
	// Random random = new Random();
	// int seed = random.nextInt(100);
	// // 加密
	// String signature = signature(timeStamp, key, seed);
	// Map<String, String> params = buildParamOpenOrRenewal(timeStamp, seed,
	// signature);
	// if ("1".equals(operationType)) {
	// return open(count, unit, zdsuser, packageId, operationType, params);
	// } else if ("2".equals(operationType)) {
	// // TODO 续费
	// return true;
	// }
	// return false;
	// }

	// private boolean open(int count, String unit, String zdsuser, String
	// packageId, String operationType,
	// Map<String, String> params) throws BlAppException {
	// params.put("SzUserName", zdsuser);
	// params.put("PackageId", packageId);
	// params.put("OperationType", operationType);
	// params.put("AccoountType", accoount_type);
	// params.put("Count", count + "");
	// if (!packageId.equals(this.b2btimes)) {
	// params.put("ModeType", "1");
	// params.put("Unit", unit);
	// } else {
	// params.put("ModeType", "2");
	// params.put("PackageBagId", "32");
	// }
	//
	// String result = null;
	// JSONObject jsonResult = null;
	// try {
	// // 开通api请求
	// result = HttpHelper.sendPost(app_api_openRenewal, params, "UTF-8");
	// jsonResult = JSONObject.parseObject(result);
	// } catch (Exception e) {
	// UserRelationManger.zdsLog(context, zdsuser,
	// JSONObject.toJSONString(params), result, e.getMessage());
	// }
	// if (jsonResult != null && jsonResult.getJSONObject("ExtensionData") !=
	// null
	// && new
	// Integer(1).equals(jsonResult.getJSONObject("ExtensionData").getInteger("CallResult")))
	// {
	// JSONObject data = jsonResult.getJSONObject("Data");
	// if (data == null) {
	// UserRelationManger.zdsLog(context, zdsuser,
	// JSONObject.toJSONString(params), result, "data == null");
	// }
	// return true;
	// } else {
	// UserRelationManger.zdsLog(context, zdsuser,
	// JSONObject.toJSONString(params), result,
	// "jsonResult != null && jsonResult.getJSONObject('ExtensionData') != null"
	// + "&& new
	// Integer(1).equals(jsonResult.getJSONObject('ExtensionData').getInteger('CallResult'))");
	// }
	// return true;
	// }

	/**
	 * 对入参进行加密用于验证
	 * 
	 * @param timeStamp
	 * @param key
	 * @param seed
	 * @return
	 */
	private String signature(String timeStamp, String key, int seed) {
		String[] signatureArray = { key, timeStamp, seed + "" };
		// 字典排序
		Arrays.sort(signatureArray);
		// 加密
		String signature = DecriptUtil.getSha1(signatureArray[0] + signatureArray[1] + signatureArray[2]);
		return signature;
	}

	/**
	 * 构建入参
	 * 
	 * @param user
	 * @param userId
	 * @param timeStamp
	 * @param seed
	 * @param signature
	 * @return
	 * @throws DaoAppException
	 * @throws BlAppException
	 */
	private Map<String, String> buildParamRegister(FrontUserEntity user, String timeStamp, int seed, String signature)
			throws DaoAppException, BlAppException {
		Map<String, String> param = new HashMap<String, String>();
		param.put("Signature", signature);
		param.put("TimeStamp", timeStamp);
		param.put("Seed", seed + "");
		param.put("PartnerId", app_id);
		param.put("CompanyName", timeStamp + "蓝海基业");
		param.put("LinkMan", timeStamp + "蓝海基业");
		param.put("Mobile", FrontuserManger.getUserInfoByType(context, UserInfoType.PHONE, user.getUserID()));
		param.put("Email", timeStamp + "@ilanhai.com");
		param.put("OriginalUserName", Str.isNullOrEmpty(user.getUserName()) ? user.getUserName() : user.getPhoneNo());
		param.put("Address", "四川省成都市武侯区益州大道中航国际交流中心B座905");
		param.put("Telephone", "028-88888888");
		param.put("Province", "四川省");
		param.put("City", "成都市");
		return param;
	}

	/**
	 * 构建入参
	 * 
	 * @param user
	 * @param userId
	 * @param timeStamp
	 * @param seed
	 * @param signature
	 * @return
	 * @throws DaoAppException
	 * @throws BlAppException
	 */
	private Map<String, String> buildParamOpenOrRenewal(String timeStamp, int seed, String signature)
			throws DaoAppException, BlAppException {
		Map<String, String> param = new HashMap<String, String>();
		param.put("Signature", signature);
		param.put("TimeStamp", timeStamp);
		param.put("Seed", seed + "");
		param.put("PartnerId", app_id);
		return param;
	}

	/**
	 * 构建入参
	 * 
	 * @param user
	 * @param userId
	 * @param timeStamp
	 * @param seed
	 * @param signature
	 * @return
	 * @throws DaoAppException
	 * @throws BlAppException
	 */
	private StringBuffer buildParamLogin(String userName, String userId, String timeStamp, int seed, String signature)
			throws DaoAppException, BlAppException {
		StringBuffer param = new StringBuffer();
		param.append("?Signature=" + signature);
		param.append("&TimeStamp=" + timeStamp);
		param.append("&Seed=" + seed);
		param.append("&PartnerId=" + app_id);
		param.append("&UserName=" + userName);
		return param;
	}

	/**
	 * 是否已注册
	 * 
	 * @param userId
	 * @return
	 * @throws DaoAppException
	 * @throws BlAppException
	 */
	public boolean isRegisterd(String userId) throws DaoAppException, BlAppException {
		if (Str.isNullOrEmpty(userId)) {
			return true;
		}
		return !Str.isNullOrEmpty(FrontuserManger.getUserInfoByType(context, UserInfoType.ZDSUSERNAME, userId));
	}

	/**
	 * 
	 * @param ykdUser
	 * @return
	 * @throws DaoAppException
	 */
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

	/**
	 * 获取舟大师时间戳格式
	 * 
	 * @return
	 * @throws ParseException
	 */
	private String getTimestep() throws ParseException {
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		Date mydate = myFormatter.parse("1970-01-01");
		long day = (date.getTime() - mydate.getTime());
		return day / 1000 + "";
	}
}
