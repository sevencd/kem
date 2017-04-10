package cn.ilanhai.kem.bl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import cn.ilanhai.framework.app.RequestContext;
import cn.ilanhai.framework.app.bl.AbstractBl;
import cn.ilanhai.framework.app.dao.Dao;
import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.exception.BlAppException;
import cn.ilanhai.framework.common.exception.SessionContainerException;
import cn.ilanhai.framework.common.session.Session;
import cn.ilanhai.framework.common.session.enums.SessionStateType;
import cn.ilanhai.framework.uitl.ExpressionMatchUtil;
import cn.ilanhai.framework.uitl.Str;
import cn.ilanhai.kem.common.CodeTable;
import cn.ilanhai.kem.common.Constant;
import cn.ilanhai.kem.common.IntegerEnum;
import cn.ilanhai.kem.domain.enums.PluginType;
import cn.ilanhai.kem.domain.enums.UserType;

public abstract class BaseBl extends AbstractBl {
	private Logger logger = Logger.getLogger(BaseBl.class);

	protected void valiPara(Entity entity) throws BlAppException {
		CodeTable ct;
		if (entity == null) {
			ct = CodeTable.BL_COMMON_PARAMETER_NOT_NULL;
			throw new BlAppException(ct.getValue(), ct.getDesc());
		}
		// logger.info("参数为：" + FastJson.bean2Json(entity));
	}

	protected void valiParaItemListNull(List<?> list, String desc) throws BlAppException {
		CodeTable ct;
		String tmp = null;
		if (list == null || list.size() <= 0) {
			ct = CodeTable.BL_COMMON_PARAMETER_ITEM_NUMBER_NOT_NULL;
			tmp = ct.getDesc();
			tmp = String.format(tmp, desc);
			throw new BlAppException(ct.getValue(), tmp);
		}
	}

	protected void valiParaItemMapNull(Map map, String desc) throws BlAppException {
		CodeTable ct;
		String tmp = null;
		if (map == null || map.size() <= 0) {
			ct = CodeTable.BL_COMMON_PARAMETER_ITEM_LIST_NULL;
			tmp = ct.getDesc();
			tmp = String.format(tmp, desc);
			throw new BlAppException(ct.getValue(), tmp);
		}
	}
	/**
	 * 
	 * @param value 被验证的字符串
	 * @param desc 业务名称，如密码不能为空，传参密码
	 * @throws BlAppException
	 */
	protected void valiParaItemStringNull(String value, String desc) throws BlAppException {
		CodeTable ct;
		String tmp = null;
		if (Str.isNullOrEmpty(value)) {
			ct = CodeTable.BL_COMMON_PARAMETER_ITEM_NUMBER_NOT_NULL;
			tmp = ct.getDesc();
			tmp = String.format(tmp, desc);
			throw new BlAppException(ct.getValue(), tmp);
		}
	}
	protected void valiParaItemObjectNull(Object value, String desc) throws BlAppException {
		CodeTable ct;
		String tmp = null;
		if (value == null) {
			ct = CodeTable.BL_COMMON_PARAMETER_ITEM_NUMBER_NOT_NULL;
			tmp = ct.getDesc();
			tmp = String.format(tmp, desc);
			throw new BlAppException(ct.getValue(), tmp);
		}
	}
	protected void valiParaItemStrNullOrEmpty(String value, String desc) throws BlAppException {
		CodeTable ct;
		String tmp = null;
		if (Str.isNullOrEmpty(value)) {
			ct = CodeTable.BL_COMMON_PARAMETER_ITEM_STRING_NULLOREMPTY;
			tmp = ct.getDesc();
			tmp = String.format(tmp, desc);
			throw new BlAppException(ct.getValue(), tmp);
		}
	}

	/**
	 * 检查名称
	 * 
	 * @throws BlAppException
	 */
	protected void valiName(String value, String desc, int nameSize) throws BlAppException {
		this.valiParaItemStrNullOrEmpty(value, "请填写" + desc + "哦", false);
		this.valiParaItemNumBetween(0, nameSize, value.length(), desc + "太长了哦,最多只允许" + nameSize + "个字哦", false);
		if (!ExpressionMatchUtil.isName(value)) {
			throw new BlAppException(CodeTable.BL_TEMPLATE_PUBLISH_STATUS.getValue(), "试试只包含汉字、数字或者英文字母的" + desc + "哦");
		}
	}
	
