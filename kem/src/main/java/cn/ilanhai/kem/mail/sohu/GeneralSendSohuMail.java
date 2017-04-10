package cn.ilanhai.kem.mail.sohu;

import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;

import cn.ilanhai.framework.common.exception.AppException;
import cn.ilanhai.framework.uitl.FastJson;
import cn.ilanhai.framework.uitl.Str;
import cn.ilanhai.kem.mail.MailConfig;
import cn.ilanhai.kem.mail.protocol.MailInfo;
import cn.ilanhai.kem.mail.protocol.Result;
import cn.ilanhai.kem.mail.protocol.sohu.SohuMailInfo;
import cn.ilanhai.kem.mail.protocol.sohu.SohuResult;
import cn.ilanhai.kem.util.HttpHelper;

public class GeneralSendSohuMail {
	public GeneralSendSohuMail() {

	}

	public Result sendTo(MailConfig config, MailInfo info) throws AppException {
		GeneralSendSohuMailConfig sohuMailConfig = null;
		SohuMailInfo sohuMailInfo = null;
		Map<String, String> configParams = null;
		Map<String, String> infoParams = null;
		String doc = null;
		if (config instanceof GeneralSendSohuMailConfig)
			sohuMailConfig = (GeneralSendSohuMailConfig) config;
		if (sohuMailConfig == null)
			throw new AppException(-1, "邮件配置参数出错");
		if (Str.isNullOrEmpty(sohuMailConfig.getApiUser()))
			throw new AppException(-1, "邮件配置ApiUser出错");
		if (Str.isNullOrEmpty(sohuMailConfig.getApiKey()))
			throw new AppException(-1, "邮件配置ApiKey出错");
		if (Str.isNullOrEmpty(sohuMailConfig.getFrom()))
			throw new AppException(-1, "邮件配置From出错");

		if (info instanceof SohuMailInfo)
			sohuMailInfo = (SohuMailInfo) info;
		if (sohuMailInfo == null)
			throw new AppException(-1, "邮件信息参数出错");
		if (sohuMailInfo.getTo() == null || sohuMailInfo.getTo().size() <= 0)
			throw new AppException(-1, "邮件配置To出错");
		if (sohuMailInfo.getTo() == null || sohuMailInfo.getTo().size() > TO_MAX_COUNT)
			throw new AppException(-1, String.format("邮件配置To最大个数为%s", TO_MAX_COUNT));
		if (Str.isNullOrEmpty(sohuMailInfo.getSubject()))
			throw new AppException(-1, "邮件信息Subject出错");
		if (Str.isNullOrEmpty(sohuMailInfo.getHtml()))
			throw new AppException(-1, "邮件信息Html出错");
		configParams = sohuMailConfig.toMap();
		infoParams = sohuMailInfo.toMap();
		configParams = sohuMailInfo.map(configParams, infoParams);
		outputParams(configParams);
		logger.info("configParams = " + configParams);
		logger.info("infoParams = " + infoParams);
		doc = HttpHelper.sendPost(API, configParams, CHARSET);
		logger.info(doc);
		if (Str.isNullOrEmpty(doc))
			throw new AppException(-1, "调用远程接口失败");
		SohuResult result = FastJson.json2Bean(doc, SohuResult.class);
		if (result == null)
			throw new AppException(-1, "调用远程返回值格式错误");
		return result;
	}

	private void outputParams(Map<String, String> params) {
		if (params == null || params.size() <= 0)
			logger.info("提交参数为空");
		logger.info("提交参数");
		Iterator<String> iterator = params.keySet().iterator();
		while (iterator.hasNext()) {
			String key = iterator.next();
			if ("html".equals(key)) {
				continue;
			}
			logger.info(String.format("%s=%s", key, params.get(key)));
		}

	}

	private final String CHARSET = "utf-8";
	/**
	 * 发送邮件的最大数
	 */
	private final int TO_MAX_COUNT = 5;
	/**
	 * 普通发送邮件api
	 */
	private final String API = "http://api.sendcloud.net/apiv2/mail/send";
	/**
	 * 日志
	 */
	private final Logger logger = Logger.getLogger(SohuMail.class);
}
