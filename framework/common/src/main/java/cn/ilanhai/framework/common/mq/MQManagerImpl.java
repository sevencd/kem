package cn.ilanhai.framework.common.mq;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.jms.JMSException;

import cn.ilanhai.framework.common.configuration.app.mq.MQConf;
import cn.ilanhai.framework.common.exception.JMSAppException;
import cn.ilanhai.framework.common.mq.consumer.AbstractConsumer;
import cn.ilanhai.framework.common.mq.producer.AbstractProducer;
import cn.ilanhai.framework.uitl.Str;

public class MQManagerImpl implements MQManager {
	private Map<String, AbstractProducer> producer = null;
	private Map<String, AbstractConsumer> consumer = null;
	private boolean flag = false;

	public MQManagerImpl() {
		this.producer = new HashMap<String, AbstractProducer>();
		this.consumer = new HashMap<String, AbstractConsumer>();
	}


	public boolean register(AbstractMQ abstractMQ) {
		if (this.flag)
			return false;
		if (abstractMQ == null)
			return false;
		if (abstractMQ instanceof AbstractProducer)
			return this.register((AbstractProducer) abstractMQ);
		else if (abstractMQ instanceof AbstractConsumer)
			return this.register((AbstractConsumer) abstractMQ);
		else
			return false;
	}

	private boolean register(AbstractProducer abstractProducer) {
		String id = null;
		if (abstractProducer == null)
			return false;
		id = abstractProducer.getId();
		if (Str.isNullOrEmpty(id))
			return false;
		if (this.producer.containsKey(abstractProducer.getId()))
			return false;
		this.producer.put(id, abstractProducer);
		return true;
	}

	private boolean register(AbstractConsumer abstractConsumer) {
		String key = null;
		if (abstractConsumer == null)
			return false;
		key = this.getKey(abstractConsumer);
		if (Str.isNullOrEmpty(key))
			return false;
		if (this.consumer.containsKey(key))
			return false;
		this.consumer.put(key, abstractConsumer);
		return true;
	}

	public boolean unRegister(AbstractMQ abstractMQ) {
		if (this.flag)
			return false;
		if (abstractMQ == null)
			return false;
		if (abstractMQ instanceof AbstractProducer)
			return this.unRegister((AbstractProducer) abstractMQ);
		else if (abstractMQ instanceof AbstractConsumer)
			return this.unRegister((AbstractConsumer) abstractMQ);
		else
			return false;
	}

	private boolean unRegister(AbstractProducer abstractProducer) {
		String id = null;
		if (abstractProducer == null)
			return false;
		id = abstractProducer.getId();
		if (Str.isNullOrEmpty(id))
			return false;
		if (!this.producer.containsKey(abstractProducer.getId()))
			return false;
		this.producer.remove(id);
		return true;
	}

	private boolean unRegister(AbstractConsumer abstractConsumer) {
		String key = null;
		if (abstractConsumer == null)
			return false;
		key = this.getKey(abstractConsumer);
		if (Str.isNullOrEmpty(key))
			return false;
		if (!this.consumer.containsKey(key))
			return false;
		this.consumer.remove(key);
		return true;
	}

	public AbstractProducer getProducer(String id) throws JMSAppException {
		if (!this.flag)
			throw new JMSAppException("");
		if (Str.isNullOrEmpty(id))
			return null;
		if (!this.producer.containsKey(id))
			return null;
		return this.producer.get(id);
	}

	public boolean init(List<MQConf> mqConf) throws JMSAppException {
		try {
			if (this.flag)
				return true;
			this.initProducer();
			this.initConsumer();
			this.flag = true;
			return true;
		} catch (JMSAppException e) {
			throw e;
		}
	}

	private void initProducer() throws JMSAppException {

		Iterator<Entry<String, AbstractProducer>> iterator = null;
		Entry<String, AbstractProducer> entry = null;
		try {
			iterator = this.producer.entrySet().iterator();
			while (iterator.hasNext()) {
				entry = iterator.next();
				entry.getValue().initConnectionFactory();
			}
		} catch (JMSException e) {
			throw new JMSAppException();
		}
	}

	private void initConsumer() throws JMSAppException {
		Iterator<Entry<String, AbstractConsumer>> iterator = null;
		Entry<String, AbstractConsumer> entry = null;
		try {
			iterator = this.consumer.entrySet().iterator();
			while (iterator.hasNext()) {
				entry = iterator.next();
				entry.getValue().initConnectionFactory();
				entry.getValue().recvice();
			}
		} catch (JMSException e) {
			throw new JMSAppException();
		}
	}

	public void close() throws JMSAppException {
		Iterator<Entry<String, AbstractConsumer>> iterator = null;
		Entry<String, AbstractConsumer> entry = null;
		try {
			if (!this.flag)
				return;
			iterator = this.consumer.entrySet().iterator();
			while (iterator.hasNext()) {
				entry = iterator.next();
				entry.getValue().close();
			}
			this.flag = false;
		} catch (JMSAppException e) {
			throw e;
		}
	}
	
	private String getKey(AbstractConsumer consumer){
		return consumer.getId()+consumer.getClass().getName();
	}

}
