package cn.ilanhai.kem.mail.protocol.sohu.address;

import java.util.HashMap;
import java.util.Map;

import cn.ilanhai.framework.uitl.Str;
import cn.ilanhai.kem.mail.protocol.MailInfo;
import cn.ilanhai.kem.mail.protocol.MailInfo.DataType;

/**
 * 添加地址请求
 * 
 * @author he
 *
 */
public class AddAddressRequest extends MailInfo {

	public AddAddressRequest() {
		this.dataType = DataType.AddAddress;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Address.ListType getListType() {
		return listType;
	}

	public void setListType(Address.ListType listType) {
		this.listType = listType;
	}

	public Map<String, String> toMap() {
		Map<String, String> map = null;
		String tmp = null;
		map = new HashMap<String, String>();
		tmp = this.getAddress();
		if (!Str.isNullOrEmpty(tmp))
			map.put("address",  this.encode(tmp));
		tmp = this.name;
		if (!Str.isNullOrEmpty(tmp))
			map.put("name",  this.encode(tmp));
		tmp = this.desc;
		if (!Str.isNullOrEmpty(tmp))
			map.put("desc",  this.encode(tmp));
		if (listType != null)
			map.put("listType", "0");
		return map;
	}

	/**
	 * 是 别称地址, 使用该别称地址进行调用, 格式为xxx@maillist.sendcloud.org
	 */
	private String address;//
	/**
	 * 是 列表名称
	 */
	private String name;
	/**
	 * 否 对列表的描述信息
	 */
	private String desc;
	/**
	 * 否 列表的类型. 0: 普通地址列表, 默认为0
	 */
	private Address.ListType listType;

}
