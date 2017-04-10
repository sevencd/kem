package cn.ilanhai.framework.common.mq.consumer;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Session;

import org.apache.activemq.command.ActiveMQDestination;

public abstract class TopicAbstractConsumer extends AbstractConsumer {

	@Override
	protected Destination createDestination(Session session)
			throws JMSException {
		String id = null;
		if (session == null)
			throw new JMSException("session not null");
		id = this.getId();
		if (id == null || id.length() <= 0)
			throw new JMSException("topic name error");
		
		return session.createTopic(id);
	}
}
