package cn.ilanhai.kem.domain.template;

import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * 查询收藏
 * 
 * @author hy
 *
 */
public class SearchCollectionTemplateDto extends AbstractEntity {
	private static final long serialVersionUID = 3830987303729815700L;
	private Integer startCount;
	private Integer pageSize;
	/**
	 * 可空，为空则查询所有模板类型，模板类型：1：PC端；2：移动端
	 */
	private Integer templateType;
	private String templateName;
	
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

	public Integer getTemplateType() {
		return templateType;
	}

	public void setTemplateType(Integer templateType) {
		this.templateType = templateType;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
}
