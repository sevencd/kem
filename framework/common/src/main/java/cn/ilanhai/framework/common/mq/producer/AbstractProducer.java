package cn.ilanhai.framework.common.mq.producer;

import java.io.Serializable;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;

import cn.ilanhai.framework.common.exception.AppException;
import cn.ilanhai.framework.common.exception.JMSAppException;
import cn.ilanhai.framework.common.mq.AbstractMQ;

/**
 * @author he
 *
 */
public abstract class AbstractProducer extends AbstractMQ {

	protected MessageProducer createMessageProducer(Session session,
			Destination destination) throws JMSException {
		if (session == null)
			throw new JMSException("session not null");
		if (destination == null)
			throw new JMSException("destination not null");
		return session.createProducer(destination);
	}

	protected final <T> void send(T t) throws JMSException {
		Connection connection = null;
		Session session = null;
		Destination destination = null;
		MessageProducer messageProducer = null;
		Message message = null;
		try {
			//创建消息消息队列连接
			connection = this.createConnection(this.connectionFactory);
			//开启连接
			connection.start();
			//使用连接创建消息队列session
			session = this.createSession(connection);
			//使用session创建目标（队列）
			destination = this.createDestination(session);
			//创建生产者
			messageProducer = this.createMessageProducer(session, destination);
			//如果是字符串，就传入字符串
			if (t.getClass() == String.class)
				message = session.createTextMessage((String) t);
			//如果是可序列化的对象，则传入对象
			else if (t instanceof Serializable)
				message = session.createObjectMessage((Serializable) t);
			//发送消息
			messageProducer.send(message);
			//提交事务
			session.commit();
		} catch (JMSException e) {
			//有异常则回滚消息
			session.rollback();
			throw e;
		} finally {
			//关闭生产者
			if (messageProducer != null)
				messageProducer.close();
			//关闭会话
			if (session != null)
				session.close();
			//停止并关闭连接
			if (connection != null) {
				connection.stop();
				connection.close();
			}

		}

	}

	public void sendText(String text) throws JMSAppException {
		try {
			this.send(text);
		} catch (JMSException e) {
			throw new JMSAppException(e);
		}
	}

	public void sendObject(Serializable obj) throws JMSAppException {
		try {
			this.send(obj);
		} catch (JMSException e) {
			throw new JMSAppException(e);
		}
	}

	public void sendBytes(String text) throws JMSAppException {
		throw new JMSAppException("功能没实现");
	}

	public void sendStream(String text) throws JMSAppException {
		throw new JMSAppException("功能没实现");
	}

	public void sendMap(String text) throws JMSAppException {
		throw new JMSAppException("功能没实现");
	}

	public void close() throws JMSAppException {

		this.connectionFactory = null;

	}
}
