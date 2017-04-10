package cn.ilanhai.kem.bl.plugin.activeplugin;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.ilanhai.framework.InterfaceUtil.InterfaceDocAnnotation;
import cn.ilanhai.framework.app.RequestContext;
import cn.ilanhai.framework.app.dao.Dao;
import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.exception.AppException;
import cn.ilanhai.framework.common.exception.BlAppException;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.framework.common.exception.SessionContainerException;
import cn.ilanhai.framework.common.session.Session;
import cn.ilanhai.framework.common.session.enums.SessionStateType;
import cn.ilanhai.framework.uitl.ExpressionMatchUtil;
import cn.ilanhai.framework.uitl.Str;
import cn.ilanhai.kem.bl.BaseBl;
import cn.ilanhai.kem.bl.contacts.ContactManager;
import cn.ilanhai.kem.bl.customer.CustomerManager;
import cn.ilanhai.kem.bl.extension.ExtensionManager;
import cn.ilanhai.kem.bl.manuscript.ManuscriptManager;
import cn.ilanhai.kem.bl.plugin.PluginManager;
import cn.ilanhai.kem.bl.special.SpecialManager;
import cn.ilanhai.kem.bl.template.TemplateManager;
import cn.ilanhai.kem.bl.user.trafficuser.TrafficuserManager;
import cn.ilanhai.kem.common.CodeTable;
import cn.ilanhai.kem.common.Constant;
import cn.ilanhai.kem.dao.special.SpecialDao;
import cn.ilanhai.kem.dao.user.trafficuser.TrafficuserPluginDao;
import cn.ilanhai.kem.domain.CountDto;
import cn.ilanhai.kem.domain.IdEntity;
import cn.ilanhai.kem.domain.contacts.dto.ContactMsgDto;
import cn.ilanhai.kem.domain.customer.CustomerInfoEntity;
import cn.ilanhai.kem.domain.customer.CustomerMainEntity;
import cn.ilanhai.kem.domain.enums.ActivePluginType;
import cn.ilanhai.kem.domain.enums.ContactInfoType;
import cn.ilanhai.kem.domain.enums.ContactType;
import cn.ilanhai.kem.domain.enums.ManuscriptParameterType;
import cn.ilanhai.kem.domain.enums.ManuscriptState;
import cn.ilanhai.kem.domain.enums.PluginType;
import cn.ilanhai.kem.domain.enums.TerminalType;
import cn.ilanhai.kem.domain.enums.ManuscriptType;
import cn.ilanhai.kem.domain.enums.TrafficuserType;
import cn.ilanhai.kem.domain.enums.UserType;
import cn.ilanhai.kem.domain.extension.ExtensionEntity;
import cn.ilanhai.kem.domain.manuscript.ManuscriptEntity;
import cn.ilanhai.kem.domain.manuscript.ManuscriptParameterEntity;
import cn.ilanhai.kem.domain.plugin.activeplugin.dto.*;
import cn.ilanhai.kem.domain.plugin.activeplugin.entity.*;
import cn.ilanhai.kem.domain.special.SpecialEntity;
import cn.ilanhai.kem.domain.template.TemplateEntity;
import cn.ilanhai.kem.domain.user.trafficuser.QueryTrafficUserTypeEntity;
import cn.ilanhai.kem.domain.user.trafficuser.TrafficuserEntity;
import cn.ilanhai.kem.keyfac.KeyFactory;
import cn.ilanhai.kem.mail.protocol.sohu.SohuMailInfo;
import cn.ilanhai.kem.queue.QueueManager;
import cn.ilanhai.kem.queue.msg.ContactMsg;
import cn.ilanhai.kem.queue.msg.MailMsg;
import cn.ilanhai.kem.util.KeyUtil;
import cn.ilanhai.kem.util.LockUtil;
import cn.ilanhai.kem.util.TimeUtil;
import cn.ilanhai.kem.util.ValicodeHepler;

@Component("activeplugin")
public class ActivePluginImpl extends BaseBl implements ActivePlugin {
	private static String desc = "活动插件";
	private static Logger logger = Logger.getLogger(ActivePluginImpl.class);

	@Override
	@InterfaceDocAnnotation(methodVersion = "1.0.0")
	@Transactional(rollbackFor = { AppException.class, Exception.class,
			Throwable.class }, propagation = Propagation.REQUIRED)
	public Integer create(RequestContext context) throws SessionContainerException, BlAppException, DaoAppException {

		// 校验用户状态，需要是前台用户已登录
		this.checkFrontUserLogined(context);

		// 获取请求对象
		CreateRequestDto request = context.getDomain(CreateRequestDto.class);
		// 校验请求内容
		this.valiPara(request);
		// 活动插件类型是否为空，是否存在
		this.valiParaItemEnumNotExists(ActivePluginType.getEnum(request.getActiveType()), request.getActiveType(),
				desc);
		// 关联ID是否为空
		this.valiParaItemStrNullOrEmpty(request.getRelationId(), desc);
		// 关联类型是否为空，是否合法
		this.valiParaItemIntegerNull(request.getRelationType(), desc);
		// 关联ID是否存在
		this.verifyRelationId(request.getRelationId());

		// 创建活动插件
		ActivePluginType activePluginType = ActivePluginType.getEnum(request.getActiveType());

		ActivePluginEntity activePluginEntity = new ActivePluginEntity();
		activePluginEntity.setActivePluginType(activePluginType);

		activePluginEntity.setCreatetime(new Date());
		activePluginEntity.setRelationId(request.getRelationId());
		activePluginEntity.setRelationType(ManuscriptType.getEnum(request.getRelationType()));
		activePluginEntity.setUsed(true);
		activePluginEntity.setUserId(this.getSessionUserId(context));

		// 保存活动插件
		Dao dao = this.daoProxyFactory.getDao(context);
		valiDaoIsNull(dao, desc);

		this.valiUpdateDomain(dao.save(activePluginEntity), desc);

		// 返回创建的活动插件ID
		return activePluginEntity.getPluginId();
	}

