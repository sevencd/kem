package cn.ilanhai.kem.queue.consumer;

import java.util.Map;

import org.apache.log4j.Logger;

import cn.ilanhai.framework.common.exception.AppException;
import cn.ilanhai.kem.App;

import cn.ilanhai.kem.queue.QueueConsumer;

import cn.ilanhai.kem.queue.msg.SmsMsg;
import cn.ilanhai.kem.sms.protocol.tosms.TosmsSmsInfo;
import cn.ilanhai.kem.sms.tosms.TosmsSms;
import cn.ilanhai.kem.sms.tosms.TosmsSmsConfig;

/**
 * 短信定阅者
 * 
 * @author he
 *
 */
@SuppressWarnings("rawtypes")
public class SmsConsumer extends QueueConsumer<SmsMsg> {

	public SmsConsumer(App app) {
		super(app, SmsMsg.class);
	}

	@Override
	protected String getId() {
		return SmsMsg.class.getName();
	}

	@Override
	protected void recieveMsg(SmsMsg msg) throws AppException {
		if (msg.getMsgContent() == null)
			return;
		TosmsSms tosmsSms = new TosmsSms();

		TosmsSmsInfo info = msg.getMsgContent();
		if (info == null) {
			logger.error("发送消息为空");
			return;
		}

		tosmsSms.sendTo(this.getConfig(), info);
	}

	private TosmsSmsConfig getConfig() {
		TosmsSmsConfig config = new TosmsSmsConfig();
		Object tmp = null;
		Map<String, String> setting = this.app.getConfigure().getSettings();
		tmp = setting.get("tosmsUsername");
		if (tmp instanceof String)
			config.setUserName((String) tmp);
		tmp = setting.get("tosmsPassword");
		if (tmp instanceof String)
			config.setPassword((String) tmp);
		return config;
	}

	/**
	 * 日志
	 */
	private Logger logger = Logger.getLogger(SmsConsumer.class);

}
