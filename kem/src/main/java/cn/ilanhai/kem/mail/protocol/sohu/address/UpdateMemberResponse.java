package cn.ilanhai.kem.mail.protocol.sohu.address;

import cn.ilanhai.kem.mail.protocol.sohu.SohuResult;

public class UpdateMemberResponse extends SohuResult {
	public UpdateMemberResponse() {

	}

	public UpdateMemberInfo getInfo() {
		return info;
	}

	public void setInfo(UpdateMemberInfo info) {
		this.info = info;
	}

	private UpdateMemberInfo info;
}
