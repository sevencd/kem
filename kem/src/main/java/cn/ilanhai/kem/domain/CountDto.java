package cn.ilanhai.kem.domain;

import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * 总条数实体
 * 
 * @author hy
 *
 */
public class CountDto extends AbstractEntity {
	private static final long serialVersionUID = -5825807534293204052L;
	private int count;

	public CountDto() {

	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

}
