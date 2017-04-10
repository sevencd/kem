package cn.ilanhai.kem.domain.extension;

import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * 推广id请求dto
 * 
 * @author hy
 *
 */
public class ExtensionIdRequestDto extends AbstractEntity {
	private static final long serialVersionUID = 1345591773431720654L;
	/**
	 * 专题编号
	 */
	private String extensionId;

	public String getExtensionId() {
		return extensionId;
	}

	public void setExtensionId(String extensionId) {
		this.extensionId = extensionId;
	}
}
