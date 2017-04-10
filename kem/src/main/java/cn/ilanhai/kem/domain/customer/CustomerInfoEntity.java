package cn.ilanhai.kem.domain.customer;

import cn.ilanhai.framework.app.domain.AbstractEntity;
import cn.ilanhai.framework.common.exception.BlAppException;
import cn.ilanhai.framework.uitl.ExpressionMatchUtil;
import cn.ilanhai.framework.uitl.Str;
import cn.ilanhai.kem.bl.BLContextUtil;
import cn.ilanhai.kem.common.CodeTable;

public class CustomerInfoEntity extends AbstractEntity {
	private static final long serialVersionUID = -6687504826747471003L;
	private String customerId;
	private String customerKey;
	private String customerValue;

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getCustomerKey() {
		return customerKey;
	}

	public void setCustomerKey(String customerKey) {
		this.customerKey = customerKey;
	}

	public String getCustomerValue() {
		return customerValue;
	}

	public void setCustomerValue(String customerValue) {
		this.customerValue = customerValue;
	}

	/**
	 * 信息key<br>
	 * name:姓名<br>
	 * phone :手机号<br>
	 * qq :qq号<br>
	 * email :邮箱<br>
	 * address :地址<br>
	 * originate: 来源 
	 * extensionName : 来源稿件名称
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
	public static final String KEY_NAME = "name";
	public static final String KEY_PHONE = "phone";
	public static final String KEY_QQ = "qq";
	public static final String KEY_EMAIL = "email";
	public static final String KEY_ADDRESS = "address";
	public static final String KEY_ORIGINATE = "originate";
	public static final String KEY_EXTENSIONNAME = "extensionName";
	public static final String KEY_EXTENSIONID = "extensionId";
	public static final String KEY_INDUSTRY = "industry";
	public static final String KEY_COMPANY = "company";
	public static final String KEY_PROVINCE = "province";
	public static final String KEY_CITY = "city";
	public static final String KEY_AREA = "area";
	public static final String KEY_QQAUTOGRAPH = "qqAutograph";
	public static final String KEY_AUTOGRAPH = "autograph";
	public static final String KEY_REMARK = "remark";
	public static final String KEY_TYPE = "type";
	public static final String KEY_SEX = "sex";

	/**
	 * 请不要增加key
	 */
	public static final String[] KEYS = new String[] { KEY_NAME, KEY_PHONE, KEY_QQ, KEY_EMAIL, KEY_ADDRESS };

	/**
	 * 验证信息是否正确 非法信息直接过滤
	 * 
	 * @throws BlAppException
	 */
	public boolean checkInfo(){
		if (KEY_NAME.equals(this.customerKey)) {
			if (!ExpressionMatchUtil.isName(customerValue)) {
				// ct = CodeTable.BL_COMMON_PARAMETER_ITEM_STRING_MAXLENGTH;
				// throw new BlAppException(ct.getValue(),
				// "请输入不超过20个字且不包含非法字符的姓名");
				return false;
			}
		} else if (KEY_PHONE.equals(this.customerKey)) {
			// 验证电话是否正确
			if (!ExpressionMatchUtil.isPhoneNo(customerValue)) {
				// throw new
				// BlAppException(CodeTable.BL_ACTIVEPLUGIN_DRAWPRIZE_ERROR.getValue(),
				// "客户电话号码错误");
				return false;
			}
		} else if (KEY_EMAIL.equals(this.customerKey)) {
			// 验证电话是否正确
			if (!ExpressionMatchUtil.isEmailAddress(customerValue)) {
				// throw new
				// BlAppException(CodeTable.BL_ACTIVEPLUGIN_DRAWPRIZE_ERROR.getValue(),
				// "客户邮件地址错误");
				return false;
			}
		} else if (KEY_QQ.equals(this.customerKey)) {
			// 验证电话是否正确
			if (!ExpressionMatchUtil.isQQ(customerValue)) {
				// throw new
				// BlAppException(CodeTable.BL_ACTIVEPLUGIN_DRAWPRIZE_ERROR.getValue(),
				// "客户QQ错误");
				return false;
			}
		} else if (KEY_ADDRESS.equals(this.customerKey)) {
			return true;
		} else if (KEY_INDUSTRY.equals(this.customerKey)) {
			return true;
		} else if (KEY_COMPANY.equals(this.customerKey)) {
			return true;
		} else if (KEY_PROVINCE.equals(this.customerKey)) {
			return true;
		} else if (KEY_CITY.equals(this.customerKey)) {
			return true;
		} else if (KEY_AREA.equals(this.customerKey)) {
			return true;
		} else if (KEY_QQAUTOGRAPH.equals(this.customerKey)) {
			return true;
		} else if (KEY_AUTOGRAPH.equals(this.customerKey)) {
			return true;
		} else if (KEY_REMARK.equals(this.customerKey)) {
			return true;
		} else if (KEY_TYPE.equals(this.customerKey)) {
			return true;
		} else if (KEY_SEX.equals(this.customerKey)) {
			return true;
		} else {
			customerKey = null;
			customerValue = null;
			return false;
		}
		return true;
	}

