package cn.ilanhai.kem.bl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.ilanhai.framework.app.RequestContext;
import cn.ilanhai.framework.app.dao.Dao;
import cn.ilanhai.framework.app.dao.DaoProxyFactory;
import cn.ilanhai.framework.app.dao.DefaultDaoProxyFactoryImpl;
import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.exception.AppException;
import cn.ilanhai.framework.common.exception.BlAppException;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.framework.common.exception.SessionContainerException;
import cn.ilanhai.framework.common.session.Session;
import cn.ilanhai.framework.common.session.enums.SessionStateType;
import cn.ilanhai.framework.uitl.ExpressionMatchUtil;
import cn.ilanhai.framework.uitl.Str;
import cn.ilanhai.kem.App;
import cn.ilanhai.kem.common.CodeTable;
import cn.ilanhai.kem.common.Constant;
import cn.ilanhai.kem.common.IntegerEnum;
import cn.ilanhai.kem.dao.bindhost.BindHostDao;
import cn.ilanhai.kem.dao.contacts.ContactsDao;
import cn.ilanhai.kem.domain.bindhost.SearchSysBind;
import cn.ilanhai.kem.domain.enums.ManuscriptType;
import cn.ilanhai.kem.domain.enums.PluginType;
import cn.ilanhai.kem.domain.enums.TerminalType;
import cn.ilanhai.kem.domain.enums.UserType;
import cn.ilanhai.kem.domain.paymentservice.PayConfig.PayConfigResponseEntity;
import cn.ilanhai.kem.util.KeyUtil;
import cn.ilanhai.kem.util.StringVerifyUtil;

public class BLContextUtil {
	private static DaoProxyFactory daoFactory = DefaultDaoProxyFactoryImpl.getInstance();
	
	private static String IP_REGULAR = "([1-9]|[1-9]//d|1//d{2}|2[0-4]//d|25[0-5])(//.(//d|[1-9]//d|1//d{2}|2[0-4]//d|25[0-5])){3}";
	//验证5c2baca6-ed5a-4c01-9588-a2b4d885ba09格式的TASK_ID,后续可能恢复此种格式的校验
	//private static String TASK_ID = "[a-z0-9]{8}-[a-z0-9]{4}-[a-z0-9]{4}-[a-z0-9]{4}-[a-z0-9]{12}";
	/*
	 * 验证非中文字符的TASK_ID
	 */
	private static String TASK_ID = "^[\\x01-\\x7f]*$";//[\u4e00-\u9fa5] 

	public static void valiPara(Entity entity) throws BlAppException {
		CodeTable ct;
		if (entity == null) {
			ct = CodeTable.BL_COMMON_PARAMETER_NOT_NULL;
			throw new BlAppException(ct.getValue(), ct.getDesc());
		}
	}

	public static void valiParaItemListNull(List<?> list, String desc) throws BlAppException {
		CodeTable ct;
		String tmp = null;
		if (list == null || list.size() <= 0) {
			ct = CodeTable.BL_COMMON_PARAMETER_ITEM_LIST_NULL;
			tmp = ct.getDesc();
			tmp = String.format(tmp, desc);
			throw new BlAppException(ct.getValue(), tmp);
		}
	}

	public static void valiParaItemMapNull(Map map, String desc) throws BlAppException {
		CodeTable ct;
		String tmp = null;
		if (map == null || map.size() <= 0) {
			ct = CodeTable.BL_COMMON_PARAMETER_ITEM_LIST_NULL;
			tmp = ct.getDesc();
			tmp = String.format(tmp, desc);
			throw new BlAppException(ct.getValue(), tmp);
		}
	}

	public static void valiParaItemObjectNull(Object value, String desc) throws BlAppException {
		CodeTable ct;
		String tmp = null;
		if (value == null) {
			ct = CodeTable.BL_COMMON_PARAMETER_ITEM_OBJECT_NULL;
			tmp = ct.getDesc();
			tmp = String.format(tmp, desc);
			throw new BlAppException(ct.getValue(), tmp);
		}
	}

