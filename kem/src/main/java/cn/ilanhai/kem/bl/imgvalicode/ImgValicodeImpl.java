package cn.ilanhai.kem.bl.imgvalicode;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import javax.imageio.ImageIO;

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
import cn.ilanhai.kem.bl.BaseBl;
import cn.ilanhai.kem.common.CodeTable;
import cn.ilanhai.kem.common.Constant;
import cn.ilanhai.kem.dao.imgvalicode.ImgValicodeDao;
import cn.ilanhai.kem.domain.IdDto;
import cn.ilanhai.kem.domain.enums.UserType;
import cn.ilanhai.kem.domain.enums.ValidateStatus;
import cn.ilanhai.kem.domain.imgvalicode.StatusDto;
import cn.ilanhai.kem.domain.imgvalicode.ImgVailcodeDto;
import cn.ilanhai.kem.domain.imgvalicode.ImgVailcodeEntity;
import cn.ilanhai.kem.domain.imgvalicode.ImgValicodeResponseDto;
import cn.ilanhai.kem.domain.imgvalicode.ImgValicodeValiDto;
import cn.ilanhai.kem.util.ValicodeHepler;
import cn.ilanhai.kem.util.ValicodePicture;

/**
 * 验证码业务逻辑实现
 * 
 * @author he
 *
 */
@Component("imgvalicode")
public class ImgValicodeImpl extends BaseBl implements ImgValicode {
	private static Logger logger = Logger.getLogger(ImgValicodeImpl.class);
	private final String sessionKey = "img_code";
	private ImgValicodeManager imgValicodeManager = new ImgValicodeManager(this.daoProxyFactory);
	
	@InterfaceDocAnnotation(methodVersion = "1.0.0")
	@Transactional(rollbackFor = { AppException.class, Exception.class,
			Throwable.class }, propagation = Propagation.REQUIRED)
	public Entity generate(RequestContext context) throws BlAppException, DaoAppException {
		logger.info("构建验证码");
		String vailcodeDto = null;
		ImgVailcodeEntity entity = null;
		String code = null;
		StatusDto statusDto = null;
		Calendar rightNow = null;
		Dao dao = null;
		try {
			// 获取入参
			vailcodeDto = context.getDomain(String.class);
			// 校验入参对象不可为空
//			this.valiPara(vailcodeDto);
			// 校验模块代码不可为空
			this.valiParaItemStrNullOrEmpty(vailcodeDto, "模块代码");
			// 获取用户信息
			String userId = (String) context.getSession().getParameter(Constant.KEY_SESSION_USERID);
			UserType userType = context.getSession().getParameter(Constant.KEY_SESSION_USERTYPE, UserType.class);
			// 获取流程ID
			String workId = (Integer) context.getSession().getParameter(vailcodeDto + sessionKey) + "";

			code = ValicodeHepler.generate(VAILCODE_COUNT);
			rightNow = Calendar.getInstance();
			rightNow.add(Calendar.MINUTE, ImgValicode.VAILCODE_EXPIRED);
			statusDto = new StatusDto();
			statusDto.setStatus(ValidateStatus.NOT_VALIDATE);
			// save
			entity = new ImgVailcodeEntity();
			Date currentTime = new Date();
			entity.setCreatetime(new java.sql.Date(currentTime.getTime()));

			// 过期时间为创建时间之后五分钟
			rightNow = Calendar.getInstance();
			rightNow.setTime(currentTime);
			rightNow.add(Calendar.MINUTE, ImgValicode.VAILCODE_EXPIRED);
			entity.setDeadline(new java.sql.Date(rightNow.getTime().getTime()));

			entity.setIdentityCode(code);
			entity.setModuleCode(vailcodeDto);
			entity.setStatus(ValidateStatus.NOT_VALIDATE);
			entity.setUserId(userId);
			entity.setUserType(userType);
			entity.setWorkId(workId);
			dao = this.daoProxyFactory.getDao(context,ImgValicodeDao.class);
			this.valiDaoIsNull(dao, "验证码");
			logger.info("验证码:"+code);
			int val = dao.save(entity);
			logger.info("val:"+val);
			this.valiSaveDomain(val, "验证码");
			// 向session中写入workid
			logger.info("id:"+entity.getId());
			
			
			context.getSession().setParameter(vailcodeDto + sessionKey, entity.getId());
			logger.info("put session:"+entity.getId());
			ImgValicodeResponseDto imgValicodeResponseDto = new ImgValicodeResponseDto();
			imgValicodeResponseDto.setImgCodeId(entity.getId());
			logger.info("getBase64FromImage(code) 前");
			String imgString = getBase64FromImage(code);
			logger.info("imgString:"+imgString);
			imgValicodeResponseDto.setImgCode(imgString);
			logger.info("getBase64FromImage(code) 后");
			return imgValicodeResponseDto;
		} catch (BlAppException e) {
			logger.error(e);
			throw e;
		} catch (Exception e) {
			logger.error(e);
			throw new BlAppException(CodeTable.BL_UNHANDLED_EXCEPTION.getValue(),
					CodeTable.BL_UNHANDLED_EXCEPTION.getDesc(), e);
		}

	}