	@Override
	@InterfaceDocAnnotation(methodVersion = "1.0.0")
	@Transactional(rollbackFor = { AppException.class, Exception.class,
			Throwable.class }, propagation = Propagation.REQUIRED)
	public Entity loadsetting(RequestContext context)
			throws BlAppException, DaoAppException, SessionContainerException {
		// 组织返回内容
		LoadSettingResponseDto response = new LoadSettingResponseDto();
		// // 校验用户状态，需要是前台用户已登录
		// this.checkFrontUserLogined(context);

		// 校验请求
		LoadSettingRequestDto request = context.getDomain(LoadSettingRequestDto.class);

		this.valiPara(request);
		if (Str.isNullOrEmpty(request.getRelationId())) {
			return response;
		}
		// 关联ID是否存在
		this.verifyRelationId(request.getRelationId());

		// 根据不同的稿件类型加载设置
		loadSettingByType(context, response, request);

		// 加载活动设置
		Dao dao = this.daoProxyFactory.getDao(context);
		valiDaoIsNull(dao, desc);

		QueryActivePluginByRelationId queryDto = new QueryActivePluginByRelationId();
		queryDto.setRelationId(request.getRelationId());
		queryDto.setActiveType(ActivePluginType.getEnum(request.getActivePluginType()));
		queryDto.setIsUsed(1);
		ActivePluginEntity entity = (ActivePluginEntity) dao.query(queryDto, false);
		this.valiActivePulginIsNull(entity);

		response.setDrawTime(entity.getDrawTime());
		response.setIntervalTime(entity.getIntervalTime());
		response.setIntervalTimeType(entity.getIntervalTimeType());
		response.setMerchantPhone(entity.getMerchantPhone());
		response.setOuterUrl(entity.getOuterUrl());
		response.setPrizeCollectInfo(entity.getPrizeCollectInfo());
		response.setPrizeCollectRequiredInfo(entity.getPrizeCollectRequiredInfo());
		response.setWinTime(entity.getWinTime());
		if (entity.getActivePluginPrizeSettings() != null) {
			for (ActivePluginPrizeSettingEntity item : entity.getActivePluginPrizeSettings()) {
				PrizeSettingDto prizeSettingDto = new PrizeSettingDto();
				prizeSettingDto.setAmount(item.getAmount());
				prizeSettingDto.setOptionName(item.getOptionName());
				prizeSettingDto.setPrizeName(item.getPrizeName());
				prizeSettingDto.setRate(item.getRate());

				response.getPrizes().add(prizeSettingDto);
			}
		}
		return response;
	}

	@Override
	@InterfaceDocAnnotation(methodVersion = "1.0.0")
	@Transactional(rollbackFor = { AppException.class, Exception.class,
			Throwable.class }, propagation = Propagation.REQUIRED)
	public String savesetting(RequestContext context)
			throws SessionContainerException, BlAppException, DaoAppException {
		// 校验用户状态，需要是前台用户已登录
		this.checkFrontUserLogined(context);
		// 获取请求对象
		SaveSettingRequestDto request = context.getDomain(SaveSettingRequestDto.class);
		this.valiPara(request);
		String userId = null;
		userId = this.getSessionUserId(context);
		synchronized (LockUtil.getUserLock(userId)) {

			// 关联ID是否存在
			// this.verifyRelationId(request.getRelationId());
			String relationId = request.getRelationId();
			if (Str.isNullOrEmpty(relationId)) {
				UserType userTypeValue = context.getSession().getParameter(Constant.KEY_SESSION_USERTYPE,
						UserType.class);
				ManuscriptType manuscriptType = null;
				if (userTypeValue.equals(UserType.DESIGNERS)) {
					manuscriptType = ManuscriptType.TEMPLATE;
				}
				if (userTypeValue.equals(UserType.GENERAL_USER)) {
					manuscriptType = ManuscriptType.SPECIAL;
				}
				ActivePluginType activeType = ActivePluginType.getEnum(request.getActivePluginType());
				this.valiParaNotNull(activeType, "活动插件类型错误", false);
				relationId = ManuscriptManager.createManuscript(context, getSessionUserId(context), manuscriptType,
						TerminalType.WAP);
				PluginManager.creatPlugin(context, relationId, ManuscriptType.SPECIAL, PluginType.ACTIVEPLUGIN,
						activeType);
			}
			// 奖项设置是否存在，应至少存在一项
			this.valiParaNotNull(request.getPrizes(), desc);
			if (request.getPrizes().size() < 1) {
				throw new BlAppException(CodeTable.BL_ACTIVEPLUGIN_SAVESETTING_ERROR.getValue(), "应该至少有一个奖项");
			}
			// 每个奖项所有内容均为必填
			Double totalRate = 0.0;
			for (PrizeSettingDto prize : request.getPrizes()) {
				// 奖项名称，不超过20个字
				this.valiParaItemStrNullOrEmpty(prize.getOptionName(), desc);
				if (prize.getOptionName().length() > 20) {
					throw new BlAppException(CodeTable.BL_ACTIVEPLUGIN_SAVESETTING_ERROR.getValue(), "奖项名称不应超过20个字哦");
				}
				// 奖品名称，不超过20个字
				this.valiParaItemStrNullOrEmpty(prize.getPrizeName(), desc);
				if (prize.getPrizeName().length() > 20) {
					throw new BlAppException(CodeTable.BL_ACTIVEPLUGIN_SAVESETTING_ERROR.getValue(), "奖品名称不应超过20个字哦");
				}
				// 奖品数量，非负整数
				this.valiParaItemIntegerNull(prize.getAmount(), desc);
				if (prize.getAmount() < 0) {
					throw new BlAppException(CodeTable.BL_ACTIVEPLUGIN_SAVESETTING_ERROR.getValue(), "奖品数量不应小于0哦");
				}
				// 中奖概率，0到100的整数
				this.valiParaItemIntegerNull(prize.getRate(), desc);
				if (prize.getRate() < 0 || prize.getRate() > 100) {
					throw new BlAppException(CodeTable.BL_ACTIVEPLUGIN_SAVESETTING_ERROR.getValue(), "中奖概率不应超过100哦");
				}
				totalRate += prize.getRate();
			}
			// 所有的中奖概率相加，不超过100
			if (totalRate > 100) {
				throw new BlAppException(CodeTable.BL_ACTIVEPLUGIN_SAVESETTING_ERROR.getValue(), "中奖总概率不应超过100哦");
			}

			// 抽奖次数，必填，非负整数
			this.valiParaItemIntegerNull(request.getDrawTime(), desc);
			if (request.getDrawTime() < 0) {
				throw new BlAppException(CodeTable.BL_ACTIVEPLUGIN_SAVESETTING_ERROR.getValue(), "请填写每人抽奖次数");
			}
			// 中奖次数，必填，非负整数，不能大于抽奖次数
			this.valiParaItemIntegerNull(request.getWinTime(), desc);
			if (request.getWinTime() < 0) {
				throw new BlAppException(CodeTable.BL_ACTIVEPLUGIN_SAVESETTING_ERROR.getValue(), "请填写每人可中奖次数哦");
			}
			if (request.getWinTime() > request.getDrawTime()) {
				throw new BlAppException(CodeTable.BL_ACTIVEPLUGIN_SAVESETTING_ERROR.getValue(), "中奖次数不应该大于抽奖次数");
			}
			// 时间间隔，必填，非负整数
			this.valiParaItemIntegerNull(request.getIntervalTime(), "请设置每人中奖时间间隔", false);
			if (request.getIntervalTime() < 0) {
				throw new BlAppException(CodeTable.BL_ACTIVEPLUGIN_SAVESETTING_ERROR.getValue(), "中奖时间间隔应该大于0");
			}
			// 间隔单位，必填，仅可为1,2,3
			this.valiParaItemIntegerNull(request.getIntervalTimeType(), "请设置每人中奖时间间隔单位", false);
			this.valiParaItemNumBetween(1, 3, request.getIntervalTimeType(), "请设置每人中奖时间间隔单位", false);

			// 获取数据连接
			Dao actdao = this.daoProxyFactory.getDao(context, SpecialDao.class);
			this.valiDaoIsNull(actdao, "专题");
			IdEntity<String> searchSpecialRequest = new IdEntity<String>();
			searchSpecialRequest.setId(relationId);

			// 根据不同的稿件类型 保存设置
			buildSettingByType(context, request, actdao, searchSpecialRequest);

			// 用户中奖信息采集，必填，必须能有name和phone,可选项为address和qq
			this.valiParaNotNull(request.getPrizeCollectInfo(), desc);
			// 用户中奖信息采集必填项，必填，必须有name和phone，可选项为qq
			this.valiParaNotNull(request.getPrizeCollectRequiredInfo(), desc);
			// 商家电话，选填，需要是电话

			// 存储数据
			Dao dao = this.daoProxyFactory.getDao(context);
			valiDaoIsNull(dao, desc);
			// 加载设置
			QueryActivePluginByRelationId queryDto = new QueryActivePluginByRelationId();
			this.valiParaItemObjectNull(request.getActivePluginType(), desc + "类型不能为空");
			queryDto.setRelationId(relationId);
			ActivePluginType activePluginType = ActivePluginType.getEnum(request.getActivePluginType());
			this.valiParaNotNull(activePluginType, "无该活动类型", false);
			queryDto.setActiveType(activePluginType);
			// 查询所有插件接口
			queryDto.setIsUsed(0);
			ActivePluginEntity entity = (ActivePluginEntity) dao.query(queryDto, false);
			PluginManager.disableAllPlugin(context, relationId);
			int val = -1;
			// 如果活动插件不存在，则创建
			if (entity == null) {
				entity = new ActivePluginEntity();
			}
			entity.setActivePluginType(ActivePluginType.getEnum(request.getActivePluginType()));
			entity.setCreatetime(new Date());
			entity.setPluginType(PluginType.ACTIVEPLUGIN);
			entity.setUserId(userId);
			entity.setRelationId(relationId);
			entity.setUsed(true);
			entity.setRelationType(this.getRelationTypeByRelationId(relationId));

			if (entity.getActivePluginPrizeSettings() != null)
				entity.getActivePluginPrizeSettings().clear();
			for (PrizeSettingDto prize : request.getPrizes()) {
				ActivePluginPrizeSettingEntity prizeSetting = new ActivePluginPrizeSettingEntity(entity.getPluginId());
				prizeSetting.setOptionName(prize.getOptionName());
				prizeSetting.setPrizeName(prize.getPrizeName());
				prizeSetting.setAmount(prize.getAmount());
				prizeSetting.setRate(prize.getRate());

				if (entity.getActivePluginPrizeSettings() == null)
					entity.setActivePluginPrizeSettings(new ArrayList<ActivePluginPrizeSettingEntity>());
				entity.getActivePluginPrizeSettings().add(prizeSetting);
			}
			entity.setDrawTime(request.getDrawTime());
			entity.setWinTime(request.getWinTime());
			entity.setIntervalTime(request.getIntervalTime());
			entity.setIntervalTimeType(request.getIntervalTimeType());
			entity.setPrizeCollectInfo(request.getPrizeCollectInfo());
			entity.setPrizeCollectRequiredInfo(request.getPrizeCollectRequiredInfo());
			entity.setOuterUrl(request.getOuterUrl());
			entity.setMerchantPhone(request.getMerchantPhone());

			val = dao.save(entity);
			this.valiSaveDomain(val, "活动插件");
			return relationId;
		}
	}

