package cn.ilanhai.kem.queue.msg;

import cn.ilanhai.kem.domain.contacts.dto.ContactMsgDto;
import cn.ilanhai.kem.queue.BaseQueueMsg;

/**
 * 联系人消息
 * 
 * @author hy
 *
 */
public class ContactMsg<T extends ContactMsgDto> extends BaseQueueMsg {
	public ContactMsg() {

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
