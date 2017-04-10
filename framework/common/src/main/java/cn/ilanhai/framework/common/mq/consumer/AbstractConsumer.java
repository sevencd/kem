package cn.ilanhai.framework.common.mq.consumer;

import javax.jms.BytesMessage;
import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.StreamMessage;
import javax.jms.TextMessage;

import com.alibaba.fastjson.JSONException;

import cn.ilanhai.framework.common.exception.JMSAppException;
import cn.ilanhai.framework.common.mq.AbstractMQ;

/**
 * @author he
 *
 */
public abstract class AbstractConsumer extends AbstractMQ {
	private Connection connection = null;
	private Session session = null;
	private Destination destination = null;
	private MessageConsumer messageConsumer = null;

	protected MessageConsumer createMessageConsumer(Session session,
			Destination destination) throws JMSException {
		if (session == null)
			throw new JMSException("session not null");
		if (destination == null)
			throw new JMSException("destination not null");
		return session.createConsumer(destination);
	}

	public void recvice() throws JMSAppException {

		try {
			connection = this.createConnection(this.connectionFactory);
			connection.start();
			session = this.createSession(connection);
			destination = this.createDestination(session);
			messageConsumer = this.createMessageConsumer(session, destination);
			//设置监听者，用来监听消息
			messageConsumer.setMessageListener(new MessageListener() {
				//收到消息后会自动调用onMessage方法
				@Override
				public void onMessage(Message message) throws RuntimeException{
					if (message == null)
						return;
					if (message instanceof TextMessage) {
						recviceText((TextMessage) message);
					} else if (message instanceof ObjectMessage)
						recviceObject((ObjectMessage) message);
					else if (message instanceof BytesMessage)
						recviceBytes((BytesMessage) message);
					else if (message instanceof StreamMessage)
						recviceStream((StreamMessage) message);
					else if (message instanceof MapMessage)
						recviceMap((MapMessage) message);
					else
						;
				}
			});

		} catch (JMSException e) {
			throw new JMSAppException(e);
		} finally {

		}
	}

	protected void recviceText(TextMessage textMessage) throws RuntimeException{

	}

	protected void recviceObject(ObjectMessage objectMessage) throws RuntimeException{

	}

	protected void recviceBytes(BytesMessage bytesMessage) throws RuntimeException{

	}

	protected void recviceStream(StreamMessage streamMessage) throws RuntimeException{

	}

	protected void recviceMap(MapMessage mapMessage) throws RuntimeException{

	}

	public void close() throws JMSAppException {
		try {
			if (this.messageConsumer != null)
				this.messageConsumer.close();
			if (this.session != null)
				this.session.close();
			if (this.connection != null) {
				this.connection.stop();
				this.session.close();
			}
			this.connectionFactory = null;
		} catch (JMSException e) {
			throw new JMSAppException(e);
		}
	}

}
