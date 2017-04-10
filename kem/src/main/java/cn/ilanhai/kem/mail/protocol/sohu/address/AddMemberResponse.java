package cn.ilanhai.kem.mail.protocol.sohu.address;

import cn.ilanhai.kem.mail.protocol.sohu.SohuResult;

public class AddMemberResponse extends SohuResult {
	public AddMemberResponse() {

	}

	public AddAddressInfo getInfo() {
		return info;
	}

	public void setInfo(AddAddressInfo info) {
		this.info = info;
	}

	private AddAddressInfo info;
}
