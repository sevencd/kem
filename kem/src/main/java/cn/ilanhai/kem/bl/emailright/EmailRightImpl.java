package cn.ilanhai.kem.bl.emailright;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
import cn.ilanhai.kem.bl.BLContextUtil;
import cn.ilanhai.kem.bl.BaseBl;
import cn.ilanhai.kem.bl.contacts.ContactManager;
import cn.ilanhai.kem.bl.customer.CustomerManager;
import cn.ilanhai.kem.common.CodeTable;
import cn.ilanhai.kem.dao.emailright.EmailRightDao;
import cn.ilanhai.kem.dao.emailright.SearchEmailDao;
import cn.ilanhai.kem.domain.CountDto;
import cn.ilanhai.kem.domain.contacts.group.CreateGroupRequest;
import cn.ilanhai.kem.domain.email.EmailContractEntity;
import cn.ilanhai.kem.domain.email.EmailGroupEntity;
import cn.ilanhai.kem.domain.email.MailEntity;
import cn.ilanhai.kem.domain.email.MailInfoEntity;
import cn.ilanhai.kem.domain.email.MailSendRequest;
import cn.ilanhai.kem.domain.email.QueryEmailContractRequest;
import cn.ilanhai.kem.domain.email.dto.AnalysisDataRecordDto;
import cn.ilanhai.kem.domain.email.dto.AnalysisDataReponse;
import cn.ilanhai.kem.domain.email.dto.AnalysisDataRequest;
import cn.ilanhai.kem.domain.email.dto.DeleteEmailDto;
import cn.ilanhai.kem.domain.email.dto.QueryEmailDto;
import cn.ilanhai.kem.domain.email.dto.QueryEmailResponseDto;
import cn.ilanhai.kem.domain.email.dto.SaveContractDto;
import cn.ilanhai.kem.domain.email.dto.SearchEmailDto;
import cn.ilanhai.kem.keyfac.KeyFactory;
import cn.ilanhai.kem.mail.protocol.sohu.address.StatdayListRequest;
import cn.ilanhai.kem.mail.protocol.sohu.address.StatdayListResponse;
import cn.ilanhai.kem.mail.protocol.sohu.address.StatdayListResponseRecord;
import cn.ilanhai.kem.mail.sohu.GeneralSendSohuMailConfig;
import cn.ilanhai.kem.mail.sohu.SohuMail;
import cn.ilanhai.kem.util.TimeUtil;
import cn.ilanhai.kem.domain.email.MailLoadDtoRequest;
import cn.ilanhai.kem.domain.email.MailSaveRequest;

import cn.ilanhai.kem.domain.email.QueryEmailContractResponse;
import cn.ilanhai.kem.domain.email.QueryEmailGroupDto;
import cn.ilanhai.kem.domain.email.QueryEmailGroupRequest;
import cn.ilanhai.kem.domain.email.QueryMailInfoDto;
import cn.ilanhai.kem.domain.email.QueryOneMailDto;
import cn.ilanhai.kem.domain.email.SaveContractRequest;
import cn.ilanhai.kem.domain.email.SaveAllCustomerRequest;

@Component("email")
public class EmailRightImpl extends BaseBl implements EmailRight {
	private Logger logger = Logger.getLogger(EmailRightImpl.class);

