package cn.ilanhai.kem.queue;

import java.util.List;

import org.apache.log4j.Logger;

import cn.ilanhai.framework.common.configuration.app.mq.MQConf;
import cn.ilanhai.framework.common.exception.BlAppException;
import cn.ilanhai.framework.common.exception.JMSAppException;
import cn.ilanhai.framework.common.mq.MQManager;
import cn.ilanhai.framework.common.mq.MQManagerImpl;
import cn.ilanhai.framework.common.mq.producer.AbstractProducer;
import cn.ilanhai.framework.uitl.FastJson;
import cn.ilanhai.kem.App;
import cn.ilanhai.kem.event.MQConfig;
import cn.ilanhai.kem.queue.consumer.ContactConsumer;
import cn.ilanhai.kem.queue.consumer.MailConsumer;
import cn.ilanhai.kem.queue.consumer.ManuscriptStatisticConsumer;
import cn.ilanhai.kem.queue.consumer.SmsConsumer;
import cn.ilanhai.kem.queue.msg.ContactMsg;
import cn.ilanhai.kem.queue.msg.MailMsg;
import cn.ilanhai.kem.queue.msg.ManuscriptStatisticMsg;
import cn.ilanhai.kem.queue.msg.SmsMsg;

public class QueueManager {

	private Logger logger = Logger.getLogger(QueueManager.class);

	// 单例相关内容
	private static QueueManager instance;
	static {
		instance = new QueueManager();
	}

	public static QueueManager getInstance() {
		return instance;
	}

	// mq管理器
	private static MQManager manager = new MQManagerImpl();

	public void init(App app) throws JMSAppException {
		List<MQConf> conf = null;
		MQConf mqConf = null;
		String tmp = null;
		// 加载配置
		conf = app.getConfigure().getMQConf();
		if (conf != null && conf.size() > 0) {
			mqConf = conf.get(0);
			if (mqConf != null) {
				tmp = mqConf.getBrokerUrl();
				if (tmp != null && tmp.length() > 0)
					MQConfig.setBrokeUrl(tmp);
				tmp = mqConf.getUserName();
				if (tmp != null && tmp.length() > 0)
					MQConfig.setUsername(tmp);
				tmp = mqConf.getPassword();
				if (tmp != null && tmp.length() > 0)
					MQConfig.setPassword(tmp);
			}
		}
		// 注册 稿件统计消息队列
		manager.register(new QueueProducer(ManuscriptStatisticMsg.class, app));
		manager.register(new ManuscriptStatisticConsumer(app));
		// 注册 邮件消息队列
		manager.register(new QueueProducer(MailMsg.class, app));
		manager.register(new MailConsumer(app));
		// 注册 短信消息队列
		manager.register(new QueueProducer(SmsMsg.class, app));
		manager.register(new SmsConsumer(app));
		// 注册 联系人消息队列
		manager.register(new QueueProducer(ContactMsg.class, app));
		manager.register(new ContactConsumer(app));
		// 初始化消费者及生产者
		manager.init(conf);
	}

	public void put(BaseQueueMsg msg) throws BlAppException {
		if (msg == null) {
			throw new BlAppException(-1, "消息不可为空");
		}

		String tmp = null;
		AbstractProducer producer = null;
		try {
			// 将事件序列化成字符串
			tmp = FastJson.bean2Json(msg);
			producer = manager.getProducer(msg.getClass().getName());
			// 发送事件内容
			if (producer != null)
				producer.sendText(tmp);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
}
