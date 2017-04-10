
package cn.ilanhai.kem.bl.userImg.impl;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.ilanhai.framework.InterfaceUtil.InterfaceDocAnnotation;
import cn.ilanhai.framework.app.RequestContext;
import cn.ilanhai.framework.app.dao.Dao;
import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.exception.AppException;
import cn.ilanhai.framework.common.exception.BlAppException;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.framework.common.session.enums.SessionStateType;
import cn.ilanhai.framework.uitl.FastJson;
import cn.ilanhai.framework.uitl.Str;
import cn.ilanhai.kem.bl.BaseBl;
import cn.ilanhai.kem.bl.material.MaterialManager;
import cn.ilanhai.kem.bl.userImg.UserImg;
import cn.ilanhai.kem.common.CodeTable;
import cn.ilanhai.kem.common.Constant;
import cn.ilanhai.kem.dao.userImg.MybatisUserImgDao;
import cn.ilanhai.kem.domain.CountDto;
import cn.ilanhai.kem.domain.IdEntity;
import cn.ilanhai.kem.domain.enums.MaterialInfoType;
import cn.ilanhai.kem.domain.enums.UserResourceType;
import cn.ilanhai.kem.domain.material.MaterialEntity;
import cn.ilanhai.kem.domain.material.MaterialImgEntity;
import cn.ilanhai.kem.domain.userImg.DeleteUserImgDataDto;
import cn.ilanhai.kem.domain.userImg.DeleteUserImgDataEntity;
import cn.ilanhai.kem.domain.userImg.DeleteUserImgRequestDto;
import cn.ilanhai.kem.domain.userImg.SearchUserImgRequestDto;
import cn.ilanhai.kem.domain.userImg.SearchUserImgRequestEntity;
import cn.ilanhai.kem.domain.userImg.SearchUserImgResponseDto;
import cn.ilanhai.kem.domain.userImg.UserImgEntity;
import cn.ilanhai.kem.domain.userImg.UserImgUploadDto;
import cn.ilanhai.kem.domain.userImg.UserImgUploadEntity;
import cn.ilanhai.kem.domain.userImg.UserImgUploadResponseDto;
import cn.ilanhai.kem.keyfac.KeyFactory;

@Component("userImg")
public class UserImgImpl extends BaseBl implements UserImg {
	private static String desc = "用户图片";
	private Logger logger = Logger.getLogger(UserImgImpl.class);

	@Override
	@InterfaceDocAnnotation(methodVersion = "1.0.1")
	@Transactional(rollbackFor = { AppException.class, Exception.class,
			Throwable.class }, propagation = Propagation.REQUIRED)
	public Entity search(RequestContext context) throws BlAppException, DaoAppException {
		logger.info("进入userImg\\search方法");
		CodeTable ct;
		try {
			// 获取入参
			SearchUserImgRequestDto request = context.getDomain(SearchUserImgRequestDto.class);
			logger.info(FastJson.bean2Json(request));
			valiPara(request);
			valiParaNotNull(request.getPageSize(), "查询条数");
			valiParaNotNull(request.getStartCount(), "开始条数");
			// 获取数据了连接资源
			Dao dao = this.daoProxyFactory.getDao(context, MybatisUserImgDao.class);
			valiDaoIsNull(dao, desc);

			SearchUserImgRequestEntity requestEntity = new SearchUserImgRequestEntity();
			requestEntity.buildSearchUserImgRequestEntity(request);

			if (context.getSession().getSessionState().getSessionStateType()
					.equals(SessionStateType.FRONT_USER_LOGINED_STATE)) {
				requestEntity.setUserId(getSessionUserId(context));
			} else {
				requestEntity.setUserId("0");
			}

			requestEntity.setServiceName(this.getValue(context, "serviceName"));
			Iterator<Entity> resultDatas = dao.query(requestEntity);
			logger.info("查询记录总条数");
			CountDto count = (CountDto) dao.query(requestEntity, false);
			// 返回结果
			SearchUserImgResponseDto result = new SearchUserImgResponseDto();
			List<Entity> datas = transformation(resultDatas);
			// 数据集
			result.setList(datas);
			result.setPageSize(request.getPageSize());
			result.setStartCount(request.getStartCount());
			result.setTotalCount(count.getCount());
			return result;
		} catch (BlAppException e) {
			throw e;
		} catch (DaoAppException e) {
			ct = CodeTable.BL_DATA_ERROR;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		} catch (Exception e) {
			ct = CodeTable.BL_UNHANDLED_EXCEPTION;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		}
	}

