package cn.ilanhai.kem.domain;

import java.util.Map;

import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * 以map为result的，可以使用该对象
 * @author Nature
 *
 */
public class MapEntity extends AbstractEntity{

	private static final long serialVersionUID = -7347594897263822738L;

	private Map<String,Object> data;

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}
	
}
