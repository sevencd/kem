package cn.ilanhai.kem.mail.protocol.sohu.label;

import cn.ilanhai.kem.mail.protocol.Result;

public class AddLabelResponse extends Result {
	public AddLabelResponse() {

	}

	public AddLabelInfo getInfo() {
		return info;
	}

	public void setInfo(AddLabelInfo info) {
		this.info = info;
	}

	private AddLabelInfo info;
}