	private ActivePluginEntity buildNewActivePlugin(RequestContext context, SaveSettingRequestDto request)
			throws SessionContainerException, BlAppException {
		ActivePluginEntity entity;
		entity = new ActivePluginEntity();
		// entity=KeyFactory.newKey(KeyFactory.KEY_EXTENSION)
		entity.setActivePluginType(ActivePluginType.getEnum(request.getActivePluginType()));
		entity.setCreatetime(new Date());
		entity.setPluginType(PluginType.ACTIVEPLUGIN);
		entity.setUserId(this.getSessionUserId(context));
		entity.setRelationId(request.getRelationId());
		entity.setUsed(true);
		entity.setRelationType(this.getRelationTypeByRelationId(request.getRelationId()));
		return entity;
	}

	@Override
	@InterfaceDocAnnotation(methodVersion = "1.1.1.0")
	@Transactional(rollbackFor = { AppException.class, Exception.class,
			Throwable.class }, propagation = Propagation.REQUIRED)
	public boolean addprizeinfo(RequestContext context)
			throws SessionContainerException, BlAppException, DaoAppException {

		// if (context.getSession().getSessionState().getSessionStateType()
		// .equals(SessionStateType.BACK_USER_LOGINED_STATE)) {
		// return true;
		// } else if
		// (context.getSession().getSessionState().getSessionStateType()
		// .equals(SessionStateType.FRONT_USER_LOGINED_STATE)) {
		// return true;
		// }

		Map<String, String> prizeCollectInfo = null;
		AddPrizeInfoRequestDto request = null;
		Dao dao = null;
		String tmp = null;
		// 获取请求对象
		request = context.getDomain(AddPrizeInfoRequestDto.class);
		// 校验请求内容
		this.valiPara(request);
		this.valiParaItemStrNullOrEmpty(request.getRelationId(), "关联编号");
		prizeCollectInfo = request.getPrizeCollectInfo();
		this.valiParaItemMapNull(prizeCollectInfo, "用户中奖信息");
		if (!prizeCollectInfo.containsKey("name"))
			throw new BlAppException(-1, "用户中奖信息找不到用户名");
		if (!prizeCollectInfo.containsKey("phone"))
			throw new BlAppException(-1, "用户中奖信息找不到手机号");
		tmp = prizeCollectInfo.get("name");
		this.valiParaItemStrNullOrEmpty(tmp, "用户中奖信息中用户名");
		this.valiParaItemStrLength(tmp, 20, "用户中奖信息中用户名");
		tmp = prizeCollectInfo.get("phone");
		this.valiParaItemStrNullOrEmpty(tmp, "用户中奖信息中手机号");
		if (!ExpressionMatchUtil.isPhoneNo(tmp)) {
			throw new BlAppException(CodeTable.BL_ACTIVEPLUGIN_DRAWPRIZE_ERROR.getValue(), "用户中奖信息中手机号错误");
		}
		// this.valiParaItemStrRegular(PHONE_REGULAR, tmp, "用户中奖信息中手机号");
		// 获取插件配置信息
		dao = this.daoProxyFactory.getDao(context);
		valiDaoIsNull(dao, desc);
		QueryActivePluginByRelationId queryDto = new QueryActivePluginByRelationId();
		queryDto.setRelationId(request.getRelationId());

		ActivePluginEntity entity = (ActivePluginEntity) dao.query(queryDto, false);

		// 从会话中获取中奖信息
		DrawPrizeResultEntity result = (DrawPrizeResultEntity) context.getSession()
				.getParameter(Constant.KEY_SESSION_ACTIVEPLUGIN_PRIZEINFO + entity.getActivePluginType().getValue());
		Integer trafficuserId = (Integer) context.getSession().getParameter(
				Constant.KEY_SESSION_ACTIVEPLUGIN_TRAFFICUSERID + entity.getActivePluginType().getValue());
		if (result == null) {
			CodeTable ct = CodeTable.BL_ACTIVEPLUGIN_ADDPRIZEINFO;
			throw new BlAppException(ct.getValue(), "未中奖");
		}
		// 校验获奖信息是否合法
		for (Map.Entry<String, Boolean> record : entity.getPrizeCollectRequiredInfo().entrySet()) {
			String key = "";
			if (record.getValue()) {
				key = record.getKey();
				if ("address".equals(key)) {
					tmp = prizeCollectInfo.get(key);
					if (tmp != null)
						this.valiParaItemStrLength(tmp, 50, "用户中奖信息中地址");
				}
				if ("qq".equals(key)) {
					tmp = prizeCollectInfo.get(key);

					if (!ExpressionMatchUtil.isQQ(tmp)) {
						throw new BlAppException(CodeTable.BL_ACTIVEPLUGIN_DRAWPRIZE_ERROR.getValue(), "用户中奖QQ号错误");
					}
				}
				if (!request.getPrizeCollectInfo().containsKey(record.getKey())) {
					CodeTable ct = CodeTable.BL_ACTIVEPLUGIN_ADDPRIZEINFO;
					throw new BlAppException(ct.getValue(), ct.getDesc());
				} else {

				}

			}
		}

		// 修改获奖记录信息，存入数据库，扣除库存
		// 判断是否中奖
		if (result.getOptionId() <= 0) {
			CodeTable ct = CodeTable.BL_ACTIVEPLUGIN_ADDPRIZEINFO;
			throw new BlAppException(ct.getValue(), "未中奖");
		}

		// 读取抽奖记录
		QueryDrawPrizeRecordByRecordIdDto queryRecordDto = new QueryDrawPrizeRecordByRecordIdDto();
		queryRecordDto.setRecordId(result.getRecordId());
		DrawPrizeRecordEntity drawPrizeRecord = (DrawPrizeRecordEntity) dao.query(queryRecordDto, false);
		if (drawPrizeRecord == null) {
			CodeTable ct = CodeTable.BL_ACTIVEPLUGIN_ADDPRIZEINFO;
			throw new BlAppException(ct.getValue(), "未中奖");
		}
		for (Map.Entry<String, Boolean> record : entity.getPrizeCollectInfo().entrySet()) {
			if (record.getValue()) {
				if (request.getPrizeCollectInfo().containsKey(record.getKey())) {
					switch (record.getKey()) {
					case "name":
						drawPrizeRecord.setName(request.getPrizeCollectInfo().get(record.getKey()));
						break;
					case "phone":
						// drawPrizeRecord.setPhoneNo(request.getPrizeCollectInfo().get(record.getKey()));
						break;
					case "address":
						drawPrizeRecord.setAddress(request.getPrizeCollectInfo().get(record.getKey()));
						break;
					case "qq":
						drawPrizeRecord.setQq(request.getPrizeCollectInfo().get(record.getKey()));
						break;
					}
				}
			}
		}
		TrafficuserEntity trafficuserEntity = null;

		Dao traDao = this.daoProxyFactory.getDao(context, TrafficuserPluginDao.class);

		QueryTrafficUserTypeEntity requestType = new QueryTrafficUserTypeEntity();
		requestType.setRelationId(request.getRelationId());
		Iterator<Entity> typeResponse = traDao.query(requestType);
		QueryTrafficUserTypeEntity responseType = null;

		trafficuserEntity = TrafficuserManager.getTrafficuserByExtensionAndPhone(context, request.getRelationId(),
				request.getPrizeCollectInfo().get("phone"));
		// 保存用户信息
		if (trafficuserEntity == null) {
			trafficuserEntity = new TrafficuserEntity();
			trafficuserEntity.setExtensionId(request.getRelationId());
			trafficuserEntity.setTrafficuserType(TrafficuserType.ACTIVEPLUGINUSER);

			// 发送联系人消息队列
			sendContactQueue(context, request.getRelationId(), request.getPrizeCollectInfo().get("phone"),
					drawPrizeRecord.getName(), ContactType.phone);
		}
		trafficuserEntity.setName(drawPrizeRecord.getName());
		trafficuserEntity.setPhoneNo(request.getPrizeCollectInfo().get("phone"));
		trafficuserEntity.setQqNo(drawPrizeRecord.getQq());
		trafficuserEntity.setAddress(drawPrizeRecord.getAddress());
		if (typeResponse.hasNext()) {
			responseType = (QueryTrafficUserTypeEntity) typeResponse.next();
			trafficuserEntity.setTrafficfrom(responseType.getType());
		}
		TrafficuserManager.saveTrafficuser(context, trafficuserEntity);
		// 保存客户
		String customerId = (String) context.getSession()
				.getParameter(Constant.KEY_SESSION_ACTIVEPLUGIN_CUSTOMERID + entity.getActivePluginType().getValue());
		Map<String, String> infos = request.getPrizeCollectInfo();
		if (Str.isNullOrEmpty(customerId)) {
			infos.put(CustomerInfoEntity.KEY_ORIGINATE, trafficuserEntity.getTrafficfrom() + "");
			ManuscriptParameterEntity manuscriptParameterEntity = ManuscriptManager.getManuscriptParameterById(context,
					request.getRelationId(), ManuscriptParameterType.manuscriptName);
			String extensionName = manuscriptParameterEntity == null ? null : manuscriptParameterEntity.getParameter();
			infos.put(CustomerInfoEntity.KEY_EXTENSIONNAME, extensionName);
			infos.put(CustomerInfoEntity.KEY_EXTENSIONID, entity.getRelationId());
			infos.put(CustomerInfoEntity.KEY_TYPE, "1");
		}
		CustomerManager.saveCustomer(context, infos, entity.getUserId(),
				ManuscriptManager.getTag(context, request.getRelationId(), ManuscriptParameterType.tag), customerId);
		// 将中奖结果存入数据库
		dao.save(drawPrizeRecord);
		return true;
	}

