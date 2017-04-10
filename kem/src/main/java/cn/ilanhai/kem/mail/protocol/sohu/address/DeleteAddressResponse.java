package cn.ilanhai.kem.mail.protocol.sohu.address;

import cn.ilanhai.kem.mail.protocol.sohu.SohuResult;

/**
 * 删除地址响应
 * 
 * @author he
 *
 */
public class DeleteAddressResponse extends SohuResult {
	public DeleteAddressResponse() {

	}

	public DeleteAddressInfo getInfo() {
		return info;
	}

	public void setInfo(DeleteAddressInfo info) {
		this.info = info;
	}

	/**
	 * 信息
	 */
	private DeleteAddressInfo info;
}
