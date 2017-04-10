package cn.ilanhai.kem.queue.msg;

import cn.ilanhai.kem.domain.statistic.ManuscriptVisitEntity;
import cn.ilanhai.kem.queue.BaseQueueMsg;

/**
 * 稿件统计消息
 * 
 * @author he
 *
 */
public class ManuscriptStatisticMsg<T extends ManuscriptVisitEntity> extends
		BaseQueueMsg {
	public ManuscriptStatisticMsg() {

	}

	public T getMsgContent() {
		return msgContent;
	}

	public void setMsgContent(T msgContent) {
		this.msgContent = msgContent;
	}

	private static final long serialVersionUID = 1L;
	/**
	 * 消息内容
	 */
	private T msgContent;

}
