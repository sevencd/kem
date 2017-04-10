package cn.ilanhai.kem.sms.protocol.tosms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import cn.ilanhai.kem.sms.protocol.SmsInfo;

/**
 * 短信内容
 * 
 * @author he
 *
 */
public class TosmsSmsInfo extends SmsInfo {
	public TosmsSmsInfo() {
		this.phone = new ArrayList<String>();
	}

	public String getPhoneToString() {
		if (this.phone == null || this.phone.size() <= 0)
			return new String();
		StringBuilder builder = null;
		builder = new StringBuilder();
		int offset = this.phone.size() - 1;
		for (int i = 0; i < offset; i++)
			builder.append(this.phone.get(i)).append(",");
		builder.append(this.phone.get(offset));
		return builder.toString();
	}

	public List<String> getPhone() {
		return phone;
	}

	public void setPhone(List<String> phone) {
		this.phone = phone;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	/**
	 * 电话号码* 多个以逗号(,)分隔
	 */
	private List<String> phone;
	/**
	 * 内容* 短信内容，计费长度根据通道而定
	 */
	private String content;
	/**
	 * 定时发送时间 时间格式 yyyy-MM-dd HH:mm:ss
	 */
	private Date sendTime;
}
