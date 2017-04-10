package cn.ilanhai.kem.event;


/**
 * 领域事件主题
 * 所有的领域事件均发向该队列
 * 之后再处理该队列中的队列
 * @author Nature
 *
 */
public class EventTopic extends BaseTopic{

	public static final String topicName="event_topic";
	
	@Override
	protected String getId() {
		return topicName;
	}

}
