package cn.ilanhai.kem.domain.template;

import java.util.Date;

import cn.ilanhai.framework.app.domain.AbstractEntity;
import cn.ilanhai.kem.domain.enums.UserType;

/**
 * 模版使用记录
 * 
 * @author hy
 *
 */
public class TemplateUseLogEntity extends AbstractEntity {
	private static final long serialVersionUID = 9179242416091412768L;
	/**
	 * 记录ID
	 */
	private Integer logId;
	/**
	 * 用户ID
	 */
	private String userId;
	/**
	 * 用户类型
	 */
	private UserType userType;
	/**
	 * 模版id
	 */
	private String templateId;
	/**
	 * 创建时间
	 */
	private Date createTime;

	public Integer getLogId() {
		return logId;
	}

	public void setLogId(Integer logId) {
		this.logId = logId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
