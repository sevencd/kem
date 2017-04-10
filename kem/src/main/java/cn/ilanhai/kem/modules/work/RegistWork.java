package cn.ilanhai.kem.modules.work;

import java.util.Map.Entry;

public class RegistWork extends Work {
	private static final long serialVersionUID = 4373469785562344988L;
	public static final String REGISTWORK = "registWork";

	@Override
	public boolean isFinished() {
		boolean result = true;
		if (workStates != null && workStates.size() > 0) {
			for (Entry<String, WorkState> entry : this.workStates.entrySet()) {
				if (!entry.getValue().isSatisfied()) {
					return false;
				}
			}
		}
		return result;
	}

	@Override
	public String getWorkName() {
		return REGISTWORK;
	}

}