	/**
	 * 检查名称
	 * 
	 * @throws BlAppException
	 */
	protected void valiName(String value, String desc, int nameSize,boolean flag) throws BlAppException {
		this.valiParaItemNumBetween(0, nameSize, value.length(), desc + "太长了哦,最多只允许" + nameSize + "个字哦", false);
		if (!ExpressionMatchUtil.isName(value)) {
			throw new BlAppException(CodeTable.BL_TEMPLATE_PUBLISH_STATUS.getValue(), "试试只包含汉字、数字或者英文字母的" + desc + "哦");
		}
	}

	/**
	 * 检查名称
	 * 
	 * @throws BlAppException
	 */
	protected void valiSummary(String value, String desc, int nameSize) throws BlAppException {
		this.valiParaItemStrNullOrEmpty(value, "请填写" + desc + "哦", false);
		this.valiParaItemNumBetween(-1, nameSize, value.length(), desc + "太长了哦,最多只允许" + nameSize + "个字哦", false);
	}

	/**
	 * 检查名称
	 * 
	 * @throws BlAppException
	 */
	protected void valiSummary(String value, String desc, int nameSize, boolean tag) throws BlAppException {
		if (Str.isNullOrEmpty(value)) {
			return;
		}
		this.valiParaItemNumBetween(-1, nameSize, value.length(), desc + "太长了哦,最多只允许" + nameSize + "个字哦", false);
	}

	protected void valiParaItemStrNullOrEmpty(String value, String desc, boolean flag) throws BlAppException {
		CodeTable ct;
		if (Str.isNullOrEmpty(value)) {
			ct = CodeTable.BL_COMMON_PARAMETER_ITEM_STRING_NULLOREMPTY;
			throw new BlAppException(ct.getValue(), desc);
		}
	}

