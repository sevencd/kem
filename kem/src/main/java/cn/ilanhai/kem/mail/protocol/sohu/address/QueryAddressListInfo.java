package cn.ilanhai.kem.mail.protocol.sohu.address;

import java.util.List;

/**
 * 查询信息
 * 
 * @author he
 *
 */
public class QueryAddressListInfo {

	public QueryAddressListInfo() {

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

	public List<Address> getDataList() {
		return dataList;
	}

	public void setDataList(List<Address> dataList) {
		this.dataList = dataList;
	}

	/**
	 * 合
	 */
	private int total;
	/**
	 * 数据
	 */
	private int count;
	/**
	 * 数据
	 */
	private List<Address> dataList;
}
