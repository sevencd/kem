package cn.ilanhai.kem.bl.sms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.ilanhai.framework.InterfaceUtil.InterfaceDocAnnotation;
import cn.ilanhai.framework.app.RequestContext;
import cn.ilanhai.framework.app.dao.Dao;
import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.consts.ModuleCode;
import cn.ilanhai.framework.common.exception.AppException;
import cn.ilanhai.framework.common.exception.BlAppException;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.framework.common.exception.SessionContainerException;
import cn.ilanhai.framework.common.mq.MQManager;
import cn.ilanhai.framework.uitl.ExpressionMatchUtil;
import cn.ilanhai.kem.bl.BaseBl;
import cn.ilanhai.kem.bl.contacts.ContactManager;
import cn.ilanhai.kem.common.CodeTable;
import cn.ilanhai.kem.common.Constant;
import cn.ilanhai.kem.dao.smsvalicode.MyBatisSmsValicodeDao;
import cn.ilanhai.kem.domain.IdDto;
import cn.ilanhai.kem.domain.enums.UserType;
import cn.ilanhai.kem.domain.enums.ValidateStatus;
import cn.ilanhai.kem.domain.smsvalicode.SmsValidCodeGenerateRequestDto;
import cn.ilanhai.kem.domain.smsvalicode.SmsValidateCodeEntity;
import cn.ilanhai.kem.domain.smsvalicode.SmsValidateCodeValiRequestDto;
import cn.ilanhai.kem.queue.QueueManager;
import cn.ilanhai.kem.queue.msg.SmsMsg;
import cn.ilanhai.kem.sms.protocol.tosms.TosmsSmsInfo;
import cn.ilanhai.kem.util.ValicodeHepler;

@Component("smsvalicode")
public class SmsValicodeImpl extends BaseBl implements SmsValicode {

	private final String sessionKey = "sms_code";
	private SmsValicodeManager smsValicodeManager = new SmsValicodeManager(
			this.daoProxyFactory);

	// 生成验证码
	@InterfaceDocAnnotation(methodVersion = "1.0.0")
	@Transactional(rollbackFor = { AppException.class, Exception.class,
			Throwable.class }, propagation = Propagation.REQUIRED)
	public Entity generate(RequestContext context)
			throws SessionContainerException, BlAppException, DaoAppException {
		System.out.println("enter sms generate");
		CodeTable ct;
		// 获取入参
		SmsValidCodeGenerateRequestDto request = context
				.getDomain(SmsValidCodeGenerateRequestDto.class);
		if (request == null) {
			ct = CodeTable.BL_COMMON_PARAMETER_NOT_NULL;
			throw new BlAppException(ct.getValue(), ct.getDesc());
		}
		if (!ExpressionMatchUtil.isPhoneNo(request.getPhoneNo())) {
			throw new BlAppException();
		}
		// 校验ModuleCode不为空
		if (request.getModuleCode() == null
				|| request.getModuleCode().equals("")) {
			throw new BlAppException(
					CodeTable.BL_SMSVALICODE_REQUESTDTO.getValue(),
					"非法流程验证码请求。");
		}
		// 创建验证码
		// 获取用户信息
		String userId = (String) context.getSession().getParameter(
				Constant.KEY_SESSION_USERID);
		UserType userType = context.getSession().getParameter(
				Constant.KEY_SESSION_USERTYPE, UserType.class);
		// 获取流程ID
		String workId = (String) context.getSession().getParameter(
				request.getModuleCode());
		String smsCode = ValicodeHepler
				.generateOnlyNum(SmsValicode.VAILCODE_COUNT);
		SmsValidateCodeEntity entity = new SmsValidateCodeEntity();

		entity.setCreatetime(new Date());
		// 过期时间为创建时间之后五分钟
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(entity.getCreatetime());
		rightNow.add(Calendar.MINUTE, SmsValicode.VAILCODE_EXPIRED);
		entity.setDeadline(rightNow.getTime());

		entity.setIdentityCode(smsCode);
		entity.setModuleCode(request.getModuleCode());
		entity.setStatus(ValidateStatus.NOT_VALIDATE);
		entity.setUserId(userId);
		entity.setUserType(userType);
		entity.setWorkId(workId);

		// 存储验证码
		Dao dao = this.daoProxyFactory.getDao(context,
				MyBatisSmsValicodeDao.class);
		dao.save(entity);

		// 将流程ID存入session
		context.getSession().setParameter(
				request.getModuleCode() + sessionKey + request.getPhoneNo(),
				entity.getId());
		// 发送验证码
		String smsContent = "【集客云销】亲爱的集客云销用户，您的验证码是："
				+ entity.getIdentityCode() + "，有效时间为"
				+ SmsValicode.VAILCODE_EXPIRED + "分钟";
		String url = this.getValue(context, "smsUrl");
		this.sendSMS(request.getPhoneNo(), smsContent, url);

		IdDto response = new IdDto();
		response.setId(entity.getId());
		return response;
	}

