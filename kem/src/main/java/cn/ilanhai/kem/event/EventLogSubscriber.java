package cn.ilanhai.kem.event;

import javax.jms.JMSException;
import javax.jms.TextMessage;

import org.apache.log4j.Logger;
import org.jboss.netty.util.NetUtil;
import org.springframework.context.ApplicationContext;

import cn.ilanhai.framework.app.dao.Dao;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.framework.common.exception.JMSAppException;
import cn.ilanhai.framework.uitl.FastJson;
import cn.ilanhai.kem.App;
import cn.ilanhai.kem.dao.event.EventDao;
import cn.ilanhai.kem.domain.event.EventEntity;

/**
 * 领域事件日志订阅者
 * 所有的领域事件均会被该订阅者订阅并存入日志
 * @author Nature
 *
 */
public class EventLogSubscriber extends BaseSubscriber {
	private Logger logger = Logger.getLogger(EventLogSubscriber.class);

	protected EventLogSubscriber() throws JMSException, JMSAppException {
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
		Dao dao = null;
		EventEntity entity = null;
		ApplicationContext ctx = null;
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
			if (this.app == null) {
				logger.info("app错误");
				return;
			}

			ctx = this.app.getApplicationContext();
			dao = ctx.getBean(EventDao.class);
			if (dao == null) {
				logger.info("事件数据访问对象错误");
				return;
			}
			//组装领域事件实体
			entity = new EventEntity();
			entity.setArgs(de.getArgs());
			entity.setArgsName(de.getArgsName());
			entity.setId(de.getId());
			entity.setCreateTime(de.getCreateTime());
			entity.setSessionId(de.getSessionId());
			entity.setUri(de.getUri());
			entity.setUserId(de.getUserId());
			if (dao.save(entity) <= 0) {
				logger.info("保存事件数据失败");
				return;
			}
			logger.info("保存事件数据成功");
		} catch (JMSException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (DaoAppException e) {
			throw new RuntimeException(e.getMessage(), e);
		}

	}
}