	protected void valiParaItemStrRegular(String regular, String value, String desc) throws BlAppException {
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

	protected void valiParaItemStrLength(String value, int maxLength, String desc) throws BlAppException {
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

	protected void valiParaItemStrLength(String value, int maxLength, String desc, boolean flag) throws BlAppException {
		CodeTable ct;
		String tmp = null;
		if (Str.isNullOrEmpty(value)) {
			return;
		}
		if (value.length() > maxLength) {
			ct = CodeTable.BL_COMMON_PARAMETER_ITEM_STRING_MAXLENGTH;
			tmp = ct.getDesc();
			tmp = String.format(tmp, desc, maxLength);
			throw new BlAppException(ct.getValue(), tmp);
		}
	}

	protected void valiParaItemStrNotEqual(String rawValue, String value, boolean ignoreCase, String desc)
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

	protected void valiParaItemNumNotEqual(int rawValue, int value, String desc) throws BlAppException {
		CodeTable ct;
		String tmp = null;
		if (rawValue != value) {
			ct = CodeTable.BL_COMMON_PARAMETER_ITEM_NUMBER_NOT_EQUAL;
			tmp = ct.getDesc();
			tmp = String.format(tmp, desc);
			throw new BlAppException(ct.getValue(), tmp);
		}
	}

	protected void valiParaItemIntegerNull(Number value, String desc) throws BlAppException {
		CodeTable ct;
		String tmp = null;
		if (value == null) {
			ct = CodeTable.BL_COMMON_PARAMETER_ITEM_NUMBER_NOT_NULL;
			tmp = ct.getDesc();
			tmp = String.format(tmp, desc);
			throw new BlAppException(ct.getValue(), tmp);
		}
	}

	protected void valiParaItemIntegerNull(Number value, String desc, boolean flag) throws BlAppException {
		CodeTable ct;
		if (value == null) {
			ct = CodeTable.BL_COMMON_PARAMETER_ITEM_NUMBER_NOT_NULL;
			throw new BlAppException(ct.getValue(), desc);
		}
	}

	protected void valiParaItemNumLassThan(int minValue, int value, String desc) throws BlAppException {
		CodeTable ct;
		String tmp = null;
		if (value < minValue) {
			ct = CodeTable.BL_COMMON_PARAMETER_ITEM_NUMBER_LESSTHAN;
			tmp = ct.getDesc();
			tmp = String.format(tmp, desc, minValue);
			throw new BlAppException(ct.getValue(), tmp);
		}
	}

	protected void valiParaItemNumGreaterThan(int maxValue, int value, String desc) throws BlAppException {
		CodeTable ct;
		String tmp = null;
		if (value > maxValue) {
			ct = CodeTable.BL_COMMON_PARAMETER_ITEM_NUMBER_GREATERTHAN;
			tmp = ct.getDesc();
			tmp = String.format(tmp, desc, maxValue);
			throw new BlAppException(ct.getValue(), tmp);
		}
	}

	protected void valiParaItemNumBetween(int rawMinValue, int rawMaxValue, int value, String desc)
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

	protected void valiParaItemNumBetween(int rawMinValue, int rawMaxValue, int value, String desc, boolean flag)
			throws BlAppException {
		CodeTable ct;
		if (value < rawMinValue || value > rawMaxValue) {
			ct = CodeTable.BL_COMMON_PARAMETER_ITEM_NUMBER_BETWEEN;
			throw new BlAppException(ct.getValue(), desc);
		}
	}

	@SuppressWarnings("unchecked")
	protected <T extends Enum> void valiParaItemEnumNotExists(Class<T> t, String value, String desc)
			throws BlAppException {
		CodeTable ct;
		String tmp = null;
		try {

			if (Enum.valueOf(t, value) == null) {
				ct = CodeTable.BL_COMMON_PARAMETER_ITEM_ENUM_NOT_EXISTS;
				tmp = ct.getDesc();
				tmp = String.format(tmp, desc);
				throw new BlAppException(ct.getValue(), tmp);
			}

		} catch (Exception e) {
			ct = CodeTable.BL_COMMON_PARAMETER_ITEM_ENUM_NOT_EXISTS;
			tmp = ct.getDesc();
			tmp = String.format(tmp, desc);
			throw new BlAppException(ct.getValue(), tmp);
		}

	}

	protected void valiParaItemEnumNotExists(IntegerEnum t, Integer value, String desc) throws BlAppException {
		CodeTable ct;

		String tmp = null;

		if (t.valueOf(value) == null) {
			ct = CodeTable.BL_COMMON_PARAMETER_ITEM_ENUM_NOT_EXISTS;
			tmp = ct.getDesc();
			tmp = String.format(tmp, desc);
			throw new BlAppException(ct.getValue(), tmp);
		}
	}

	protected void valiDaoIsNull(Dao value, String desc) throws BlAppException {
		CodeTable ct;
		String tmp = null;
		if (value == null) {
			ct = CodeTable.BL_COMMON_GET_DAO;
			tmp = ct.getDesc();
			tmp = String.format(tmp, desc);
			throw new BlAppException(ct.getValue(), tmp);
		}

	}

	/**
	 * 验证活动插件是否设置
	 * 
	 * @param value
	 * @throws BlAppException
	 */
	protected void valiActivePulginIsNull(Entity value) throws BlAppException {
		CodeTable ct;
		if (value == null) {
			ct = CodeTable.BL_COMMON_ENTITY_NOTEXIST;
			throw new BlAppException(ct.getValue(), "请先对活动插件进行设置");
		}

	}

	/**
	 * 验证活动插件是否设置
	 * 
	 * @param value
	 * @throws BlAppException
	 */
	protected void valiActivePulginIsNull(Entity value, boolean falg) throws BlAppException {
		CodeTable ct;
		if (value == null) {
			ct = CodeTable.BL_COMMON_ENTITY_NOTEXIST;
			throw new BlAppException(ct.getValue(), "活动插件未设置");
		}

	}

	/**
	 * 验证表单插件是否存在
	 * 
	 * @param value
	 * @throws BlAppException
	 */
	protected void valiFormPulginIsNull(Entity value) throws BlAppException {
		CodeTable ct;
		String tmp = null;
		if (value == null) {
			ct = CodeTable.BL_COMMON_ENTITY_NOTEXIST;
			tmp = ct.getDesc();
			tmp = String.format(tmp, "表单插件");
			throw new BlAppException(ct.getValue(), tmp);
		}

	}

	protected void valiSaveDomain(int value, String desc) throws BlAppException {
		CodeTable ct;
		String tmp = null;
		if (value <= 0) {
			ct = CodeTable.BL_COMMON_SAVE_DOAMIN;
			tmp = ct.getDesc();
			tmp = String.format(tmp, desc);
			throw new BlAppException(ct.getValue(), tmp);
		}
	}

	protected void valiDomainIsNull(Entity value, String desc) throws BlAppException {
		CodeTable ct;
		String tmp = null;
		if (value == null) {
			ct = CodeTable.BL_COMMON_GET_DOAMIN;
			tmp = ct.getDesc();
			tmp = String.format(tmp, desc);
			throw new BlAppException(ct.getValue(), tmp);
		}
	}

	protected void valiDomainIsNull(Entity value, String desc, boolean flag) throws BlAppException {
		CodeTable ct;
		if (value == null) {
			ct = CodeTable.BL_COMMON_GET_DOAMIN;
			throw new BlAppException(ct.getValue(), desc);
		}
	}

	protected String getSessionUserId(RequestContext context) throws SessionContainerException, BlAppException {
		String id = context.getSession().getParameter(Constant.KEY_SESSION_USERID, String.class);
		CodeTable ct;
		if (Str.isNullOrEmpty(id)) {
			ct = CodeTable.BL_SESSION_STATE_USERID_ERROR;
			throw new BlAppException(ct.getValue(), ct.getDesc());
		}
		return id;

	}

	/**
	 * 迭代器转换为list
	 * 
	 * @param datas
	 * @return
	 */
	protected List<Entity> transformation(Iterator<Entity> datas) {
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
	protected List<String> transformationString(String str, boolean isCheck) throws BlAppException {
		String desc;
		if (isCheck) {
			desc = "标签";
		} else {
			desc = "关键词";
		}
		this.valiParaItemStrNullOrEmpty(str, "请填写" + desc + "哦", false);

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
				throw new BlAppException(-1, desc + "太长了哦,最多只允许"+Constant.TAG_LENGTH+"个字哦");
			}
		}
		if (result.size() > Constant.TAG_NUM) {
			throw new BlAppException(-1, desc + "太多了哦,最多只允许"+Constant.TAG_NUM+"个" + desc + "哦");
		}
		return result;
	}

	protected List<String> transformationTags(String str) throws BlAppException {
		if (Str.isNullOrEmpty(str)) {
			return null;
		}
		List<String> result = new ArrayList<String>();
		String srts[] = str.split(",");
		for (String string : srts) {
			string = string.trim();
			if (Str.isNullOrEmpty(str)) {
				return null;
			}
			if (!ExpressionMatchUtil.isName(string)) {
				throw new BlAppException(CodeTable.BL_TEMPLATE_PUBLISH_STATUS.getValue(), "试试只包含汉字、数字或者英文字母的标签哦");
			}
			if ((string.length() > 0 && string.length() <= Constant.TAG_LENGTH)) {
				result.add(string);
			} else {
				throw new BlAppException(-1, "标签太长了哦,最多只允许"+Constant.TAG_LENGTH+"个字哦");
			}
		}
		if (result.size() > Constant.TAG_NUM) {
			throw new BlAppException(-1, "标签太多了哦,最多只允许"+Constant.TAG_NUM+"个标签哦");
		}
		return result;
	}

	/**
	 * 校验单个标签
	 * 
	 * @param str
	 * @param isCheck
	 * @throws BlAppException
	 */
	protected void transformationBackTag(String str) throws BlAppException {
		String desc = "标签";
		this.valiParaItemStrNullOrEmpty(str, "请填写" + desc + "哦", false);

		if (!ExpressionMatchUtil.isName(str)) {
			throw new BlAppException(CodeTable.BL_TEMPLATE_PUBLISH_STATUS.getValue(), "试试只包含汉字、数字或者英文字母的" + desc + "哦");
		}
		if (str.length() > Constant.TAG_LENGTH) {
			throw new BlAppException(-1, desc + "太长了哦,最多只允许"+Constant.TAG_LENGTH+"个字哦");
		}
	}

	/**
	 * 验证删除
	 * 
	 * @param value
	 * @param desc
	 * @throws BlAppException
	 */
	protected void valiDeleteDomain(int value, String desc) throws BlAppException {
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
	protected void valiUpdateDomain(int value, String desc) throws BlAppException {
		CodeTable ct;
		String tmp = null;
		if (value <= 0) {
			ct = CodeTable.BL_COMMON_UPDATE_DOAMIN;
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
	protected void valiParaItemBooleanNull(Boolean value, String desc) throws BlAppException {
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
	 * @param desc
	 * @throws BlAppException
	 */
	protected void valiParaNotNull(Object entity, String desc) throws BlAppException {
		CodeTable ct;
		String tmp = null;
		if (entity == null) {
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
	 * @param desc
	 * @throws BlAppException
	 */
	protected void valiParaNotNull(Object entity, String desc, boolean flasg) throws BlAppException {
		CodeTable ct;
		if (entity == null) {
			ct = CodeTable.BL_COMMON_PARAMETER_ITEM_STRING_NULLOREMPTY;
			throw new BlAppException(ct.getValue(), desc);
		}
	}

	protected void checkFrontUserLogined(RequestContext context) throws BlAppException {
		CodeTable ct;
		if (!context.getSession().getSessionState().getSessionStateType()
				.equals(SessionStateType.FRONT_USER_LOGINED_STATE)) {
			ct = CodeTable.BL_COMMON_USER_NOT_LOGINED;
			throw new BlAppException(ct.getValue(), "前台" + ct.getDesc());
		}
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
	protected void checkFrontUserType(RequestContext context, UserType userType)
			throws SessionContainerException, BlAppException {
		if (userType == null) {
			return;
		}
		CodeTable ct;
		Session Session = context.getSession();
		// 获取当前用户类型
		UserType userTypeValue = Session.getParameter(Constant.KEY_SESSION_USERTYPE, UserType.class);
		if (!userType.equals(userTypeValue)) {
			ct = CodeTable.BL_COMMON_USER_NOLIMIT;
			throw new BlAppException(ct.getValue(), "用户类型错误," + UserType.valueOf(userType) + "可使用该功能");
		}
	}

	protected void checkBackUserLogined(RequestContext context) throws BlAppException {
		CodeTable ct;
		if (!context.getSession().getSessionState().getSessionStateType()
				.equals(SessionStateType.BACK_USER_LOGINED_STATE)) {
			ct = CodeTable.BL_COMMON_USER_NOT_LOGINED;
			throw new BlAppException(ct.getValue(), "后台" + ct.getDesc());
		}
	}

	protected boolean checkCreatePlugin(Integer pluginType) {
		return PluginType.FORMPLUGIN.getValue().equals(pluginType)
				|| PluginType.ACTIVEPLUGIN.getValue().equals(pluginType);
	}

	@SuppressWarnings("unchecked")
	protected String getValue(RequestContext context, String key) {
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

	protected void checkLimit(Integer startCount, Integer pageSize) throws BlAppException {
		valiParaNotNull(startCount, "开始条数");
		valiParaNotNull(pageSize, "查询条数");
		if (startCount < 0 || pageSize > 1000) {
			throw new BlAppException(-1, "查询条数和开始条数错误");
		}
	}

	protected void checkCurrentUser(RequestContext requestContext, String userId)
			throws SessionContainerException, BlAppException {
		CodeTable ct;
		if (!this.getSessionUserId(requestContext).equals(userId)) {
			ct = CodeTable.BL_FRONTUSER_LOGIN_ERROR;
			throw new BlAppException(ct.getValue(), "无该权限");
		}
	}
}
