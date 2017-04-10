package cn.ilanhai.kem.domain.crawler;

import cn.ilanhai.framework.app.domain.AbstractEntity;
/**
 * 
 * @Description 爬虫规则查询参数dto
 * @TypeName CrawlerRuleRequestDto
 * @time 2017年3月2日 上午10:05:21
 * @author csz
 */
public class CrawlerRuleRequestDto extends AbstractEntity {

	private static final long serialVersionUID = 1L;
	/*
	 * 采集规则id
	 */
	private Long id;
	/*
	 * 采集任务id
	 */
	private String taskId;
	/*
	 * 一级行业id
	 */
	private Long typeNumOne;
	/*
	 * 二级行业ID
	 */
	private Long typeIdTwo;
	private Integer startCount;//开始条数
	private Integer pageSize;//页面大小
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public Long getTypeIdOne() {
		return typeNumOne;
	}
	public void setTypeIdOne(Long typeIdOne) {
		this.typeNumOne = typeIdOne;
	}
	public Long getTypeIdTwo() {
		return typeIdTwo;
	}
	public void setTypeIdTwo(Long typeIdTwo) {
		this.typeIdTwo = typeIdTwo;
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
}
