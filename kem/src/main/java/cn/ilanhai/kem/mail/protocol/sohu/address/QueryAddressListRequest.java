package cn.ilanhai.kem.mail.protocol.sohu.address;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.ilanhai.framework.uitl.Str;
import cn.ilanhai.kem.mail.protocol.MailInfo;
import cn.ilanhai.kem.mail.protocol.MailInfo.DataType;

public class QueryAddressListRequest extends MailInfo {

	public QueryAddressListRequest() {
		this.address = new ArrayList<String>();
		this.dataType = DataType.QueryAddressList;
	}

	public List<String> getAddress() {
		return address;
	}

	public void setAddress(List<String> address) {
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
		map = new HashMap<String, String>();
		if (this.address != null && this.address.size() > 0)
			map.put("address",  this.encode(this.listToString(this.address, SPLIT)));
		if (this.start > 0)
			map.put("start", this.encode( String.format("%s", this.start)));
		if (this.limit > 0)
			map.put("limit",  this.encode(String.format("%s", this.limit)));
		return map;
	}

	/**
	 * 否 别名地址的列表, 多个用 ; 分隔
	 */
	private List<String> address = null;
	/**
	 * 否 查询起始位置, 取值区间 [0-], 默认为 0
	 */
	private int start = 0;
	/**
	 * 否 查询个数, 取值区间 [0-100], 默认为 100
	 */
	private int limit = 0;
	private final char SPLIT = ';';
}