	/**
	 * 判断字符串是否为null或者空字符串
	 * 
	 * @param value
	 *            需要被校验的字符串
	 * @param desc
	 *            参数名称
	 * @throws BlAppException
	 */
	public static void valiParaItemStrNullOrEmpty(String value, String desc) throws BlAppException {
		CodeTable ct;
		String tmp = null;
		if (Str.isNullOrEmpty(value)) {
			ct = CodeTable.BL_COMMON_PARAMETER_ITEM_STRING_NULLOREMPTY;
			tmp = ct.getDesc();
			tmp = String.format(tmp, desc);
			throw new BlAppException(ct.getValue(), tmp);
		}
	}

	public static void valiParaItemStrRegular(String regular, String value, String desc) throws BlAppException {
		CodeTable ct;
		String tmp = null;
		Pattern pattern = Pattern.compile(regular);
		Matcher matcher = pattern.matcher(value);
		if (!matcher.matches()) {
			ct = CodeTable.BL_COMMON_PARAMETER_ITEM_STRING_REGULAR;
			tmp = ct.getDesc();
			tmp = String.format(tmp, desc);
			throw new BlAppException(ct.getValue(), tmp);
		}
	}

	public static void valiParaItemStrLength(String value, int maxLength, String desc) throws BlAppException {
		CodeTable ct;
		String tmp = null;
		if (Str.isNullOrEmpty(value)) {
			ct = CodeTable.BL_COMMON_PARAMETER_ITEM_STRING_NULLOREMPTY;
			tmp = ct.getDesc();
			tmp = String.format(tmp, desc);
			throw new BlAppException(ct.getValue(), tmp);
		}
		if (value.length() > maxLength) {
			ct = CodeTable.BL_COMMON_PARAMETER_ITEM_STRING_MAXLENGTH;
			tmp = ct.getDesc();
			tmp = String.format(tmp, desc, maxLength);
			throw new BlAppException(ct.getValue(), tmp);
		}
	}

	public static void valiParaItemStrNotEqual(String rawValue, String value, boolean ignoreCase, String desc)
			throws BlAppException {
		CodeTable ct;
		String tmp = null;
		boolean flag = false;
		if (rawValue == null || value == null) {
			ct = CodeTable.BL_COMMON_PARAMETER_ITEM_STRING_NOT_EQUAL;
			tmp = ct.getDesc();
			tmp = String.format(tmp, desc);
			throw new BlAppException(ct.getValue(), tmp);
		}
		if (ignoreCase)
			flag = rawValue.equalsIgnoreCase(value);
		else
			flag = rawValue.equals(value);
		if (!flag) {
			ct = CodeTable.BL_COMMON_PARAMETER_ITEM_STRING_NOT_EQUAL;
			tmp = ct.getDesc();
			tmp = String.format(tmp, desc);
			throw new BlAppException(ct.getValue(), tmp);
		}
	}

	public static void valiParaItemNumNotEqual(int rawValue, int value, String desc) throws BlAppException {
		CodeTable ct;
		String tmp = null;
		if (rawValue != value) {
			ct = CodeTable.BL_COMMON_PARAMETER_ITEM_NUMBER_NOT_EQUAL;
			tmp = ct.getDesc();
			tmp = String.format(tmp, desc);
			throw new BlAppException(ct.getValue(), tmp);
		}
	}

	public static void valiParaItemIntegerNull(Integer value, String desc) throws BlAppException {
		CodeTable ct;
		String tmp = null;
		if (value == null) {
			ct = CodeTable.BL_COMMON_PARAMETER_ITEM_NUMBER_NOT_NULL;
			tmp = ct.getDesc();
			tmp = String.format(tmp, desc);
			throw new BlAppException(ct.getValue(), tmp);
		}
	}

	public static void valiParaItemNumLassThan(int minValue, int value, String desc) throws BlAppException {
		CodeTable ct;
		String tmp = null;
		if (value < minValue) {
			ct = CodeTable.BL_COMMON_PARAMETER_ITEM_NUMBER_LESSTHAN;
			tmp = ct.getDesc();
			tmp = String.format(tmp, desc, minValue);
			throw new BlAppException(ct.getValue(), tmp);
		}
	}

	public static void valiParaItemNumGreaterThan(int maxValue, int value, String desc) throws BlAppException {
		CodeTable ct;
		String tmp = null;
		if (value > maxValue) {
			ct = CodeTable.BL_COMMON_PARAMETER_ITEM_NUMBER_GREATERTHAN;
			tmp = ct.getDesc();
			tmp = String.format(tmp, desc, maxValue);
			throw new BlAppException(ct.getValue(), tmp);
		}
	}

