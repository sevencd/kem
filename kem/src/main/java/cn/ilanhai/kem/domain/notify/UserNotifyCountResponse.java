package cn.ilanhai.kem.domain.notify;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class UserNotifyCountResponse extends AbstractEntity {

	private static final long serialVersionUID = 1L;
	private  Integer count;
	private Integer readCount;
	public UserNotifyCountResponse(){
		
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
	
}
