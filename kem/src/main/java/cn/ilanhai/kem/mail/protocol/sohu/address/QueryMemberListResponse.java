package cn.ilanhai.kem.mail.protocol.sohu.address;

import cn.ilanhai.kem.mail.protocol.sohu.SohuResult;

/**
 * 查询会员列表响应
 * 
 * @author he
 *
 */
public class QueryMemberListResponse extends SohuResult {
	public QueryMemberListResponse() {

	}

	public QueryMemberListInfo getInfo() {
		return info;
	}

	public void setInfo(QueryMemberListInfo info) {
		this.info = info;
	}

	private QueryMemberListInfo info;
}
