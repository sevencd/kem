
package cn.ilanhai.kem.dao.crawler;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.kem.dao.MybatisBaseDao;
import cn.ilanhai.kem.domain.crawler.CrawlerCustomerDto;

@Component("CrawlerSearchDao")
public class CrawlerSearchDao extends MybatisBaseDao {
	private SqlSession sqlSession = this.getSqlSession();

	public CrawlerSearchDao() throws DaoAppException {
		super();
	}

	@Override
	public int save(Entity entity) throws DaoAppException {
		if (entity instanceof CrawlerCustomerDto) {
			return saveCustomerList((CrawlerCustomerDto) entity);
		}
		return 0;
	}

	/**
	 * 批量保存客户信息,客户数据来自于爬虫
	 * 
	 * @param entity
	 * @return
	 * @throws DaoAppException
	 */
	private int saveCustomerList(CrawlerCustomerDto crawlerCustomerDto) throws DaoAppException {
		int main = sqlSession.insert(baseNamespace + "CrawlerRuleDao.insertCustomerMainList", crawlerCustomerDto);
		int info = sqlSession.insert(baseNamespace + "CrawlerRuleDao.insertCustomerInfoList", crawlerCustomerDto);
		return main;
	}
	/**
	 * 批量更新客户信息,客户数据来自于爬虫
	 * 
	 * @param entity
	 * @return
	 * @throws DaoAppException
	 */
	private int updateCustomerList(CrawlerCustomerDto crawlerCustomerDto) throws DaoAppException {
		int main = sqlSession.update(baseNamespace + "CrawlerRuleDao.updateCustomerMainList", crawlerCustomerDto);
		int info = sqlSession.update(baseNamespace + "CrawlerRuleDao.updateCustomerInfoList", crawlerCustomerDto);
		return info;
	}
}
