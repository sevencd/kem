package cn.ilanhai.kem.modules.work;

import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * 字符串型状态
 * 如果状态满足指定值，则状态完成
 * @author Nature
 *
 */
public class StringWorkState extends AbstractEntity implements WorkState{

	private static final long serialVersionUID = 2578465343072866988L;
	
	protected String stateName;
	protected String state;
	protected String desState;
	
	public StringWorkState(String stateName,String state,String desState){
		this.stateName=stateName;
		this.state=state;
		this.desState=desState;
	}

	public String getStateName() {
		return this.stateName;
	}

	public boolean isSatisfied() {
		return state.equals(desState);
	}

}
