package cn.ilanhai.kem.mail.protocol.sohu.address;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.ilanhai.framework.uitl.Str;
import cn.ilanhai.kem.mail.protocol.MailInfo;
import cn.ilanhai.kem.mail.protocol.MailInfo.DataType;

/**
 * 查询成员请求
 * 
 * @author he
 *
 */
public class QueryMemberRequest extends MailInfo {

	public QueryMemberRequest() {
		this.member = new ArrayList<String>();
		this.dataType = DataType.QueryMember;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<String> getMember() {
		return member;
	}

	public void setMember(List<String> member) {
		this.member = member;
	}

	public Map<String, String> toMap() {
		Map<String, String> map = null;
		String tmp = null;
		map = new HashMap<String, String>();
		tmp = this.getAddress();
		if (!Str.isNullOrEmpty(tmp))
			map.put("address",  this.encode(tmp));
		if (this.member != null && this.member.size() > 0)
			map.put("members", this.encode(this.listToString(this.member, SPLIT)));
		return map;
	}

	/**
	 * 是 地址列表的别称地址
	 */
	private String address;
	/**
	 * 是 列表成员地址
	 */
	private List<String> member;
	private final char SPLIT = ';';
}
