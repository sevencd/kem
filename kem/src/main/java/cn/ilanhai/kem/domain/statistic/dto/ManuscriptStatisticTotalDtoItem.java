package cn.ilanhai.kem.domain.statistic.dto;

import java.util.Date;

import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * 数据总览数据条目
 * 
 * @author he
 *
 */
public class ManuscriptStatisticTotalDtoItem extends AbstractEntity {

	public ManuscriptStatisticTotalDtoItem() {

	}

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

	public Date getEndDateTime() {
		return endDateTime;
	}

	public void setEndDateTime(Date endDateTime) {
		this.endDateTime = endDateTime;
	}

	private static final long serialVersionUID = 1L;
	/**
	 * 数量(两位小数)
	 */
	private double quantity;
	/**
	 * 日期
	 */
	private Date dateTime;
	/**
	 * 截止时间
	 */
	private Date endDateTime;
}
