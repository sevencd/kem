package cn.ilanhai.kem.event.args;

import cn.ilanhai.kem.event.DomainEventArgs;

/**
 * 后台用户登录事件
 * @author Nature
 *
 */
public class BackUserLoginEvent extends DomainEventArgs{

	private static final long serialVersionUID = 6529631345507875683L;

	private String username;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
