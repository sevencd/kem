package cn.ilanhai.kem.modules.work;

import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * 是否型状态 如果状态为true，则满足状态
 * 
 * @author Nature
 *
 */
public class BoolWorkState extends AbstractEntity implements WorkState {

	private static final long serialVersionUID = 8790918253793235873L;

	protected String stateName;

	protected Boolean state;

	public BoolWorkState(String stateName, Boolean state) {
		this.stateName = stateName;
		this.state = state;
	}

	public String getStateName() {

		return this.stateName;
	}

	public boolean isSatisfied() {

		return state;
	}

}
