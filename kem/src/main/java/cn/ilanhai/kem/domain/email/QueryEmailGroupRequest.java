package cn.ilanhai.kem.domain.email;

import cn.ilanhai.framework.app.domain.AbstractEntity;
/**
 * 根据邮件id查询群组联系人
 * @author hy
 *
 */
public class QueryEmailGroupRequest extends AbstractEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 774705962918037888L;
	private String emailId;
	private Integer startCount;
	private Integer count;
	private Integer pageSize;
	
	public Integer getStartCount() {
		return startCount;
	}
	public void setStartCount(Integer startCount) {
		this.startCount = startCount;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

}
