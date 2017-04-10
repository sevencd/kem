package cn.ilanhai.kem.event;

/**
 * 事件被分发
 * @author Nature
 *
 */
public class EventDispatchedTopic extends BaseTopic{

	private String topicId;
	
	public EventDispatchedTopic(String topicId){
		this.topicId=topicId;
	}
	
	@Override
	protected String getId() {
		return this.topicId;
	}

}
