package cn.ilanhai.kem.domain.auth;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class QueryUserBound extends AbstractEntity {
	private String type;
	private String tag;
	public QueryUserBound()
	{
		
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
}
