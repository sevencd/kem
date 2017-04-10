package cn.ilanhai.kem.domain.statistic.dto;

import java.util.ArrayList;
import java.util.List;

import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * 数据总览数据,客户端响应数据
 * 
 * @author he
 *
 */
public class ManuscriptStatisticTotalDtoResponse extends AbstractEntity {
	public ManuscriptStatisticTotalDtoResponse() {
		this.data = new ArrayList<ManuscriptStatisticTotalDtoItem>();
	}

	public double getTotalQuantity() {
		return totalQuantity;
	}

	public void setTotalQuantity(double totalQuantity) {
		this.totalQuantity = totalQuantity;
	}

	public List<ManuscriptStatisticTotalDtoItem> getData() {
		return data;
	}

	public void setData(List<ManuscriptStatisticTotalDtoItem> data) {
		this.data = data;
	}

	private static final long serialVersionUID = 1L;
	/**
	 * 总数量(两位小数)
	 */
	private double totalQuantity;
	/**
	 * 数据
	 */
	private List<ManuscriptStatisticTotalDtoItem> data;

}
