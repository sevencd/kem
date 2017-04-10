package cn.ilanhai.kem.event.args;

import cn.ilanhai.kem.event.DomainEventArgs;

public class MsgDomainEventArgs extends DomainEventArgs {
	static final long serialVersionUID = 1L;
	private String msg;

	public MsgDomainEventArgs(String msg) {
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	

}
