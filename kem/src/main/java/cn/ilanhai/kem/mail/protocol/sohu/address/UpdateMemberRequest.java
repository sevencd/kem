package cn.ilanhai.kem.mail.protocol.sohu.address;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.ilanhai.framework.uitl.ExpressionMatchUtil;
import cn.ilanhai.framework.uitl.Str;
import cn.ilanhai.kem.mail.protocol.MailInfo;
import cn.ilanhai.kem.mail.protocol.MailInfo.DataType;

/**
 * 更新成员信息
 * 
 * @author he
 *
 */
public class UpdateMemberRequest extends MailInfo {
	public UpdateMemberRequest() {
		this.member = new ArrayList<String>();
		this.newMember = new ArrayList<String>();
		this.name = new ArrayList<String>();
		this.var = new ArrayList<String>();
		this.dataType = DataType.UpdateMember;
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

	public List<String> getNewMember() {
		return newMember;
	}

	public void setNewMember(List<String> newMember) {
		this.newMember = newMember;
	}

	public List<String> getName() {
		return name;
	}

	public void setName(List<String> name) {
		this.name = name;
	}

	public List<String> getVar() {
		return var;
	}

	public void setVar(List<String> var) {
		this.var = var;
	}

	public boolean mailMaxMemberIsOk(int maxValue) {
		if (this.member.size() > maxValue)
			return false;
		if (this.name.size() > maxValue)
			return false;
		return true;
	}

	public boolean mailAddressFormatIsOk(List<String> ls) {
		if (ls == null || ls.size() <= 0)
			return false;
		for (int i = 0; i < ls.size(); i++) {
			if (!ExpressionMatchUtil.isEmailAddress(ls.get(i)))
				return false;
		}
		return true;
	}

	public Map<String, String> toMap() {
		Map<String, String> map = null;
		String tmp = null;
		map = new HashMap<String, String>();
		tmp = this.getAddress();
		if (!Str.isNullOrEmpty(tmp))
			map.put("address", this.encode(tmp));
		if (this.member != null && this.member.size() > 0)
			map.put("members",
					this.encode(this.listToString(this.member, SPLIT)));
		if (this.newMember != null && this.newMember.size() > 0)
			map.put("newMembers",
					this.encode(this.listToString(this.newMember, SPLIT)));
		if (this.name != null && this.name.size() > 0)
			map.put("names", this.encode(this.listToString(this.name, SPLIT)));

		return map;
	}

	/**
	 * 是 地址列表的别称地址
	 */
	private String address;
	/**
	 * 是 需要更新成员的旧地址, 多个地址用 ; 分隔
	 */
	private List<String> member;
	/**
	 * 是 需要更新成员的新地址, 多个地址用 ; 分隔,并且必须和members中的成员一一对应
	 */
	private List<String> newMember;
	/**
	 * 否 地址成员姓名, 多个地址用 ; 分隔
	 */
	private List<String> name;
	/**
	 * 否 替换变量, 与 members 一一对应, 变量格式为 {"money":"1000"} , 多个用 ; 分隔
	 */
	private List<String> var;
	private final char SPLIT = ';';
}
