package cn.ilanhai.kem.domain.statistic.dto;

import java.util.List;

import cn.ilanhai.framework.app.domain.AbstractEntity;
import cn.ilanhai.framework.app.domain.Entity;
/**
 * 
 * 应用用户明细数据,客户端响应数据
 * @author csz
 *
 */
public class APPUserResponseDto extends AbstractEntity {
	private static final long serialVersionUID = 1L;
	/**
	 * 用户信息明细
	 */
	private List<Entity> detail;
	private Integer startCount;//开始条数
	private Integer pageSize;//页面大小
	private Integer totalCount;//总记录数
	public Integer getStartCount() {
		return startCount;
	}
	public void setStartCount(Integer startCount) {
		this.startCount = startCount;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
	public List<Entity> getDetail() {
		return detail;
	}
	public void setDetail(List<Entity> detail) {
		this.detail = detail;
	}

}
