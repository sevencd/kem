package cn.ilanhai.kem.domain.statistic.dto;

import java.util.ArrayList;
import java.util.List;

import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * 数据传播数据,客户端响应
 * 
 * @author he
 *
 */
public class ManuscriptStatisticPropagateDtoResponse extends AbstractEntity {
	public ManuscriptStatisticPropagateDtoResponse() {
		this.data = new ArrayList<ManuscriptStatisticPropagateDtoItem>();
	}

	public List<ManuscriptStatisticPropagateDtoItem> getData() {
		return data;
	}

	public void setData(List<ManuscriptStatisticPropagateDtoItem> data) {
		this.data = data;
	}

	private static final long serialVersionUID = 1L;
	/**
	 * 数据
	 */
	private List<ManuscriptStatisticPropagateDtoItem> data = null;
}
