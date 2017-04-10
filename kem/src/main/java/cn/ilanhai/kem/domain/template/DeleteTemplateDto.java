package cn.ilanhai.kem.domain.template;

import java.util.List;

import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * 删除模版
 * 
 * @author hy
 *
 */
public class DeleteTemplateDto extends AbstractEntity {
	private static final long serialVersionUID = -4489310394416790506L;
	private List<String> templateIds;

	public DeleteTemplateDto() {

	}

	public List<String> getTemplateIds() {
		return templateIds;
	}

	public void setTemplateIds(List<String> templateIds) {
		this.templateIds = templateIds;
	}
}
