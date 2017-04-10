package cn.ilanhai.kem.event;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.jms.JMSException;

import org.apache.log4j.Logger;

import cn.ilanhai.framework.app.RequestContext;
import cn.ilanhai.framework.common.configuration.app.mq.MQConf;
import cn.ilanhai.framework.common.exception.BlAppException;
import cn.ilanhai.framework.common.exception.JMSAppException;
import cn.ilanhai.framework.common.exception.SessionContainerException;
import cn.ilanhai.framework.common.mq.MQManager;
import cn.ilanhai.framework.common.mq.MQManagerImpl;
import cn.ilanhai.framework.common.mq.producer.AbstractProducer;
import cn.ilanhai.framework.common.session.Session;
import cn.ilanhai.framework.uitl.FastJson;
import cn.ilanhai.kem.App;
import cn.ilanhai.kem.bl.paymentservice.OrderManager;
import cn.ilanhai.kem.common.Constant;

/**
 * 领域事件管理类
 * 
 * @author Nature
 *
 */
public class DomainEventManager {
	
	private Logger logger = Logger.getLogger(DomainEventManager.class);
	
	private Map<String,EventRegistRecord> registRecords=new HashMap<String,EventRegistRecord>();

	// 单例相关内容
	private static DomainEventManager instance;
	static {
		instance = new DomainEventManager();
	}

	public static DomainEventManager getInstance() {
		return instance;
	}

	// mq管理器
	private static MQManager manager = new MQManagerImpl();

	// 初始化管理器
	public void init(App app) throws JMSAppException {
		List<MQConf> conf = null;
		MQConf mqConf = null;
		String tmp = null;
		EventLogSubscriber eventLogSubscriber = null;
		EventDispatchSubscriber eventDispatchSubscriber=null;
		try {
			conf = app.getConfigure().getMQConf();
			if (conf != null && conf.size() > 0) {
				mqConf = conf.get(0);
				if (mqConf != null) {
					tmp = mqConf.getBrokerUrl();
					if (tmp != null && tmp.length() > 0)
						MQConfig.setBrokeUrl(tmp);
					tmp = mqConf.getUserName();
					if (tmp != null && tmp.length() > 0)
						MQConfig.setUsername(tmp);
					tmp = mqConf.getPassword();
					if (tmp != null && tmp.length() > 0)
						MQConfig.setPassword(tmp);
				}
			}
			// 注册topic
			manager.register(new EventTopic());
			// 注册subscriber
			eventDispatchSubscriber=new EventDispatchSubscriber();
			eventDispatchSubscriber.setApp(app);
			manager.register(eventDispatchSubscriber);
			eventLogSubscriber = new EventLogSubscriber();
			eventLogSubscriber.setApp(app);
			manager.register(eventLogSubscriber);
			
			//领域事件相关初始化
			this.initEvents(app);
			
			manager.init(conf);

		} catch (JMSException e) {
			throw new JMSAppException(e.getMessage(), e);
		}
	}
	
	/**
	 * 初始化领域事件相关内容
	 */
	private void initEvents(App app){
		//加载所有事件订阅者
		Map<String, BaseEventSubscriber> subscribers= app.getApplicationContext().getBeansOfType(BaseEventSubscriber.class);
		if(subscribers!=null){
			//遍历素有的订阅者
			for(BaseEventSubscriber subscriber :subscribers.values()){
				//遍历订阅者订阅的所有消息类型
				for(Class<? extends DomainEventArgs> event:subscriber.getRegistEventList().values()){
					//是否已经存在该消息类型的订阅记录
					if(registRecords.containsKey(event.getName())){
						//已存在该记录
						EventRegistRecord registRecord=registRecords.get(event.getName());
						if(!registRecord.getSubscribers().containsKey(subscriber.getId())){
							registRecord.getSubscribers().put(subscriber.getId(), subscriber);
						}
					}else{
						//不存在该记录
						EventRegistRecord registRecord=new EventRegistRecord();
						registRecord.setQueueId(subscriber.getId());
						registRecord.getSubscribers().put(subscriber.getId(), subscriber);
						
						this.registRecords.put(event.getName(), registRecord);
					}
				}
				
			}
		}
		//初始化被订阅的消息的消息队列
		for(EventRegistRecord record:this.registRecords.values()){
			//创建消息topic
			manager.register(new EventDispatchedTopic(record.getQueueId()));
			
			
		}
		//注册对应的订阅者
		for(BaseEventSubscriber subscriber: subscribers.values()){
			manager.register(subscriber);
		}
		
		
	}