	public static void valiParaItemNumBetween(int rawMinValue, int rawMaxValue, int value, String desc)
			throws BlAppException {
		CodeTable ct;
		String tmp = null;
		if (value < rawMinValue || value > rawMaxValue) {
			ct = CodeTable.BL_COMMON_PARAMETER_ITEM_NUMBER_BETWEEN;
			tmp = ct.getDesc();
			tmp = String.format(tmp, desc, rawMinValue, rawMaxValue);
			throw new BlAppException(ct.getValue(), tmp);
		}
	}

	public static void valiParaItemNumBetween(int rawMinValue, int rawMaxValue, int value, String desc, boolean flag)
			throws BlAppException {
		CodeTable ct;
		if (value < rawMinValue || value > rawMaxValue) {
			ct = CodeTable.BL_COMMON_PARAMETER_ITEM_NUMBER_BETWEEN;
			throw new BlAppException(ct.getValue(), desc);
		}
	}

	public static <T extends Enum> void valiParaItemEnumNotExists(Class<T> t, String value, String desc)
			throws BlAppException {
		CodeTable ct;
		String tmp = null;

		if (Enum.valueOf(t, value) == null) {
			ct = CodeTable.BL_COMMON_PARAMETER_ITEM_ENUM_NOT_EXISTS;
			tmp = ct.getDesc();
			tmp = String.format(tmp, desc);
			throw new BlAppException(ct.getValue(), tmp);
		}
	}

	public static void valiParaItemEnumNotExists(IntegerEnum t, Integer value, String desc) throws BlAppException {
		CodeTable ct;

		String tmp = null;

		if (t.valueOf(value) == null) {
			ct = CodeTable.BL_COMMON_PARAMETER_ITEM_ENUM_NOT_EXISTS;
			tmp = ct.getDesc();
			tmp = String.format(tmp, desc);
			throw new BlAppException(ct.getValue(), tmp);
		}
	}

	/**
	 * 校验Dao对象是否为空
	 * 
	 * @param value
	 *            需要被校验的Dao对象
	 * @param desc
	 *            dao的名称
	 * @throws BlAppException
	 */
	public static void valiDaoIsNull(Dao value, String desc) throws BlAppException {
		CodeTable ct;
		String tmp = null;
		if (value == null) {
			ct = CodeTable.BL_COMMON_GET_DAO;
			tmp = ct.getDesc();
			tmp = String.format(tmp, desc);
			throw new BlAppException(ct.getValue(), tmp);
		}

	}

	public static void valiSaveDomain(int value, String desc) throws BlAppException {
		CodeTable ct;
		String tmp = null;
		if (value < 0) {
			ct = CodeTable.BL_COMMON_SAVE_DOAMIN;
			tmp = ct.getDesc();
			tmp = String.format(tmp, desc);
			throw new BlAppException(ct.getValue(), tmp);
		}
	}

	/**
	 * 实体不可为空
	 * 
	 * @param value
	 *            需要被校验的实体
	 * @param desc
	 *            实体名称
	 * @throws BlAppException
	 */
	public static void valiDomainIsNull(Entity value, String desc) throws BlAppException {
		CodeTable ct;
		String tmp = null;
		if (value == null) {
			ct = CodeTable.BL_COMMON_GET_DOAMIN;
			tmp = ct.getDesc();
			tmp = String.format(tmp, desc);
			throw new BlAppException(ct.getValue(), tmp);
		}
	}

	public static String getSessionUserId(RequestContext context) throws SessionContainerException, BlAppException {
		String id = context.getSession().getParameter(Constant.KEY_SESSION_USERID, String.class);
		CodeTable ct;
		if (Str.isNullOrEmpty(id)) {
			ct = CodeTable.BL_SESSION_STATE_USERID_ERROR;
			throw new BlAppException(ct.getValue(), ct.getDesc());
		}
		return id;

	}

	public static String getSessionUserId(RequestContext context, boolean flag)
			throws SessionContainerException, BlAppException {
		String id = context.getSession().getParameter(Constant.KEY_SESSION_USERID, String.class);
		return id;
	}