	private String getBase64FromImage(String code) throws BlAppException, IOException {
		BufferedImage image = null;
		ByteArrayOutputStream os = null;
		CodeTable ct;
		try {
			image = ValicodePicture.createPicture(60, 160, 100, code);
			logger.info("create image success");
			os = new ByteArrayOutputStream();
			ImageIO.write(image, "png", os);
			logger.info("write image success");
			String base64String = org.apache.commons.codec.binary.Base64.encodeBase64String(os.toByteArray());
			logger.info("base64String:"+base64String);
			return String.format("data:image/png;base64,%s",
					base64String);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			ct = CodeTable.BL_IMGVALICODE_CREATE_IMAGER_ERROR;
			throw new BlAppException(ct.getValue(), ct.getDesc());
		} finally {
			if (os != null)
				os.close();
			if (image != null)
				image.flush();

		}

	}
	
	@InterfaceDocAnnotation(methodVersion = "1.0.0")
	@Transactional(rollbackFor = { AppException.class, Exception.class,
			Throwable.class }, propagation = Propagation.REQUIRED)
	public boolean vali(RequestContext context) throws BlAppException, DaoAppException {
		ImgValicodeValiDto request = null;
		CodeTable ct;
		ImgVailcodeEntity entity = null;
		Dao dao = null;
		IdDto idDto = null;
		int val = -1;
		try {
			// 处理传入参数
			request = context.getDomain(ImgValicodeValiDto.class);
			// 校验入参是否为空
			this.valiPara(request);
			// 获取workid
			// Integer workid =
			// context.getSession().getParameter(request.getModuleCode() +
			// sessionKey, Integer.class);
			// idDto = new IdDto();
			// idDto.setId(workid);
			// dao = this.daoProxyFactory.getDao(context);
			// this.valiDaoIsNull(dao, "验证码");
			// // 获取验证码记录
			// entity = (ImgVailcodeEntity) dao.query(idDto, false);
			// this.valiDomainIsNull(entity, "验证码");
			// // 校验是否已超时
			// if ((new Date()).after(entity.getDeadline())) {;
			// this.valiSaveDomain(val, "验证码");
			// }
			// // 校验是否已验证过
			// if (entity.getStatus().getValue() ==
			// ValidateStatus.VERIFIED.getValue()) {
			// ct = CodeTable.BL_IMGVALICODE_EXPIRE;
			// throw new BlAppException(ct.getValue(), ct.getDesc());
			// }
			// // 校验验证码是否匹配
			// if (!entity.getIdentityCode().equals(request.getImgCode())) {
			// throw new
			// BlAppException(CodeTable.BL_IMGVALICODE_ERROR.getValue(),
			// CodeTable.BL_IMGVALICODE_ERROR.getDesc());
			// }
			// 校验是否有待验证的短信验证码流程
			Integer recordId = context.getSession().getParameter(request.getModuleCode() + sessionKey, Integer.class);
			if (recordId == null) {
				throw new BlAppException(CodeTable.BL_SMSVALICODE_WRONGCODE.getValue(), "无效的验证码");
			}
			// 校验图形验证码
			imgValicodeManager.verify(request.getImgCode(), recordId, context,false);
			return true;
		} catch (BlAppException e) {
			throw e;
		} catch (Exception e) {
			ct = CodeTable.BL_UNHANDLED_EXCEPTION;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		}
	}
	
	@InterfaceDocAnnotation(methodVersion = "1.0.0")
	@Transactional(rollbackFor = { AppException.class, Exception.class,
			Throwable.class }, propagation = Propagation.REQUIRED)
	public Entity status(RequestContext context) throws BlAppException, DaoAppException {
		StatusDto statusDto = null;
		IdDto idDto = null;
		CodeTable ct;
		ImgVailcodeEntity entity = null;
		Dao dao = null;
		try {
			idDto = context.getDomain(IdDto.class);
			this.valiPara(idDto);
			dao = this.daoProxyFactory.getDao(context,ImgValicodeDao.class);
			this.valiDaoIsNull(dao, "验证码");
			entity = (ImgVailcodeEntity) dao.query(idDto, false);
			this.valiDomainIsNull(entity, "验证码");
			statusDto = new StatusDto();
			statusDto.setStatus(entity.getStatus());
			return statusDto;
		} catch (BlAppException e) {
			throw e;
		} catch (Exception e) {
			ct = CodeTable.BL_UNHANDLED_EXCEPTION;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		}
	}

}
