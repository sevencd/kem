package cn.ilanhai.kem.domain.crawler;

import java.util.List;

import cn.ilanhai.framework.app.domain.AbstractEntity;
import cn.ilanhai.framework.app.domain.Entity;
/**
 * 
 * @Description 爬虫规则响应dto
 * @TypeName CrawlerRuleResponseDto
 * @time 2017年3月2日 上午10:05:21
 * @author csz
 */
public class CrawlerRuleResponseDto extends AbstractEntity {
	private static final long serialVersionUID = 1L;
	/**
	 * 采集规则明细
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
