package cn.ilanhai.kem.mail.protocol.sohu.address;

import cn.ilanhai.kem.mail.protocol.sohu.SohuResult;

/**
 * 添加地址响应数据
 * 
 * @author he
 *
 */
public class AddAddressResponse extends SohuResult {
	public AddAddressResponse() {

	}

	public AddAddressInfo getInfo() {
		return info;
	}

	public void setInfo(AddAddressInfo info) {
		this.info = info;
	}

	/**
	 * 信息
	 */
	private AddAddressInfo info;
}
