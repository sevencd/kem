package cn.ilanhai.kem.mail.protocol.sohu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.ilanhai.framework.uitl.Str;
import cn.ilanhai.kem.mail.protocol.MailInfo;

/**
 * Sohu邮件信息
 * 
 * @author he
 *
 */
public class SohuMailInfo extends MailInfo {
	private String groupAddress = "@mail.cloudmarkee.com";

	public SohuMailInfo() {
		this.dataType = DataType.GeneralSendSohuMail;
		this.to = new ArrayList<String>();
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}

	public String getLabelId() {
		return labelId;
	}

	public void setLabelId(String labelId) {
		this.labelId = labelId;
	}

	public String getAttachments() {
		return attachments;
	}

	public void setAttachments(String attachments) {
		this.attachments = attachments;
	}

	public List<String> getTo() {
		return to;
	}

	public void setTo(List<String> to) {
		this.to = to;
	}

	public String getFromName() {
		return fromName;
	}

	public void setFromName(String fromName) {
		this.fromName = fromName;
	}

	public Map<String, String> toMap() {
		Map<String, String> map = null;
		String tmp = null;
		map = new HashMap<String, String>();
		tmp = this.subject;
		if (!Str.isNullOrEmpty(tmp))
			map.put("subject",  this.encode(tmp));
		tmp = this.html;
		if (!Str.isNullOrEmpty(tmp))
			map.put("html",  this.encode(tmp));
		// tmp = String.valueOf(this.labelId);
		// if (!Str.isNullOrEmpty(tmp))
		// map.put("labelId", tmp);
		tmp = this.groupIdListToString(this.to);
		if (!Str.isNullOrEmpty(tmp))
			map.put("to",  this.encode(tmp));
		tmp = this.fromName;
		if (!Str.isNullOrEmpty(tmp))
			map.put("fromName", this.encode(tmp));
		tmp = this.labelId;
		if (!Str.isNullOrEmpty(tmp))
			map.put("labelId", this.encode(tmp));
		
		// 默认值: false. 是否使用地址列表发送. 比如:
		// to=group1@maillist.sendcloud.org;group2@maillist.sendcloud.org
		if (!Str.isNullOrEmpty(groupAddress)) {
			map.put("useAddressList", "true");
		}
		
		return map;
	}

	private String listToString(List<String> list) {
		if (list == null || list.size() <= 0)
			return null;
		StringBuilder builder = null;
		builder = new StringBuilder();
		int offset = list.size() - 1;
		for (int i = 0; i < offset; i++)
			builder.append(list.get(i)).append(";");
		builder.append(list.get(offset));
		return builder.toString();
	}

	private String groupIdListToString(List<String> list) {
		if (list == null || list.size() <= 0)
			return null;
		StringBuilder builder = null;
		builder = new StringBuilder();
		int offset = list.size() - 1;
		for (int i = 0; i < offset; i++)
			// 添加地址拼接
			builder.append(list.get(i) + groupAddress).append(";");
		builder.append(list.get(offset) + groupAddress);
		return builder.toString();
	}

	/**
	 * subject string 是 标题. 不能为空
	 */
	private String subject;

	/**
	 * html string * 邮件的内容. 邮件格式为 text/html
	 */
	private String html;

	/**
	 * labelId int 否 本次发送所使用的标签ID. 此标签需要事先创建
	 */
	private String labelId;
	/**
	 * attachments file 否 邮件附件. 发送附件时, 必须使用 multipart/form-data 进行 post 提交
	 * (表单提交)
	 */
	private String attachments;
	/**
	 * to string * 收件人地址. 多个地址使用';'分隔, 如 ben@ifaxin.com;joe@ifaxin.com
	 */
	private List<String> to;
	/**
	 * fromName string 否 发件人名称. 显示如: ifaxin客服支持<support@ifaxin.com>
	 */
	private String fromName;
	/**
	 * 默认值: false. 是否使用地址列表发送. 比如:
	 * to=group1@maillist.sendcloud.org;group2@maillist.sendcloud.org
	 */
	private String useAddressList;

	public String getUseAddressList() {
		return useAddressList;
	}

	public void setUseAddressList(String useAddressList) {
		this.useAddressList = useAddressList;
	}

	public String getGroupAddress() {
		return groupAddress;
	}

	public void setGroupAddress(String groupAddress) {
		this.groupAddress = groupAddress;
	}
}
