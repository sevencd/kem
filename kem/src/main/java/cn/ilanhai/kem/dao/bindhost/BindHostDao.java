package cn.ilanhai.kem.dao.bindhost;

import java.util.Iterator;
import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.kem.dao.MybatisBaseDao;
import cn.ilanhai.kem.domain.bindhost.BindHostRequestEntity;
import cn.ilanhai.kem.domain.bindhost.QueryBindHostRequestEntity;
import cn.ilanhai.kem.domain.bindhost.QueryBindHostResponseEntity;
import cn.ilanhai.kem.domain.bindhost.QueryExtensionByuserRequest;
import cn.ilanhai.kem.domain.bindhost.SearchSysBind;
import cn.ilanhai.kem.domain.bindhost.UpdateUserHostStatus;

@Component("bindhostDao")
public class BindHostDao extends MybatisBaseDao {

	public BindHostDao() throws DaoAppException {
		super();
	}

	private Logger logger = Logger.getLogger(BindHostDao.class);

	public int save(Entity entity) throws DaoAppException {
		if (entity instanceof BindHostRequestEntity) {
			return saveOne((BindHostRequestEntity) entity);
		} else if (entity instanceof UpdateUserHostStatus) {
			return saveOne((UpdateUserHostStatus) entity);
		}
		return 0;
	}

	public Iterator<Entity> query(Entity entity) throws DaoAppException {
		if (entity instanceof QueryExtensionByuserRequest) {
			return queryOne((QueryExtensionByuserRequest) entity).iterator();
		}
		return null;
	}

	public Entity query(Entity entity, boolean arg1) throws DaoAppException {
		if (entity instanceof QueryBindHostRequestEntity) {
			return queryOne((QueryBindHostRequestEntity) entity);
		} else if (entity instanceof SearchSysBind) {
			return queryOne((SearchSysBind) entity);
		}
		return null;
	}

	// 查询系统配置域名相关内容
	private Entity queryOne(SearchSysBind entity) throws DaoAppException {
		logger.info("查询系统配置域名相关内容");
		SqlSession sqlSession = null;
		try {
			sqlSession = this.getSqlSession();
			return sqlSession.selectOne("BindHost.querysysbind", entity);
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		} finally {

		}
	}

	// 查询推广信息
	private List<Entity> queryOne(QueryExtensionByuserRequest entity) throws DaoAppException {
		logger.info("查询推广");
		SqlSession sqlSession = null;
		try {
			sqlSession = this.getSqlSession();
			return sqlSession.selectList("BindHost.queryextensionbyuser", entity);
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		} finally {

		}
	}

	// 查询用户绑定的域名
	private Entity queryOne(QueryBindHostRequestEntity entity) throws DaoAppException {
		logger.info("查询用户绑定域名");
		SqlSession sqlSession = null;
		try {
			sqlSession = this.getSqlSession();
			return sqlSession.selectOne("BindHost.loadbindhost", entity);
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		} finally {

		}
	}

	private int saveOne(BindHostRequestEntity entity) throws DaoAppException {

		logger.info("保存绑定域名" + entity.getHost());
		SqlSession sqlSession = null;
		try {
			sqlSession = this.getSqlSession();
			int result = 0;
			if (entity.getHost() == null || entity.getHost().length() <= 0) {
				result = sqlSession.insert("BindHost.deletebinduser", entity);
				return result;
			}
			if (this.hasHost(entity.getUserId())) {
				// 保存用户域名
				result = sqlSession.insert("BindHost.savebindhost", entity);
			} else {
				result = sqlSession.update("BindHost.updatebindhost", entity);
			}
			return result;
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		} finally {
		}
	}

	private boolean hasHost(String userId) throws DaoAppException {
		QueryBindHostRequestEntity user = new QueryBindHostRequestEntity();
		user.setUserId(userId);
		QueryBindHostResponseEntity userInfo = (QueryBindHostResponseEntity) this.queryOne(user);
		if (userInfo != null) {
			return false;
		}

		return true;
	}

	private int saveOne(UpdateUserHostStatus entity) throws DaoAppException {
		logger.info("修改绑定域名状态");
		SqlSession sqlSession = null;
		try {
			sqlSession = this.getSqlSession();
			int result = 0;
			result = sqlSession.update("BindHost.updatehoststatus", entity);
			return result;
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		} finally {
		}
	}

}
