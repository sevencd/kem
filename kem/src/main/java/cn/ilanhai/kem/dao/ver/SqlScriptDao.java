package cn.ilanhai.kem.dao.ver;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.kem.dao.BaseDao;

@Component("sqlScriptDao")
public class SqlScriptDao extends BaseDao {
	private Logger logger = Logger.getLogger(SqlScriptDao.class);

	public SqlScriptDao() {
		super();
	}

	/**
	 * 测试数据库连接
	 * 
	 * @return
	 * @throws DaoAppException
	 */
	public boolean testConnectDb() throws DaoAppException {
		Integer retVal = null;
		String sql = "SELECT COUNT(*) SCHEMA_NAME FROM information_schema.SCHEMATA ;";
		retVal = this.execQueryForObject(sql, Integer.class);
		if (retVal == null)
			return false;
		if (retVal.intValue() <= 0)
			return false;
		return true;
	}

	/**
	 * 执行脚本
	 * 
	 * @param script
	 * @return
	 * @throws DaoAppException
	 */
	public boolean execScript(String script) throws DaoAppException {
		boolean flag = false;
		try {
			this.logger.info("begin execute script");
			if (script == null || script.length() <= 0)
				return false;
			script = script.replace("{{dataBaseName}}", this.schema);
			this.logger.info(script);
			flag = this.exec(script);
			this.logger.info(flag);
			this.logger.info("end execute script");
			return flag;
		} catch (DaoAppException e) {
			throw e;
		} catch (Exception e) {
			throw new DaoAppException(e);
		} finally {

		}
	}

	/**
	 * 数据库版本信息表是否存在
	 * 
	 * @return
	 * @throws DaoAppException
	 */
	public boolean dbVerTableIsExists() throws DaoAppException {
		String retVal = null;
		ArrayList<Object> args = null;
		String sql = "SELECT TABLE_NAME from information_schema.`TABLES` WHERE TABLE_NAME=? AND TABLE_SCHEMA=? ;";
		args = new ArrayList<Object>();
		args.add("db_ver");
		args.add(schema);
		retVal = this.execQueryForObject(sql, args.toArray(), String.class);
		if (retVal == null || retVal.length() <= 0)
			return false;
		return true;
	}
}
