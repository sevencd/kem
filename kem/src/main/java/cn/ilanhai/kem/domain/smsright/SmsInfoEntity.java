package cn.ilanhai.kem.domain.smsright;

import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * 短信信息实体
 * 
 * @author he
 *
 */
public class SmsInfoEntity extends AbstractEntity {
	public SmsInfoEntity() {

	}

	public int getInfoId() {
		return infoId;
	}

	public void setInfoId(int infoId) {
		this.infoId = infoId;
	}

	public String getSmsId() {
		return smsId;
	}

	public void setSmsId(String smsId) {
		this.smsId = smsId;
	}

	public String getInfoKey() {
		return infoKey;
	}

	public void setInfoKey(String infoKey) {
		this.infoKey = infoKey;
	}

	public String getInfoValue() {
		return infoValue;
	}

	public void setInfoValue(String infoValue) {
		this.infoValue = infoValue;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	private static final long serialVersionUID = 1L;
	/**
	 * 信息key<br>
	 * content:内容<br>
	 * sendType:发送方式，不可空 1 立即 0 定时<br>
	 * sendTime:发送时间，不可空<br>
	 * contracts:联系人，电话号码，用逗号隔开，不可空
	 * contracts:联系人，电话号码，用逗号隔开，不可空
	 */

	public static final String KEY_CONTENT = "content";
	public static final String KEY_SENDTYPE = "sendType";
	public static final String KEY_SENDTIME = "sendTime";
	public static final String KEY_CONTRACTS = "contracts";
	public static final String KEY_SENDAMOUNT = "sendAmount";
	public static final String KEY_CUSTOMER = "customers";
	/**
	 * 请不要增加key
	 */
	public static final String[] KEYS = new String[] { KEY_CONTENT,
			KEY_SENDTYPE, KEY_SENDTIME ,KEY_CUSTOMER};
	/**
	 * 编号
	 */
	private int infoId;
	/**
	 * 邮件编号
	 */
	private String smsId;
	/**
	 * key
	 */
	private String infoKey;
	/**
	 */
	private String infoValue;
	/**
	 * 状态 1 可用 0 不可用
	 */
	private boolean enable;
}
