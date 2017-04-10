package cn.ilanhai.kem.dao.smsvalicode;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.kem.dao.MybatisBaseDao;
import cn.ilanhai.kem.domain.IdDto;
import cn.ilanhai.kem.domain.IdEntity;
import cn.ilanhai.kem.domain.smsvalicode.SmsValidateCodeEntity;

@Component("MyBatisSmsvalicodeDao")
public class MyBatisSmsValicodeDao extends MybatisBaseDao {
	private SqlSession sqlSession = this.getSqlSession();

	public MyBatisSmsValicodeDao() throws DaoAppException {
		super();

	}

	@Override
	public int save(Entity enity) throws DaoAppException {
		SmsValidateCodeEntity smsValidateCodeEntity = null;
		try {
			smsValidateCodeEntity = (SmsValidateCodeEntity) enity;
			if (!this.isExists(smsValidateCodeEntity.getId()))
				return this.add(smsValidateCodeEntity);
			return this.update(smsValidateCodeEntity);
		} catch (DaoAppException e) {
			throw e;
		}
	}

	private int add(final SmsValidateCodeEntity entity) throws DaoAppException {
		return sqlSession.insert(this.baseNamespace + "SmsValicode.insert", entity);
	}

	private int update(final SmsValidateCodeEntity entity) throws DaoAppException {
		return sqlSession.update(this.baseNamespace + "SmsValicode.update", entity);
	}

	private boolean isExists(final Integer id) throws DaoAppException {
		// 如果无ID，则
		if (id == null) {
			return false;
		}
		IdEntity<Integer> idEntity=new IdEntity<Integer>();
		idEntity.setId(id);
		return sqlSession.selectOne(this.baseNamespace+"SmsValicode.querySmsValicodeById", idEntity) != null ? true : false;

	}

	@Override
	public Entity query(Entity entity, boolean flag) throws DaoAppException {
		IdDto idDto = null;
		try {
			if (entity.getClass() == IdDto.class) {
				idDto = (IdDto) entity;
				return this.queryById(idDto);
			}
			return null;
		} catch (DaoAppException e) {
			throw e;
		}
	}

	private Entity queryById(final IdDto entity) throws DaoAppException {
		if (entity == null)
			throw new DaoAppException("请指定查询参数");
		return sqlSession.selectOne(this.baseNamespace+"SmsValicode.querySmsValicodeById2", entity);

	}
}