	/**
	 * 迭代器转换为list
	 * 
	 * @param datas
	 * @return
	 */
	public static List<Entity> transformation(Iterator<Entity> datas) {
		List<Entity> result = new ArrayList<Entity>();
		while (datas.hasNext()) {
			result.add(datas.next());
		}
		return result;
	}

	/**
	 * String 转换为list
	 * 
	 * @param datas
	 * @return
	 * @throws BlAppException
	 */
	/**
	 * String 转换为list
	 * 
	 * @param datas
	 * @return
	 * @throws BlAppException
	 */
	public static List<String> transformationString(String str, boolean isCheck) throws BlAppException {
		String desc;
		if (isCheck) {
			desc = "标签";
		} else {
			desc = "关键词";
		}
		valiParaItemStrNullOrEmpty(str, "请填写" + desc + "哦", false);

		List<String> result = new ArrayList<String>();
		String srts[] = str.split(",");
		for (String string : srts) {
			string = string.trim();
			if (!ExpressionMatchUtil.isName(string)) {
				throw new BlAppException(CodeTable.BL_TEMPLATE_PUBLISH_STATUS.getValue(),
						"试试只包含汉字、数字或者英文字母的" + desc + "哦");
			}
			if (!isCheck || (string.length() > 0 && string.length() <= Constant.TAG_LENGTH)) {
				result.add(string);
			} else {
				throw new BlAppException(-1, desc + "太长了哦,最多只允许" + Constant.TAG_LENGTH + "个字哦");
			}
		}
		if (result.size() > Constant.TAG_NUM) {
			throw new BlAppException(-1, desc + "太多了哦,最多只允许" + Constant.TAG_NUM + "个" + desc + "哦");
		}
		return result;
	}
	
	public static List<String> transformationStringCustomerTag(String str, boolean isCheck) throws BlAppException {
		String desc = "标签";
		if (Str.isNullOrEmpty(str)) {
			return null;
		}
		List<String> result = new ArrayList<String>();
		String srts[] = str.split(",");
		for (String string : srts) {
			string = string.trim();
			if (!ExpressionMatchUtil.isName(string)) {
				throw new BlAppException(CodeTable.BL_TEMPLATE_PUBLISH_STATUS.getValue(),
						"试试只包含汉字、数字或者英文字母的" + desc + "哦");
			}
			if (!isCheck || (string.length() > 0 && string.length() <= Constant.TAG_LENGTH)) {
				result.add(string);
			} else {
				throw new BlAppException(-1, desc + "太长了哦,最多只允许" + Constant.TAG_LENGTH + "个字哦");
			}
		}
		if (result.size() > Constant.TAG_NUM) {
			throw new BlAppException(-1, desc + "太多了哦,最多只允许" + Constant.TAG_NUM + "个" + desc + "哦");
		}
		return result;
	}

	public static void valiParaItemStrNullOrEmpty(String value, String desc, boolean flag) throws BlAppException {
		CodeTable ct;
		if (Str.isNullOrEmpty(value)) {
			ct = CodeTable.BL_COMMON_PARAMETER_ITEM_STRING_NULLOREMPTY;
			throw new BlAppException(ct.getValue(), desc);
		}
	}

	/**
	 * 验证删除
	 * 
	 * @param value
	 * @param desc
	 * @throws BlAppException
	 */
	public static void valiDeleteDomain(int value, String desc) throws BlAppException {
		CodeTable ct;
		String tmp = null;
		if (value <= 0) {
			ct = CodeTable.BL_COMMON_DELETE_DOAMIN;
			tmp = ct.getDesc();
			tmp = String.format(tmp, desc);
			throw new BlAppException(ct.getValue(), tmp);
		}
	}

	/**
	 * 验证更新
	 * 
	 * @param value
	 * @param desc
	 * @throws BlAppException
	 */
	public static void valiUpdateDomain(int value, String desc) throws BlAppException {
		CodeTable ct;
		String tmp = null;
		if (value <= 0) {
			ct = CodeTable.BL_COMMON_DELETE_DOAMIN;
			tmp = ct.getDesc();
			tmp = String.format(tmp, desc);
			throw new BlAppException(ct.getValue(), tmp);
		}
	}

