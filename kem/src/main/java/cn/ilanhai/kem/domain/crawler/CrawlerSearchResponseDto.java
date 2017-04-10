package cn.ilanhai.kem.domain.crawler;

import java.util.List;

import cn.ilanhai.framework.app.domain.AbstractEntity;
/**
 * 
 * @Description 爬虫搜索返回的带分页的dto
 * @TypeName CrawlerSearchResponseDto
 * @time 2017年3月3日 上午10:05:21
 * @author csz
 */
public class CrawlerSearchResponseDto extends AbstractEntity {

	private static final long serialVersionUID = 1L;
	 /*
     * 采集来源  
     */
    private String dataFrom;
	/**
	 * 用户信息明细
	 */
	private List<CrawlerSearchDto> detail;
	private Integer startCount;//开始条数
	private Integer pageSize;//页面大小
	private Integer totalCount;//总记录数
	public String getDataFrom() {
		return dataFrom;
	}
	public void setDataFrom(String dataFrom) {
		this.dataFrom = dataFrom;
	}
	public List<CrawlerSearchDto> getDetail() {
		return detail;
	}
	public void setDetail(List<CrawlerSearchDto> detail) {
		this.detail = detail;
	}
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
	
}
