package cn.ilanhai.framework.common.mq.consumer;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Session;

public abstract class QueueAbstractConsumer extends AbstractConsumer {
	@Override
	protected Destination createDestination(Session session)
			throws JMSException {
		String id = null;
		if (session == null)
			throw new JMSException("session not null");
		id = this.getId();
		if (id == null || id.length() <= 0)
			throw new JMSException("queue name error");
		return session.createQueue(id);
	}
}
