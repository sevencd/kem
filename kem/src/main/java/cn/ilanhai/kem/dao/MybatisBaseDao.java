package cn.ilanhai.kem.dao;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;

import cn.ilanhai.framework.app.Application;
import cn.ilanhai.framework.app.SyncApplication;
import cn.ilanhai.framework.app.dao.AbstractMyBatisDao;
import cn.ilanhai.framework.common.configuration.ds.DsConf;
import cn.ilanhai.framework.common.ds.DsManager;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.kem.common.Constant;

public abstract class MybatisBaseDao extends AbstractMyBatisDao {
	protected String schema = null;
	protected String baseNamespace="cn.ilanhai.mybatismapper.";
	public MybatisBaseDao() throws DaoAppException {
		super();
		this.init();
	}

	/**
	 * @throws DaoAppException
	 */
	private void init() throws DaoAppException {
		DataSource dataSource = null;
		MybatisSqlSessionFactory mybatisSqlSessionFactory = null;
		SqlSessionFactoryBean sqlSessionFactoryBean = null;
		SqlSessionFactory sqlSessionFactory = null;
		try {
			dataSource = this.ds();
			mybatisSqlSessionFactory = MybatisSqlSessionFactory.getInstance();
			mybatisSqlSessionFactory.init(dataSource);
			sqlSessionFactoryBean = mybatisSqlSessionFactory
					.getSqlSessionFactoryBean();
			sqlSessionFactory = sqlSessionFactoryBean.getObject();
			this.setSqlSessionFactory(sqlSessionFactory);
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		}
	}

	@Override
	protected DataSource ds() {
		DataSource ds = null;
		DsConf conf = null;
		DsManager dsManager = null;
		Application application = null;
		application = SyncApplication.getApplication();
		dsManager = application.getDs();
		ds = dsManager.get(Constant.KEY_DATABASE_NAME_KEM);
		conf = dsManager.getConf(Constant.KEY_DATABASE_NAME_KEM);
		this.schema = conf.getDataBaseName();
		return ds;
	}

}
