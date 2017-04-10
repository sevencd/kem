package cn.ilanhai.kem.domain.statistic.dto;


import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * 区域统计实体
 * 
 * @author he
 *
 */
public class ManuscriptAreaDto extends AbstractEntity {
	private static final long serialVersionUID = -6152367696751045630L;

	public ManuscriptAreaDto() {

	}

	/**
	 * 地域名称
	 */
	private String areaName;

	/**
	 * 地域访问数
	 */
	private Integer areaQuantity;

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public Integer getAreaQuantity() {
		return areaQuantity;
	}

	public void setAreaQuantity(Integer areaQuantity) {
		this.areaQuantity = areaQuantity;
	}
}
