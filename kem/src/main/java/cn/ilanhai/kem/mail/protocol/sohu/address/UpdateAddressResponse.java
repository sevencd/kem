package cn.ilanhai.kem.mail.protocol.sohu.address;

import cn.ilanhai.kem.mail.protocol.sohu.SohuResult;

/**
 * 修改地址听响应
 * 
 * @author he
 *
 */
public class UpdateAddressResponse extends SohuResult {
	public UpdateAddressResponse() {

	}

	public UpdateAddressInfo getInfo() {
		return info;
	}

	public void setInfo(UpdateAddressInfo info) {
		this.info = info;
	}

	private UpdateAddressInfo info;
}
