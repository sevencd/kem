package cn.ilanhai.kem.domain.notify;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class UserNotifyCount extends AbstractEntity {
	private static final long serialVersionUID = 1L;
	private String target;
	private  Integer count;
	private Integer readCount;
	public UserNotifyCount(){
		
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public Integer getReadCount() {
		return readCount;
	}
	public void setReadCount(Integer readCount) {
		this.readCount = readCount;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	
}
