package cn.ilanhai.kem.bl.crawler;

import cn.ilanhai.framework.app.RequestContext;
import cn.ilanhai.framework.common.exception.BlAppException;
import cn.ilanhai.framework.common.exception.CacheContainerException;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.framework.common.exception.SessionContainerException;
import cn.ilanhai.kem.domain.crawler.CrawlerSearchResponseDto;
/**
 * 查询客户，保存客户
 * @author csz
 * @time 2017-03-06 10:00
 */
public interface CrawlerSearch {

	/**
	 * 查询用户（从八爪鱼取数据）
	 * 
	 * @param context	请求上下文
	 * @throws BlAppException
	 * @throws DaoAppException
	 * @return CrawlerSearchResponseDto
	 */
	CrawlerSearchResponseDto listCustomer(RequestContext context) throws BlAppException, DaoAppException, SessionContainerException;
	/**
	 * 将八爪鱼数据同步到集客云销DB
	 * 
	 * @param  context	请求上下文
	 * @throws BlAppException
	 * @throws SessionContainerException
	 * @throws CacheContainerException 
	 */
	void saveCustomer(RequestContext context) throws BlAppException, SessionContainerException,CacheContainerException;

}
