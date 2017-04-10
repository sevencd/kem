package cn.ilanhai.kem.mail.protocol.sohu.address;

import java.util.HashMap;
import java.util.Map;

import cn.ilanhai.framework.uitl.Str;
import cn.ilanhai.kem.mail.protocol.MailInfo;
import cn.ilanhai.kem.mail.protocol.MailInfo.DataType;

/**
 * 查询成员列表请示
 * 
 * @author he
 *
 */
public class QueryMemberListRequest extends MailInfo {
	public QueryMemberListRequest() {
		this.dataType = DataType.QueryMemberList;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public Map<String, String> toMap() {
		Map<String, String> map = null;
		String tmp = null;
		map = new HashMap<String, String>();
		tmp = this.getAddress();
		if (!Str.isNullOrEmpty(tmp))
			map.put("address", tmp);
		if (this.start > 0)
			map.put("start", this.encode( String.format("%s", this.start)));
		if (this.limit > 0)
			map.put("limit", this.encode( String.format("%s", this.limit)));
		return map;
	}

	/**
	 * 是 地址列表的别称地址
	 */
	private String address;
	/**
	 * 否 查询起始位置, 取值区间 [0-], 默认为 0
	 */
	private int start;
	/**
	 * 否 查询个数, 取值区间 [0-100], 默认为 100
	 */
	private int limit;
}
