package cn.ilanhai.kem.sms.tosms;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;

import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.log4j.Logger;
import org.springframework.remoting.support.UrlBasedRemoteAccessor;

import cn.ilanhai.framework.common.exception.AppException;
import cn.ilanhai.framework.uitl.Str;
import cn.ilanhai.kem.sms.AbstractSms;
import cn.ilanhai.kem.sms.SmsConfig;
import cn.ilanhai.kem.sms.protocol.Result;
import cn.ilanhai.kem.sms.protocol.SmsInfo;
import cn.ilanhai.kem.sms.protocol.tosms.TosmsResult;
import cn.ilanhai.kem.sms.protocol.tosms.TosmsSmsInfo;
import cn.ilanhai.kem.sms.protocol.tosms.TosmsResult.ReturnValue;
import cn.ilanhai.kem.util.HttpHelper;

/**
 * 传信网短信发送
 * 
 * @author he
 *
 */
public class TosmsSms extends AbstractSms {

	@Override
	public Result sendTo(SmsConfig config, SmsInfo info) throws AppException {
		TosmsSmsConfig tosmsSmsConfig = null;
		TosmsSmsInfo tosmsSmsInfo = null;
		String url = null;
		String tmp = null;
		if (config instanceof TosmsSmsConfig)
			tosmsSmsConfig = (TosmsSmsConfig) config;
		if (tosmsSmsConfig == null)
			throw new AppException(-1, "短信配置参数错误");
		if (Str.isNullOrEmpty(tosmsSmsConfig.getUserName()))
			throw new AppException(-1, "短信配置参数用户名错误");
		if (Str.isNullOrEmpty(tosmsSmsConfig.getPassword()))
			throw new AppException(-1, "短信配置参数密码错误");
		if (info instanceof TosmsSmsInfo)
			tosmsSmsInfo = (TosmsSmsInfo) info;
		if (tosmsSmsInfo == null)
			throw new AppException(-1, "短信信息参数错误");
		if (tosmsSmsInfo.getPhone() == null
				|| tosmsSmsInfo.getPhone().size() <= 0)
			throw new AppException(-1, "短信信息电话号错误");
		if (Str.isNullOrEmpty(tosmsSmsInfo.getContent()))
			throw new AppException(-1, "短信信息内容错误");
		if (tosmsSmsInfo.getContent().length() > CONTENT_MAX_LENGTH)
			throw new AppException(-1, String.format("短信信息内容最大长度为%s",
					CONTENT_MAX_LENGTH));
		try {
			url = String.format(
					"%s?username=%s&Password=%s&Phones=%s&Content=%s", API,
					encode(tosmsSmsConfig.getUserName()),
					encode(tosmsSmsConfig.getPassword()),
					encode(tosmsSmsInfo.getPhoneToString()),
					encode(tosmsSmsInfo.getContent()));
			if (tosmsSmsInfo.getSendTime() != null) {
				SimpleDateFormat format = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				url = String.format("%s&SendTime=%s", url,
						encode(format.format(tosmsSmsInfo.getSendTime())));
			}
		} catch (UnsupportedEncodingException e) {

		}
		logger.info("url:"+url);
		tmp = HttpHelper.sendGet(url, ENCODE);
		logger.info(tmp);
		if (Str.isNullOrEmpty(tmp))
			throw new AppException(-1, "调用远程接口失败");
		if (!isDigit(tmp))
			throw new AppException(-1, "远程返回数据出错");

		ReturnValue retVal = ReturnValue.valueOf(Integer.valueOf(tmp));
		TosmsResult result = null;
		result = new TosmsResult();
		result.setReturnValue(retVal);
		return result;
	}

	private String encode(String str) throws UnsupportedEncodingException {
		if (Str.isNullOrEmpty(str))
			return "";
		return URLEncoder.encode(str, "utf-8");
	}

	private boolean isDigit(String str) {
		if (Str.isNullOrEmpty(str))
			return false;
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(0) == '-')
				continue;
			if (!Character.isDigit(str.charAt(i)))
				return false;
		}
		return true;
	}

	private final int CONTENT_MAX_LENGTH = 128;
	/**
	 * 编码
	 */
	private final String ENCODE = "utf-8";
	/**
	 * api
	 */
	private final String API = "http://www.tosms.cn/Api/SendSms.ashx";
	/**
	 * 日志
	 */
	private final Logger logger = Logger.getLogger(TosmsSms.class);
}
