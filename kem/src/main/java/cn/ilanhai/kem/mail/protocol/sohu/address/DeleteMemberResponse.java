package cn.ilanhai.kem.mail.protocol.sohu.address;

import cn.ilanhai.kem.mail.protocol.sohu.SohuResult;

public class DeleteMemberResponse extends SohuResult {
	public DeleteMemberResponse() {

	}

	public DeleteMemberInfo getInfo() {
		return info;
	}

	public void setInfo(DeleteMemberInfo info) {
		this.info = info;
	}

	private DeleteMemberInfo info;
}
