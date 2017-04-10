package kem;

import cn.ilanhai.framework.app.dao.Dao;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.kem.dao.user.frontuser.FrontuserDao;
import cn.ilanhai.kem.domain.user.frontuser.FrontUserEntity;
import cn.ilanhai.kem.domain.user.frontuser.QueryByPhoneNoConditionEntity;
import cn.ilanhai.kem.domain.user.frontuser.RegistFrontUserEntity;
import junit.framework.TestCase;

public class FrontuserDaoTest extends TestCase{

	private Dao dao=null;
	public FrontuserDaoTest(){
		dao=new FrontuserDao();
	}
	
	public void testquery() throws DaoAppException {
		QueryByPhoneNoConditionEntity entity = new QueryByPhoneNoConditionEntity();
		entity.setPhoneNo("13088292253");
		FrontUserEntity result= (FrontUserEntity) dao.query(entity, true);
		System.out.println(result.getPhoneNo());
	}
}
