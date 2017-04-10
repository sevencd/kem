package cn.ilanhai.kem.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import cn.ilanhai.framework.app.Application;
import cn.ilanhai.framework.app.SyncApplication;
import cn.ilanhai.framework.app.dao.AbstractJdbcDao;
import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.configuration.ds.DsConf;
import cn.ilanhai.framework.common.ds.DsManager;
import cn.ilanhai.kem.bl.user.frontuser.impl.FrontuserImpl;
import cn.ilanhai.kem.common.Constant;
import cn.ilanhai.kem.domain.plugin.formplugin.FormPluginEntity;

public class BaseDao extends AbstractJdbcDao {
	private Logger logger = Logger.getLogger(BaseDao.class);
	protected String schema = null;
	public BaseDao() {
		super();
		DataSource ds = null;
		String temp;
		ds = this.ds();
		this.setDataSource(ds);
		temp = String.format("%s %s", this.getClass().getName(), ds);
		this.logger.info(temp);
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

	protected Iterator<Entity> rowSetToCollection(SqlRowSet rs) {
		List<Entity> ls = null;
		Entity e = null;
		if (rs == null)
			return null;
		ls = new ArrayList<Entity>();
		while (rs.next()) {
			e = this.rowToEntity(rs);
			if (e != null)
				ls.add(e);
		}
		return ls.iterator();
	}

	protected Entity rowToEntity(SqlRowSet rs) {
		return null;
	}
}