	/**
	 * 验证信息是否正确 非法信息直接过滤
	 * 
	 * @throws BlAppException
	 */
	public void exceptionInfo() throws BlAppException {
		CodeTable ct = null;
		if (KEY_NAME.equals(this.customerKey)) {
			if (!Str.isNullOrEmpty(customerValue)) {
				BLContextUtil.valiParaItemStrLength(customerValue, 20, "姓名");
			}
		} else if (KEY_PHONE.equals(this.customerKey)) {
			// 验证电话是否正确
			if (!ExpressionMatchUtil.isPhoneNo(customerValue)) {
				throw new BlAppException(CodeTable.BL_ACTIVEPLUGIN_DRAWPRIZE_ERROR.getValue(), "客户电话号码错误");
			}
		} else if (KEY_EMAIL.equals(this.customerKey)) {
			// 验证电话是否正确
			if (!ExpressionMatchUtil.isEmailAddress(customerValue)) {
				throw new BlAppException(CodeTable.BL_ACTIVEPLUGIN_DRAWPRIZE_ERROR.getValue(), "客户邮件地址错误");
			}
		} else if (KEY_QQ.equals(this.customerKey)) {
			// 验证电话是否正确
			if (!ExpressionMatchUtil.isQQ(customerValue)) {
				throw new BlAppException(CodeTable.BL_ACTIVEPLUGIN_DRAWPRIZE_ERROR.getValue(), "客户QQ错误");
			}
		} else if (KEY_ADDRESS.equals(this.customerKey)) {
			if (!Str.isNullOrEmpty(customerValue)) {
				BLContextUtil.valiParaItemStrLength(customerValue, 50, "地址");
			}
		} else if (KEY_INDUSTRY.equals(this.customerKey)) {
			if (!Str.isNullOrEmpty(customerValue)) {
				BLContextUtil.valiParaItemStrLength(customerValue, 20, "行业");
			}
		} else if (KEY_COMPANY.equals(this.customerKey)) {
			if (!Str.isNullOrEmpty(customerValue)) {
				BLContextUtil.valiParaItemStrLength(customerValue, 50, "所在公司");
			}
		} else if (KEY_PROVINCE.equals(this.customerKey)) {
		} else if (KEY_CITY.equals(this.customerKey)) {
		} else if (KEY_AREA.equals(this.customerKey)) {
		} else if (KEY_QQAUTOGRAPH.equals(this.customerKey)) {
		} else if (KEY_AUTOGRAPH.equals(this.customerKey)) {
		} else if (KEY_REMARK.equals(this.customerKey)) {
		} else if (KEY_TYPE.equals(this.customerKey)) {
		} else if (KEY_SEX.equals(this.customerKey)) {
		} else {
			customerKey = null;
			customerValue = null;
		}
	}
}
