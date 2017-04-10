package cn.ilanhai.kem.bl.rights;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.ilanhai.framework.app.RequestContext;
import cn.ilanhai.framework.app.dao.Dao;
import cn.ilanhai.framework.common.exception.AppException;
import cn.ilanhai.framework.common.exception.BlAppException;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.kem.bl.BLContextUtil;
import cn.ilanhai.kem.common.CodeTable;
import cn.ilanhai.kem.dao.rights.RightsDao;
import cn.ilanhai.kem.domain.paymentservice.PayInfo.PayInfoServiceInfoEntity;
import cn.ilanhai.kem.domain.rights.UnRightsLogEntity;
import cn.ilanhai.kem.domain.rights.UnRightsTimesEntity;
import cn.ilanhai.kem.domain.rights.dto.QueryUnRightsLogDto;
import cn.ilanhai.kem.domain.rights.dto.QueryUnRightsTimesDto;
import cn.ilanhai.kem.domain.rights.dto.SaveUnRightsTimesDto;
import cn.ilanhai.kem.domain.rights.dto.UseUnRightsTimesDto;

/**
 * 去版权功能
 * 
 * @author hy
 *
 */
public class UnRightsManger {
	private static Class<?> currentclass = RightsDao.class;
	private static Logger logger = Logger.getLogger(UnRightsManger.class);

	/**
	 * 添加 去版权次数
	 * 
	 * @param context
	 *            全局上下文
	 * @param userId
	 *            用户编号
	 * @param addTimes
	 *            添加次数
	 * @throws DaoAppException
	 * @throws BlAppException
	 */
	public static void addTimes(RequestContext context, String userId, Integer addTimes)
			throws DaoAppException, BlAppException {
		BLContextUtil.valiParaItemIntegerNull(addTimes, "去版权次数");
		BLContextUtil.valiParaItemStrNullOrEmpty(userId, "用户编号");
		logger.info("用户编号:" + userId);
		logger.info("当前用户[" + userId + "] 购买去版权次数为:[" + addTimes + "]");
		Dao dao = BLContextUtil.getDao(context, currentclass);
		SaveUnRightsTimesDto saveUnRightsTimesDto = new SaveUnRightsTimesDto();
		saveUnRightsTimesDto.setAddTimes(addTimes);
		saveUnRightsTimesDto.setUserId(userId);

		int val = dao.save(saveUnRightsTimesDto);
		BLContextUtil.valiSaveDomain(val, "去版权次数");
		logger.info("消耗结果:" + searchTimes(context, userId).getUnrightsTimes());
	}

