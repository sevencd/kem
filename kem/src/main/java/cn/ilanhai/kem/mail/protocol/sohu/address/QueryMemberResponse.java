package cn.ilanhai.kem.mail.protocol.sohu.address;

import cn.ilanhai.kem.mail.protocol.sohu.SohuResult;

public class QueryMemberResponse extends SohuResult {
	public QueryMemberResponse() {

	}

	public QueryMemberInfo getInfo() {
		return info;
	}

	public void setInfo(QueryMemberInfo info) {
		this.info = info;
	}

	private QueryMemberInfo info;
}