	@Override
	@InterfaceDocAnnotation(methodVersion = "1.0.0")
	@Transactional(rollbackFor = { AppException.class, Exception.class,
			Throwable.class }, propagation = Propagation.REQUIRED)
	public Entity searchmyselfimg(RequestContext context) throws BlAppException, DaoAppException {
		logger.info("进入userImg\\searchmyselfimg方法");
		CodeTable ct;
		try {
			this.checkFrontUserLogined(context);
			// 获取入参
			SearchUserImgRequestDto request = context.getDomain(SearchUserImgRequestDto.class);
			logger.info(FastJson.bean2Json(request));
			valiPara(request);
			valiParaNotNull(request.getPageSize(), "查询条数");
			valiParaNotNull(request.getStartCount(), "开始条数");
			// 获取数据了连接资源
			Dao dao = this.daoProxyFactory.getDao(context, MybatisUserImgDao.class);
			valiDaoIsNull(dao, desc);

			SearchUserImgRequestEntity requestEntity = new SearchUserImgRequestEntity();
			requestEntity.buildSearchUserImgRequestEntity(request);
			requestEntity.setUserId(getSessionUserId(context));
			requestEntity.setServiceName(this.getValue(context, "serviceName"));
			Iterator<Entity> resultDatas = dao.query(requestEntity);
			logger.info("查询记录总条数");
			CountDto count = (CountDto) dao.query(requestEntity, false);
			// 返回结果
			SearchUserImgResponseDto result = new SearchUserImgResponseDto();
			List<Entity> datas = transformation(resultDatas);
			// 数据集
			result.setList(datas);
			result.setPageSize(request.getPageSize());
			result.setStartCount(request.getStartCount());
			result.setTotalCount(count.getCount());
			return result;
		} catch (BlAppException e) {
			throw e;
		} catch (DaoAppException e) {
			ct = CodeTable.BL_DATA_ERROR;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		} catch (Exception e) {
			ct = CodeTable.BL_UNHANDLED_EXCEPTION;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		}
	}

	@Override
	@InterfaceDocAnnotation(methodVersion = "1.0.0")
	@Transactional(rollbackFor = { AppException.class, Exception.class,
			Throwable.class }, propagation = Propagation.REQUIRED)
	public void delete(RequestContext context) throws BlAppException, DaoAppException {
		CodeTable ct;
		boolean isBackUser = false;
		try {
			if (context.getSession().getSessionState().getSessionStateType()
					.equals(SessionStateType.BACK_USER_LOGINED_STATE)) {
				isBackUser = true;
			} else if (context.getSession().getSessionState().getSessionStateType()
					.equals(SessionStateType.FRONT_USER_LOGINED_STATE)) {
			} else {
				ct = CodeTable.BL_COMMON_USER_NOT_LOGINED;
				throw new BlAppException(ct.getValue(), ct.getDesc());
			}

			// 获取入参
			DeleteUserImgRequestDto request = context.getDomain(DeleteUserImgRequestDto.class);
			valiPara(request);
			List<DeleteUserImgDataDto> data = request.getData();
			// 获取数据了连接资源
			Dao dao = this.daoProxyFactory.getDao(context, MybatisUserImgDao.class);
			valiDaoIsNull(dao, desc);
			IdEntity<String> id = new IdEntity<String>();
			// 更新数据
			for (DeleteUserImgDataDto deleteUserImgDataDto : data) {
				DeleteUserImgDataEntity entity = new DeleteUserImgDataEntity();
				entity.setImgId(deleteUserImgDataDto.getImgId());
				if (!isBackUser) {
					id.setId(deleteUserImgDataDto.getImgId());
					UserImgEntity userImgEntity = (UserImgEntity) dao.query(id, false);
					if (userImgEntity == null) {
						throw new BlAppException(-1, "请检查图片是否存在!");
					}
					this.checkCurrentUser(context, userImgEntity.getUserId());
				}
				dao.delete(entity);
				MaterialManager.deleteMaterialImg(context, deleteUserImgDataDto.getImgId());
			}
		} catch (BlAppException e) {
			throw e;
		} catch (DaoAppException e) {
			ct = CodeTable.BL_DATA_ERROR;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		} catch (Exception e) {
			ct = CodeTable.BL_UNHANDLED_EXCEPTION;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		}
	}

