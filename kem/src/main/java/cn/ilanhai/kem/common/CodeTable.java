package cn.ilanhai.kem.common;

/**
 * 代码表 业务逻辑代码范围为[1-20000] 数据访问代码范围为[20001-40000]
 * 
 * @author he
 *
 */
public enum CodeTable {

	/**
	 * 所有业务逻辑中的未处理异常
	 */
	BL_UNHANDLED_EXCEPTION(1, "未知处理异常"),
	
	//已固定编号
	BL_COMMON_USER_NOT_LOGINED(2, "用户没有登陆"),
	BL_COMMON_ENTITY_NOTEXIST(3, "%s实体不存在"),
	BL_COMMON_USER_NOLIMIT(4, "用户无操作权限"),
	BL_COMMON_USER_DISABLE(5, "用户已禁用"),
	BL_COMMON_USER_TRIAL(9, "用户还在审核中,请耐心等待"),
	BL_COMMON_USER_MEMBER(10, "版本已过期"),
	BL_COMMON_USER_TRIALMEMBER(11, "试用已过期"),
	BL_COMMON_PUBLISH_STATUS(6,"未做发布设置"),
	BL_COMMON_USER_REEXIST(7,"用户已存在"),
	BL_COMMON_MATERIAL_ERROR(8,"模版或源文件缺失"),
	BL_PAYMENT_ERROR(512,"支付错误码"),
	BL_SMS_ERROR(513,"处理发短信错误"),
	BL_EMAIL_ERROR(514,"处理发短邮件错误"),
	//未确认编号
	BL_COMMON_PARAMETER_NOT_NULL(21, "参数错误"),
	BL_COMMON_PARAMETER_ONLY_ONE(21,"只能有一个参数"),
	BL_COMMON_PARAMETER_ITEM_LIST_NULL(22, "参数 %s 值错误"), 
	BL_COMMON_PARAMETER_ITEM_OBJECT_NULL(22, "参数 %s 值错误"), 
	BL_COMMON_PARAMETER_ITEM_STRING_NULLOREMPTY(22, "参数 %s 值错误"), 
	BL_COMMON_PARAMETER_ITEM_STRING_REGULAR(23,"参数 %s 值错误"), 
	BL_COMMON_PARAMETER_ITEM_STRING_NOT_EQUAL(24,"参数 %s 值不等于 %s "),
	BL_COMMON_PARAMETER_ITEM_STRING_MAXLENGTH(23,"参数 %s 值最大长度不能超过 %s 字"),
	BL_COMMON_PARAMETER_ITEM_NUMBER_NOT_EQUAL(25,"参数 %s 值不等于 %s "),
	BL_COMMON_PARAMETER_ITEM_NUMBER_NOT_NULL(25,"%s 值不能为空"),
	BL_COMMON_PARAMETER_ITEM_NUMBER_LESSTHAN(26,"参数 %s 值不能小于 %s "),
	BL_COMMON_PARAMETER_ITEM_NUMBER_GREATERTHAN(27, "参数 %s 值不能大于 %s "),
	BL_COMMON_PARAMETER_ITEM_NUMBER_BETWEEN(28,"参数 %s 取值只能在[%s-%s]范围内"),
	BL_COMMON_PARAMETER_ITEM_ENUM_NOT_EXISTS(29, "参数 %s 值在枚举中不存在"), 
	BL_COMMON_GET_DAO(30, "获取 %s 数据访问对象出错"), 
	BL_COMMON_SAVE_DOAMIN(31, "保存 %s 失败"),
	BL_COMMON_DELETE_DOAMIN(32, "删除 %s 失败"),
	BL_COMMON_UPDATE_DOAMIN(33, "更新 %s 失败"),
	BL_COMMON_GET_DOAMIN(34, "获取 %s 失败"),
	
	BL_USER_RESOURCE_DOAMIN(35, "余额不足，充值/升级后使用"),
	
	
	BL_IMGVALICODE_EXPIRE(12, "验证码过期"), 
	BL_IMGVALICODE_CREATE_IMAGER_ERROR(12, "验证码错误"),
	BL_IMGVALICODE_ERROR(12, "验证码错误"),
	BL_IMGVALICODE_VALIDATED(11, "验证码不可被重复校验"), 
	
