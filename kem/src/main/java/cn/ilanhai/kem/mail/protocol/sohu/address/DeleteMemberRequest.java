package cn.ilanhai.kem.mail.protocol.sohu.address;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.ilanhai.framework.uitl.Str;
import cn.ilanhai.kem.mail.protocol.MailInfo;
import cn.ilanhai.kem.mail.protocol.MailInfo.DataType;

public class DeleteMemberRequest extends MailInfo {
	public DeleteMemberRequest() {
		this.member = new ArrayList<String>();
		this.dataType = DataType.DeleteMember;
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
			map.put("members",  this.encode(this.listToString(this.member, SPLIT)));
		return map;
	}

	/**
	 * 是 地址列表的别称地址
	 */
	private String address;
	/**
	 * 是 需要删除成员的地址, 多个地址用 ; 分隔
	 */
	private List<String> member;
	private final char SPLIT = ';';
}
