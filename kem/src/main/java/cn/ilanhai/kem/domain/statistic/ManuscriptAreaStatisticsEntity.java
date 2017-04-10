package cn.ilanhai.kem.domain.statistic;

import java.util.Date;

import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * 稿件区域统计实体
 * 
 * @author he
 *
 */
public class ManuscriptAreaStatisticsEntity extends AbstractEntity {
	public ManuscriptAreaStatisticsEntity() {

	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getVisitUrl() {
		return visitUrl;
	}

	public void setVisitUrl(String visitUrl) {
		this.visitUrl = visitUrl;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	private static final long serialVersionUID = 1L;
	/**
	 * 编号
	 */
	private long id;
	/**
	 * 区域名称
	 */
	private String name;
	/**
	 * 数量
	 */
	private int quantity;
	/**
	 * 添加时间
	 */
	private Date addTime;
	/**
	 * 修改时间
	 */
	private Date updateTime;
	/**
	 * 浏览稿件URL
	 */
	private String visitUrl;
}
