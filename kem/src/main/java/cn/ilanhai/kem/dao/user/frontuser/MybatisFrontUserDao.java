package cn.ilanhai.kem.dao.user.frontuser;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.kem.dao.MybatisBaseDao;
@Component("MybatisFrontUserDao")
public class MybatisFrontUserDao extends MybatisBaseDao {
	private SqlSession sqlSession = this.getSqlSession();

	public MybatisFrontUserDao() throws DaoAppException {
		super();
	}
	
	
}
