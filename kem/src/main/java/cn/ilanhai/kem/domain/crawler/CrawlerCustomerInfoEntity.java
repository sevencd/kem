package cn.ilanhai.kem.domain.crawler;

import cn.ilanhai.kem.domain.customer.CustomerInfoEntity;
/**
 * 
 * @Description 添加到客户管理   客户信息key value
 * @TypeName CrawlerCustomerInfoEntity
 * @time 2017年3月3日 上午10:05:21
 * @author csz
 */
public class CrawlerCustomerInfoEntity extends CustomerInfoEntity {
	private static final long serialVersionUID = -6687504826747471003L;
	/**
	 * 信息key<br>
	 * name:姓名<br>
	 * phone :手机号<br>
	 * qq :qq号<br>
	 * email :邮箱<br>
	 * address :地址<br>
	 * originate: 来源 extensionName : 来源稿件名称
	 * industry:行业
	 * company:公司
	 * province:省
	 * city:市
	 * area:区
	 * qqAutograph:qq签名
	 * autograph:签名
	 * remark:备注
	 * type:客户分类
	 */
	/*public static final String KEY_NAME = "name";
	public static final String KEY_PHONE = "phone";
	public static final String KEY_QQ = "qq";
	public static final String KEY_EMAIL = "email";
	public static final String KEY_ADDRESS = "address";
	public static final String KEY_ORIGINATE = "originate";
	public static final String KEY_EXTENSIONNAME = "extensionName";
	public static final String KEY_INDUSTRY = "industry";
	public static final String KEY_COMPANY = "company";
	public static final String KEY_PROVINCE = "province";
	public static final String KEY_CITY = "city";
	public static final String KEY_AREA = "area";
	public static final String KEY_QQAUTOGRAPH = "qqAutograph";
	public static final String KEY_AUTOGRAPH = "autograph";
	public static final String KEY_REMARK = "remark";
	public static final String KEY_TYPE = "type";*/

	public static final String KEY_HOWNTOWN_PROVINCE = "hometownProvince";
	public static final String KEY_IS_OPEN_WECHAT = "isOpenWeChat";
	public static final String KEY_JOB = "job";
	public static final String KEY_AGE = "age";

	
}
