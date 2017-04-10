package cn.ilanhai.kem.domain.special;

import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * 根据模版创建专题请求Dto
 * 
 * @author hy
 *
 */
public class CreateSpecialRequestDto extends AbstractEntity {
	private static final long serialVersionUID = 785516121305853494L;
	/**
	 * 模版id
	 */
	private String templateId;
	/**
	 * 专题名
	 */
	private String specialName;

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public String getSpecialName() {
		return specialName;
	}

	public void setSpecialName(String specialName) {
		this.specialName = specialName;
	}
}
