
package cn.ilanhai.kem.dao.statistic;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.kem.dao.MybatisBaseDao;
import cn.ilanhai.kem.domain.CountDto;
import cn.ilanhai.kem.domain.statistic.dto.EmailUserRequestDto;
import cn.ilanhai.kem.domain.statistic.dto.SMSUserRequestDto;

@Component("applicationStatisticDao")
public class ApplicationStatisticDao extends MybatisBaseDao {
	private SqlSession sqlSession = this.getSqlSession();

	public ApplicationStatisticDao() throws DaoAppException {
		super();
	}

	@Override
	public Entity query(Entity entity, boolean flag) throws DaoAppException {
		CountDto count = new CountDto();
		if (entity == null)
			throw new DaoAppException("");
		if (entity instanceof EmailUserRequestDto) {
			count.setCount(queryDataForTotal((EmailUserRequestDto) entity));
			return count;
		}
		if (entity instanceof SMSUserRequestDto) {
			count.setCount(queryDataForTotal((SMSUserRequestDto) entity));
			return count;
		}
		return null;
	}
	/*
	 * 查询邮件应用用户开通数量
	 */
	private int queryDataForTotal(EmailUserRequestDto entity) {
		int count = sqlSession.selectOne(this.baseNamespace+"ApplicationStatisticDao.selectEmaiLUserForTotalCount", entity);
		return count;
	}
	/*
	 * 查询短信应用用户开通数量
	 */
	private int queryDataForTotal(SMSUserRequestDto entity) {
		int count = sqlSession.selectOne(this.baseNamespace+"ApplicationStatisticDao.selectSMSUserForTotalCount", entity);
		return count;
	}

	@Override
	public List<Entity> queryList(Entity entity) throws DaoAppException {
		if (entity == null)
			return queryDataForCount();
		if (entity instanceof EmailUserRequestDto) {
			  //查询邮件应用用户明细
			return sqlSession.selectList(this.baseNamespace + "ApplicationStatisticDao.selectEmaiLUserForPage", entity);
		}
		if (entity instanceof SMSUserRequestDto) {
			 //查询短信应用用户明细
			return sqlSession.selectList(this.baseNamespace + "ApplicationStatisticDao.selectSMSUserForPage", entity);
		}
		return null;
	}

	/**
	 * 查询应用统计总览
	 * 
	 * @param entity
	 * @return
	 * @throws DaoAppException
	 */
	private List<Entity> queryDataForCount() throws DaoAppException {
		List<Entity> list = sqlSession
				.selectList(this.baseNamespace + "ApplicationStatisticDao.selectAppStatisticForCount");
		return list;
	}

}