	/**
	 * boolean不能为null
	 * 
	 * @param value
	 * @param desc
	 * @throws BlAppException
	 */
	public static void valiParaItemBooleanNull(Boolean value, String desc) throws BlAppException {
		CodeTable ct;
		String tmp = null;
		if (value == null) {
			ct = CodeTable.BL_COMMON_PARAMETER_ITEM_STRING_NULLOREMPTY;
			tmp = ct.getDesc();
			tmp = String.format(tmp, desc);
			throw new BlAppException(ct.getValue(), tmp);
		}
	}

	/**
	 * 参数不能为null
	 * 
	 * @param entity
	 *            需要被校验的参数
	 * @param desc
	 *            被验证的参数名称
	 * @throws BlAppException
	 */
	public static void valiParaNotNull(Object entity, String desc) throws BlAppException {
		CodeTable ct;
		String tmp = null;
		if (entity == null) {
			ct = CodeTable.BL_COMMON_PARAMETER_ITEM_STRING_NULLOREMPTY;
			tmp = ct.getDesc();
			tmp = String.format(tmp, desc);
			throw new BlAppException(ct.getValue(), tmp);
		}
	}

	public static void checkFrontUserLogined(RequestContext context) throws BlAppException {
		CodeTable ct;
		if (!context.getSession().getSessionState().getSessionStateType()
				.equals(SessionStateType.FRONT_USER_LOGINED_STATE)) {
			ct = CodeTable.BL_COMMON_USER_NOT_LOGINED;
			throw new BlAppException(ct.getValue(), "前台" + ct.getDesc());
		}
	}

	public static void checkUserLogined(RequestContext context) throws BlAppException {
		CodeTable ct;
		if (!context.getSession().getSessionState().getSessionStateType()
				.equals(SessionStateType.FRONT_USER_LOGINED_STATE)
				&& !context.getSession().getSessionState().getSessionStateType()
						.equals(SessionStateType.BACK_USER_LOGINED_STATE)) {
			ct = CodeTable.BL_COMMON_USER_NOT_LOGINED;
			throw new BlAppException(ct.getValue(), ct.getDesc());
		}
	}

	public static void checkFrontUserType(RequestContext context, UserType userType)
			throws SessionContainerException, BlAppException {
		if (userType == null) {
			return;
		}
		CodeTable ct;
		Session Session = context.getSession();
		UserType userTypeValue = Session.getParameter(Constant.KEY_SESSION_USERTYPE, UserType.class);
		if (!userType.equals(userTypeValue)) {
			ct = CodeTable.BL_COMMON_USER_NOLIMIT;
			throw new BlAppException(ct.getValue(), "用户类型错误," + UserType.valueOf(userType) + "可使用该功能");
		}
	}

	public static void checkBackUserLogined(RequestContext context) throws BlAppException {
		CodeTable ct;
		if (!context.getSession().getSessionState().getSessionStateType()
				.equals(SessionStateType.BACK_USER_LOGINED_STATE)) {
			ct = CodeTable.BL_COMMON_USER_NOT_LOGINED;
			throw new BlAppException(ct.getValue(), "后台" + ct.getDesc());
		}
	}

	public static Dao getDao(RequestContext requestContext, Class<?> c) throws DaoAppException, BlAppException {
		CodeTable ct;
		Dao dao = daoFactory.getDao(requestContext, c);
		if (dao == null) {
			ct = CodeTable.BL_COMMON_GET_DAO;
			String tmp = ct.getDesc();
			// codereview 这里的描述不对
			tmp = String.format(tmp, "专题");
			throw new BlAppException(ct.getValue(), tmp);
		}
		return dao;
	}

	public static boolean checkCreatePlugin(Integer pluginType) {
		return PluginType.FORMPLUGIN.getValue().equals(pluginType)
				|| PluginType.ACTIVEPLUGIN.getValue().equals(pluginType);
	}

	public static void checkCurrentUser(RequestContext requestContext, String userId)
			throws SessionContainerException, BlAppException {
		CodeTable ct;
		if (!getSessionUserId(requestContext).equals(userId)) {
			ct = CodeTable.BL_FRONTUSER_LOGIN_ERROR;
			throw new BlAppException(ct.getValue(), "无该权限");
		}
	}

