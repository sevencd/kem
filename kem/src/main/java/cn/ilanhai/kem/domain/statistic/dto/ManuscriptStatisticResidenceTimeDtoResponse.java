package cn.ilanhai.kem.domain.statistic.dto;

import java.util.ArrayList;
import java.util.List;

import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * @author 停留时间响应
 *
 */
public class ManuscriptStatisticResidenceTimeDtoResponse extends AbstractEntity {

	public ManuscriptStatisticResidenceTimeDtoResponse() {
		this.data = new ArrayList<ManuscriptStatisticResidenceTimeDtoItem>();
	}

	public int getTotalResidenceTime() {
		return totalResidenceTime;
	}

	public void setTotalResidenceTime(int totalResidenceTime) {
		this.totalResidenceTime = totalResidenceTime;
	}

	public int getTotalUvQuantity() {
		return totalUvQuantity;
	}

	public void setTotalUvQuantity(int totalUvQuantity) {
		this.totalUvQuantity = totalUvQuantity;
	}

	public double getAverageResidenceTime() {
		return averageResidenceTime;
	}

	public void setAverageResidenceTime(double averageResidenceTime) {
		this.averageResidenceTime = averageResidenceTime;
	}

	public List<ManuscriptStatisticResidenceTimeDtoItem> getData() {
		return data;
	}

	public void setData(List<ManuscriptStatisticResidenceTimeDtoItem> data) {
		this.data = data;
	}

	private static final long serialVersionUID = 1L;
	/**
	 * 合计停留时间
	 */
	private int totalResidenceTime;//
	/**
	 * 合计Uv 数量
	 */
	private int totalUvQuantity;//
	/**
	 * 平均停留时间(两位小数) 合计Uv 数量/合计停留时间
	 */
	private double averageResidenceTime;//
	/**
	 * 数据
	 */
	private List<ManuscriptStatisticResidenceTimeDtoItem> data;
}
