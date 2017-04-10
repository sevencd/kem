package cn.ilanhai.kem.mail.protocol.sohu.address;

import java.util.HashMap;
import java.util.Map;

import cn.ilanhai.framework.uitl.Str;
import cn.ilanhai.kem.mail.protocol.MailInfo;
import cn.ilanhai.kem.mail.protocol.MailInfo.DataType;

/**
 * 修改地址请求
 * 
 * @author he
 *
 */
public class UpdateAddressRequest extends MailInfo {
	public UpdateAddressRequest() {
		this.dataType = DataType.UpdateAddress;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getNewAddress() {
		return newAddress;
	}

	public void setNewAddress(String newAddress) {
		this.newAddress = newAddress;
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

	public Map<String, String> toMap() {
		Map<String, String> map = null;
		String tmp = null;
		map = new HashMap<String, String>();
		tmp = this.address;
		if (!Str.isNullOrEmpty(tmp))
			map.put("address",  this.encode(tmp));
		tmp = this.newAddress;
		if (!Str.isNullOrEmpty(tmp))
			map.put("newAddress",  this.encode(tmp));
		tmp = this.name;
		if (!Str.isNullOrEmpty(tmp))
			map.put("name",  this.encode(tmp));
		tmp = this.desc;
		if (!Str.isNullOrEmpty(tmp))
			map.put("desc",  this.encode(tmp));

		return map;
	}

	/**
	 * 是 别称地址, 使用该别称地址进行调用, 格式为xxx@maillist.sendcloud.org
	 */
	private String address;
	/**
	 * 否 修改后的别称地址
	 */
	private String newAddress;
	/**
	 * 否 修改后的列表名称
	 */
	private String name;
	/**
	 * 否 修改后的列表描述信息
	 */
	private String desc;

}
