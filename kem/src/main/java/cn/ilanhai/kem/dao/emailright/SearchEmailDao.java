package cn.ilanhai.kem.dao.emailright;

import java.util.Iterator;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.kem.dao.MybatisBaseDao;
import cn.ilanhai.kem.domain.CountDto;
import cn.ilanhai.kem.domain.email.dto.DeleteEmailDto;
import cn.ilanhai.kem.domain.email.dto.QueryEmailDto;

@Component("searchEmailDao")
public class SearchEmailDao extends MybatisBaseDao {
	private SqlSession sqlSession = this.getSqlSession();

	public SearchEmailDao() throws DaoAppException {
		super();
	}

	public Iterator<Entity> query(Entity entity) throws DaoAppException {
		if (entity instanceof QueryEmailDto) {
			return queryEmails((QueryEmailDto) entity).iterator();
		}
		return super.query(entity);
	}

	@Override
	public Entity query(Entity entity, boolean flag) throws DaoAppException {
		if (entity instanceof QueryEmailDto) {
			return queryEmailsCount((QueryEmailDto) entity);
		}
		return super.query(entity, flag);
	}

	@Override
	public int delete(Entity entity) throws DaoAppException {
		if (entity instanceof DeleteEmailDto) {
			return deleteEmails((DeleteEmailDto) entity);
		}
		return super.delete(entity);
	}

	public int deleteEmails(DeleteEmailDto dto) {
		if (dto == null) {
			return -1;
		}
		return sqlSession.update("searchEmail.deleteEmails", dto);
	}

	private List<Entity> queryEmails(QueryEmailDto dto) {
		if (dto == null) {
			return null;
		}
		return sqlSession.selectList("searchEmail.queryEmails", dto);
	}

	private Entity queryEmailsCount(QueryEmailDto dto) {
		if (dto == null) {
			return new CountDto();
		}
		return sqlSession.selectOne("searchEmail.queryEmailsCount", dto);
	}
}
