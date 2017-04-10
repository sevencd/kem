package cn.ilanhai.kem.domain.crawler;

import java.util.List;

import cn.ilanhai.framework.app.domain.AbstractEntity;
import cn.ilanhai.kem.domain.customer.CustomerMainEntity;
/**
 * 
 * @Description 添加到客户管理
 * @TypeName CrawlerCustomerDto
 * @time 2017年3月8日 上午10:05:21
 * @author csz
 */
public class CrawlerCustomerDto extends AbstractEntity {

	private static final long serialVersionUID = 1L;
	/*
	 * 客户信息main
	 */
	private List<CustomerMainEntity> mainList;
	/*
	 * 客户信息info
	 */
	private List<CrawlerCustomerInfoEntity> infoList;
	public List<CustomerMainEntity> getMainList() {
		return mainList;
	}
	public void setMainList(List<CustomerMainEntity> mainList) {
		this.mainList = mainList;
	}
	public List<CrawlerCustomerInfoEntity> getInfoList() {
		return infoList;
	}
	public void setInfoList(List<CrawlerCustomerInfoEntity> infoList) {
		this.infoList = infoList;
	}
}
