package cn.ilanhai.kem.domain.compositematerial;

public enum CMState {

	New(0), Passed(1), NotPass(2);
	private int val = -1;

	private CMState(int v) {
		this.val = v;
	}

	public int getVal() {
		return this.val;
	}

	public static CMState valueOf(int v) {
		switch (v) {
		case 0:
			return CMState.New;
		case 1:
			return CMState.Passed;
		case 2:
			return CMState.NotPass;
		default:
			return null;
		}
	}
}