	public static String rediscombination(String manuscriptId, Integer terminalType) throws BlAppException {
		String desc = null;
		ManuscriptType manuscriptType = KeyUtil.getKey(manuscriptId);
		switch (manuscriptType) {

		case EXTENSION:
			desc = "推广";
			break;
		case SPECIAL:
			desc = "专题";
			break;
		case EXCELLENTCASE:
			desc = "优秀案例";
			break;
		case TEMPLATE:
			desc = "模板";
			break;
		case DEF:
			desc = "未登陆搞件";
			break;
		}
		String key = "kem:";
		if (TerminalType.PC.getValue().equals(terminalType)) {
			key += "pc_";
		} else if (TerminalType.WAP.getValue().equals(terminalType)) {
			key += "wap_";
		} else {
			throw new BlAppException(-100, "获取" + desc + "类型错误");
		}

		key += manuscriptId;
		return key;
	}

	public static Map<String, String> redisValueForPublish(String manuscriptId, Integer terminalType, String apiHost,
			String url) throws BlAppException {
		url = StringVerifyUtil.unHttpAndPost(url);
		Map<String, String> redisMap = new HashMap<String, String>();
		redisMap.put("api_host", apiHost);
		redisMap.put("kid", manuscriptId);
		if (TerminalType.PC.getValue().equals(terminalType)) {
			redisMap.put("main_domain", url);
			redisMap.put("mobile_host", "");
		} else if (TerminalType.WAP.getValue().equals(terminalType)) {
			redisMap.put("main_domain", "");
			redisMap.put("mobile_host", url);
		} else {
			throw new BlAppException(-100, "稿件终端类型错误");
		}
		return redisMap;
	}

	public static String redisKeyForPublish(String manuscriptId) throws BlAppException {
		return "host:" + manuscriptId;
	}

	public static String getValue(RequestContext context, String key) {
		Map<String, Object> settings = null;
		if (key == null || key.length() <= 0)
			return null;
		settings = context.getApplication().getConfigure().getSettings();
		if (settings == null)
			return null;
		if (!settings.containsKey(key))
			return null;
		return (String) settings.get(key);
	}

	public static String getValue(App context, String key) {
		Map<String, Object> settings = null;
		if (key == null || key.length() <= 0)
			return null;
		settings = context.getConfigure().getSettings();
		if (settings == null)
			return null;
		if (!settings.containsKey(key))
			return null;
		return (String) settings.get(key);
	}

	public static String analysisUrl(App app, String url) throws BlAppException {
		String manuscriptId = "";
		if (Str.isNullOrEmpty(url)) {
			return manuscriptId;
		}
		String domainNamePreView = getValue(app, "deployPreViewUrl");
		String myDomainNamePreView = domainNamePreView.substring(12, domainNamePreView.length());
		String domainNamePublish = getValue(app, "deployPublishUrl");
		String myDomainNamePublish = domainNamePublish.substring(12, domainNamePublish.length());
		// 获取 url 包含的稿件id
		int domainIndex = -1;
		if (url.contains(myDomainNamePreView)) {
			throw new BlAppException(-100, "预览不收集数据");
		}
		if (url.contains(myDomainNamePublish)) {
			domainIndex = url.indexOf(myDomainNamePublish);
			if (domainIndex < 16) {
				throw new BlAppException(-100, "访问页面错误");
			}
			manuscriptId = url.substring(domainIndex - 16, domainIndex);
		}
		if (url.contains("/_1")) {
			manuscriptId = url.substring(url.indexOf("/_1") + 2, url.indexOf("/_1") + 2 + 16);
		}
		return manuscriptId;
	}

	public static String analysisHeader(String str) {
		if (Str.isNullOrEmpty(str)) {
			return "";
		}
		if ("none".equals(str)) {
			return "";
		}
		return str;
	}

	public static String analysisMainId(String content, String id) {
		// 替换 id
		return content.replaceAll("\\{\\{\\{%main_id%\\}\\}\\}", id);
	}

	/**
	 * 获取绑定域名URL 固定连接符
	 * 
	 * @param context
	 * @param host
	 * @param extensionId
	 * @return
	 * @throws DaoAppException
	 * @throws BlAppException
	 */
	public static String getRedisFlag(RequestContext context) throws DaoAppException, BlAppException {
		Dao dao = null;
		dao = BLContextUtil.getDao(context, BindHostDao.class);
		SearchSysBind sysKey = new SearchSysBind();
		sysKey.setKey("hostConfig");
		PayConfigResponseEntity sysConfigHost = (PayConfigResponseEntity) dao.query(sysKey, false);
		return sysConfigHost.getSysValue();
	}

