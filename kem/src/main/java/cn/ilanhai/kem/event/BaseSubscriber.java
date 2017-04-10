package cn.ilanhai.kem.event;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

import cn.ilanhai.framework.common.exception.JMSAppException;
import cn.ilanhai.framework.common.mq.consumer.TopicAbstractConsumer;
import cn.ilanhai.kem.App;

/**
 * 基础订阅者，kem中所有的订阅者均需要继承该类
 * @author Nature
 *
 */
public abstract class BaseSubscriber  extends TopicAbstractConsumer{
	
	protected App app=null;
	protected BaseSubscriber() throws JMSException, JMSAppException{
		//this.initConnectionFactory();
		//this.recvice();
	}

	
	@Override
	protected void initConnectionFactory() throws JMSException {
		this.connectionFactory = new ActiveMQConnectionFactory(MQConfig.getUsername(),
				MQConfig.getPassword(), MQConfig.getBrokeUrl());
	}
	@Override
	protected Session createSession(Connection connection) throws JMSException {
		if (connection == null)
			throw new JMSException("connection not null");

		return connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
	}

	public void setApp(App app) {
		this.app = app;
	}
	 
}