	@Override
	@InterfaceDocAnnotation(methodVersion = "1.1.1.0")
	@Transactional(rollbackFor = { AppException.class, Exception.class,
			Throwable.class }, propagation = Propagation.REQUIRED)
	public Entity upload(RequestContext context) throws BlAppException, DaoAppException {
		logger.info("进入userImg\\upload方法");
		CodeTable ct;
		UserImgUploadResponseDto response = null;
		String userId;
		boolean isBackUser = false;
		String manuscriptId = null;
		String imgId = null;
		try {

			// 获取入参
			UserImgUploadDto request = context.getDomain(UserImgUploadDto.class);
			valiPara(request);
			if (!Str.isNullOrEmpty(request.getImgMd5Search())) {
				return this.search(context);
			}
			imgId = request.getImgId();
			manuscriptId = request.getManuscriptId();
			logger.info("获取入参:" + request.toString());
			if (context.getSession().getSessionState().getSessionStateType()
					.equals(SessionStateType.BACK_USER_LOGINED_STATE)) {
				isBackUser = true;
			} else if (context.getSession().getSessionState().getSessionStateType()
					.equals(SessionStateType.FRONT_USER_LOGINED_STATE)) {
			} else {
				ct = CodeTable.BL_COMMON_USER_NOT_LOGINED;
				throw new BlAppException(ct.getValue(), ct.getDesc());
			}
			logger.info("验证登录成功");
			// 获取数据了连接资源
			Dao dao = this.daoProxyFactory.getDao(context, MybatisUserImgDao.class);
			valiDaoIsNull(dao, desc);
			userId = context.getSession().getParameter(Constant.KEY_SESSION_USERID, String.class);
			if (isBackUser) {
				userId = "0";
			}
			logger.info("获取userId:" + userId);
			for (int i = 0; i < request.getImgName().size(); i++) {
				// 封装数据
				UserImgUploadEntity userImgUploadEntity = new UserImgUploadEntity();
				logger.info("图片名称:" + request.getImgName().get(i));
				userImgUploadEntity.setImgName(request.getImgName().get(i));
				logger.info("图片路径:" + request.getImgPath().get(i));
				userImgUploadEntity.setImgPath(request.getImgPath().get(i));
				logger.info("图片md5:" + request.getImgMd5().get(i));
				userImgUploadEntity.setImgMd5(request.getImgMd5().get(i));
				userImgUploadEntity.setType(UserResourceType.getEnum(request.getType()).getValue());
				userImgUploadEntity.setUserId(userId);
				userImgUploadEntity
						.setImgId(Str.isNullOrEmpty(imgId) ? KeyFactory.newKey(KeyFactory.KEY_RESOURCE_IMG) : imgId);
				userImgUploadEntity.setCreatetime(new Date());
				logger.info("保存数据");
				int val = dao.save(userImgUploadEntity);
				// 校验保存
				this.valiSaveDomain(val, desc);
				String ImgId = userImgUploadEntity.getImgId();
				if (isBackUser && Str.isNullOrEmpty(imgId) && !Str.isNullOrEmpty(request.getMaterialId())) {
					MaterialEntity materialEntity = MaterialManager.queryMaterialType(context, request.getMaterialId());
					this.valiDomainIsNull(materialEntity, "素材分类编号错误", false);
					MaterialImgEntity materialImgEntity = new MaterialImgEntity();
					materialImgEntity.setImg_id(ImgId);
					materialImgEntity.setMaterial_id(request.getMaterialId());
					MaterialManager.saveMaterialImg(context, materialImgEntity);

				}
				// 添加新增的素材
				if (!Str.isNullOrEmpty(manuscriptId) && Str.isNullOrEmpty(imgId)) {
					logger.info("添加素材[" + ImgId + "]到模板[" + manuscriptId + "]");
					MaterialManager.saveMaterialInfo(context, MaterialInfoType.manuscriptid, manuscriptId, ImgId);
				}
			}
			// 添加重复的素材
			if (!Str.isNullOrEmpty(manuscriptId) && Str.isNullOrEmpty(imgId)) {
				for (String imgIds : request.getImgIds()) {
					if (Str.isNullOrEmpty(imgIds)) {
						continue;
					}
					logger.info("添加素材[" + imgIds + "]到模板[" + manuscriptId + "]");
					MaterialManager.saveMaterialInfo(context, MaterialInfoType.manuscriptid, manuscriptId, imgIds);
				}
			}
			response = new UserImgUploadResponseDto();
			response.setAbsolutePath(request.getAbsolutePath());
		} catch (BlAppException e) {
			KeyFactory.inspects();
			throw e;
		} catch (DaoAppException e) {
			KeyFactory.inspects();
			ct = CodeTable.BL_DATA_ERROR;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		} catch (Exception e) {
			KeyFactory.inspects();
			ct = CodeTable.BL_UNHANDLED_EXCEPTION;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		}
		return response;
	}

}
