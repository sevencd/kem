package cn.ilanhai.kem.mail.protocol.sohu.address;

import java.util.List;

public class QueryMemberListInfo {
	public QueryMemberListInfo() {

	}

	public List<QueryMemberListInfo> getDataList() {
		return dataList;
	}

	public void setDataList(List<QueryMemberListInfo> dataList) {
		this.dataList = dataList;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	private List<QueryMemberListInfo> dataList;
	private int total;
	private int count;
}
