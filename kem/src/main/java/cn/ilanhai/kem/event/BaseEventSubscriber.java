package cn.ilanhai.kem.event;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jms.*;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

import cn.ilanhai.framework.app.dao.Dao;
import cn.ilanhai.framework.common.exception.JMSAppException;
import cn.ilanhai.framework.uitl.FastJson;
import cn.ilanhai.kem.domain.event.EventEntity;

/**
 * 领域事件处理者基类
 * 
 * @author Nature
 *
 */
public abstract class BaseEventSubscriber extends BaseSubscriber {

	private Logger logger = Logger.getLogger(BaseEventSubscriber.class);

	// 处理的消息类型
	protected Class<? extends DomainEventArgs> eventClass;
	// 注册的消息类型，该列表存有所有监听的消息类型
	protected Map<String, Class<? extends DomainEventArgs>> registEventList = new HashMap<String, Class<? extends DomainEventArgs>>();

	private Map<String, Connection> connections = new HashMap<String, Connection>();
	private Map<String, Session> sessions = new HashMap<String, Session>();
	private Map<String, Destination> destinations = new HashMap<String, Destination>();
	private Map<String, MessageConsumer> messageConsumers = new HashMap<String, MessageConsumer>();

	// 构造函数，必须传入该订阅者订阅的消息类型
	protected BaseEventSubscriber(Class<? extends DomainEventArgs> eventClass) throws JMSException, JMSAppException {
		super();
		this.eventClass = eventClass;
	}

	public BaseEventSubscriber cloneSubscriber(String queueId) {
		BaseEventSubscriber newSub = null;

		return newSub;
	}

	public Class<?> getEventClass() {
		return this.eventClass;
	}

	// 该ID用作消息队列的ID
	@Override
	public String getId() {
		return "event_" + this.getEventClass().getName();
	}

	/**
	 * 该订阅者监听的事件列表
	 * 
	 * @return
	 */
	public Map<String, Class<? extends DomainEventArgs>> getRegistEventList() {
		return registEventList;
	}

	/**
	 * 重载监听方法
	 */
	@Override
	public void recvice() throws JMSAppException {
		// 遍历所有监听事件类型
		for (Class<? extends DomainEventArgs> eventClass : this.registEventList.values()) {
			this.recive(eventClass);
		}
	}

	// 添加监听的事件
	protected void addRegistEvent(Class<? extends DomainEventArgs> clazz) {
		if (this.registEventList.containsKey(clazz.getName())) {
			this.registEventList.remove(clazz.getName());
		}
		this.getRegistEventList().put(clazz.getName(), clazz);
	}

	/**
	 * 创建指定消息类型的监听目标
	 * 
	 * @param session
	 * @param eventClass
	 * @return
	 * @throws JMSException
	 */
	private Destination createDestination(Session session, Class<? extends DomainEventArgs> eventClass)
			throws JMSException {
		String id = null;
		if (session == null)
			throw new JMSException("session not null");
		id = this.getId();
		if (id == null || id.length() <= 0)
			throw new JMSException("topic name error");

		return session.createTopic("event_" + eventClass.getName());
	}

	// 监听指定消息
	private void recive(Class<? extends DomainEventArgs> eventClass) throws JMSAppException {
		// 开启指定事件的监听

		try {
			if (this.connections.containsKey(eventClass.getName())) {
				return;
			}
			Connection connection = this.createConnection(this.connectionFactory);
			this.connections.put(eventClass.getName(), connection);
			connection.start();

			Session session = this.createSession(connection);
			this.sessions.put(eventClass.getName(), session);

			// Destination destination = this.createDestination(session);
			Destination destination = this.createDestination(session, eventClass);
			this.destinations.put(eventClass.getName(), destination);

			MessageConsumer messageConsumer = this.createMessageConsumer(session, destination);
			this.messageConsumers.put(eventClass.getName(), messageConsumer);

			// 设置监听者，用来监听消息
			messageConsumer.setMessageListener(new MessageListener() {
				// 收到消息后会自动调用onMessage方法
				@Override
				public void onMessage(Message message) throws RuntimeException {
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

	@Override
	protected void recviceText(TextMessage textMessage) throws RuntimeException {
		String tmp = null;
		DomainEvent de = null;
		DomainEventArgs args = null;
		// 获得文本数据
		try {
			tmp = textMessage.getText();
		} catch (JMSException e) {
			logger.error(this.getClass().getName() + "未接收到消息");
			e.printStackTrace();
		}
		if (tmp == null || tmp.length() <= 0) {
			logger.info("接收到文本消息错误");
			return;
		}
		// 反序列化对象，获得事件对象
		de = FastJson.json2Bean(tmp, DomainEvent.class);
		if (de == null) {
			logger.info("接收到文本消息反序列化对象错误");
			return;
		}

		// 反序列化出对应的args
		if (this.registEventList.containsKey(de.getArgsName())) {
			args = FastJson.json2Bean(de.getArgs(), this.registEventList.get(de.getArgsName()));
		} else {
			logger.warn("未订阅类型：" + de.getArgsName());
		}

		args=this.convertArgs(args);
		
		this.reciveMsg(de,args);
	}
	
	/**
	 * 将对象转换为自身的参数对象
	 * @param srcArgs
	 * @return
	 */
	protected DomainEventArgs convertArgs(DomainEventArgs srcArgs){
		Object result=null;
		//获得所有convert，入参是该类型的方法
		try {
			Method method=this.getClass().getMethod("convert", srcArgs.getClass());
			result=method.invoke(this, srcArgs);
		} catch (NoSuchMethodException 
				| SecurityException 
				| IllegalAccessException 
				| IllegalArgumentException 
				| InvocationTargetException e) {
			return null;
		}
		
		return (DomainEventArgs) result;
	}

	protected abstract void reciveMsg(DomainEvent event, DomainEventArgs args);
}
