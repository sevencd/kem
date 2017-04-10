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
public class CrawlerCustomerRequestDto extends AbstractEntity {

	private static final long serialVersionUID = 1L;
	/*
	 * 记录id
	 */
	private List<String> ids;
	/**
	 * 用户id
	 */
	private String userId;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public List<String> getIds() {
		return ids;
	}
	public void setIds(List<String> ids) {
		this.ids = ids;
	}
}