	BL_SMSVALICODE_REQUESTDTO(10, "短信验证码请求传输对象错误"),
	BL_SMSVALICODE_VALIDATED(11, "验证码不可被重复校验"), 
	BL_SMSVALICODE_TIMTOUT(12, "验证码已超时"), 
	BL_SMSVALICODE_WRONGCODE(13, "验证码错误"),
	BL_WORK_ISNOT_FINISHED(14, "事务未全部通过错误"),
	
	BL_FRONTUSER_LOGIN_ERROR(16,"用户名或者密码错误"),
	BL_FRONTUSER_MODIFY_LOGIN_PWD_ERROR(22, "旧密码错误"),
	BL_FRONTUSER_PHONE_REPEAT(23, "该手机号已注册"),
	BL_COMPANYUSER_PHONE_REPEAT(23, "账号已存在于系统"),

	BL_FRONTUSER_OLDEQUSENEW_PASSWORD(24, "新旧密码相同"),
	BL_SESSION_STATE_ERROR(17,"会话状态错误"),
	BL_SESSION_STATE_USERID_ERROR(33,"获取会话状态里用户编号错误"),
	BL_SESSION_WRONGCLIENT(18,"客户端类型错误"),
	
	BL_BACKTUSER_LOGINNAME_ERROR(19,"用户名格式错误"),
	BL_BACKTUSER_LOGINPWD_ERROR(20,"密码格式错误"),
	BL_BACKTUSER_LOGINPWD_NOTNULL(20,"密码不能为空"),
	BL_BACKUSER_LOGIN_ERROR(21,"用户名或者密码错误"),
	
	BL_TAG_ADDEXISTS_ERROR(34,"添加 %s 名已存在"),
	BL_TAG_ADDNAME_ERROR(34,"添加 %s 名格式不正确 不能出现特殊字符"),
	BL_TAG_UPDATEId_ERROR(35,"更新 %s id错误"),
	
	BL_IMG_PATH_ERROR(36,"图片路径错误"),
	
    BL_TEMPLATE_PUBLISH_STATUS(22,"发布模板状态错误"),
    BL_TEMPLATE_SAVE_STATUS(22,"保存模板状态错误"),
    BL_TEMPLATE_NAME_ERROR(44,"模版名称错误,长度不超过20个字"),
    BL_TEMPLATE_STATE_ERROR(45,"模版状态错误"),
    
    BL_ACTIVEPLUGIN_SAVESETTING_ERROR(99,"保存设置信息错误"),
    BL_ACTIVEPLUGIN_DRAWPRIZE_ERROR(100,"抽奖错误"),
    BL_ACTIVEPLUGIN_LOADOPTIONS_ERROR(101,"生成奖项异常"),
    BL_ACTIVEPLUGIN_ADDPRIZEINFO(102,"填写领奖信息异常"),
    
    BL_UNRIGHTS_TIMES_ERROR(200,"去版权次数不足"),
    BL_UNRIGHTS_USER_ERROR(201,"该用户没有该推广去版权权限"),
    
    BL_CUSTOMER_NULL_ERROR(202,"有效客户信息为空"),
    BL_CUSTOMER_TIMES_ERROR(203,"当前用户版本最多支持一次导入%s条"),
    BL_CUSTOMER_IMPL_ERROR(204,"当前用户没有导入权限"),
    
    BL_ADDMATERIALTYPE_ERROR(500,"该添加类型已存在"),
    
    BL_IP_ERROR(5001,"ip错误"),
    BL_TASKID_ERROR(5001,"taskid不能包含中文"),
    BL_TASKID_ERROR_NULL(5001,"taskid不能为空"),

    BL_SOLRAPI_REQUEST_ERROR(5002,"服务器正在休息，请稍后再试"),

	BL_DATA_ERROR(20001,"数据库访问错误");

	
	private int value;
	private String desc;

	private CodeTable(int value, String desc) {
		this.value = value;
		this.desc = desc;
	}

	public int getValue() {
		return this.value;
	}

	public String getDesc() {
		return this.desc;
	}

	@Override
	public String toString() {
		return String.format("value:%s desc:%s", this.value, this.desc);
	}
}
