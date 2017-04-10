package cn.ilanhai.kem.domain.template;

import java.util.List;

import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * 模板实体
 * 
 * @author he
 *
 */
public class SavePublishTemplateEntity extends AbstractEntity {
	private static final long serialVersionUID = -1684332603048753890L;
	private TemplateEntity obj;
	private List<String> keywords;
	private List<String> tagNames;
	public TemplateEntity getObj() {
		return obj;
	}

	public void setObj(TemplateEntity obj) {
		this.obj = obj;
	}

	public List<String> getKeywords() {
		return keywords;
	}

	public void setKeywords(List<String> keywords) {
		this.keywords = keywords;
	}

	public List<String> getTagNames() {
		return tagNames;
	}

	public void setTagNames(List<String> tagNames) {
		this.tagNames = tagNames;
	}
}