	/**
	 * 发送领域事件 该领域事件一定会被发送到EventTopic事件中去
	 * 
	 * @param event
	 *            被发送的事件
	 * @throws JMSAppException
	 * @throws BlAppException 
	 */
	public void raise(DomainEvent event) throws JMSAppException, BlAppException {
		
		if(event==null){
			throw new BlAppException(-1,"事件不可为空");
		}
		if(event.getId()==null||event.getId().isEmpty()){
			throw new BlAppException(-1,"事件ID必须存在");
		}
		
		String tmp = null;
		AbstractProducer producer = null;
		try {
			//将事件序列化成字符串
			tmp = FastJson.bean2Json(event);
			producer = manager.getProducer(EventTopic.topicName);
			//发送事件内容
			if (producer != null)
				producer.sendText(tmp);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	/**
	 * 使用上下文可以用该方法快速发布事件
	 * @param ctx
	 * @param args
	 * @throws JMSAppException
	 * @throws BlAppException 
	 */
	public void raise(RequestContext ctx, DomainEventArgs args)
			throws JMSAppException, BlAppException {
		
		if(args==null){
			throw new BlAppException(-1,"事件内容不可为空");
		}
		//组装领域事件
		DomainEvent event = this.packageDomainEvent(ctx, args);
		
		this.raise(event);

	}
	
	/**
	 * 广播领域事件
	 * 该方法将会被DomainDispatchSubscriber直接调用
	 * @param ctx
	 * @param args
	 * @throws BlAppException 
	 */
	public void publishRaiseEvent(DomainEvent event){
		//args不可为空
		if(event==null){
			logger.error("事件不可以为空："+event);
			return;
		}
		if(event.getId()==null||event.getId().isEmpty()){
			logger.error("事件ID必须存在"+event);
			return;
		}
		if(event.getArgsName()==null||event.getArgsName().isEmpty()){
			logger.error("参数名称必须存在"+event);
			return;
		}
		
		String tmp = null;
		AbstractProducer producer = null;
		try {
			//将事件序列化成字符串
			tmp = FastJson.bean2Json(event);
			producer = manager.getProducer("event_"+event.getArgsName());
			//发送事件内容
			if (producer != null)
				producer.sendText(tmp);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		//将event发送至对应的消息队列
	}
	
	/**
	 * 根据参数组装领域事件
	 * @param ctx
	 * @param args
	 * @return
	 */
	private DomainEvent packageDomainEvent(RequestContext ctx, DomainEventArgs args){
		DomainEvent event=null;
		Session session = null;
		String tmp = null;
		
		event = new DomainEvent();
		event.setId(UUID.randomUUID().toString());
		event.setCreateTime(new Date());
		event.setArgs(args);
		
		if (ctx != null) {
			session = ctx.getSession();
			//赋值uri
			event.setUri(ctx.getLocation().toString());
			//如果有会话，则赋值sessionId
			if (session != null)
				event.setSessionId(session.getSessionId());
			
			//获取用户ID
			try {
				//获取用户ID
				tmp = session.getParameter(Constant.KEY_SESSION_USERID,
						String.class);
			} catch (SessionContainerException e) {
				//不存在用户ID
			}
			//设置用户ID
			event.setUserId(tmp);
		}
		return event;
	}
}
