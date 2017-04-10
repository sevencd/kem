package cn.ilanhai.kem.queue.msg;

import cn.ilanhai.framework.uitl.FastJson;
import cn.ilanhai.framework.uitl.Str;
import cn.ilanhai.kem.mail.protocol.MailInfo;
import cn.ilanhai.kem.mail.protocol.sohu.SohuMailInfo;
import cn.ilanhai.kem.queue.BaseQueueMsg;

/**
 * 稿件统计消息
 * 
 * @author he
 *
 */
public class MailMsg<T extends MailInfo> extends BaseQueueMsg {
	public MailMsg() {

	}

	public T getMsgContent() {
		return msgContent;
	}

	public void setMsgContent(T msgContent) {
		this.rawMsg = FastJson.bean2Json(msgContent);
		this.msgContent = msgContent;

	}

	public String getRawMsg() {
		return rawMsg;
	}

	public void setRawMsg(String rawMsg) {
		this.rawMsg = rawMsg;
	}

	private static final long serialVersionUID = 1L;
	/**
	 * 消息内容
	 */
	private T msgContent;
	/**
	 * 源消息
	 */
	private String rawMsg;

}
