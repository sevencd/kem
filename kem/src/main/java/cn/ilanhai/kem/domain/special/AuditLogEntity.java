package cn.ilanhai.kem.domain.special;

import java.util.Date;

import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * 审核记录实体
 * 
 * @author hy
 *
 */
public class AuditLogEntity extends AbstractEntity {

	private static final long serialVersionUID = -3265462403176886224L;
	/**
	 * 审核id
	 */
	private Integer auditId;
	/**
	 * 模块连接id
	 */
	private Integer modelConfigId;
	/**
	 * 禁用原因
	 */
	private String disable_reason;
	/**
	 * 创建时间
	 */
	private Date createtime;

	public Integer getAuditId() {
		return auditId;
	}

	public void setAuditId(Integer auditId) {
		this.auditId = auditId;
	}

	public Integer getModelConfigId() {
		return modelConfigId;
	}

	public void setModelConfigId(Integer modelConfigId) {
		this.modelConfigId = modelConfigId;
	}

	public String getDisable_reason() {
		return disable_reason;
	}

	public void setDisable_reason(String disable_reason) {
		this.disable_reason = disable_reason;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
}
