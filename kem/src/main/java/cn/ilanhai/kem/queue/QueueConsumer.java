package cn.ilanhai.kem.queue;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

import cn.ilanhai.framework.app.dao.Dao;
import cn.ilanhai.framework.common.exception.AppException;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.framework.common.mq.consumer.QueueAbstractConsumer;
import cn.ilanhai.framework.uitl.FastJson;
import cn.ilanhai.kem.App;
import cn.ilanhai.kem.dao.event.EventDao;
import cn.ilanhai.kem.domain.event.EventEntity;
import cn.ilanhai.kem.event.DomainEvent;
import cn.ilanhai.kem.event.DomainEventManager;
import cn.ilanhai.kem.event.MQConfig;
import cn.ilanhai.kem.queue.msg.MailMsg;

public abstract class QueueConsumer<T extends BaseQueueMsg> extends
		QueueAbstractConsumer {

	private Logger logger = Logger.getLogger(QueueConsumer.class);

	protected App app;
	protected Class<T> msgClass;

	public QueueConsumer(App app, Class<T> msgClass) {
		this.app = app;
		this.msgClass = msgClass;
	}

	@Override
	protected Session createSession(Connection connection) throws JMSException {
		if (connection == null)
			throw new JMSException("connection not null");
		return connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
	}

	@Override
	protected void initConnectionFactory() throws JMSException {
		this.connectionFactory = new ActiveMQConnectionFactory(
				MQConfig.getUsername(), MQConfig.getPassword(),
				MQConfig.getBrokeUrl());
	}

	/**
	 * 反序列化消息
	 * 
	 * @param msg
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected T deserializeMsg(String msg) {
		T msgResult;
		msgResult = (T) FastJson.json2Bean(msg, msgClass);
		return msgResult;
	}

	@Override
	protected void recviceText(TextMessage textMessage) throws RuntimeException {

		T msg = null;
		try {
			if (textMessage.getText() == null
					|| textMessage.getText().length() <= 0) {
				logger.info("接收到文本消息错误");
				return;
			}

			// 反序列化消息
			msg = deserializeMsg(textMessage.getText());
			if (msg == null) {
				logger.info("接收到文本消息反序列化对象错误");
				return;
			}
			// if (msg instanceof MailMsg) {
			// ((MailMsg) msg).setRawMsg(textMessage.getText());
			// }
			// 处理消息
			this.recieveMsg(msg);
			// textMessage.acknowledge();

		} catch (JMSException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (AppException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	protected abstract void recieveMsg(T msg) throws AppException;
}