	/**
	 * 使用去版权次数
	 * 
	 * @param context
	 *            全局上下文
	 * @param userId
	 *            用户编号
	 * @param manuscriptId
	 *            稿件编号
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	@Transactional(rollbackFor = { AppException.class, Exception.class,
			Throwable.class }, propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
	public static void useTimes(RequestContext context, String userId, String manuscriptId)
			throws BlAppException, DaoAppException {
		logger.info("消耗去版权次数");
		BLContextUtil.valiParaItemStrNullOrEmpty(userId, "用户编号");
		BLContextUtil.valiParaItemStrNullOrEmpty(manuscriptId, "推广编号");
		logger.info("用户编号:" + userId);
		logger.info("推广编号:" + manuscriptId);
		Dao dao = BLContextUtil.getDao(context, currentclass);

		// 判断是否已经发布过去版权
		QueryUnRightsLogDto queryUnRightsLogDto = new QueryUnRightsLogDto();
		queryUnRightsLogDto.setManuscriptId(manuscriptId);
		UnRightsLogEntity entity = (UnRightsLogEntity) dao.query(queryUnRightsLogDto, false);
		if (entity == null) {
			logger.info("推广:" + manuscriptId + "为首次去版权,需要扣除[" + userId + "]用户的去版权次数");
			// 判断去版权次数是否为0
			UnRightsTimesEntity unRightsTimesEntity = searchTimes(context, userId);
			logger.info("当前用户[" + userId + "] 剩余去版权次数为:[" + unRightsTimesEntity.getUnrightsTimes() + "]");
			if (unRightsTimesEntity == null || unRightsTimesEntity.getUnrightsTimes() <= 0) {
				CodeTable ct = CodeTable.BL_UNRIGHTS_TIMES_ERROR;
				throw new BlAppException(ct.getValue(), ct.getDesc());
			} else {
				// 消耗去版权次数和添加去版权记录
				UseUnRightsTimesDto useUnRightsTimesDto = new UseUnRightsTimesDto();
				useUnRightsTimesDto.setUserId(userId);
				int val = dao.save(useUnRightsTimesDto);
				BLContextUtil.valiSaveDomain(val, "使用去版权次数");
				UnRightsLogEntity unRightsLogEntity = new UnRightsLogEntity();
				unRightsLogEntity.setManuscriptId(manuscriptId);
				unRightsLogEntity.setUserId(userId);
				int val2 = dao.save(unRightsLogEntity);
				BLContextUtil.valiSaveDomain(val2, "保存去版权记录");

				logger.info("消耗结果:" + searchTimes(context, userId).getUnrightsTimes());
			}
		} else {
			logger.info("推广:" + manuscriptId + "已去版权,不需要扣除[" + userId + "]用户的去版权次数");
			if (!userId.equals(entity.getUserId())) {
				CodeTable ct = CodeTable.BL_UNRIGHTS_USER_ERROR;
				throw new BlAppException(ct.getValue(), ct.getDesc());
			}
		}
	}

	/**
	 * 查询 去版权记录
	 * 
	 * @param context
	 *            全局上下文
	 * @param manuscriptId
	 *            稿件编号
	 * @return
	 * @throws DaoAppException
	 * @throws BlAppException
	 */
	public static UnRightsLogEntity queryUnRightsLog(RequestContext context, String manuscriptId)
			throws DaoAppException, BlAppException {
		BLContextUtil.valiParaItemStrNullOrEmpty(manuscriptId, "推广编号");
		Dao dao = BLContextUtil.getDao(context, currentclass);

		QueryUnRightsLogDto queryUnRightsLogDto = new QueryUnRightsLogDto();
		queryUnRightsLogDto.setManuscriptId(manuscriptId);
		UnRightsLogEntity entity = (UnRightsLogEntity) dao.query(queryUnRightsLogDto, false);
		return entity;
	}

	/**
	 * 查询 去版权次数
	 * 
	 * @param context
	 *            全局上下文
	 * @param userId
	 *            用户编号
	 * @return
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	public static UnRightsTimesEntity searchTimes(RequestContext context, String userId)
			throws BlAppException, DaoAppException {
		BLContextUtil.valiParaItemStrNullOrEmpty(userId, "用户编号");
		Dao dao = BLContextUtil.getDao(context, currentclass);
		QueryUnRightsTimesDto queryUnRightsTimesDto = new QueryUnRightsTimesDto();
		queryUnRightsTimesDto.setUserId(userId);
		UnRightsTimesEntity unRightsTimesEntity = (UnRightsTimesEntity) dao.query(queryUnRightsTimesDto, false);
		return unRightsTimesEntity;
	}

	/**
	 * 下单购买去版权次数
	 * 
	 * @param context
	 * @param info
	 * @param userId
	 * @throws DaoAppException
	 * @throws BlAppException
	 */
	public static void buyUnrightsTimes(RequestContext context, PayInfoServiceInfoEntity info, String userId)
			throws DaoAppException, BlAppException {
		logger.info("购买去版权次数:" + info.getQuantity());
		logger.info("用户:" + userId);

		logger.info("购买前:" + searchTimes(context, userId));
		addTimes(context, userId, info.getQuantity());
		logger.info("购买结果:" + searchTimes(context, userId));
	}
}
