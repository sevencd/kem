package cn.ilanhai.kem.modules.work;

import java.util.HashMap;
import java.util.Map;

import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * 
 * @author Nature
 *
 */
public abstract class Work extends AbstractEntity {

	private static final long serialVersionUID = -4532127361872210985L;

	protected Map<String, WorkState> workStates = new HashMap<String, WorkState>();
	protected int workID;

	public abstract boolean isFinished();

	public abstract String getWorkName();

	public int getWorkID() {
		return this.workID;
	}

	public void setState(WorkState state) {
		if (this.workStates.containsKey(state.getStateName())) {
			this.workStates.remove(state.getStateName());
		}
		this.workStates.put(state.getStateName(), state);
	}
}
