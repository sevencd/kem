package cn.ilanhai.kem.domain.material;

import java.util.Date;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class MaterialMainEntity extends AbstractEntity {
	private static final long serialVersionUID = -8488920934758183071L;

	private String userId;
	private String materialId;
	private Date createtime;
	private Date updatetime;
	private Integer enable;

	public String getMaterialId() {
		return materialId;
	}

	public void setMaterialId(String materialId) {
		this.materialId = materialId;
	}

	public Integer getEnable() {
		return enable;
	}

	public void setEnable(Integer enable) {
		this.enable = enable;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
}
