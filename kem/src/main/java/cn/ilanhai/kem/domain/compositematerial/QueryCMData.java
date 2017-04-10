package cn.ilanhai.kem.domain.compositematerial;

import cn.ilanhai.kem.domain.Page;

/**
 * @author he
 *
 */
public class QueryCMData extends Page {
	private static final long serialVersionUID = 1L;
	private String userId;
	private String orderMode;
	private String type;
	private Integer clientType;
	private String orderBy;
	public QueryCMData() {

	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getOrderMode() {
		return orderMode;
	}
	public void setOrderMode(String orderMode) {
		this.orderMode = orderMode;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getClientType() {
		return clientType;
	}
	public void setClientType(Integer clientType) {
		this.clientType = clientType;
	}
	/**
	 * @return the orderBy
	 */
	public String getOrderBy() {
		return orderBy;
	}
	/**
	 * @param orderBy the orderBy to set
	 */
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	
	
	

}
