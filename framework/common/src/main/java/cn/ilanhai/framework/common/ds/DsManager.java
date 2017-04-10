package cn.ilanhai.framework.common.ds;

import java.util.List;

import javax.sql.DataSource;

import cn.ilanhai.framework.common.configuration.ds.DsConf;
import cn.ilanhai.framework.common.exception.AppException;

public interface DsManager {

	boolean init(List<DsConf> conf) throws AppException;

	//boolean add(String key, DataSource dataSource) throws AppException;

	DataSource get(String key);

	DsConf getConf(String key);

	void close();
}
