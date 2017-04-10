
package cn.ilanhai.kem.dao.crawler;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.kem.common.CodeTable;
import cn.ilanhai.kem.dao.MybatisBaseDao;
import cn.ilanhai.kem.domain.CountDto;
import cn.ilanhai.kem.domain.crawler.CrawlerRuleDeleteDto;
import cn.ilanhai.kem.domain.crawler.CrawlerRuleDto;
import cn.ilanhai.kem.domain.crawler.CrawlerRuleRequestDto;

@Component("CrawlerRuleDao")
public class CrawlerRuleDao extends MybatisBaseDao {
	private SqlSession sqlSession = this.getSqlSession();

	public CrawlerRuleDao() throws DaoAppException {
		super();
	}

	@Override
	public int save(Entity entity) throws DaoAppException {
		if (entity instanceof CrawlerRuleDto) {
			CrawlerRuleDto dto = (CrawlerRuleDto) entity;
			if (null == dto.getId()) {
				return this.add(dto);
			}
			return this.update(dto);
		}
		return 0;
	}

	private int update(CrawlerRuleDto dto) {
		return sqlSession.update(this.baseNamespace + "CrawlerRuleDao.update", dto);
	}

	private int add(CrawlerRuleDto dto) {
		return sqlSession.insert(this.baseNamespace + "CrawlerRuleDao.insert", dto);
	}

	@Override
	public Entity query(Entity entity, boolean flag) throws DaoAppException {
		CountDto count = new CountDto();
		if (entity == null)
			throw new DaoAppException("");
		if (entity instanceof CrawlerRuleRequestDto) {
			count.setCount(queryDataForTotal((CrawlerRuleRequestDto) entity));
			return count;
		}
		return null;
	}

	private int queryDataForTotal(CrawlerRuleRequestDto entity) throws DaoAppException {
		try {
			return sqlSession.selectOne(this.baseNamespace + "CrawlerRuleDao.selectCrawlerRuleForTotalCount", entity);
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoAppException(CodeTable.BL_DATA_ERROR.getValue(), CodeTable.BL_DATA_ERROR.getDesc(), e);
		}
	}

	@Override
	public List<Entity> queryList(Entity entity) throws DaoAppException {
		try {
			return sqlSession.selectList(this.baseNamespace + "CrawlerRuleDao.selectCrawlerRuleForPage", entity);
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoAppException(CodeTable.BL_DATA_ERROR.getValue(), CodeTable.BL_DATA_ERROR.getDesc(), e);
		}
	}

	@Override
	public int delete(Entity entity) throws DaoAppException {
		CrawlerRuleDeleteDto dto = (CrawlerRuleDeleteDto) entity;
		return sqlSession.delete(this.baseNamespace + "CrawlerRuleDao.deleteByIdList", dto);
	}
}
