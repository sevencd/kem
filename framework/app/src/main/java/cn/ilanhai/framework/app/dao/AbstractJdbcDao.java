package cn.ilanhai.framework.app.dao;

import java.util.Iterator;
import java.util.List;

import javax.sql.DataSource;

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
 * 定义抽象jdbc数据访问
 * 
 * @author he
 *
 */
public abstract class AbstractJdbcDao extends JdbcDaoSupport implements Dao {
	private Application application = null;

	public AbstractJdbcDao() {
		
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

	public DriverManagerDataSource getDs() {
		// jdbc:mysql://localhost:3306/test?user=root&useUnicode=true&characterEncoding=gbk&serverTimezone=UTC&useSSL=false"
		// jdbc:mysql://192.168.1.228:3306/kem_dev?user=root&password=123456&useUnicode=true&characterEncoding=gbk&serverTimezone=UTC&useSSL=false"
		DriverManagerDataSource ds = null;
		ds = new DriverManagerDataSource();
		ds.setDriverClassName("com.mysql.jdbc.Driver");
		ds.setUrl("jdbc:mysql://192.168.1.228:3306/kem_dev?user=root&password=123456&useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC&useSSL=false");

		// ds.setUsername("root");
		// ds.setPassword("");
		// Properties properties = null;
		// properties = new Properties();
		// properties.put("useUnicode", "true");
		// properties.put("characterEncoding", "gbk");
		// properties.put("serverTimezone", "UTC");
		// properties.put("useSSL", "false");
		// ds.setConnectionProperties(properties);
		return ds;
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
	/**
	 * 执行sql，返回影响行数
	 * 
	 * @param preparedStatementCreator
	 *            构造sql
	 * @param keyHolder
	 *            如果是insert into 表中有自动增加字段，可从keyHolder得到
	 * @return -1 失败 大于-1返回影响行数
	 * @throws DaoAppException
	 */
	protected int execUpdate(PreparedStatementCreator preparedStatementCreator,
			KeyHolder keyHolder) throws DaoAppException {
		JdbcTemplate template = null;
		try {
			if (preparedStatementCreator == null || keyHolder == null)
				return -1;
			template = this.getJdbcTemplate();

			return template.update(preparedStatementCreator, keyHolder);
		} catch (Exception e) {
			throw new DaoAppException(e);
		}
	}

	/**
	 * 执行sql，返回影响行数
	 * 
	 * @param preparedStatementCreator
	 *            构造sql语句
	 * @return -1 失败 大于-1返回影响行数
	 * @throws DaoAppException
	 */
	protected int execUpdate(PreparedStatementCreator preparedStatementCreator)
			throws DaoAppException {
		JdbcTemplate template = null;
		try {
			if (preparedStatementCreator == null)
				return -1;
			template = this.getJdbcTemplate();
			return template.update(preparedStatementCreator);
		} catch (Exception e) {
			throw new DaoAppException(e);
		}
	}

	/**
	 * 执行sql，返回影响行数
	 * 
	 * @param sql
	 *            sql语句
	 * @return -1 失败 大于-1返回影响行数
	 * @throws DaoAppException
	 */
	protected int execUpdate(String sql) throws DaoAppException {
		JdbcTemplate template = null;
		try {
			if (sql == null || sql.length() <= 0)
				return -1;
			template = this.getJdbcTemplate();
			return template.update(sql);
		} catch (Exception e) {
			throw new DaoAppException(e);
		}
	}

	/**
	 * 执行sql，返回影响行数
	 * 
	 * @param sql
	 *            sql语句
	 * @param args
	 *            sql语句占位符的填充值
	 * @return -1 失败 大于-1返回影响行数
	 * @throws DaoAppException
	 */
	protected int execUpdate(String sql, Object... args) throws DaoAppException {
		JdbcTemplate template = null;
		try {
			if (sql == null || sql.length() <= 0 || args == null
					|| args.length <= 0)
				return -1;
			template = this.getJdbcTemplate();
			return template.update(sql, args);
		} catch (Exception e) {
			throw new DaoAppException(e);
		}
	}

	/**
	 * 执行sql，返回影响行数
	 * 
	 * @param sql
	 *            sql语句
	 * @param args
	 *            sql语句占位符的填充值
	 * @param argTypes
	 *            sql语句占位符的填充值类型
	 * @return -1 失败 大于-1返回影响行数
	 * @throws DaoAppException
	 */
	protected int execUpdate(String sql, Object[] args, int[] argTypes)
			throws DaoAppException {
		JdbcTemplate template = null;
		try {
			if (sql == null || sql.length() <= 0 || args == null
					|| args.length <= 0 || argTypes == null
					|| argTypes.length <= 0)
				return -1;
			template = this.getJdbcTemplate();
			return template.update(sql, args, argTypes);
		} catch (Exception e) {
			throw new DaoAppException(e);
		}
	}

	/**
	 * 批量执行sql，返回影响行数
	 * 
	 * @param sql
	 *            sql语句
	 * @return -1 失败 大于-1返回影响行数
	 * @throws DaoAppException
	 */
	protected int[] execBatchUpdate(String sql) throws DaoAppException {
		JdbcTemplate template = null;
		try {
			if (sql == null || sql.length() <= 0)
				return null;
			template = this.getJdbcTemplate();
			return template.batchUpdate(sql);
		} catch (Exception e) {
			throw new DaoAppException(e);
		}
	}

	/**
	 * 批量执行sql，返回影响行数
	 * 
	 * @param sql
	 *            sql语句
	 * @param args
	 *            sql语句占位符的填充值
	 * @return -1 失败 大于-1返回影响行数
	 * @throws DaoAppException
	 */
	protected int[] execBatchUpdate(String sql, List<Object[]> batchArgs)
			throws DaoAppException {
		JdbcTemplate template = null;
		try {
			if (sql == null || sql.length() <= 0 || batchArgs == null
					|| batchArgs.size() <= 0)
				return null;
			template = this.getJdbcTemplate();
			return template.batchUpdate(sql, batchArgs);
		} catch (Exception e) {
			throw new DaoAppException(e);
		}
	}

	/**
	 * 批量执行sql，返回影响行数
	 * 
	 * @param sql
	 *            sql语句
	 * @param args
	 *            sql语句占位符的填充值
	 * @param argTypes
	 *            sql语句占位符的填充值类型
	 * @return -1 失败 大于-1返回影响行数
	 * @throws DaoAppException
	 */
	protected int[] execBatchUpdate(String sql, List<Object[]> batchArgs,
			int[] argTypes) throws DaoAppException {
		JdbcTemplate template = null;
		try {
			if (sql == null || sql.length() <= 0 || batchArgs == null
					|| batchArgs.size() <= 0 || argTypes == null
					|| argTypes.length <= 0)
				return null;
			template = this.getJdbcTemplate();
			return template.batchUpdate(sql, batchArgs, argTypes);
		} catch (Exception e) {
			throw new DaoAppException(e);
		}
	}

	/**
	 * 获取单例单行值
	 * 
	 * @param sql
	 *            sql语句
	 * @param t
	 *            返回的类型，一般为基类型的对象类型 如: Integer.class
	 * @return 返回的类型对应值
	 * @throws DaoAppException
	 */
	protected <T> T execQueryForObject(String sql, Class<T> t)
			throws DaoAppException {
		JdbcTemplate template = null;
		try {
			if (sql == null || sql.length() <= 0 || t == null)
				return null;
			template = this.getJdbcTemplate();
			return template.queryForObject(sql, t);
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (Exception e) {
			throw new DaoAppException(e);
		}
	}

	/**
	 * 获取单例单行值
	 * 
	 * @param sql
	 *            sql语句
	 * @param args
	 *            sql语句占位符的填充值
	 * @param t
	 *            返回的类型，一般为基类型的对象类型 如: Integer.class
	 * @return 返回的类型对应值
	 * @throws DaoAppException
	 */
	protected <T> T execQueryForObject(String sql, Object[] args, Class<T> t)
			throws DaoAppException {
		JdbcTemplate template = null;
		try {
			if (sql == null || sql.length() <= 0 || args == null
					|| args.length <= 0 || t == null)
				return null;
			template = this.getJdbcTemplate();
			return template.queryForObject(sql, args, t);
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (Exception e) {
			throw new DaoAppException(e);
		}
	}

	/**
	 * 获取单例单行值
	 * 
	 * @param sql
	 *            sql语句
	 * @param args
	 *            sql语句占位符的填充值
	 * @param argTypes
	 *            sql语句占位符的填充值类型
	 * @param t
	 *            返回的类型，一般为基类型的对象类型 如: Integer.class
	 * @return 返回的类型对应值
	 * @throws DaoAppException
	 */
	protected <T> T execQueryForObject(String sql, Object[] args,
			int[] argTypes, Class<T> t) throws DaoAppException {
		JdbcTemplate template = null;
		try {
			if (sql == null || sql.length() <= 0 || args == null
					|| args.length <= 0 || argTypes == null
					|| argTypes.length <= 0 || t == null)
				return null;
			template = this.getJdbcTemplate();
			return template.queryForObject(sql, args, argTypes, t);
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (Exception e) {
			throw new DaoAppException(e);
		}
	}

	/**
	 * 查询数据
	 * 
	 * @param sql
	 *            sql语句
	 * @return RowSet对象
	 * 
	 * @throws DaoAppException
	 */
	protected SqlRowSet execQueryForRowSet(String sql) throws DaoAppException {
		JdbcTemplate template = null;
		try {
			if (sql == null || sql.length() <= 0)
				return null;
			template = this.getJdbcTemplate();
			return template.queryForRowSet(sql);
		} catch (Exception e) {
			throw new DaoAppException(e);
		}
	}

	/**
	 * 查询数据
	 * 
	 * @param sql
	 *            sql语句
	 * @param args
	 *            sql语句占位符的填充值
	 * @return RowSet对象
	 * 
	 * @throws DaoAppException
	 */
	protected SqlRowSet execQueryForRowSet(String sql, Object... args)
			throws DaoAppException {
		JdbcTemplate template = null;
		try {
			if (sql == null || sql.length() <= 0 || args == null
					|| args.length <= 0)
				return null;
			template = this.getJdbcTemplate();
			return template.queryForRowSet(sql, args);
		} catch (Exception e) {
			throw new DaoAppException(e);
		}
	}

	/**
	 * 查询数据
	 * 
	 * @param sql
	 *            sql语句
	 * @param args
	 *            sql语句占位符的填充值
	 * @param argTypes
	 *            sql语句占位符的填充值类型
	 * @return RowSet对象
	 * 
	 * @throws DaoAppException
	 */
	protected SqlRowSet execQueryForRowSet(String sql, Object[] args,
			int[] argTypes) throws DaoAppException {
		JdbcTemplate template = null;
		try {
			if (sql == null || sql.length() <= 0 || args == null
					|| args.length <= 0 || argTypes == null
					|| argTypes.length <= 0)
				return null;
			template = this.getJdbcTemplate();

			return template.queryForRowSet(sql, args, argTypes);
		} catch (Exception e) {
			throw new DaoAppException(e);
		}

	}

	/**
	 * 执行脚本
	 * 
	 * @param script
	 * @return
	 * @throws DaoAppException
	 */
	protected boolean exec(String script) throws DaoAppException {
		JdbcTemplate template = null;
		try {
			if (script == null || script.length() <= 0)
				return false;
			template = this.getJdbcTemplate();
			template.execute(script);
			return true;
		} catch (Exception e) {
			throw new DaoAppException(e);
		}

	}

}
