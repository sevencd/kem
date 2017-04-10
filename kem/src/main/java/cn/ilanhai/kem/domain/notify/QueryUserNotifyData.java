package cn.ilanhai.kem.domain.notify;

import cn.ilanhai.kem.domain.Page;

/**
 * @author he
 *
 */
public class QueryUserNotifyData extends Page {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String target;
	private String orderMode;
	public QueryUserNotifyData() {

	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public String getOrderMode() {
		return orderMode;
	}
	public void setOrderMode(String orderMode) {
		this.orderMode = orderMode;
	}
	
	

}
