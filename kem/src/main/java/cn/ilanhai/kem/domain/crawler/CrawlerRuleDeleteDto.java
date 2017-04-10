package cn.ilanhai.kem.domain.crawler;

import java.util.List;

import cn.ilanhai.framework.app.domain.AbstractEntity;
/**
 * 
 * @Description 爬虫规则批量删除
 * @TypeName CrawlerRuleDeleteDto
 * @time 2017年3月2日 上午10:05:21
 * @author csz
 */
public class CrawlerRuleDeleteDto extends AbstractEntity {

	private static final long serialVersionUID = 1L;
	/*
	 * 采集规则id
	 */
	private List<Long> ids;
	public List<Long> getIds() {
		return ids;
	}
	public void setIds(List<Long> ids) {
		this.ids = ids;
	}
}
