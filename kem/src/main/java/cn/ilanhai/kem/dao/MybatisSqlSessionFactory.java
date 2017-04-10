package cn.ilanhai.kem.dao;

import javax.sql.DataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import cn.ilanhai.framework.common.exception.DaoAppException;

public class MybatisSqlSessionFactory {
	private static MybatisSqlSessionFactory instance = null;
	private SqlSessionFactoryBean sqlSessionFactoryBean = null;
	private boolean flag = false;

	private MybatisSqlSessionFactory() {

	}

	public void init(DataSource dataSource) throws DaoAppException {
		Resource resource = null;
		try {
			if (this.flag)
				return;
			this.sqlSessionFactoryBean = new SqlSessionFactoryBean();
			resource = new ClassPathResource("mybatis.cfg.xml");
			this.sqlSessionFactoryBean.setConfigLocation(resource);
			if (dataSource == null)
				throw new DaoAppException("数据源错误");
			this.sqlSessionFactoryBean.setDataSource(dataSource);
			
			this.flag = true;
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		}
	}
	public static MybatisSqlSessionFactory getInstance() {
		if (instance == null)
			instance = new MybatisSqlSessionFactory();
		return instance;
	}

	public SqlSessionFactoryBean getSqlSessionFactoryBean()
			throws DaoAppException {
		if (!this.flag)
			throw new DaoAppException("类型SqlSessionFactoryBean没有初始化");
		return this.sqlSessionFactoryBean;
	}
}