	public static void vailIpParam(String ip) throws BlAppException {
		CodeTable ct;
		String tmp = null;
		if (isIP(ip)) {
			ct = CodeTable.BL_IP_ERROR;
			tmp = ct.getDesc();
			throw new BlAppException(ct.getValue(), tmp);
		}

	}

	private static boolean isIP(String ip) {
		if (Str.isNullOrEmpty(ip))
			return false;
		return Pattern.matches(IP_REGULAR, ip);
	}

	/*
	 * @Description 匹配采集任务taskId
	 * 
	 * @time 2017年3月7日 下午5:05:21
	 * 
	 * @author csz
	 */
	public static void valiTaskId(String taskId) throws BlAppException {
		CodeTable ct;
		String tmp = null;
		if (Str.isNullOrEmpty(taskId)){
			ct = CodeTable.BL_TASKID_ERROR_NULL;
			tmp = ct.getDesc();
			throw new BlAppException(ct.getValue(), tmp);
		}
		if (!Pattern.matches(TASK_ID, taskId)) {
			ct = CodeTable.BL_TASKID_ERROR;
			tmp = ct.getDesc();
			throw new BlAppException(ct.getValue(), tmp);
		}

	}
	/**
	 * 素组是否包含该内容
	 * 
	 * @param arr
	 * @param targetValue
	 * @return
	 */
	public static boolean useList(List<String> arr, String targetValue) {
		return arr.contains(targetValue);
	}

	/**
	 * 检查名称
	 * 
	 * @throws BlAppException
	 */
	public static void valiName(String value, String desc, int nameSize) throws BlAppException {
		valiParaItemStrNullOrEmpty(value, "请填写" + desc + "哦", false);
		valiParaItemNumBetween(0, nameSize, value.length(), desc + "太长了哦,最多只允许" + nameSize + "个字哦", false);
		if (!ExpressionMatchUtil.isName(value)) {
			throw new BlAppException(CodeTable.BL_TEMPLATE_PUBLISH_STATUS.getValue(), "试试只包含汉字、数字或者英文字母的" + desc + "哦");
		}
	}

	/**
	 * 检查名称
	 * 
	 * @throws BlAppException
	 */
	public static void valiSummary(String value, String desc, int nameSize) throws BlAppException {
		valiParaItemStrNullOrEmpty(value, "请填写" + desc + "哦", false);
		valiParaItemNumBetween(-1, nameSize, value.length(), desc + "太长了哦,最多只允许" + nameSize + "个字哦", false);
	}

	/**
	 * 检查名称
	 * 
	 * @throws BlAppException
	 */
	public static void valiSummary(String value, String desc, int nameSize, boolean tag) throws BlAppException {
		if (Str.isNullOrEmpty(value)) {
			return;
		}
		valiParaItemNumBetween(-1, nameSize, value.length(), desc + "太长了哦,最多只允许" + nameSize + "个字哦", false);
	}

	/**
	 * 校验用户类型
	 * 
	 * @param context
	 *            上下文
	 * @param userType
	 *            如果用户是该用户类型，跑出异常
	 * @throws SessionContainerException
	 * @throws BlAppException
	 */
	public static UserType getFrontUserType(RequestContext context) throws SessionContainerException, BlAppException {
		Session session = context.getSession();
		// 获取当前用户类型
		return session.getParameter(Constant.KEY_SESSION_USERTYPE, UserType.class);
	}
	
	public static boolean isPrice(double price) throws BlAppException {
		Pattern patt = Pattern.compile("^(0|[1-9][0-9]{0,9})(\\.[0-9]{1,2})?$", Pattern.CASE_INSENSITIVE);
		Matcher matcher = patt.matcher(Double.toString(price));
		boolean isMatch = matcher.matches();
		if (!isMatch) {
			throw new BlAppException(-1, "价格最多保留两位小数并且大于0");
		}
		if (price <= 0) {
			throw new BlAppException(-1, "价格最多保留两位小数并且大于0");
		}
		
		return true;
	}
}
