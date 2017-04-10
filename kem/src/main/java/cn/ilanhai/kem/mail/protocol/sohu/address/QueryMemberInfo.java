package cn.ilanhai.kem.mail.protocol.sohu.address;

import java.util.ArrayList;
import java.util.List;

public class QueryMemberInfo {
	public QueryMemberInfo() {
		this.dataList = new ArrayList<Member>();
	}

	public List<Member> getDataList() {
		return dataList;
	}

	public void setDataList(List<Member> dataList) {
		this.dataList = dataList;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	private List<Member> dataList;
	private int count;
}
