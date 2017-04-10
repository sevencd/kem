package cn.ilanhai.kem.dao.smsright;

import java.util.Iterator;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.kem.dao.MybatisBaseDao;
import cn.ilanhai.kem.domain.CountDto;
import cn.ilanhai.kem.domain.smsright.dto.DeleteSmsDto;
import cn.ilanhai.kem.domain.smsright.dto.QuerySmsDto;

@Component("searchSmsDao")
public class SearchSmsDao extends MybatisBaseDao {
	private SqlSession sqlSession = this.getSqlSession();

	public SearchSmsDao() throws DaoAppException {
		super();
	}

	public Iterator<Entity> query(Entity entity) throws DaoAppException {
		if (entity instanceof QuerySmsDto) {
			return querySmss((QuerySmsDto) entity).iterator();
		}
		return super.query(entity);
	}

	@Override
	public Entity query(Entity entity, boolean flag) throws DaoAppException {
		if (entity instanceof QuerySmsDto) {
			return querySmssCount((QuerySmsDto) entity);
		}
		return super.query(entity, flag);
	}

	@Override
	public int delete(Entity entity) throws DaoAppException {
		if (entity instanceof DeleteSmsDto) {
			return deleteSmss((DeleteSmsDto) entity);
		}
		return super.delete(entity);
	}

	public int deleteSmss(DeleteSmsDto dto) {
		if (dto == null) {
			return -1;
		}
		return sqlSession.update("searchSms.deleteSmss", dto);
	}

	private List<Entity> querySmss(QuerySmsDto dto) {
		if (dto == null) {
			return null;
		}
		return sqlSession.selectList("searchSms.querySmss", dto);
	}

	private Entity querySmssCount(QuerySmsDto dto) {
		if (dto == null) {
			return new CountDto();
		}
		return sqlSession.selectOne("searchSms.querySmssCount", dto);
	}
}
