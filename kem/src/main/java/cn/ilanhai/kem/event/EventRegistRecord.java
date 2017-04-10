package cn.ilanhai.kem.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 领域事件注册信息
 * @author Nature
 *
 */
public class EventRegistRecord {

	//队列ID
	private String queueId;
	//订阅者实例列表
	private Map<String,BaseEventSubscriber> subscribers=new HashMap<String,BaseEventSubscriber>();
	
	public String getQueueId() {
		return queueId;
	}
	public void setQueueId(String queueId) {
		this.queueId = queueId;
	}
	public Map<String,BaseEventSubscriber> getSubscribers() {
		return subscribers;
	}
	
}
