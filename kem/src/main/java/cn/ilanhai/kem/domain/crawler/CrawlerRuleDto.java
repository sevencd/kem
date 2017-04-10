package cn.ilanhai.kem.domain.crawler;

import java.sql.Timestamp;

import cn.ilanhai.framework.app.domain.AbstractEntity;
/**
 * 
 * @Description 爬虫规则
 * @TypeName CrawlerRuleDto
 * @time 2017年3月2日 上午10:05:21
 * @author csz
 */
public class CrawlerRuleDto extends AbstractEntity {

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
	private Long typeIdOne;
	/*
	 * 二级行业ID
	 */
	private Long typeIdTwo;
	/*
	 *网址 
	 */
	private String url;
	/*
	 * 创建时间
	 */
	private Timestamp createTime;
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
		return typeIdOne;
	}
	public void setTypeIdOne(Long typeIdOne) {
		this.typeIdOne = typeIdOne;
	}
	public Long getTypeIdTwo() {
		return typeIdTwo;
	}
	public void setTypeIdTwo(Long typeIdTwo) {
		this.typeIdTwo = typeIdTwo;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
}
