package cn.ilanhai.kem.sms.protocol.tosms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import cn.ilanhai.kem.domain.smsright.dto.SmsInfoDto;
import cn.ilanhai.kem.sms.protocol.SmsInfo;

/**
 * 短信内容
 * 
 * @author he
 *
 */
public class ToSmsInfo extends SmsInfo {
	/**
	 * 电话号码* 多个以逗号(,)分隔
	 */
	private String phone;
	/**
	 * 内容* 短信内容，计费长度根据通道而定
	 */
	private String content;
	/**
	 * 定时发送时间 时间格式 yyyy-MM-dd HH:mm:ss
	 */
	private Date sendTime;
	/**
	 * 短信详情
	 */
	private List<SmsInfoDto> list;
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public List<SmsInfoDto> getList() {
		return list;
	}

	public void setList(List<SmsInfoDto> list) {
		this.list = list;
	}


}
