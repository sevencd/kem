package cn.ilanhai.kem.event;

import javax.jms.JMSException;
import javax.jms.TextMessage;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

import cn.ilanhai.framework.app.dao.Dao;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.framework.common.exception.JMSAppException;
import cn.ilanhai.framework.uitl.FastJson;
import cn.ilanhai.kem.dao.event.EventDao;
import cn.ilanhai.kem.domain.event.EventEntity;

/**
 * 散播分配领域事件订阅者
 * 
 * @author Nature
 *
 */
public class EventDispatchSubscriber extends BaseSubscriber{
	private Logger logger = Logger.getLogger(EventDispatchSubscriber.class);

	protected EventDispatchSubscriber() throws JMSException, JMSAppException {
		super();
	}

	@Override
	protected String getId() {
		return EventTopic.topicName;
	}
	
	@Override
	protected void recviceText(TextMessage textMessage) throws RuntimeException {
		String tmp = null;
		DomainEvent de = null;

		try {
			//数据校验
			tmp = textMessage.getText();
			if (tmp == null || tmp.length() <= 0) {
				logger.info("接收到文本消息错误");
				return;
			}
			de = FastJson.json2Bean(tmp, DomainEvent.class);
			if (de == null) {
				logger.info("接收到文本消息反序列化对象错误");
				return;
			}
			
			//发布事件
			DomainEventManager.getInstance().publishRaiseEvent(de);
			
		} catch (JMSException e) {
			throw new RuntimeException(e.getMessage(), e);
		}

	}

}
