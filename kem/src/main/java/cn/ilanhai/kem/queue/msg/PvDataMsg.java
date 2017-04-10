package cn.ilanhai.kem.queue.msg;

import cn.ilanhai.kem.queue.BaseQueueMsg;

/**
 * pv数据消息
 * @author Nature
 *
 */
public class PvDataMsg extends BaseQueueMsg{

	private static final long serialVersionUID = 2461999610204112369L;
	
	private String testMsg;

	public String getTestMsg() {
		return testMsg;
	}

	public void setTestMsg(String testMsg) {
		this.testMsg = testMsg;
	}
	
	
}
