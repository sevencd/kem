package cn.ilanhai.kem.dao.userImg;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.framework.uitl.FastJson;
import cn.ilanhai.framework.uitl.Str;
import cn.ilanhai.kem.dao.BaseDao;
import cn.ilanhai.kem.dao.MybatisBaseDao;
import cn.ilanhai.kem.domain.CountDto;
import cn.ilanhai.kem.domain.IdEntity;
import cn.ilanhai.kem.domain.enums.UserImgOrderType;
import cn.ilanhai.kem.domain.enums.UserResourceType;
import cn.ilanhai.kem.domain.tag.DeleteTagRequestEntity;
import cn.ilanhai.kem.domain.tag.SearchTagRequestEntity;
import cn.ilanhai.kem.domain.userImg.DeleteUserImgDataEntity;
import cn.ilanhai.kem.domain.userImg.SearchUserImgRequestEntity;
import cn.ilanhai.kem.domain.userImg.UserImgDataDto;
import cn.ilanhai.kem.domain.userImg.UserImgEntity;
import cn.ilanhai.kem.domain.userImg.UserImgUploadEntity;

@Component("MybatisUserImgDao")
public class MybatisUserImgDao extends MybatisBaseDao {
	private SqlSession sqlSession = this.getSqlSession();

	private Logger logger = Logger.getLogger(MybatisUserImgDao.class);

	public MybatisUserImgDao() throws DaoAppException {
		super();
	}

	public Iterator<Entity> query(Entity entity) throws DaoAppException {
		if (entity == null)
			throw new DaoAppException("");
		if (entity instanceof SearchUserImgRequestEntity) {
			return queryUserImg((SearchUserImgRequestEntity) entity);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Entity query(Entity entity, boolean flag) throws DaoAppException {
		CountDto count = new CountDto();
		if (entity == null)
			throw new DaoAppException("");
		if (entity instanceof SearchUserImgRequestEntity) {
			count.setCount(queryUserImgForCount((SearchUserImgRequestEntity) entity));
			return count;
		}
		if (entity instanceof IdEntity) {
			return queryUserImgById((IdEntity<String>) entity);
		}
		return null;
	}

	private Entity queryUserImgById(IdEntity<String> entity) throws DaoAppException {
		if (entity == null)
			throw new DaoAppException("请指定查询参数");
		return sqlSession.selectOne(this.baseNamespace + "UserImg.queryById", entity);
	}

	private Iterator<Entity> queryUserImg(SearchUserImgRequestEntity entity) {
		List<Entity> list = sqlSession.selectList(this.baseNamespace + "UserImg.queryUserImgForPage", entity);
		return list.iterator();
	}

	private int queryUserImgForCount(SearchUserImgRequestEntity entity) throws DaoAppException {
		Integer count = sqlSession.selectOne(this.baseNamespace + "UserImg.queryUserImgForCount", entity);
		return count;
	}

	@Override
	public int save(Entity enity) throws DaoAppException {
		UserImgUploadEntity imgEntity = null;
		try {
			imgEntity = (UserImgUploadEntity) enity;
			if (!this.isExists(imgEntity))
				return this.add(imgEntity);
			return this.update(imgEntity);
		} catch (DaoAppException e) {
			throw e;
		}
	}

	private boolean isExists(final UserImgUploadEntity entity) throws DaoAppException {// 如果无ID，则
		if (entity == null) {
			return false;
		}
		IdEntity<String> idEntity = new IdEntity<String>();
		idEntity.setId(entity.getImgId());
		return sqlSession.selectOne(this.baseNamespace + "UserImg.queryById", idEntity) != null ? true : false;
	}

	private int add(final UserImgUploadEntity entity) throws DaoAppException {
		return sqlSession.insert(this.baseNamespace + "UserImg.insert", entity);
	}

	private int update(final UserImgUploadEntity entity) throws DaoAppException {
		return sqlSession.update(this.baseNamespace + "UserImg.update", entity);
	}

	@Override
	public int delete(Entity entity) throws DaoAppException {
		if (entity == null) {
			return -1;
		}
		IdEntity<String> idEntity = new IdEntity<String>();
		DeleteUserImgDataEntity deleteTagRequestEntity = (DeleteUserImgDataEntity) entity;
		idEntity.setId(deleteTagRequestEntity.getImgId());
		return sqlSession.update(this.baseNamespace + "UserImg.deleteByPrimaryKey", idEntity);
	}
}
