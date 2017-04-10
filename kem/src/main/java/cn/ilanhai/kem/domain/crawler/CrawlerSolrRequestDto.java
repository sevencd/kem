package cn.ilanhai.kem.domain.crawler;

import java.util.List;

import cn.ilanhai.framework.app.domain.AbstractEntity;
/**
 * 
 * @Description 添加到客户管理
 * @TypeName CrawlerCustomerRequestDto
 * @time 2017年3月2日 上午10:05:21
 * @author csz
 */
public class CrawlerSolrRequestDto extends AbstractEntity {

	private static final long serialVersionUID = 1L;
	/*
	 * 记录id
	 */
	private List<String> daqIdList;
	/**
	 * 用户id
	 */
	private String consumerId;
	public List<String> getDaqIdList() {
		return daqIdList;
	}
	public void setDaqIdList(List<String> daqIdList) {
		this.daqIdList = daqIdList;
	}
	public String getConsumerId() {
		return consumerId;
	}
	public void setConsumerId(String consumerId) {
		this.consumerId = consumerId;
	}
}