	@InterfaceDocAnnotation(methodVersion = "1.0.0")
	@Transactional(rollbackFor = { AppException.class, Exception.class,
			Throwable.class }, propagation = Propagation.REQUIRED)
	public boolean vali(RequestContext context)
			throws SessionContainerException, BlAppException, DaoAppException {
		CodeTable ct;
		// 获取入参
		SmsValidateCodeValiRequestDto request = context
				.getDomain(SmsValidateCodeValiRequestDto.class);
		if (request == null) {
			ct = CodeTable.BL_COMMON_PARAMETER_NOT_NULL;
			throw new BlAppException(ct.getValue(), ct.getDesc());
		}

		if (!ExpressionMatchUtil.isPhoneNo(request.getPhoneNo())) {
			throw new BlAppException(
					CodeTable.BL_SMSVALICODE_REQUESTDTO.getValue(), "电话号码不正确");
		}
		// 模块代码合法性
		if (!ModuleCode.contains(request.getModuleCode())) {
			throw new BlAppException(
					CodeTable.BL_SMSVALICODE_REQUESTDTO.getValue(), "非法请求");
		}
		// 短信验证码合法性
		if (request.getSmsCode() == null || request.getSmsCode().isEmpty()) {
			throw new BlAppException(
					CodeTable.BL_SMSVALICODE_REQUESTDTO.getValue(), "请输入短信验证码");
		}
		// 校验是否有待验证的短信验证码流程
		Integer recordId = context.getSession().getParameter(
				request.getModuleCode() + sessionKey + request.getPhoneNo(),
				Integer.class);
		if (recordId == null) {
			throw new BlAppException(
					CodeTable.BL_SMSVALICODE_WRONGCODE.getValue(), "无效的验证码");
		}
		// 校验短信验证码
		smsValicodeManager.verify(request.getSmsCode(), recordId, context,
				false);

		return true;
	}

	@InterfaceDocAnnotation(methodVersion = "1.0.0")
	@Transactional(rollbackFor = { AppException.class, Exception.class,
			Throwable.class }, propagation = Propagation.REQUIRED)
	private void sendSMS(String phoneNo, String smsContent, String url) {
		// SendSMSHelper smsHelper = new SendSMSHelper(phoneNo, smsContent,
		// url);
		// Thread smsThread = new Thread(smsHelper);
		// smsThread.start();
		List<String> phones = null;
		phones = new ArrayList<String>();
		phones.add(phoneNo);
		TosmsSmsInfo tosmsSmsInfo = new TosmsSmsInfo();
		tosmsSmsInfo.setContent(smsContent);
		tosmsSmsInfo.setPhone(phones);
		// tosmsSmsInfo.setSendTime(sendDate);
		SmsMsg<TosmsSmsInfo> msg = null;
		msg = new SmsMsg<TosmsSmsInfo>();
		msg.setMsgContent(tosmsSmsInfo);
		try {
			QueueManager.getInstance().put(msg);
		} catch (BlAppException e) {
			e.printStackTrace();
		}

	}

}
