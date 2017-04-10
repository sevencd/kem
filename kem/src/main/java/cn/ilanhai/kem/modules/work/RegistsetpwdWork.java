package cn.ilanhai.kem.modules.work;

import java.util.Map.Entry;

public class RegistsetpwdWork extends Work {
	private static final long serialVersionUID = -3773366773643119524L;
	public static final String REGISTSETPWDWORK = "registsetpwdWork";

	@Override
	public boolean isFinished() {
		boolean result = true;
		if (workStates != null && workStates.size() > 0) {
			for (Entry<String, WorkState> entry : this.workStates.entrySet()) {
				result = entry.getValue().isSatisfied();
			}
		}
		return result;
	}

	@Override
	public String getWorkName() {
		return REGISTSETPWDWORK;
	}

}
