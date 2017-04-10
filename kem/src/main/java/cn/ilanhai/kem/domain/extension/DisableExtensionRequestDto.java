package cn.ilanhai.kem.domain.extension;

import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * 禁用推广请求dto
 * 
 * @author hy
 *
 */
public class DisableExtensionRequestDto extends AbstractEntity {
	private static final long serialVersionUID = 1345591773431720654L;
	/**
	 * 专题编号
	 */
	private String extensionId;
	/**
	 * 禁用原因
	 */
	private String disableReason;

	public String getExtensionId() {
		return extensionId;
	}

	public void setExtensionId(String extensionId) {
		this.extensionId = extensionId;
	}

	public String getDisableReason() {
		return disableReason;
	}

	public void setDisableReason(String disableReason) {
		this.disableReason = disableReason;
	}
}
