package cn.ilanhai.framework.app.dao;

import java.util.Iterator;
import java.util.List;

import javax.sql.DataSource;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import cn.ilanhai.framework.app.Application;
import cn.ilanhai.framework.app.domain.*;
import cn.ilanhai.framework.common.exception.DaoAppException;

/**
 * 定义抽象MyBatis数据访问
 * 
 * @author he
 *
 */
public abstract class AbstractMyBatisDao extends SqlSessionDaoSupport implements
		Dao {
	private Application application = null;

	public AbstractMyBatisDao() {
		
	}	

	public final void setApplication(Application application) {
		this.application = application;
	}

	public final Application getApplication() {
		return this.application;
	}

	protected DataSource ds() {
		return null;
	}

	

	public int save(Entity entity) throws DaoAppException {
		return 0;
	}

	public int[] save(Iterator<Entity> entity) throws DaoAppException {

		return null;
	}

	public int delete(Entity entity) throws DaoAppException {

		return 0;
	}

	public int[] delete(Iterator<Entity> entity) throws DaoAppException {

		return null;
	}

	public Entity query(Entity entity, boolean flag) throws DaoAppException {

		return null;
	}

	public Iterator<Entity> query(Entity entity) throws DaoAppException {

		return null;
	}
	public List<Entity> queryList(Entity entity) throws DaoAppException {

		return null;
	}
}
