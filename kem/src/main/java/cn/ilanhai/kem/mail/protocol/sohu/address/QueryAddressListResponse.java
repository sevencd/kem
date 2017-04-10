package cn.ilanhai.kem.mail.protocol.sohu.address;

import cn.ilanhai.kem.mail.protocol.sohu.SohuResult;

/**
 * 查询列表地址返回数据
 * 
 * @author he
 *
 */
public class QueryAddressListResponse extends SohuResult {

	public QueryAddressListResponse() {

	}

	public QueryAddressListInfo getInfo() {
		return info;
	}

	public void setInfo(QueryAddressListInfo info) {
		this.info = info;
	}

	/**
	 * 信息
	 */
	private QueryAddressListInfo info;
}
