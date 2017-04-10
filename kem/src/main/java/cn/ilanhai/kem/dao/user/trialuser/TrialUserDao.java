
package cn.ilanhai.kem.dao.user.trialuser;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.kem.common.CodeTable;
import cn.ilanhai.kem.dao.MybatisBaseDao;

@Component("TrialUserDao")
public class TrialUserDao extends MybatisBaseDao {
	private SqlSession sqlSession = this.getSqlSession();

	public TrialUserDao() throws DaoAppException {
		super();
	}

	@Override
	public int save(Entity entity) throws DaoAppException {
		return this.add(entity);
	}

	private int add(Entity entity) throws DaoAppException {
		try {
			return sqlSession.insert(this.baseNamespace + "TrialUserDao.insert", entity);
		} catch (Exception e) {
			throw new DaoAppException(CodeTable.BL_DATA_ERROR.getValue(), CodeTable.BL_DATA_ERROR.getDesc(), e);
		}
	}

}
