package cn.ilanhai.kem.queue.msg;

import cn.ilanhai.kem.queue.BaseQueueMsg;
import cn.ilanhai.kem.sms.protocol.tosms.TosmsSmsInfo;

/**
 * 短信消息
 * 
 * @author he
 *
 */
public class SmsMsg<T extends TosmsSmsInfo> extends BaseQueueMsg {
	public SmsMsg() {

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
