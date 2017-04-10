package cn.ilanhai.kem.mail.protocol.sohu.address;

import java.util.*;

import cn.ilanhai.framework.uitl.ExpressionMatchUtil;
import cn.ilanhai.framework.uitl.Str;
import cn.ilanhai.kem.mail.protocol.MailInfo;
import cn.ilanhai.kem.mail.protocol.MailInfo.DataType;

/**
 * 添加成员请求<br/>
 * 1. 每次请求最多可以添加1000个成员<br/>
 * 2. 如果包含 vars 变量, 则必须与 members 的成员数量一致<br/>
 * 3. 添加 vars 变量, 注意 key 不需要 带上 '%'<br/>
 * 4. vars 变量中, key 为 name 的变量会被参数 name 覆盖<br/>
 * 5. 地址列表发送时, 可以使用全局变量 recipient, 值为收件人的邮箱地址<br/>
 * 
 * @author he
 *
 */
public class AddMemberRequest extends MailInfo {
	public AddMemberRequest() {
		this.member = new ArrayList<String>();
		this.name = new ArrayList<String>();
		this.var = new HashMap<String, String>();
		this.dataType = DataType.AddMember;
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

	public List<String> getName() {
		return name;
	}

	public void setName(List<String> name) {
		this.name = name;
	}

	public Map<String, String> getVar() {
		return var;
	}

	public void setVar(Map<String, String> var) {
		this.var = var;
	}

	public boolean mailMaxMemberIsOk(int maxValue) {
		if (this.member.size() > maxValue)
			return false;
		if (this.name.size() > maxValue)
			return false;
		return true;
	}

	public boolean mailAddressFormatIsOk() {
		for (int i = 0; i < this.member.size(); i++) {
			if (!ExpressionMatchUtil.isEmailAddress(this.member.get(i)))
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
			map.put("address", tmp);
		if (this.member != null && this.member.size() > 0)
			map.put("members", this.listToString(this.member, SPLIT));
		if (this.name != null && this.name.size() > 0)
			map.put("names", this.listToString(this.name, SPLIT));

		return map;
	}

	/**
	 * 是 地址列表的别称地址
	 */
	private String address;
	/**
	 * 是 需要添加成员的地址, 多个地址用 ; 分隔
	 */
	private List<String> member;
	/**
	 * 否 地址成员姓名, 多个地址用 ; 分隔
	 */
	private List<String> name;
	/**
	 * 否 替换变量, 与 members 一一对应, 变量格式为 {"money":"1000"} , 多个用 ; 分隔
	 */
	private Map<String, String> var;

	private final char SPLIT = ';';
}
