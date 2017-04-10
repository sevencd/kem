package cn.ilanhai.framework.common.ds;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

import cn.ilanhai.framework.common.configuration.ds.DsConf;
import cn.ilanhai.framework.common.exception.AppException;
import cn.ilanhai.framework.common.exception.BlAppException;

public class DsManagerImpl implements DsManager {
	private Map<String, DataSource> ds = null;
	private Map<String, DsConf> dsConf = null;

	public DsManagerImpl() {
		this.ds = new HashMap<String, DataSource>();
		this.dsConf = new HashMap<String, DsConf>();
	}

	@Override
	public boolean init(List<DsConf> conf) throws AppException {
		Iterator<DsConf> iterator = null;
		DsConf dsConf = null;
		DataSource ds = null;
		if (conf == null || conf.size() <= 0)
			return true;
		iterator = conf.iterator();

		while (iterator.hasNext()) {
			dsConf = iterator.next();
			if (dsConf == null)
				continue;
			if (this.ds.containsKey(dsConf.getId()))
				continue;
			ds = this.loadDs(dsConf);
			if (ds == null)
				continue;
			this.ds.put(dsConf.getId(), ds);
			this.dsConf.put(dsConf.getId(), dsConf);

		}
		return true;

	}

	private DataSource loadDs(DsConf conf) throws AppException {
		ClassLoader classLoader = null;
		DriverManagerDataSource ds = null;
		Class<?> class1 = null;
		classLoader = Thread.currentThread().getContextClassLoader();
		try {
			class1 = classLoader.loadClass(conf.getClassName());
			ds = (DriverManagerDataSource) class1.newInstance();
			ds.setDriverClassName(conf.getDriverClassName());
			ds.setUrl(conf.getUrl());
			return ds;
		} catch (Exception e) {
			throw new BlAppException(e.getMessage(), e);
		}

	}

	// public boolean add(String key, DataSource dataSource) throws
	// AppException {
	// if (key == null || key.length() <= 0)
	// return false;
	// if (dataSource == null)
	// return false;
	// Iterator<DataSource> iterator = null;
	// synchronized (this) {
	// if (this.ds.containsKey(key))
	// return false;
	// iterator = this.ds.values().iterator();
	// while (iterator.hasNext()) {
	// if (dataSource == iterator.next())
	// return false;
	//
	// }
	// this.ds.put(key, dataSource);
	// return true;
	// }
	//
	// }

	@Override
	public DataSource get(String key) {
		if (key == null || key.length() <= 0)
			return null;
		if (!this.ds.containsKey(key))
			return null;
		return this.ds.get(key);

	}

	@Override
	public DsConf getConf(String key) {
		if (key == null || key.length() <= 0)
			return null;
		if (!this.dsConf.containsKey(key))
			return null;
		return this.dsConf.get(key);
	}

	@Override
	public void close() {
		this.ds.clear();

	}

}
