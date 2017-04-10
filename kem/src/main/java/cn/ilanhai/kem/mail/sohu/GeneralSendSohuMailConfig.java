package cn.ilanhai.kem.mail.sohu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.ilanhai.framework.uitl.FastJson;
import cn.ilanhai.framework.uitl.Str;
import cn.ilanhai.kem.mail.MailConfig;

/**
 * Sohu邮件配置 假设 <br>
 * 1、from 为 爱发信支持<support@ifaxin.com>. 如果 fromName 为空, 则系统会将 fromName
 * 2、设置为"爱发信支持"; 如果 fromName 为非空, 则不作处理. <br>
 * 3、地址列表发送时, 使用参数 to 指定地址列表, 地址列表中的每个地址是单独发送, 地址列表的个数不能超过 5. 此时参数 cc, bcc,
 * xsmtpapi 失效. <br>
 * 4、非地址列表发送时, 使用参数 to 指定收件人, 多个收件人是广播发送 (收件人会全部显示). 使用参数 cc 指定抄送人, 参数 bcc
 * 指定密送人. <br>
 * 5、非地址列表发送时, 使用 xsmtpapi 指定收件人, 多个收件人是单独发送. 此时参数 to, cc, bcc 失效. <br>
 * 6、参数 to, cc, bcc 的收件人个数不能超过 100, xsmtpapi 中的 to 的收件人个数不能超过 100. <br>
 * 7、html 和 plain 不能同时为空. 如果都不为空, 以 html 的值为优先. <br>
 * 8、subject, html, plain 中都可以使用变量. 由于变量的 '%' 为特殊字符, 做 HTTP 请求时请注意处理. <br>
 * 9、使用回执功能, 收件人在收到邮件之后, 可以选择是否发送阅读回执到 from 的邮箱地址. <br>
 * 10、如果参数 headers 中某个 Key 以 "SC-Custom-" 开头, 则这个 Key:Value 会通过 WebHook 返回给用户.
 * 
 * @author he
 *
 */
public class GeneralSendSohuMailConfig extends SohuMailConfig {

	public GeneralSendSohuMailConfig() {

		this.cc = new ArrayList<String>();
		this.bcc = new ArrayList<String>();
		this.headers = new HashMap<String, String>();
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public List<String> getCc() {
		return cc;
	}

	public void setCc(List<String> cc) {
		this.cc = cc;
	}

	public List<String> getBcc() {
		return bcc;
	}

	public void setBcc(List<String> bcc) {
		this.bcc = bcc;
	}

	public String getReplyTo() {
		return replyTo;
	}

	public void setReplyTo(String replyTo) {
		this.replyTo = replyTo;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}

	public Object getXsmtpapi() {
		return xsmtpapi;
	}

	public void setXsmtpapi(Object xsmtpapi) {
		this.xsmtpapi = xsmtpapi;
	}

	public String getPlain() {
		return plain;
	}

	public void setPlain(String plain) {
		this.plain = plain;
	}

	public boolean isRespEmailId() {
		return respEmailId;
	}

	public void setRespEmailId(boolean respEmailId) {
		this.respEmailId = respEmailId;
	}

	public boolean isUseNotification() {
		return useNotification;
	}

	public void setUseNotification(boolean useNotification) {
		this.useNotification = useNotification;
	}

	public boolean isUseAddressList() {
		return useAddressList;
	}

	public void setUseAddressList(boolean useAddressList) {
		this.useAddressList = useAddressList;
	}

	public Map<String, String> toMap() {
		Map<String, String> map = null;
		String tmp = null;
		map = new HashMap<String, String>();
		tmp = this.getApiUser();
		if (!Str.isNullOrEmpty(tmp))
			map.put("apiUser", this.encode(tmp));
		tmp = this.getApiKey();
		if (!Str.isNullOrEmpty(tmp))
			map.put("apiKey", this.encode(tmp));
		tmp = this.from;
		if (!Str.isNullOrEmpty(tmp))
			map.put("from", this.encode(tmp));
		tmp = this.listToString(this.cc, SPLIT);
		if (!Str.isNullOrEmpty(tmp))
			map.put("cc", this.encode(tmp));
		tmp = this.listToString(this.bcc, SPLIT);
		if (!Str.isNullOrEmpty(tmp))
			map.put("bcc", this.encode(tmp));
		tmp = this.replyTo;
		if (!Str.isNullOrEmpty(tmp))
			map.put("replyTo", this.encode(tmp));
		tmp = this.mapToJsonString(this.headers);
		if (!Str.isNullOrEmpty(tmp))
			map.put("headers", this.encode(tmp));
		tmp = this.plain;
		if (!Str.isNullOrEmpty(tmp))
			map.put("plain", this.encode(tmp));
		tmp = this.respEmailId ? "true" : "false";
		if (!Str.isNullOrEmpty(tmp))
			map.put("respEmailId", this.encode(tmp));
		tmp = this.useNotification ? "true" : "false";
		if (!Str.isNullOrEmpty(tmp))
			map.put("useNotification", this.encode(tmp));
		tmp = this.useAddressList ? "true" : "false";
		if (!Str.isNullOrEmpty(tmp))
			map.put("useAddressList", this.encode(tmp));
		return map;
	}

	

	private final char SPLIT = ';';
	/**
	 * from string 是 发件人地址. 举例: support@ifaxin.com, 爱发信支持<support@ifaxin.com>
	 */
	private String from;

	/**
	 * cc string 否 抄送地址. 多个地址使用';'分隔
	 */
	private List<String> cc;

	/**
	 * bcc string 否 密送地址. 多个地址使用';'分隔
	 */
	private List<String> bcc;
	/**
	 * replyTo string 否 设置用户默认的回复邮件地址. 如果 replyTo 没有或者为空, 则默认的回复邮件地址为 from
	 */
	private String replyTo;
	/**
	 * headers string 否 邮件头部信息. JSON 格式, 比如:{"header1": "value1", "header2":
	 * "value2"}
	 */
	private Map<String, String> headers;
	/**
	 * xsmtpapi string 否 SMTP 扩展字段. 详见 X-SMTPAPI
	 */
	private Object xsmtpapi;

	/**
	 * plain string 否 邮件的内容. 邮件格式为 text/plain
	 */
	private String plain = "text/plain";
	/**
	 * respEmailId string (true, false) 否 默认值: true. 是否返回 emailId. 有多个收件人时, 会返回
	 * emailId 的列表
	 */
	private boolean respEmailId = true;

	/**
	 * useNotification string (true, false) 否 默认值: false. 是否使用回执
	 */
	private boolean useNotification = false;
	/**
	 * useAddressList string (true, false) 否 默认值: false. 是否使用地址列表发送. 比如:
	 * to=group1@maillist.sendcloud.org;group2
	 */
	private boolean useAddressList = false;
}
