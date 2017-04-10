package cn.ilanhai.kem.event.args;

import cn.ilanhai.kem.event.DomainEventArgs;

/**
 * 测试消息2
 * @author Nature
 *
 */
public class TestDomainEventArgs2  extends DomainEventArgs{

	private static final long serialVersionUID = 4786333494541800488L;

	private String msg3;
	private String msg4;
	
	public String getMsg3() {
		return msg3;
	}
	public void setMsg3(String msg3) {
		this.msg3 = msg3;
	}
	public String getMsg4() {
		return msg4;
	}
	public void setMsg4(String msg4) {
		this.msg4 = msg4;
	}
	
	
}
