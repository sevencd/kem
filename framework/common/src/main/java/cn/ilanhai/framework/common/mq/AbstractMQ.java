package cn.ilanhai.framework.common.mq;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;

import cn.ilanhai.framework.common.exception.JMSAppException;

/**
 * 抽象消息对队
 * 
 * @author he
 *
 */
public abstract class AbstractMQ {
	// 连接工厂
	protected ConnectionFactory connectionFactory = null;

	protected AbstractMQ() {

	}

	protected abstract String getId();

	protected abstract void initConnectionFactory() throws JMSException;

	protected Connection createConnection(ConnectionFactory connectionFactory)
			throws JMSException {
		if (connectionFactory == null)
			throw new JMSException("connectionFactory not null");
		return connectionFactory.createConnection();
	}

	protected Session createSession(Connection connection) throws JMSException {
		if (connection == null)
			throw new JMSException("connection not null");
		return connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
	}

	protected abstract Destination createDestination(Session session)
			throws JMSException;

	public abstract void close() throws JMSAppException;

}