	@Override
	@InterfaceDocAnnotation(methodVersion = "1.1.1.0")
	@Transactional(rollbackFor = { AppException.class, Exception.class,
			Throwable.class }, propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
	public DrawPrizeResponseDto drawprize(RequestContext context)
			throws SessionContainerException, BlAppException, DaoAppException {
		DrawPrizeResponseDto response = new DrawPrizeResponseDto();
		// 获取请求对象
		DrawPrizeRequestDto request = context.getDomain(DrawPrizeRequestDto.class);
		// 校验请求内容
		this.valiPara(request);

		// 关联ID不可为空
		this.valiParaItemStrNullOrEmpty(request.getRelationId(), "关联ID");
		// 姓名，必填，不超过20个字
		this.valiParaItemStrNullOrEmpty(request.getName(), "姓名");
		this.valiParaItemStrLength(request.getName(), 20, "姓名");
		// 手机号，必填，不超过20个字
		this.valiParaItemStrNullOrEmpty(request.getPhone(), "手机号");
		this.valiParaItemStrLength(request.getPhone(), 20, "手机号");
		if (!ExpressionMatchUtil.isPhoneNo(request.getPhone())) {
			throw new BlAppException(CodeTable.BL_ACTIVEPLUGIN_DRAWPRIZE_ERROR.getValue(), "手机号码错误");
		}
		// 抽奖
		// 加载插件
		Dao dao = this.daoProxyFactory.getDao(context);
		valiDaoIsNull(dao, desc);

		QueryActivePluginByRelationId queryDto = new QueryActivePluginByRelationId();
		queryDto.setRelationId(request.getRelationId());
		ActivePluginEntity entity = (ActivePluginEntity) dao.query(queryDto, false);
		this.valiActivePulginIsNull(entity);
		// 验证活动时间
		Integer drawPrizeTime = PluginManager.CheckDrawPrizeTime(context, entity);
		if (drawPrizeTime.equals(1)) {
			throw new BlAppException(CodeTable.BL_ACTIVEPLUGIN_DRAWPRIZE_ERROR.getValue(), "活动还未开始");
		} else if (drawPrizeTime.equals(2)) {
			throw new BlAppException(CodeTable.BL_ACTIVEPLUGIN_DRAWPRIZE_ERROR.getValue(), "活动已结束");
		}

		Map<Integer, DrawOptionEntity> options = null;
		// 加载session中是否存在
		Session session = context.getSession();
		String key = Constant.KEY_SESSION_ACTIVEPLUGIN_OPTIONS + entity.getActivePluginType().getValue();
		Object val = session.getParameter(key);
		if (val != null) {
			options = (Map<Integer, DrawOptionEntity>) val;
		} else {
			// 如果session中不存在，则从数据库中加载
			options = this.generateOptions(entity, null);
		}
		// 是否存储用户信息
		Dao dao2 = this.daoProxyFactory.getDao(context, TrafficuserPluginDao.class);
		List<Entity> responseuser = transformation(dao2.query(request));
		int trafficuserId = -1;
		if (responseuser.size() == 0) {
			QueryTrafficUserTypeEntity requestType = new QueryTrafficUserTypeEntity();
			requestType.setRelationId(request.getRelationId());
			Iterator<Entity> typeResponse = dao2.query(requestType);
			QueryTrafficUserTypeEntity responseType = null;
			// 保存用户信息
			TrafficuserEntity trafficuserEntity = new TrafficuserEntity();
			trafficuserEntity.setExtensionId(request.getRelationId());
			trafficuserEntity.setName(request.getName());
			trafficuserEntity.setPhoneNo(request.getPhone());
			trafficuserEntity.setCreatetime(new Date());
			trafficuserEntity.setTrafficuserType(TrafficuserType.ACTIVEPLUGINUSER);
			if (typeResponse.hasNext()) {
				responseType = (QueryTrafficUserTypeEntity) typeResponse.next();
				trafficuserEntity.setTrafficfrom(responseType.getType());
			}
			trafficuserId = TrafficuserManager.saveTrafficuser(context, trafficuserEntity);

			// 发送联系人消息队列
			// sendContactQueue(context, request.getRelationId(),
			// request.getPhone(), request.getName(),
			// ContactType.phone);

		}

		// 查询用户的中奖纪录
		QueryDrawprizeDataDto queryDrawprizeDataDto = new QueryDrawprizeDataDto();
		queryDrawprizeDataDto.setPhoneNo(request.getPhone());
		queryDrawprizeDataDto.setPluginId(entity.getPluginId());
		List<Entity> queryDrawprizeDatas = transformation(dao.query(queryDrawprizeDataDto));

		int winTime = 0;
		int totalTime = 0;
		Date canWinTim = null;
		for (Entity entry : queryDrawprizeDatas) {
			if (entry instanceof DrawPrizeRecordEntity) {
				DrawPrizeRecordEntity queryDrawprizeData = (DrawPrizeRecordEntity) entry;
				if (!Str.isNullOrEmpty(queryDrawprizeData.getPrizeNo())) {
					if (winTime == 0) {
						// 校验间隔时间是否达到
						Calendar rightNow = Calendar.getInstance();
						rightNow.setTime(queryDrawprizeData.getCreatetime());
						switch (entity.getIntervalTimeType()) {
						case 1:
							rightNow.add(Calendar.MINUTE, entity.getIntervalTime());
							break;
						case 2:
							rightNow.add(Calendar.HOUR, entity.getIntervalTime());
							break;
						case 3:
							rightNow.add(Calendar.DAY_OF_YEAR, entity.getIntervalTime());
							break;
						default:
							break;
						}
						canWinTim = new Date(rightNow.getTime().getTime());
					}
					winTime++;
				}
				totalTime++;
			}
		}

		// 校验抽奖次数是否用尽
		if (totalTime >= entity.getDrawTime()) {
			throw new BlAppException(CodeTable.BL_ACTIVEPLUGIN_DRAWPRIZE_ERROR.getValue(), "抽奖次数已用尽,无法继续抽奖");
		}
		logger.info("开始抽奖");
		// 校验根据选项进行抽奖
		DrawPrizeResultEntity result = entity.drawPrize(options, canWinTim, winTime);

		logger.info("抽奖结束");
		// 返回中奖结果

		// 将抽奖结果记录入会话，无论是否中奖，插入抽奖信息，结果为未中奖
		DrawPrizeRecordEntity record = new DrawPrizeRecordEntity();
		record.setCreatetime(new Date());
		record.setExchangeState(0);
		record.setName(request.getName());
		record.setPhoneNo(request.getPhone());
		record.setPluginId(entity.getPluginId());
		dao.save(record);
		// 待填写中奖信息后，再将中奖插入数据库，否则丢弃
		if (result.getOptionId() > 0) {
			// logger.info("OptionId:" + result.getOptionId() + "已中奖");
			result.setPrizeNo(ValicodeHepler.generate(16));
			// 将信息写入中奖纪录
			record.setPrizeNo(result.getPrizeNo());
			// 检查库存是否足够
			for (ActivePluginPrizeSettingEntity setting : entity.getActivePluginPrizeSettings()) {
				if (setting.getRecordId().equals(result.getOptionId())) {
					// logger.info("开始扣库存");
					record.setPrizeName(setting.getPrizeName());
					// logger.info("库存:" + setting.getPrizeName() + " " +
					// setting.getAmount());
					// 刷新库存 库存 减1
					setting.setAmount(setting.getAmount() - 1);
					int val_save = dao.save(setting);
					this.valiSaveDomain(val_save, "奖品库存不足(刷新库存失败)");
					// logger.info("刷新库存后:" + setting.getPrizeName() + " " +
					// setting.getAmount());
				}
			}
			dao.save(record);
			// 返回抽中选项的抽奖纪录编号
			result.setRecordId(record.getRecordId());

			session.setParameter(Constant.KEY_SESSION_ACTIVEPLUGIN_PRIZEINFO + entity.getActivePluginType().getValue(),
					result);
			session.setParameter(
					Constant.KEY_SESSION_ACTIVEPLUGIN_TRAFFICUSERID + entity.getActivePluginType().getValue(),
					trafficuserId);
		}

		// logger.info("中奖流程处理完成");

		// 移除session里的奖项信息
		session.removeParameter(Constant.KEY_SESSION_ACTIVEPLUGIN_OPTIONS + entity.getActivePluginType().getValue());

		response.setOptionId(result.getOptionId());
		response.setOptionName(result.getOptionName());
		response.setPrizeName(result.getPrizeName());
		response.setRecordId(record.getRecordId());
		response.setPrizeNo(result.getPrizeNo());
		response.setMerchantPhone(entity.getMerchantPhone());
		// 封装信息
		Map<String, String> infos = CustomerManager.buildInfo(context, request, entity);
		// 新增客户
		String customerId = (String) context.getSession()
				.getParameter(Constant.KEY_SESSION_ACTIVEPLUGIN_CUSTOMERID + entity.getActivePluginType().getValue());
		logger.info("保存的客户信息为：" + infos);
		CustomerManager.saveCustomer(context, infos, entity.getUserId(),
				ManuscriptManager.getTag(context, request.getRelationId(), ManuscriptParameterType.tag), customerId);
		// 保存客户
		return response;
	}

	@Override
	@InterfaceDocAnnotation(methodVersion = "1.0.0")
	@Transactional(rollbackFor = { AppException.class, Exception.class,
			Throwable.class }, propagation = Propagation.REQUIRED)
	public boolean exchangeprize(RequestContext context) throws BlAppException, DaoAppException {
		// 校验输入
		// 校验用户状态，需要是前台用户已登录
		this.checkFrontUserLogined(context);

		// 获取请求对象
		ExchangePrizeRequestDto request = context.getDomain(ExchangePrizeRequestDto.class);
		// 校验请求内容
		this.valiPara(request);
		this.valiParaItemObjectNull(request.getRecordId(), "记录编号");
		// 将对应中奖记录兑奖状态修改为已中奖
		Dao dao = this.daoProxyFactory.getDao(context);
		valiDaoIsNull(dao, desc);

		QueryDrawPrizeRecordByRecordIdDto queryRecordDto = new QueryDrawPrizeRecordByRecordIdDto();
		queryRecordDto.setRecordId(request.getRecordId());
		DrawPrizeRecordEntity drawPrizeRecord = (DrawPrizeRecordEntity) dao.query(queryRecordDto, false);
		drawPrizeRecord.setExchangeState(1);
		drawPrizeRecord.setExchangeTime(new Date());

		int val = dao.save(drawPrizeRecord);
		return val > 0;
	}

	@Override
	@InterfaceDocAnnotation(methodVersion = "1.0.0")
	@Transactional(rollbackFor = { AppException.class, Exception.class,
			Throwable.class }, propagation = Propagation.REQUIRED)
	public Entity loadoptions(RequestContext context)
			throws SessionContainerException, BlAppException, DaoAppException {

		// 获取请求对象
		LoadOptionsRequestDto request = context.getDomain(LoadOptionsRequestDto.class);
		// 校验请求内容
		this.valiPara(request);
		// 关联编号如果为空 则自动生成未登录稿件
		String relationId = request.getRelationId();
		if (Str.isNullOrEmpty(relationId)) {
			ActivePluginType activeType = ActivePluginType.getEnum(request.getActivePluginType());
			this.valiParaNotNull(activeType, "活动插件类型错误", false);
			relationId = KeyFactory.newKey(KeyFactory.KEY_DET);
		}

		// 加载
		Dao dao = this.daoProxyFactory.getDao(context);
		valiDaoIsNull(dao, desc);

		QueryActivePluginByRelationId queryDto = new QueryActivePluginByRelationId();
		queryDto.setRelationId(relationId);
		queryDto.setActiveType(ActivePluginType.getEnum(request.getActivePluginType()));
		queryDto.setIsUsed(1);
		ActivePluginEntity entity = (ActivePluginEntity) dao.query(queryDto, false);
		this.valiActivePulginIsNull(entity, true);

		Integer drawPrizeTime = PluginManager.CheckDrawPrizeTime(context, entity);

		Map<Integer, DrawOptionEntity> options = this.generateOptions(entity, request.getOptionNum());

		// 将选项存入session，以供抽奖使用
		context.getSession().setParameter(
				Constant.KEY_SESSION_ACTIVEPLUGIN_OPTIONS + entity.getActivePluginType().getValue(), options);

		DrawOptionRsponseDto drawOptionRsponseDto = new DrawOptionRsponseDto();
		// 校验请求值
		List<Entity> response = new ArrayList<Entity>();
		Iterator<Entry<Integer, DrawOptionEntity>> iterator = options.entrySet().iterator();
		int i = request.getOptionNumMin() == null ? 0 : request.getOptionNumMin();
		int id = 0;
		boolean isEven = true;
		while (iterator.hasNext()) {
			DrawOptionEntity option = iterator.next().getValue();
			DrawOptionDto dto = new DrawOptionDto();
			dto.setOptionId(option.getOptionId());
			dto.setOptionName(option.getOptionName());
			response.add(dto);
			// 用于计算 奖项补充
			i--;
			isEven = !isEven;
			if (option.getOptionId() < id) {
				id = option.getOptionId();
			}
		}

		// 支持补充奖项个数 补充为未中奖
		for (; i > 0; i--) {
			DrawOptionDto dto = new DrawOptionDto();
			dto.setOptionId(id--);
			dto.setOptionName("再来一次");
			response.add(dto);
			isEven = !isEven;
		}
		// 支持获取奖项个数为偶数 补充为未中奖
		if (request.getIsEven() != null && !request.getIsEven().equals(isEven)) {
			DrawOptionDto dto = new DrawOptionDto();
			dto.setOptionId(id--);
			dto.setOptionName("再来一次");
			response.add(dto);
		}

		Collections.shuffle(response);
		drawOptionRsponseDto.setList(response);
		drawOptionRsponseDto.setPrizeCollectRequiredInfo(entity.getPrizeCollectRequiredInfo());
		drawOptionRsponseDto.setPrizeCollectInfo(entity.getPrizeCollectInfo());
		drawOptionRsponseDto.setDrawPrizeTime(drawPrizeTime);
		return drawOptionRsponseDto;
	}

	private void verifyRelationId(String relationId) {
		// 根据Type调用不同的dao，判断id是否存在
	}

	private Map<Integer, DrawOptionEntity> generateOptions(ActivePluginEntity entity, Integer optionNum)
			throws BlAppException {
		Map<Integer, DrawOptionEntity> result = new HashMap<Integer, DrawOptionEntity>();
		// 判断奖项数量是否符合需求
		if (optionNum != null && optionNum < entity.getActivePluginPrizeSettings().size() + 1) {
			throw new BlAppException(CodeTable.BL_ACTIVEPLUGIN_LOADOPTIONS_ERROR.getValue(), "奖项数量错误");
		}
		if (optionNum == null) {
			optionNum = entity.getActivePluginPrizeSettings().size() + 1;
		}
		// 生成奖项
		Integer leftRate = 100;
		// 先生成与奖品对应的奖项
		for (ActivePluginPrizeSettingEntity setting : entity.getActivePluginPrizeSettings()) {
			DrawOptionEntity option = new DrawOptionEntity();
			option.setOptionId(setting.getRecordId());
			option.setOptionName(setting.getOptionName());
			option.setPrizeRecordId(setting.getRecordId());
			option.setHeavy(setting.getRate());
			option.setPrizeName(setting.getPrizeName());
			result.put(option.getOptionId(), option);
			optionNum--;
			leftRate -= option.getHeavy();
		}
		// 未中奖奖项补足所需奖项数目，中奖概率平分
		Integer optionRate = leftRate / 3;
		for (int i = 0; i < optionNum - 1; i++) {
			DrawOptionEntity option = new DrawOptionEntity();
			option.setOptionId(-1 - i);
			option.setOptionName("再来一次");
			option.setHeavy(optionRate);

			result.put(option.getOptionId(), option);
			leftRate -= optionRate;
		}
		// 最后一个奖项占100剩余的所有权重
		DrawOptionEntity option = new DrawOptionEntity();
		option.setOptionId(0);
		option.setOptionName("再来一次");
		option.setHeavy(leftRate);
		result.put(option.getOptionId(), option);
		return result;
	}

	private ManuscriptType getRelationTypeByRelationId(String relationId) {
		ManuscriptType result = null;

		String keyHead = KeyFactory.getKeyHeadByKey(relationId);
		switch (keyHead) {
		case KeyFactory.KEY_EXTENSION:
			result = ManuscriptType.EXTENSION;
			break;
		case KeyFactory.KEY_SPECIAL:
			result = ManuscriptType.SPECIAL;
			break;
		case KeyFactory.KEY_TEMPLATE:
			result = ManuscriptType.TEMPLATE;
			break;
		}

		return result;
	}

	@Override
	@InterfaceDocAnnotation(methodVersion = "1.0.0")
	public Entity searchWinTrafficUser(RequestContext context) throws BlAppException, DaoAppException {
		CodeTable ct;
		try {
			// 获取请求对象
			QueryDrawprizeUserDataDto request = context.getDomain(QueryDrawprizeUserDataDto.class);
			// 校验请求内容
			this.valiPara(request);
			this.valiParaItemIntegerNull(request.getStartCount(), "起始值");
			this.valiParaItemIntegerNull(request.getPageSize(), "查询条数");
			Dao dao = this.daoProxyFactory.getDao(context);
			valiDaoIsNull(dao, desc);

			ExtensionEntity extensionEntity = ExtensionManager.getExtensionEntityById(context, request.getRelationId());
			this.valiDomainIsNull(extensionEntity, "推广编号错误");
			if (!this.getSessionUserId(context).equals(extensionEntity.getUserId())) {
				ct = CodeTable.BL_COMMON_USER_NOLIMIT;
				throw new BlAppException(ct.getValue(), ct.getDesc());
			}

			QueryDrawprizeResponseUserDataDto queryDrawprizeResponseUserDataDto = new QueryDrawprizeResponseUserDataDto();
			queryDrawprizeResponseUserDataDto.setList(dao.query(request));
			queryDrawprizeResponseUserDataDto.setPrizeNo(request.getPrizeNo());
			queryDrawprizeResponseUserDataDto.setPageSize(request.getPageSize());
			queryDrawprizeResponseUserDataDto.setStartCount(request.getStartCount());
			queryDrawprizeResponseUserDataDto.setTotalCount(((CountDto) dao.query(request, false)).getCount());
			return queryDrawprizeResponseUserDataDto;
		} catch (

		BlAppException e) {
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
	public void deleteWinTrafficUser(RequestContext context) throws BlAppException, DaoAppException {
		CodeTable ct;
		try {
			// 获取请求对象
			DeleteDrawprizeDataDto request = context.getDomain(DeleteDrawprizeDataDto.class);
			this.valiDomainIsNull(request, "删除中奖纪录");
			// 校验请求内容
			this.valiParaItemIntegerNull(request.getRecordId(), "中奖纪录编号");
			Dao dao = this.daoProxyFactory.getDao(context);
			valiDaoIsNull(dao, desc);
			this.valiParaItemStrNullOrEmpty(request.getRelationId(), "推广编号");
			ExtensionEntity extensionEntity = ExtensionManager.getExtensionEntityById(context, request.getRelationId());
			this.valiDomainIsNull(extensionEntity, "推广编号错误");
			if (!this.getSessionUserId(context).equals(extensionEntity.getUserId())) {
				ct = CodeTable.BL_COMMON_USER_NOLIMIT;
				throw new BlAppException(ct.getValue(), ct.getDesc());
			}

			ActivePluginEntity activePluginEntity = PluginManager.queryActivePluginByRelationId(context,
					request.getRelationId());
			this.valiParaItemObjectNull(activePluginEntity, "该推广无活动插件 无法删除中奖纪录");
			QueryDrawPrizeRecordByRecordIdDto queryDrawPrizeRecordByRecordIdDto = new QueryDrawPrizeRecordByRecordIdDto();
			queryDrawPrizeRecordByRecordIdDto.setRecordId(request.getRecordId());
			DrawPrizeRecordEntity drawPrizeRecordEntity = (DrawPrizeRecordEntity) dao
					.query(queryDrawPrizeRecordByRecordIdDto, false);
			this.valiParaItemObjectNull(drawPrizeRecordEntity, "无该中奖纪录 无法删除中奖纪录");

			// 校验是否为该推广的中奖信息
			if (!drawPrizeRecordEntity.getPluginId().equals(activePluginEntity.getPluginId())) {
				ct = CodeTable.BL_COMMON_USER_NOLIMIT;
				throw new BlAppException(ct.getValue(), ct.getDesc());
			}
			dao.delete(request);
		} catch (

		BlAppException e) {
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
	 * 根据不同的 稿件保存活动设置 现在只有专题 保存活动时间设置
	 * 
	 * @param context
	 * @param request
	 * @param actdao
	 * @param searchSpecialRequest
	 * @throws DaoAppException
	 * @throws BlAppException
	 * @throws SessionContainerException
	 */
	private void buildSettingByType(RequestContext context, SaveSettingRequestDto request, Dao actdao,
			IdEntity<String> searchSpecialRequest) throws DaoAppException, BlAppException, SessionContainerException {
		switch (KeyUtil.getKey(request.getRelationId())) {
		case TEMPLATE:
			break;
		case SPECIAL:
			// 查询专题数据
			SpecialEntity specialEntity = (SpecialEntity) actdao.query(searchSpecialRequest, false);
			this.valiDomainIsNull(specialEntity, "专题");
			this.checkCurrentUser(context, specialEntity.getUserId());
			this.valiParaNotNull(request.getActStartTime(), "请设置您的活动起始时间哦", false);
			this.valiParaNotNull(request.getActEndTime(), "请设置您的活动结束时间哦", false);
			if (!request.getActEndTime().after(request.getActStartTime())) {
				throw new BlAppException(CodeTable.BL_COMMON_PARAMETER_ITEM_STRING_NULLOREMPTY.getValue(),
						"活动结束时间必须大于开始时间哦");
			}

			// 保存活动设置
			ManuscriptManager.saveManscriptParameter(context, specialEntity.getSpecialId(),
					TimeUtil.format(request.getActStartTime()), ManuscriptParameterType.statrtactivetime);

			ManuscriptManager.saveManscriptParameter(context, specialEntity.getSpecialId(),
					TimeUtil.format(request.getActEndTime()), ManuscriptParameterType.endactivetime);

			ManuscriptManager.saveManscriptParameter(context, specialEntity.getSpecialId(),
					request.getActivePluginType() + "", ManuscriptParameterType.activeType);

			break;
		case EXTENSION:
			break;
		case EXCELLENTCASE:
			break;
		default:
			break;
		}
	}

	/**
	 * 根据不同的稿件类型 加载设置
	 * 
	 * @param context
	 * @param response
	 * @param request
	 * @throws DaoAppException
	 * @throws BlAppException
	 * @throws SessionContainerException
	 */
	private void loadSettingByType(RequestContext context, LoadSettingResponseDto response,
			LoadSettingRequestDto request) throws DaoAppException, BlAppException, SessionContainerException {
		String relationId = request.getRelationId();
		ActivePluginEntity pluginEntity;
		switch (KeyUtil.getKey(relationId)) {
		case TEMPLATE:
			break;
		case SPECIAL:
			// 获取专题
			SpecialEntity specialEntity = SpecialManager.getSpecialEntityById(context, relationId);
			this.valiParaNotNull(specialEntity, "专题加载结果");
			this.checkCurrentUser(context, specialEntity.getUserId());

			// 添加 活动时间设置
			if (specialEntity.getConfigActive() != null) {
				response.setActEndTime(specialEntity.getConfigActive().getEndTime());
				response.setActStartTime(specialEntity.getConfigActive().getStartTime());
			}
			// 添加活动类型
			pluginEntity = PluginManager.queryActivePluginByRelationId(context, relationId);
			if (pluginEntity == null || pluginEntity.getActivePluginType() == null) {
				response.setActiveType("-1");
			} else {
				response.setActiveType(pluginEntity.getActivePluginType().getValue() + "");
			}
			break;
		case EXTENSION:
			break;
		case EXCELLENTCASE:
			ManuscriptParameterEntity startManuscriptParameterEntity = ManuscriptManager
					.getManuscriptParameterById(context, relationId, ManuscriptParameterType.statrtactivetime);
			Date actStartTim = TimeUtil.getDate(
					startManuscriptParameterEntity == null ? null : startManuscriptParameterEntity.getParameter());
			ManuscriptParameterEntity endManuscriptParameterEntity = ManuscriptManager
					.getManuscriptParameterById(context, relationId, ManuscriptParameterType.endactivetime);
			Date actEndTim = TimeUtil
					.getDate(endManuscriptParameterEntity == null ? null : endManuscriptParameterEntity.getParameter());
			response.setActEndTime(actEndTim);
			response.setActStartTime(actStartTim);
			// 添加活动类型
			pluginEntity = PluginManager.queryActivePluginByRelationId(context, relationId);
			if (pluginEntity == null || pluginEntity.getActivePluginType() == null) {
				response.setActiveType("-1");
			} else {
				response.setActiveType(pluginEntity.getActivePluginType().getValue() + "");
			}
			break;
		default:
			break;
		}

	}

	/**
	 * 发送联系人消息队列
	 * 
	 * @param context
	 * @param request
	 * @param drawPrizeRecord
	 * @throws DaoAppException
	 * @throws BlAppException
	 */
	private void sendContactQueue(RequestContext context, String relationId, String phone, String name,
			ContactType type) throws DaoAppException, BlAppException {
		logger.info("获取relationId:" + relationId + "的用户id");
		ManuscriptEntity manuscriptEntity = ManuscriptManager.getManuscriptById(context, relationId);
		String userId = manuscriptEntity.getUserId();
		if (manuscriptEntity == null || Str.isNullOrEmpty(userId)) {
			return;
		}
		logger.info("用户id:" + userId);
		ContactMsgDto contactMsgDto = new ContactMsgDto();
		contactMsgDto.setContactType(type);
		contactMsgDto.setName(name);
		contactMsgDto.setContext(phone);
		contactMsgDto.setUserId(userId);
		ContactMsg<ContactMsgDto> msg = null;
		msg = new ContactMsg<ContactMsgDto>();
		msg.setMsgContent(contactMsgDto);
		QueueManager.getInstance().put(msg);
	}

}
