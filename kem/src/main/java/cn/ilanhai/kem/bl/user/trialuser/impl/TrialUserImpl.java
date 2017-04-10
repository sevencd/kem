package cn.ilanhai.kem.bl.user.trialuser.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.ilanhai.framework.InterfaceUtil.InterfaceDocAnnotation;
import cn.ilanhai.framework.app.RequestContext;
import cn.ilanhai.framework.app.dao.Dao;
import cn.ilanhai.framework.common.exception.BlAppException;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.framework.common.exception.JMSAppException;
import cn.ilanhai.framework.uitl.ExpressionMatchUtil;
import cn.ilanhai.kem.bl.BaseBl;
import cn.ilanhai.kem.bl.smsright.SmsRightManager;
import cn.ilanhai.kem.bl.user.frontuser.FrontuserManger;
import cn.ilanhai.kem.bl.user.trialuser.TrialUser;
import cn.ilanhai.kem.common.CodeTable;
import cn.ilanhai.kem.dao.user.backuser.BackuserDao;
import cn.ilanhai.kem.dao.user.trialuser.TrialUserDao;
import cn.ilanhai.kem.domain.enums.UserInfoType;
import cn.ilanhai.kem.domain.enums.UserStatus;
import cn.ilanhai.kem.domain.enums.UserType;
import cn.ilanhai.kem.domain.smsright.SmsInfoEntity;
import cn.ilanhai.kem.domain.smsright.dto.SmsInfoDto;
import cn.ilanhai.kem.domain.smsright.dto.SmsSendDtoRequest;
import cn.ilanhai.kem.domain.user.frontuser.CompanyUserDto;
import cn.ilanhai.kem.domain.user.frontuser.FrontUserInfoEntity;
import cn.ilanhai.kem.domain.user.trialuser.TrialUserDto;
import cn.ilanhai.kem.event.DomainEventManager;
import cn.ilanhai.kem.event.args.FrontUserRegistEvent;
import cn.ilanhai.kem.keyfac.KeyFactory;
import cn.ilanhai.kem.sms.protocol.SmsInfo;
import cn.ilanhai.kem.sms.protocol.tosms.ToSmsInfo;
import cn.ilanhai.kem.util.MD5Util;
import cn.ilanhai.kem.util.TimeUtil;

/**
 * 申请试用实现类
 * 
 * @author csz
 * @time 2017-03-20 14:54
 */
@Component("TrialUser")
public class TrialUserImpl extends BaseBl implements TrialUser {
	public TrialUserImpl(){
		System.out.println("初始化TrialUserImpl");
	}
	@Override
	@InterfaceDocAnnotation(methodVersion = "1.4.0")
	@Transactional(rollbackFor = { Throwable.class }, propagation = Propagation.REQUIRED)
	public int saveTrialUser(RequestContext context) throws BlAppException {
		// 获取入参
		TrialUserDto request = context.getDomain(TrialUserDto.class);
		// 验证手机号，公司名称，职位
		this.valiParaItemStringNull(request.getCompany(), "公司");
		// 电话号码合法
		String phoneNo=request.getPhoneNo();
		if (!ExpressionMatchUtil.isPhoneNo(phoneNo)) {
			throw new BlAppException(-1, "电话号码错误");
		}
		// 获取userId
		request.setUserId(KeyFactory.newKey(KeyFactory.KEY_USER));
		request.setStatus(UserStatus.Trial.getValue());
		request.setLoginPwd(MD5Util.getEncryptedPwd("123456"));
		
		try {
			//验证手机号是否已申请试用
			if (FrontuserManger.isExist(context, request.getPhoneNo())) {
				throw new BlAppException(CodeTable.BL_COMPANYUSER_PHONE_REPEAT.getValue(),
						CodeTable.BL_COMPANYUSER_PHONE_REPEAT.getDesc());
			}
			//设置登录账号
			request.setCompanyCode(KeyFactory.getInstance().newCode(request.getUserId()));
			// 获取数据连接资源
			Dao dao = this.daoProxyFactory.getDao(context, BackuserDao.class);
			valiDaoIsNull(dao, "添加用户");
			List<FrontUserInfoEntity> infos = new ArrayList<FrontUserInfoEntity>();
			FrontUserInfoEntity frontUserInfoEntity_name = new FrontUserInfoEntity();
			frontUserInfoEntity_name.setUserID(request.getUserId());
			frontUserInfoEntity_name.setInfoType(UserInfoType.NAME.getValue());
			frontUserInfoEntity_name.setInfo(request.getName());
			frontUserInfoEntity_name.setInfoState(UserStatus.ENABLE.getValue());
			infos.add(frontUserInfoEntity_name);
			FrontUserInfoEntity frontUserInfoEntity_company = new FrontUserInfoEntity();
			frontUserInfoEntity_company.setUserID(request.getUserId());
			frontUserInfoEntity_company.setInfoType(UserInfoType.COMPANY.getValue());
			frontUserInfoEntity_company.setInfo(request.getCompany());
			frontUserInfoEntity_company.setInfoState(UserStatus.ENABLE.getValue());
			infos.add(frontUserInfoEntity_company);
			FrontUserInfoEntity frontUserInfoEntity_phone = new FrontUserInfoEntity();
			frontUserInfoEntity_phone.setUserID(request.getUserId());
			frontUserInfoEntity_phone.setInfoType(UserInfoType.PHONE.getValue());
			frontUserInfoEntity_phone.setInfo(phoneNo);
			frontUserInfoEntity_phone.setInfoState(UserStatus.ENABLE.getValue());
			infos.add(frontUserInfoEntity_phone);
			request.setInfos(infos);
			// 返回结果
			int result = dao.save(request);
			try {
				//验证是否添加成功
				valiSaveDomain(result, "添加用户");
				//构建发送内容smsInfo
				String content="尊敬的用户，您的账号是"+request.getPhoneNo()+"  您的密码是123456"+"  请打开链接cloudmarkee.com输入账号密码登录";
				ToSmsInfo smsInfo=new ToSmsInfo();
				smsInfo.setContent(content);
				smsInfo.setPhone(request.getPhoneNo());
				//构建短信info和value
				List<SmsInfoDto> smsInfos=new ArrayList<SmsInfoDto>();
				SmsInfoDto info1=new SmsInfoDto();
				info1.setKey(SmsInfoEntity.KEY_CONTENT);
				info1.setContent(content);
				SmsInfoDto info2=new SmsInfoDto();
				info2.setKey(SmsInfoEntity.KEY_SENDTYPE);
				info2.setContent(String.valueOf(1));
				SmsInfoDto info4=new SmsInfoDto();
				info4.setKey(SmsInfoEntity.KEY_SENDTIME);
				info4.setContent(TimeUtil.format(new Date(), TimeUtil.time));
				smsInfos.add(info1);
				smsInfos.add(info2);
				smsInfos.add(info4);
				smsInfo.setList(smsInfos);
				//发送短信
				SmsRightManager.simpleSend(context, smsInfo, request.getUserId());
			} catch (BlAppException e) {
				throw e;
			}
			return result;
		} catch (DaoAppException e) {
			try {
				KeyFactory.inspects();
			} catch (DaoAppException e1) {
				throw new BlAppException(e.getErrorCode(), e.getErrorDesc(), e);
			}
			throw new BlAppException(e.getErrorCode(), e.getErrorDesc(), e);
		}
	}

}
