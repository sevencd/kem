package cn.ilanhai.kem.event.args;

import cn.ilanhai.kem.event.DomainEventArgs;

/**
 * 测试消息1
 * @author Nature
 *
 */
public class TestDomainEventArgs1 extends DomainEventArgs {

	private static final long serialVersionUID = -7154228812075729804L;
	private String msg1;
	private String msg2;
	
	public String getMsg1() {
		return msg1;
	}
	public void setMsg1(String msg1) {
		this.msg1 = msg1;
	}
	public String getMsg2() {
		return msg2;
	}
	public void setMsg2(String msg2) {
		this.msg2 = msg2;
	}

}
