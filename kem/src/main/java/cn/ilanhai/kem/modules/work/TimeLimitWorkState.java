package cn.ilanhai.kem.modules.work;

import java.util.Date;

import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * 时间限制状态
 * 如果当前时间在限制时间之内，则满足状态
 * @author Nature
 *
 */
public class TimeLimitWorkState extends AbstractEntity implements WorkState{

	private static final long serialVersionUID = 5588402655382379677L;
	
	protected String stateName;
	protected Date deadline;

	public TimeLimitWorkState(String stateName,Date deadline){
	
		this.stateName=stateName;
		this.deadline=deadline;
	}
	
	public String getStateName() {
		return this.stateName;
	}

	public boolean isSatisfied() {
		return deadline.after(new Date());
	}

}
