package cn.ilanhai.kem.domain.notify;

public enum NotifyType {
	Sys(0x0);
	private int val;

	private NotifyType(int v) {
		this.val = v;

	}

	public int getval() {
		return this.val;
	}

	public static NotifyType valueOf(int v) {
		switch (v) {
		case 0:
			return NotifyType.Sys;
		default:
			return null;
		}

	}

}
