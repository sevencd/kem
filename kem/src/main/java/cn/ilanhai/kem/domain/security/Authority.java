package cn.ilanhai.kem.domain.security;

/**
 * 定义权限
 * 
 * @author he
 *
 */
public enum Authority {

	/**
	 * 接口权限
	 */
	API(0x00000001);
	private int val;

	private Authority(int val) {
		this.val = val;
	}
	public int getVal(){
		return this.val;
	}

}
