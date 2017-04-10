package cn.ilanhai.kem.bl.sms;

import cn.ilanhai.framework.app.RequestContext;
import cn.ilanhai.framework.app.dao.Dao;
import cn.ilanhai.framework.app.dao.DaoProxyFactory;
import cn.ilanhai.framework.common.exception.BlAppException;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.kem.common.CodeTable;
import cn.ilanhai.kem.dao.smsvalicode.MyBatisSmsValicodeDao;
import cn.ilanhai.kem.domain.IdDto;
import cn.ilanhai.kem.domain.enums.ValidateStatus;
import cn.ilanhai.kem.domain.smsvalicode.SmsValidateCodeEntity;

/**
 * 短信验证码管理器
 * 该管理器供短信验证码共有逻辑的管理
 * 可供其他模块调用
 * @author Nature
 *
 */
public class SmsValicodeManager {
	private DaoProxyFactory daoFactory;
	
	public SmsValicodeManager(DaoProxyFactory daoFac){
		this.daoFactory=daoFac;
	}
	
	/**
	 * 校验短信验证码
	 * @param smsCode
	 * @param recordId
	 * @param requestContext
	 * @throws DaoAppException 
	 * @throws BlAppException 
	 */
	public void verify(String smsCode,Integer recordId,RequestContext requestContext) throws DaoAppException, BlAppException{
		
		//获取记录实体
		SmsValidateCodeEntity entity=this.getSmsValidateCodeEntity(recordId, requestContext);

		//校验验证码
		entity.verify(smsCode);
		
		// 修改校验状态
		entity.setStatus(ValidateStatus.VERIFIED);
		// 保存校验记录
		Dao dao=daoFactory.getDao(requestContext,MyBatisSmsValicodeDao.class);
		dao.save(entity);
	}
	
	/**
	 * 校验短信验证码 （只验证不保存）
	 * @param smsCode
	 * @param recordId
	 * @param requestContext
	 * @param flag 用于方法重载
	 * @throws DaoAppException 
	 * @throws BlAppException 
	 */
	public void verify(String smsCode,Integer recordId,RequestContext requestContext,boolean flag) throws DaoAppException, BlAppException{
		
		//获取记录实体
		SmsValidateCodeEntity entity=this.getSmsValidateCodeEntity(recordId, requestContext);

		//校验验证码
		entity.verify(smsCode);
	}
	
	/**
	 * 获取短信验证码记录状态
	 * @param smsCode
	 * @param recordId
	 * @param requestContext
	 * @return
	 * @throws DaoAppException
	 * @throws BlAppException
	 */
	public ValidateStatus getStatus(String smsCode,Integer recordId,RequestContext requestContext) throws DaoAppException, BlAppException{
		
		//获取记录实体
		SmsValidateCodeEntity entity=this.getSmsValidateCodeEntity(recordId, requestContext);
		
		return entity.getStatus();
	}
	
	private SmsValidateCodeEntity getSmsValidateCodeEntity(Integer recordId,RequestContext requestContext) throws DaoAppException, BlAppException{
		if(recordId==null){
			throw new BlAppException(
					CodeTable.BL_SMSVALICODE_WRONGCODE.getValue()
					,CodeTable.BL_SMSVALICODE_WRONGCODE.getDesc());
		}
		//获取smsdao
		Dao dao=daoFactory.getDao(requestContext,MyBatisSmsValicodeDao.class);
		//获取校验记录实体
		IdDto idDto = null;
		idDto = new IdDto();
		idDto.setId(recordId);

		SmsValidateCodeEntity entity = null;
		entity = (SmsValidateCodeEntity) dao.query(idDto, false);
		if(entity==null){
			throw new BlAppException(
					CodeTable.BL_SMSVALICODE_WRONGCODE.getValue()
					,CodeTable.BL_SMSVALICODE_WRONGCODE.getDesc());
		}
		
		return entity;
	}
}
