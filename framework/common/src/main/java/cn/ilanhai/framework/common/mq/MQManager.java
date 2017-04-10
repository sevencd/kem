package cn.ilanhai.framework.common.mq;


import java.util.List;

import cn.ilanhai.framework.common.configuration.app.mq.MQConf;
import cn.ilanhai.framework.common.exception.JMSAppException;
import cn.ilanhai.framework.common.mq.producer.AbstractProducer;

public interface MQManager {

	boolean register(AbstractMQ abstractMQ);

	boolean unRegister(AbstractMQ abstractMQ);

	AbstractProducer getProducer(String id) throws JMSAppException;

	boolean init(List<MQConf> mqConf) throws JMSAppException;

	void close() throws JMSAppException;

}
