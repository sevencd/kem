package cn.ilanhai.kem.dao.template;

import java.util.Iterator;
import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;
import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.kem.dao.MybatisBaseDao;
import cn.ilanhai.kem.domain.IdEntity;
import cn.ilanhai.kem.domain.template.SearchTemplateDto;

@Component("templateMabatisDao")
public class TemplateMabatisDao extends MybatisBaseDao {

	public TemplateMabatisDao() throws DaoAppException {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public Iterator<Entity> query(Entity entity) throws DaoAppException {
		if (entity instanceof SearchTemplateDto) {
			SearchTemplateDto request = (SearchTemplateDto) entity;
			if (request.isRand()) {
				return searchByRand((SearchTemplateDto) entity).iterator();
			} else {
				return searchByBackuser((SearchTemplateDto) entity).iterator();
			}
		}
		return null;
	}

	public Entity query(Entity entity, boolean flag) throws DaoAppException {
		if (entity instanceof SearchTemplateDto) {
			SearchTemplateDto request = (SearchTemplateDto) entity;
			if (request.isRand()) {
				IdEntity<Integer> entitys = new IdEntity<Integer>();
				entitys.setId(countByRand((SearchTemplateDto) entity));
				return entitys;
			} else {
				IdEntity<Integer> entitys = new IdEntity<Integer>();
				entitys.setId(countByBackuser((SearchTemplateDto) entity));
				return entitys;
			}
		}
		return null;
	}

	private List<Entity> searchByBackuser(SearchTemplateDto entity) throws DaoAppException {
		SqlSession sqlSession = null;
		try {
			sqlSession = this.getSqlSession();
			return sqlSession.selectList("TemplateSql.searchByBackuser", entity);
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		} finally {

		}
	}

	private List<Entity> searchByRand(SearchTemplateDto entity) throws DaoAppException {
		SqlSession sqlSession = null;
		try {
			sqlSession = this.getSqlSession();
			return sqlSession.selectList("TemplateSql.searchByRand", entity);
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		} finally {

		}
	}

	private int countByBackuser(SearchTemplateDto entity) throws DaoAppException {
		SqlSession sqlSession = null;
		try {
			sqlSession = this.getSqlSession();
			return sqlSession.selectOne("TemplateSql.countByBackuser", entity);
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		} finally {

		}
	}

	private int countByRand(SearchTemplateDto entity) throws DaoAppException {
		SqlSession sqlSession = null;
		try {
			sqlSession = this.getSqlSession();
			return sqlSession.selectOne("TemplateSql.searchrandcount", entity);
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		} finally {

		}
	}

}
