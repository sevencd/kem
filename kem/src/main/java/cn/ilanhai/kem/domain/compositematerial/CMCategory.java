package cn.ilanhai.kem.domain.compositematerial;

public enum CMCategory {

	Sys(0), User(1);
	private int val = -1;

	private CMCategory(int v) {
		this.val = v;
	}

	public int getVal() {
		return this.val;
	}

	public static CMCategory valueOf(int v) {
		switch (v) {
		case 0:
			return CMCategory.Sys;
		case 1:
			return CMCategory.User;
		default:
			return null;
		}
	}
}
