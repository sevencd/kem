package cn.ilanhai.kem.event;

import javax.jms.JMSException;
import org.apache.activemq.ActiveMQConnectionFactory;
import cn.ilanhai.framework.common.mq.producer.TopicAbstractProducer;

/**
 * kem中的基础topic，用来统一处理topic的相关初始化及功能
 * @author Nature
 *
 */
public abstract class BaseTopic extends TopicAbstractProducer{
	@Override
	protected void initConnectionFactory() throws JMSException {
		this.connectionFactory = new ActiveMQConnectionFactory(MQConfig.getUsername(),
				MQConfig.getPassword(), MQConfig.getBrokeUrl());
	}
}
