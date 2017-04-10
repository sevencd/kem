package cn.ilanhai.kem.queue;

import javax.jms.JMSException;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.Logger;

import cn.ilanhai.framework.common.mq.producer.QueueAbstractProducer;
import cn.ilanhai.kem.App;
import cn.ilanhai.kem.event.DomainEventManager;
import cn.ilanhai.kem.event.MQConfig;

/**
 * 通用消息队列生产者
 * 消息队列根据其传输的MSG不同而不同
 * @author Nature
 *
 * @param <MSG>
 */
public class QueueProducer extends QueueAbstractProducer{
	
	private Logger logger = Logger.getLogger(QueueProducer.class);
	
	private App app;//应用程序上下文
	private Class<?> msgClass;//消息类型
	
	public QueueProducer(Class<?> msgClass,App app){
		this.msgClass=msgClass;
		this.app=app;
		
		logger.info("构造消息生产者："+this.getId());
	}
	
	/**
	 * ID总是为消息的类型全称
	 */
	@Override
	protected String getId() {
		return msgClass.getName();
	}

	@Override
	protected void initConnectionFactory() throws JMSException {
		this.connectionFactory = new ActiveMQConnectionFactory(MQConfig.getUsername(),
				MQConfig.getPassword(), MQConfig.getBrokeUrl());
	}

}
