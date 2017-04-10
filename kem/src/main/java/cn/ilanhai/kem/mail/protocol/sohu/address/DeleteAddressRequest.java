package cn.ilanhai.kem.mail.protocol.sohu.address;

import java.util.HashMap;
import java.util.Map;

import cn.ilanhai.framework.uitl.Str;
import cn.ilanhai.kem.mail.protocol.MailInfo;
import cn.ilanhai.kem.mail.protocol.MailInfo.DataType;

/**
 * 删除地址请示
 * 
 * @author he
 *
 */
public class DeleteAddressRequest extends MailInfo {

	public DeleteAddressRequest() {
		this.dataType = DataType.DeleteAddress;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Map<String, String> toMap() {
		Map<String, String> map = null;
		String tmp = null;
		map = new HashMap<String, String>();
		tmp = this.getAddress();
		if (!Str.isNullOrEmpty(tmp))
			map.put("address",  this.encode(tmp));

		return map;
	}

	/**
	 * 地址
	 */
	private String address;

}