	/**
	 * 加载邮件
	 */
	@Override
	public Entity load(RequestContext context) throws BlAppException, DaoAppException {
		MailLoadDtoRequest request = null;
		CodeTable ct;
		try {
			// 校验前台用户是否已登录
			this.checkFrontUserLogined(context);
			// 获取请求值
			String tmp = context.getDomain(String.class);
			request = new MailLoadDtoRequest();
			request.setMailId(tmp);
			return EmailRightManager.load(context, request);
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
	@InterfaceDocAnnotation(methodVersion = "1.5.0")
	@Transactional(rollbackFor = { AppException.class, Exception.class,
			Throwable.class }, propagation = Propagation.REQUIRED)
	public Entity save(RequestContext context) throws BlAppException, DaoAppException {
		MailSaveRequest request = null;
		CodeTable ct;
		try {
			this.checkFrontUserLogined(context);
			request = context.getDomain(MailSaveRequest.class);
			String userId = this.getSessionUserId(context);
			return EmailRightManager.save(context, request, userId);
		} catch (BlAppException e) {
			KeyFactory.inspects();
			throw e;
		} catch (DaoAppException e) {
			ct = CodeTable.BL_DATA_ERROR;
			KeyFactory.inspects();
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		} catch (Exception e) {
			ct = CodeTable.BL_UNHANDLED_EXCEPTION;
			KeyFactory.inspects();
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		}
	}

	@Override
	@InterfaceDocAnnotation(methodVersion = "1.1.1.0")
	public Entity search(RequestContext context) throws BlAppException, DaoAppException {

		SearchEmailDto request;
		CodeTable ct;
		try {
			this.checkFrontUserLogined(context);
			request = context.getDomain(SearchEmailDto.class);
			valiPara(request);
			this.valiParaNotNull(request.getStatus(), "查询状态错误", false);
			this.valiParaItemNumBetween(1, 2, request.getStatus(), "查询类型错误", false);
			BLContextUtil.valiParaItemIntegerNull(request.getStartCount(), "开始数量不能为空");
			BLContextUtil.valiParaItemIntegerNull(request.getPageSize(), "查询数量不能为空");
			Dao dao = BLContextUtil.getDao(context, SearchEmailDao.class);

			QueryEmailDto query = new QueryEmailDto();
			query.setKeyword(request.getKeyword());
			query.setPageSize(request.getPageSize());
			query.setStartCount(request.getStartCount());
			query.setStatus(request.getStatus());
			query.setUserId(getSessionUserId(context));
			QueryEmailResponseDto queryContactsResponseDto = new QueryEmailResponseDto();
			queryContactsResponseDto.setList(dao.query(query));
			queryContactsResponseDto.setPageSize(request.getPageSize());
			queryContactsResponseDto.setStartCount(request.getStartCount());
			queryContactsResponseDto.setTotalCount(((CountDto) dao.query(query, false)).getCount());
			return queryContactsResponseDto;
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

	/**
	 * 保存
	 * 
	 * @param ctx
	 * @param req
	 * @return
	 * @throws BlAppException
	 * @throws DaoAppException
	 */

	@Override
	@InterfaceDocAnnotation(methodVersion = "1.5.0")
	@Transactional(rollbackFor = { AppException.class, Exception.class,
			Throwable.class }, propagation = Propagation.REQUIRED)
	public boolean send(RequestContext context) throws BlAppException, DaoAppException {

		MailSendRequest request = null;
		CodeTable ct;
		try {
			this.checkFrontUserLogined(context);
			String uesrId = this.getSessionUserId(context);
			request = context.getDomain(MailSendRequest.class);
			return EmailRightManager.send(context, request, uesrId);
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
	@InterfaceDocAnnotation(methodVersion = "1.2.0.0")
	public Entity loadcontrants(RequestContext context) throws BlAppException, DaoAppException {

		CodeTable ct;
		Dao dao = null;
		QueryEmailGroupRequest request;
		try {
			request = context.getDomain(QueryEmailGroupRequest.class);
			logger.info("入参：" + request);
			BLContextUtil.valiParaItemStrNullOrEmpty(request.getEmailId(), "邮件id");
			BLContextUtil.valiParaItemIntegerNull(request.getStartCount(), "开始数量不能为空");
			BLContextUtil.valiParaItemIntegerNull(request.getPageSize(), "查询数量不能为空");
			dao = BLContextUtil.getDao(context, EmailRightDao.class);
			BLContextUtil.valiDaoIsNull(dao, "联系人列表");
			QueryEmailContractResponse response = new QueryEmailContractResponse();
			response.setList(transformation(dao.query(request)));
			logger.info("查询联系人成功");
			response.setStartCount(request.getStartCount());
			response.setPageSize(request.getPageSize());
			response.setTotalCount(request.getCount());
			return response;
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
	@InterfaceDocAnnotation(methodVersion = "1.5.0")
	@Transactional(rollbackFor = { AppException.class, Exception.class,
			Throwable.class }, propagation = Propagation.REQUIRED)
	public boolean savecontrants(RequestContext context) throws BlAppException, DaoAppException {
		CodeTable ct;
		Dao dao = null;
		String userId = null;
		String groupId = null;
		CreateGroupRequest createGroupRequest = null;
		SaveContractDto request;
		SaveContractRequest saveRequest;
		try {
			this.checkFrontUserLogined(context);
			request = context.getDomain(SaveContractDto.class);
			logger.info("入参：" + request);
			BLContextUtil.valiPara(request);
			userId = BLContextUtil.getSessionUserId(context);
			if (request.getCustomerIds().size() <= 0) {
				throw new BlAppException(-1, "请添加客户");
			}
			//创建发送邮件的组
			createGroupRequest = new CreateGroupRequest();
			createGroupRequest.setGroupName(request.getEmailId());
			//1，手机联系人；2，邮箱联系人
			createGroupRequest.setType(2);
			groupId = ContactManager.createContactGroup(context, createGroupRequest, userId);
			logger.info("创建的群组为：" + groupId);
//			保存联系人进群组
			if (groupId == null) {
				throw new BlAppException(-2,"保存联系人失败");
			}
			boolean status = ContactManager.saveCustomerInGroup(context, groupId, request.getCustomerIds());
			if (!status) {
				throw new BlAppException(-1,"保存联系人失败");
			}
			//同步sendcloud
			ContactManager.synchronizeSendcloud(context, groupId, request.getCustomerIds(),  this.getValue(context, "groupAddress"));
			
			return EmailRightManager.saveGroupCustomer(context, userId, groupId, request.getEmailId());
			
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
	public void delete(RequestContext context) throws BlAppException, DaoAppException {
		DeleteEmailDto request;
		CodeTable ct;
		try {
			logger.info("进入删除");
			this.checkFrontUserLogined(context);
			request = context.getDomain(DeleteEmailDto.class);
			valiPara(request);
			request.setUserId(getSessionUserId(context));
			logger.info("删除:" + request);
			Dao dao = BLContextUtil.getDao(context, SearchEmailDao.class);
			dao.delete(request);
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
	@InterfaceDocAnnotation(methodVersion = "1.2.0.0")
	public Entity analysisdata(RequestContext context) throws BlAppException, DaoAppException {

		// 处理请求
		AnalysisDataRequest request = context.getDomain(AnalysisDataRequest.class);

		BLContextUtil.valiDomainIsNull(request, "邮件数据请求");
		// BLContextUtil.valiParaNotNull(request.getEmailId(), "邮件ID");

		// days为空时，开始及截止时间不可为空
		if (request.getDays() == null
				|| (request.getDays() != null && request.getStarttime() != null && request.getEndtime() != null)) {
			BLContextUtil.valiParaNotNull(request.getStarttime(), "开始时间");
			BLContextUtil.valiParaNotNull(request.getEndtime(), "截止时间");
			if (request.getEndtime().before(request.getStarttime())) {
				throw new BlAppException(-1, "截止时间不可以小于开始时间");
			}

			if (TimeUtil.getDays(request.getStarttime(), request.getEndtime()) > 30) {
				throw new BlAppException(-1, "时间跨度不可超过30天");
			}
		} else {
			BLContextUtil.valiParaItemNumBetween(0, 30, request.getDays(), "天数");
			request.setEndtime(new Date());
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(request.getEndtime());
			calendar.add(Calendar.DATE, 0 - request.getDays());
			request.setStarttime(calendar.getTime());
		}

		// 获取邮件标签
		String labelId = null;
		if (request.getEmailId() != null && !request.getEmailId().isEmpty()) {
			// 获取dao
			Dao dao = BLContextUtil.getDao(context, EmailRightDao.class);
			BLContextUtil.valiDaoIsNull(dao, "邮件");

			// 查询邮件数据
			QueryMailInfoDto queryMailInfoDto = null;
			queryMailInfoDto = new QueryMailInfoDto();
			queryMailInfoDto.setEmailId(request.getEmailId());
			Iterator<Entity> mailInfoEntity = dao.query(queryMailInfoDto);
			List<MailInfoEntity> mailInfo = null;
			mailInfo = new ArrayList<MailInfoEntity>();
			while (mailInfoEntity.hasNext())
				mailInfo.add((MailInfoEntity) mailInfoEntity.next());
			labelId = EmailRightManager.getAttributeValue(mailInfo, MailInfoEntity.KEY_LABELID);
			BLContextUtil.valiParaNotNull(labelId, "邮件标签");
		}
		// 获取配置文件
		String apiUser = (String) context.getApplication().getConfigure().getSettings().get("shouApiUser");
		BLContextUtil.valiParaNotNull(apiUser, "apiUser");
		// 组装sendcloud接口查询
		SohuMail sohuMail = new SohuMail();
		StatdayListRequest statdayListRequest = new StatdayListRequest();

		statdayListRequest.setEnddate(request.getEndtime());
		statdayListRequest.setStartdate(request.getStarttime());
		if (labelId != null && !labelId.isEmpty()) {
			List<String> label = new ArrayList<String>();
			label.add(labelId);
			statdayListRequest.setLabelIdList(label);
		}
		List<String> apiUsers = new ArrayList<String>();
		apiUsers.add(apiUser);
		statdayListRequest.setApiUserList(apiUsers);

		StatdayListResponse data = null;
		// 请求sendcloud接口，获得数据
		try {
			data = (StatdayListResponse) sohuMail.statdayList(this.getGeneralEmailConfig(context), statdayListRequest);
		} catch (AppException e) {
			throw new BlAppException(e.getMessage());
		}
		// 构造返回值对象
		AnalysisDataReponse response = new AnalysisDataReponse();
		AnalysisDataRecordDto responseRecord = null;
		for (StatdayListResponseRecord record : data.getInfo().getDataList()) {
			if (responseRecord == null || !responseRecord.getDate().equals(record.getSendDate())) {
				responseRecord = new AnalysisDataRecordDto();
				response.getList().add(responseRecord);
			}
			responseRecord.setBounceNum(record.getBounceNum());
			response.setBounceTotalNum(response.getBounceTotalNum() + record.getBounceNum());

			responseRecord.setClickNum(record.getClickNum());
			response.setClickTotalNum(response.getClickTotalNum() + record.getClickNum());

			responseRecord.setDeliveredNum(record.getDeliveredNum());
			response.setDeliveredTotalNum(response.getDeliveredTotalNum() + record.getDeliveredNum());

			responseRecord.setInvalidEmailsNum(record.getInvalidEmailsNum());
			response.setInvalidEmailsTotalNum(
					response.getInvalidEmailsTotalNum() + responseRecord.getInvalidEmailsNum());

			responseRecord.setOpenNum(record.getOpenNum());
			response.setOpenTotalNum(response.getOpenTotalNum() + record.getOpenNum());

			responseRecord.setRequestNum(record.getRequestNum());
			response.setRequestTotalNum(response.getRequestTotalNum() + record.getRequestNum());

			responseRecord.setSpamReportedNum(record.getSpamReportedNum());
			response.setSpamReportedTotalNum(response.getSpamReportedTotalNum() + record.getSpamReportedNum());

			responseRecord.setUniqueClicksNum(record.getUniqueClicksNum());
			response.setUniqueClicksTotalNum(response.getUniqueClicksTotalNum() + record.getUniqueClicksNum());

			responseRecord.setUniqueOpensNum(record.getUniqueOpensNum());
			response.setUniqueOpensTotalNum(response.getUniqueOpensTotalNum() + record.getUniqueOpensNum());

			responseRecord.setUnsubscribeNum(record.getUnsubscribeNum());
			response.setUnsubscribeTotalNum(response.getUnsubscribeTotalNum() + record.getUnsubscribeNum());

			responseRecord.setDate(record.getSendDate());
		}
		return response;
	}

	private GeneralSendSohuMailConfig getGeneralEmailConfig(RequestContext context) {
		GeneralSendSohuMailConfig config = new GeneralSendSohuMailConfig();
		Object tmp = null;
		Map<String, String> setting = context.getApplication().getConfigure().getSettings();
		tmp = setting.get("shouApiKey");
		if (tmp instanceof String)
			config.setApiKey((String) tmp);
		tmp = setting.get("shouApiUser");
		if (tmp instanceof String)
			config.setApiUser((String) tmp);
		return config;

	}

	@Override
	public boolean saveallcontrants(RequestContext context) throws BlAppException, DaoAppException {
		CodeTable ct;
		SaveAllCustomerRequest request = null;
		try {
			this.checkFrontUserLogined(context);
			request = context.getDomain(SaveAllCustomerRequest.class);
			BLContextUtil.valiDomainIsNull(request, "请求不能为空");
			BLContextUtil.valiParaItemStrNullOrEmpty(request.getEmailId(), "邮件id不能为空");
			BLContextUtil.valiParaItemIntegerNull(request.getStartCount(), "开始数不能为空");
			BLContextUtil.valiParaItemIntegerNull(request.getPageSize(), "查询数不能为空");
			if (request.getPageSize() <= 0) {
				throw new BlAppException(-1, "请选择客户");
			}
			CustomerManager.synchronizeAllCustomer(context, request);
			return true;
		} catch (BlAppException e) {
			throw e;
		} catch (Exception e) {
			ct = CodeTable.BL_UNHANDLED_EXCEPTION;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		}
	}

	
}
