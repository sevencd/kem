package cn.ilanhai.kem.bl.imgvalicode;

import cn.ilanhai.framework.app.RequestContext;
import cn.ilanhai.framework.app.dao.Dao;
import cn.ilanhai.framework.app.dao.DaoProxyFactory;
import cn.ilanhai.framework.common.exception.BlAppException;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.kem.common.CodeTable;
import cn.ilanhai.kem.dao.imgvalicode.ImgValicodeDao;
import cn.ilanhai.kem.domain.IdDto;
import cn.ilanhai.kem.domain.enums.ValidateStatus;
import cn.ilanhai.kem.domain.imgvalicode.ImgVailcodeEntity;
/**
 * 短信验证码管理器 该管理器供短信验证码共有逻辑的管理 可供其他模块调用
 * 
 * @author Nature
 *
 */
public class ImgValicodeManager {
	private DaoProxyFactory daoFactory;

	public ImgValicodeManager(DaoProxyFactory daoFac) {
		this.daoFactory = daoFac;
	}

	/**
	 * 校验图形验证码
	 * 
	 * @param smsCode
	 * @param recordId
	 * @param requestContext
	 * @throws DaoAppException
	 * @throws BlAppException
	 */
	public void verify(String imgCode, Integer recordId, RequestContext requestContext)
			throws DaoAppException, BlAppException {

		// 获取记录实体
		ImgVailcodeEntity entity = this.getImgValidateCodeEntity(recordId, requestContext);

		// 校验验证码
		entity.verify(imgCode);

		// 修改校验状态
		entity.setStatus(ValidateStatus.VERIFIED);
		// 保存校验记录
		Dao dao = daoFactory.getDao(requestContext, "imgvalicodeDao");
		dao.save(entity);
	}
	
	/**
	 * 校验图形验证码 （只做验证不做保存）
	 * 
	 * @param smsCode
	 * @param recordId
	 * @param requestContext
	 * @param flag 用户重载
	 * @throws DaoAppException
	 * @throws BlAppException
	 */
	public void verify(String imgCode, Integer recordId, RequestContext requestContext,boolean flag)
			throws DaoAppException, BlAppException {

		// 获取记录实体
		ImgVailcodeEntity entity = this.getImgValidateCodeEntity(recordId, requestContext);

		// 校验验证码
		entity.verify(imgCode);
	}

	/**
	 * 获取短信验证码记录状态
	 * 
	 * @param smsCode
	 * @param recordId
	 * @param requestContext
	 * @return
	 * @throws DaoAppException
	 * @throws BlAppException
	 */
	public ValidateStatus getStatus(String smsCode, Integer recordId, RequestContext requestContext)
			throws DaoAppException, BlAppException {

		// 获取记录实体
		ImgVailcodeEntity entity = this.getImgValidateCodeEntity(recordId, requestContext);

		return entity.getStatus();
	}

	private ImgVailcodeEntity getImgValidateCodeEntity(Integer recordId, RequestContext requestContext)
			throws DaoAppException, BlAppException {
		if (recordId == null) {
			throw new BlAppException(CodeTable.BL_IMGVALICODE_ERROR.getValue(),
					CodeTable.BL_IMGVALICODE_ERROR.getDesc());
		}
		// 获取smsdao
		Dao dao = daoFactory.getDao(requestContext, ImgValicodeDao.class);
		// 获取校验记录实体
		IdDto idDto = null;
		idDto = new IdDto();
		idDto.setId(recordId);

		ImgVailcodeEntity entity = null;
		entity = (ImgVailcodeEntity) dao.query(idDto, false);
		if (entity == null) {
			throw new BlAppException(CodeTable.BL_SMSVALICODE_WRONGCODE.getValue(),
					CodeTable.BL_SMSVALICODE_WRONGCODE.getDesc());
		}

		return entity;
	}
}
