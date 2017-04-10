package cn.ilanhai.kem.domain.statistic.dto;

import java.util.Date;

import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * 数据总览数据条目
 * 
 * @author he
 *
 */
public class ManuscriptStatisticPropagateDtoItem extends AbstractEntity {
	public ManuscriptStatisticPropagateDtoItem() {

	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

	private static final long serialVersionUID = 1L;
	/**
	 * 数量
	 */
	private int quantity;
	/**
	 * 日期
	 */
	private Date dateTime;

}
