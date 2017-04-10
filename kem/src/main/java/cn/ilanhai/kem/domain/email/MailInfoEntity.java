package cn.ilanhai.kem.domain.email;

import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * 邮件信息
 * 
 * @author he
 *
 */
public class MailInfoEntity extends AbstractEntity {
	public MailInfoEntity() {

	}

	public int getInfoId() {
		return infoId;
	}

	public void setInfoId(int infoId) {
		this.infoId = infoId;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
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
	 * title:标题<br>
	 * content:内容<br>
	 * fromName：发件人名称<br>
	 * sendType:发送方式，不可空 1 立即 0 定时<br>
	 * sendTime:发送时间，不可空<br>
	 * sendAmount:发送数量 由后端处理<br>
	 * emailState: 1，草稿；2，已发送，不可空 由后端处理
	 */
	public static final String KEY_TITLE = "title";
	public static final String KEY_CONTENT = "content";
	public static final String KEY_SENDTYPE = "sendType";
	public static final String KEY_SENDTIME = "sendTime";
	public static final String KEY_SENDAMOUNT = "sendAmount";
	public static final String KEY_EMAILSTATE = "emailState";
	public static final String KEY_FROMNAME = "fromName";
	public static final String KEY_LABELID = "labelId";

	/**
	 * 请不要增加key
	 */
	public static final String[] KEYS = new String[] { KEY_TITLE, KEY_CONTENT,
			KEY_SENDTYPE, KEY_SENDTIME, KEY_FROMNAME };
	/**
	 * 编号
	 */
	private int infoId;
	/**
	 * 邮件编号
	 */
	private String emailId;
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
