package cn.ilanhai.kem.domain.statistic;

import java.util.Date;

import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * 区域统计实体
 * 
 * @author he
 *
 */
public class ManuscriptAreaEntity extends AbstractEntity {
	private static final long serialVersionUID = -6152367696751045630L;

	public ManuscriptAreaEntity() {

	}

	/**
	 * 地域编号
	 */
	private Integer areaId;
	/**
	 * 地域名称
	 */
	private String areaName;
	
	/**
	 * 城市
	 */
	private String areaCity;
	/**
	 * 地域访问数
	 */
	private Integer areaQuantity;
	/**
	 * 添加时间
	 */
	private Date areaAddTime;
	/**
	 * 修改时间
	 */
	private Date areaUpdateTime;
	/**
	 * 浏览稿件URL
	 */
	private String visitUrl;
	/**
	 * 稿件id
	 */
	private String manuscrptId;

	public String getManuscrptId() {
		return manuscrptId;
	}

	public void setManuscrptId(String manuscrptId) {
		this.manuscrptId = manuscrptId;
	}

	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

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

	public Date getAreaAddTime() {
		return areaAddTime;
	}

	public void setAreaAddTime(Date areaAddTime) {
		this.areaAddTime = areaAddTime;
	}

	public Date getAreaUpdateTime() {
		return areaUpdateTime;
	}

	public void setAreaUpdateTime(Date areaUpdateTime) {
		this.areaUpdateTime = areaUpdateTime;
	}

	public String getVisitUrl() {
		return visitUrl;
	}

	public void setVisitUrl(String visitUrl) {
		this.visitUrl = visitUrl;
	}

	public String getAreaCity() {
		return areaCity;
	}

	public void setAreaCity(String areaCity) {
		this.areaCity = areaCity;
	}
}
