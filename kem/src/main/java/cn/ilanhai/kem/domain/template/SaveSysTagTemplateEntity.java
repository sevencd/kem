package cn.ilanhai.kem.domain.template;

import java.util.List;

import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * 模板实体
 * 
 * @author he
 *
 */
public class SaveSysTagTemplateEntity extends AbstractEntity {
	private static final long serialVersionUID = -2014363484803708499L;
	private TemplateEntity obj;
	private List<String> tagNames;

	public TemplateEntity getObj() {
		return obj;
	}

	public void setObj(TemplateEntity obj) {
		this.obj = obj;
	}

	public List<String> getTagNames() {
		return tagNames;
	}

	public void setTagNames(List<String> tagNames) {
		this.tagNames = tagNames;
	}
}
