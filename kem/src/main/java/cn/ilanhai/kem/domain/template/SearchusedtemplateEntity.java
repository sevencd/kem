package cn.ilanhai.kem.domain.template;

import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * 查询使用过的模版记录
 * 
 * @author hy
 *
 */
public class SearchusedtemplateEntity extends AbstractEntity {
	private static final long serialVersionUID = -4590737413877559069L;
	private Integer startCount;
	private Integer pageSize;
	private Integer templateType;

	public Integer getTemplateType() {
		return templateType;
	}

	public void setTemplateType(Integer templateType) {
		this.templateType = templateType;
	}

	/**
	 * 使用者ID
	 */
	private String userId;

	public Integer getStartCount() {
		return startCount;
	}

	public void setStartCount(Integer startCount) {
		this.startCount = startCount;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
