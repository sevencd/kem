package cn.ilanhai.kem.bl.emailright;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import cn.ilanhai.framework.app.RequestContext;
import cn.ilanhai.framework.app.dao.Dao;
import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.exception.AppException;
import cn.ilanhai.framework.common.exception.BlAppException;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.framework.common.exception.SessionContainerException;
import cn.ilanhai.framework.uitl.ExpressionMatchUtil;
import cn.ilanhai.framework.uitl.FastJson;
import cn.ilanhai.framework.uitl.Str;
import cn.ilanhai.kem.bl.BLContextUtil;
import cn.ilanhai.kem.bl.contacts.ContactManager;
import cn.ilanhai.kem.bl.customer.CustomerManager;
import cn.ilanhai.kem.bl.member.MemberManager;
import cn.ilanhai.kem.common.CodeTable;
import cn.ilanhai.kem.dao.emailright.EmailRightDao;
import cn.ilanhai.kem.dao.emailright.SearchEmailDao;
import cn.ilanhai.kem.domain.email.AddEmailQuantityEntity;
import cn.ilanhai.kem.domain.email.EmailContractEntity;
import cn.ilanhai.kem.domain.email.EmailGroupEntity;
import cn.ilanhai.kem.domain.email.EmailRightEntity;
import cn.ilanhai.kem.domain.email.MailEntity;
import cn.ilanhai.kem.domain.email.MailInfoEntity;
import cn.ilanhai.kem.domain.email.MailInfoItemDto;
import cn.ilanhai.kem.domain.email.MailLoadDtoRequest;
import cn.ilanhai.kem.domain.email.MailLoadDtoResponse;
import cn.ilanhai.kem.domain.email.MailSaveRequest;
import cn.ilanhai.kem.domain.email.MailSaveResponse;
import cn.ilanhai.kem.domain.email.MailSendRequest;
import cn.ilanhai.kem.domain.email.QueryEmailContractDto;
import cn.ilanhai.kem.domain.email.QueryEmailContractRequest;
import cn.ilanhai.kem.domain.email.QueryEmailGroupDto;
import cn.ilanhai.kem.domain.email.QueryMailInfoDto;
import cn.ilanhai.kem.domain.email.QueryOneMailDto;
import cn.ilanhai.kem.domain.email.QueryOneMailInfoDto;
import cn.ilanhai.kem.domain.email.SaveContractRequest;
import cn.ilanhai.kem.domain.email.SearchEmailQuantityByUser;
import cn.ilanhai.kem.domain.paymentservice.PayInfo.PayInfoServiceInfoEntity;
import cn.ilanhai.kem.domain.smsright.AddSmsQuantityEntity;
import cn.ilanhai.kem.keyfac.KeyFactory;
import cn.ilanhai.kem.mail.AbstractMail;
import cn.ilanhai.kem.mail.protocol.Result;
import cn.ilanhai.kem.mail.protocol.sohu.SohuMailInfo;
import cn.ilanhai.kem.mail.protocol.sohu.label.AddLabelRequest;
import cn.ilanhai.kem.mail.protocol.sohu.label.AddLabelResponse;
import cn.ilanhai.kem.mail.sohu.GeneralSendSohuMailConfig;
import cn.ilanhai.kem.mail.sohu.SohuMail;
import cn.ilanhai.kem.queue.QueueManager;
import cn.ilanhai.kem.queue.msg.MailMsg;

public class EmailRightManager {

	private static Logger logger = Logger.getLogger(EmailRightManager.class);
	private static Class<?> currentclass = EmailRightDao.class;
	private static SimpleDateFormat format = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");

	public static void buyEmailMember(RequestContext context, PayInfoServiceInfoEntity info, String userId)
			throws BlAppException, DaoAppException {
		logger.info("用户：" + userId + "购买邮件数量：" + info.getQuantity());
		Integer quantity = 0;
		Integer total = 0;
		EmailRightEntity emailRightEntity = searchResidualQuantity(context, userId);
		if (emailRightEntity != null) {
			quantity = emailRightEntity.getRemainTimes();
			total = emailRightEntity.getTotal();
		}
		quantity = quantity + info.getQuantity();
		total = total + info.getQuantity();
		logger.info("购买前数量：" + quantity);
		addEmailQuantity(context, userId, quantity, total);
		logger.info("购买后数量：" + searchResidualQuantity(context, userId).getRemainTimes());
		logger.info("完成添加email数量");
	}

	private static EmailRightEntity searchResidualQuantity(RequestContext context, String userId)
			throws BlAppException, DaoAppException {
		BLContextUtil.valiParaItemStrNullOrEmpty(userId, "用户编号");
		Dao dao = BLContextUtil.getDao(context, currentclass);
		SearchEmailQuantityByUser searchEmail = new SearchEmailQuantityByUser();
		searchEmail.setUserId(userId);
		EmailRightEntity email = (EmailRightEntity) dao.query(searchEmail, false);
		return email;
	}

	private static void addEmailQuantity(RequestContext context, String userId, Integer addQuantity, Integer total)
			throws BlAppException, DaoAppException {
		BLContextUtil.valiParaItemIntegerNull(addQuantity, "邮件数量");
		BLContextUtil.valiParaItemStrNullOrEmpty(userId, "用户编号");
		logger.info("当前用户[" + userId + "] 购买邮件数量为:[" + addQuantity + "]");
		Dao dao = BLContextUtil.getDao(context, currentclass);
		AddEmailQuantityEntity emailQuantity = new AddEmailQuantityEntity();
		Date now = new Date();
		emailQuantity.setUserId(userId);
		emailQuantity.setQuantity(addQuantity);
		emailQuantity.setCreateTime(now);
		emailQuantity.setUpdateTime(now);
		emailQuantity.setTotal(total);
		dao.save(emailQuantity);
	}

	/**
	 * 加载
	 * 
	 * @param ctx
	 * @param req
	 * @return
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	public static MailLoadDtoResponse load(RequestContext ctx, MailLoadDtoRequest req)
			throws BlAppException, DaoAppException {
		if (req == null)
			throw new BlAppException(-1, "请求参数错误");
		if (Str.isNullOrEmpty(req.getMailId()))
			throw new BlAppException(-1, "请求参数邮件编号错误");
		Dao dao = ctx.getApplication().getApplicationContext().getBean(EmailRightDao.class);
		if (dao == null)
			throw new BlAppException(-1, "邮件数据访问对象出错");
		QueryOneMailDto queryOneMailDto = null;
		queryOneMailDto = new QueryOneMailDto();
		queryOneMailDto.setEmailId(req.getMailId());
		Entity mailEntity = dao.query(queryOneMailDto, false);
		if (!(mailEntity instanceof MailEntity))
			throw new BlAppException(-1, "加载邮件数据不存在");
		QueryMailInfoDto queryMailInfoDto = null;
		queryMailInfoDto = new QueryMailInfoDto();
		queryMailInfoDto.setEmailId(req.getMailId());
		Iterator<Entity> mailInfoEntity = dao.query(queryMailInfoDto);
		MailLoadDtoResponse response = null;

		// 组装返回值
		response = new MailLoadDtoResponse();
		if (mailInfoEntity == null)
			return response;
		while (mailInfoEntity.hasNext()) {
			MailInfoEntity e = (MailInfoEntity) mailInfoEntity.next();

			response.getInfo().put(e.getInfoKey(), e.getInfoValue());
		}
		return response;
	}

	public static MailSaveResponse save(RequestContext ctx, MailSaveRequest req, String userId) throws AppException {
		logger.info("save邮件开始");
		if (req == null)
			throw new BlAppException(-1, "请求参数错误");
		if (Str.isNullOrEmpty(req.getEmailId()))
			// throw new BlAppException(-1, "请求参数邮件编号错误");
			req.setEmailId("");
		List<MailInfoItemDto> info = null;
		info = req.getOperates();
		if (info == null || info.size() <= 0)
			throw new BlAppException(-1, "请求参数邮件信息错误");
		if (req.checkIsContainsOtherKey())
			throw new BlAppException(-1, "请求参数邮件信息键错误");
		MailInfoItemDto mailInfoItemDto = null;
		String tmp = null;
		mailInfoItemDto = req.getOperate(MailInfoEntity.KEY_CONTENT);
		if (mailInfoItemDto != null) {
			tmp = mailInfoItemDto.getContent();
			if (Str.isNullOrEmpty(tmp))
				throw new BlAppException(-1, "请求参数邮件信息内容错误");
		}
		mailInfoItemDto = req.getOperate(MailInfoEntity.KEY_TITLE);
		if (mailInfoItemDto != null) {
			tmp = mailInfoItemDto.getContent();
			if (Str.isNullOrEmpty(tmp))
				throw new BlAppException(-1, "请求参数邮件信息标题错误");
		}
		mailInfoItemDto = req.getOperate(MailInfoEntity.KEY_SENDTYPE);
		if (mailInfoItemDto != null) {
			tmp = mailInfoItemDto.getContent();
			int v = 0;
			try {
				v = Integer.valueOf(tmp);
			} catch (Exception e) {
				throw new BlAppException(-1, "请求参数邮件信息发送类型错只能为数字");
			}
			if (v <= 0 || v > 2)
				throw new BlAppException(-1, "请求参数邮件信息发送类型错误");
		}
		mailInfoItemDto = req.getOperate(MailInfoEntity.KEY_SENDTIME);

		if (mailInfoItemDto != null) {
			tmp = mailInfoItemDto.getContent();
			if (Str.isNullOrEmpty(tmp))
				throw new BlAppException(-1, "请求参数邮件信息发送时间错误");
			try {
				format.parse(mailInfoItemDto.getContent());
			} catch (Exception e) {
				throw new BlAppException(-1, "请求参数邮件信息发送时间格式错误");
			}
		} else {
			// 如果不指定时间为立即发送 给当前时间
			MailInfoItemDto mailInfoItemDtoSendTime = new MailInfoItemDto();
			mailInfoItemDtoSendTime.setKey(MailInfoEntity.KEY_SENDTIME);
			mailInfoItemDtoSendTime.setContent(format.format(new Date()));
			info.add(mailInfoItemDtoSendTime);
		}

		mailInfoItemDto = req.getOperate(MailInfoEntity.KEY_FROMNAME);
		if (mailInfoItemDto != null) {
			tmp = mailInfoItemDto.getContent();
			if (Str.isNullOrEmpty(tmp))
				throw new BlAppException(-1, "请求参数邮件信息发送名称错误");
		}
		Dao dao = ctx.getApplication().getApplicationContext().getBean(EmailRightDao.class);
		if (dao == null)
			throw new BlAppException(-1, "邮件数据访问对象出错");
		QueryOneMailDto queryOneMailDto = null;
		queryOneMailDto = new QueryOneMailDto();
		queryOneMailDto.setEmailId(req.getEmailId());
		Entity mailEntity = dao.query(queryOneMailDto, false);
		MailEntity mail = null;
		if (mailEntity instanceof MailEntity)
			mail = (MailEntity) mailEntity;
		String id;
		String labelId = null;
		id = KeyFactory.newKey(KeyFactory.KEY_EMAIL);
		logger.info("mailId:" + id);
		if (mail == null) {
			// 获取labelId
			labelId = getLabelId(ctx, id);
			logger.info("labelId:" + labelId);
			mail = new MailEntity();
			mail.setCreateTime(new Date());
			mail.setDelete(false);
			mail.setUserId(userId);
			mail.setEmailId(id);
		}
		// emailState: 1，草稿；2，已发送，不可空 由后端处理
		// 短信类型 sendType 1:立即发送 2定时发送
		// 邮件类型 sendType 1:立即发送 2定时发送
		mail.setUpdateTime(new Date());
		int val = dao.save(mail);
		if (val <= 0)
			throw new BlAppException(-1, "保存邮件出错");
		QueryOneMailInfoDto queryOneMailInfoDto = null;
		queryOneMailInfoDto = new QueryOneMailInfoDto();
		mailInfoItemDto = new MailInfoItemDto();
		mailInfoItemDto.setKey(MailInfoEntity.KEY_EMAILSTATE);
		mailInfoItemDto.setContent("1");
		info.add(mailInfoItemDto);

		// 添加保存labelId
		if (!Str.isNullOrEmpty(labelId)) {
			MailInfoItemDto MailInfoLabelId = new MailInfoItemDto();
			MailInfoLabelId.setKey(MailInfoEntity.KEY_LABELID);
			MailInfoLabelId.setContent(labelId);
			info.add(MailInfoLabelId);
		}

		for (int i = 0; i < info.size(); i++) {
			mailInfoItemDto = info.get(i);
			queryOneMailInfoDto.setEmailId(mail.getEmailId());
			queryOneMailInfoDto.setKey(mailInfoItemDto.getKey());
			MailInfoEntity mailInfoEntity = (MailInfoEntity) dao.query(queryOneMailInfoDto, false);
			if (mailInfoEntity == null) {
				mailInfoEntity = new MailInfoEntity();
				mailInfoEntity.setEmailId(mail.getEmailId());
				mailInfoEntity.setEnable(true);
				mailInfoEntity.setInfoKey(mailInfoItemDto.getKey());
			}
			mailInfoEntity.setInfoValue(mailInfoItemDto.getContent());
			val = dao.save(mailInfoEntity);
			if (val <= 0)
				throw new BlAppException(-1, "保存邮件信息失败");
		}
		MailSaveResponse response = null;
		response = new MailSaveResponse();
		response.setEmailId(mail.getEmailId());
		return response;

	}

	private static String getLabelId(RequestContext ctx, String id) throws AppException {
		// 获取loablid
		AddLabelRequest addlabel = new AddLabelRequest();
		addlabel.setLabelName(BLContextUtil.getValue(ctx, "shouApiUser") + id);
		AbstractMail mailLable = new SohuMail();
		Result result = mailLable.addLabel(getGeneralSendConfig(ctx), addlabel);
		logger.info("获取labelId的返回:" + FastJson.bean2Json((result)));
		if (result instanceof AddLabelResponse) {
			AddLabelResponse addLabelResponse = (AddLabelResponse) result;
			try {
				return addLabelResponse.getInfo().getData().getLabelId();
			} catch (Exception e) {
				return null;
			}
		}
		return null;
	}

	/**
	 * 发送邮件
	 * 
	 * @param ctx
	 * @param req
	 * @return
	 * @throws SessionContainerException
	 * @throws AppException
	 */
	public static boolean send(RequestContext ctx, MailSendRequest req, String userId)
			throws SessionContainerException, AppException {
		if (req == null)
			throw new BlAppException(-1, "请求参数错误");
		if (Str.isNullOrEmpty(req.getEmailId()))
			throw new BlAppException(-1, "请求参数邮件编号错误");
		if (req.getSendType() < 1 || req.getSendType() > 2)
			throw new BlAppException(-1, "请求参数发送类型错误");
		if (req.getSendType() == 2) {
			if (Str.isNullOrEmpty(req.getEmailTitle()))
				throw new BlAppException(-1, "请求参数邮件标题错误");
			if (Str.isNullOrEmpty(req.getFromName()))
				throw new BlAppException(-1, "请求参数邮件名称错误");
			if (Str.isNullOrEmpty(req.getToEmail()))
				throw new BlAppException(-1, "请求参数邮件发送地址错误");
		}
		if (Str.isNullOrEmpty(userId))
			throw new BlAppException(-1, "用户编号错误");
		Dao dao = ctx.getApplication().getApplicationContext().getBean(EmailRightDao.class);
		if (dao == null)
			throw new BlAppException(-1, "邮件数据访问对象出错");
		QueryOneMailDto queryOneMailDto = null;
		queryOneMailDto = new QueryOneMailDto();
		queryOneMailDto.setEmailId(req.getEmailId());
		Entity mailIterator = dao.query(queryOneMailDto, false);
		if (!(mailIterator instanceof MailEntity))
			throw new BlAppException(-1, "加载邮件数据出错");
		QueryMailInfoDto queryMailInfoDto = null;
		queryMailInfoDto = new QueryMailInfoDto();
		queryMailInfoDto.setEmailId(req.getEmailId());
		Iterator<Entity> mailInfoIterator = dao.query(queryMailInfoDto);
		if (!mailInfoIterator.hasNext())
			throw new BlAppException(-1, "加载邮件信息数据出错");
		List<MailInfoEntity> mailInfo = null;
		mailInfo = new ArrayList<MailInfoEntity>();
		while (mailInfoIterator.hasNext())
			mailInfo.add((MailInfoEntity) mailInfoIterator.next());
		// QueryEmailContractDto queryEmailContractDto = null;
		// queryEmailContractDto = new QueryEmailContractDto();
		// queryEmailContractDto.setEmailId(req.getEmailId());
		// Iterator<Entity> emailContractIterator =
		// dao.query(queryEmailContractDto);

		QueryEmailGroupDto queryEmailGroupDto = null;
		queryEmailGroupDto = new QueryEmailGroupDto();
		queryEmailGroupDto.setEmailId(req.getEmailId());
		Iterator<Entity> emailGroupIterator = dao.query(queryEmailGroupDto);

		String title = getAttributeValue(mailInfo, MailInfoEntity.KEY_TITLE);
		String content = getAttributeValue(mailInfo, MailInfoEntity.KEY_CONTENT);
		String fromName = getAttributeValue(mailInfo, MailInfoEntity.KEY_FROMNAME);
		String labelId = getAttributeValue(mailInfo, MailInfoEntity.KEY_LABELID);
		// 验证labelId 并返回正确的labelId
		logger.info("验证前:" + labelId);
		labelId = checkLabelId(ctx, req, dao, labelId);
		logger.info("验证后:" + labelId);
		// List<String> to = getEmailContractValue(emailContractIterator);
		// 拼接 群组
		List<String> toGroup = getEmailGroupValue(emailGroupIterator);
		List<EmailContractEntity> groupContractList = CustomerManager.getEmailContractByGroupId(ctx, toGroup,
				BLContextUtil.getSessionUserId(ctx));
		if (req.getSendType() != 2) {
			if (groupContractList == null || toGroup == null || groupContractList.size() <= 0) {
				throw new BlAppException(-1, "无联系人请添加群组联系人");
			}

			// BLContextUtil.valiParaItemNumBetween(0, 5, toGroup.size(),
			// "最多选择5个群组", false);
		}

		String groupAddress = BLContextUtil.getValue(ctx, "groupAddress");
		// 发送测试邮件
		if (req.getSendType() == 2) {
			logger.info("发送测试邮件");
			title = req.getEmailTitle();
			fromName = req.getFromName();
			toGroup = new ArrayList<String>();
			if (!ExpressionMatchUtil.isEmailAddress(req.getToEmail()))
				throw new BlAppException(-1, "邮件发送地址格式错误");
			toGroup.add(req.getToEmail());
			groupAddress = "";
			logger.info("title:" + title);
			logger.info("fromName:" + fromName);
			logger.info("toGroup:" + req.getToEmail());
			logger.info("groupAddress:" + groupAddress);
		}
		if (Str.isNullOrEmpty(title))
			throw new BlAppException(-1, "邮件标题错误");
		if (Str.isNullOrEmpty(content))
			throw new BlAppException(-1, "邮件内容错误");
		if (Str.isNullOrEmpty(fromName))
			throw new BlAppException(-1, "邮件名称错误");
		if (toGroup == null || toGroup.size() <= 0)
			throw new BlAppException(-1, "邮件发送地址错误");
		logger.info("发送邮件的群组为：" + toGroup + "后缀" + groupAddress);
		SohuMailInfo sohuMailInfo = null;
		sohuMailInfo = new SohuMailInfo();
		sohuMailInfo.setHtml(content);
		sohuMailInfo.setSubject(title);
		sohuMailInfo.setLabelId(labelId);
		sohuMailInfo.setTo(toGroup);
		sohuMailInfo.setFromName(fromName);
		// 添加群组地址
		sohuMailInfo.setGroupAddress(groupAddress);
		MailMsg<SohuMailInfo> msg = null;
		msg = new MailMsg<SohuMailInfo>();
		msg.setMsgContent(sohuMailInfo);
		// 修邮件发送数量与状态
		MailInfoEntity mailInfoEntity = null;
		if (req.getSendType() == 1) {

			mailInfoEntity = getMailInfoEntity(mailInfo, MailInfoEntity.KEY_EMAILSTATE);
			if (mailInfoEntity == null) {
				mailInfoEntity = new MailInfoEntity();
				mailInfoEntity.setEnable(true);
				mailInfoEntity.setEmailId(req.getEmailId());
				mailInfoEntity.setInfoKey(MailInfoEntity.KEY_EMAILSTATE);
			}
			mailInfoEntity.setInfoValue("2");
			int val = dao.save(mailInfoEntity);
			if (val <= 0)
				throw new BlAppException(-1, "修改邮件发送状态失败");

			mailInfoEntity = getMailInfoEntity(mailInfo, MailInfoEntity.KEY_SENDTIME);
			if (mailInfoEntity == null) {
				mailInfoEntity = new MailInfoEntity();
				mailInfoEntity.setEnable(true);
				mailInfoEntity.setEmailId(req.getEmailId());
				mailInfoEntity.setInfoKey(MailInfoEntity.KEY_SENDTIME);
			}
			mailInfoEntity.setInfoValue(format.format(new Date()));
			val = dao.save(mailInfoEntity);
			if (val <= 0)
				throw new BlAppException(-1, "修改邮件发送时间失败");

		}

		mailInfoEntity = getMailInfoEntity(mailInfo, MailInfoEntity.KEY_SENDAMOUNT);
		if (mailInfoEntity == null) {
			mailInfoEntity = new MailInfoEntity();
			mailInfoEntity.setEnable(true);
			mailInfoEntity.setEmailId(req.getEmailId());
			mailInfoEntity.setInfoKey(MailInfoEntity.KEY_SENDAMOUNT);
		}
		Integer amount = req.getSendType() == 2 ? toGroup.size() : groupContractList.size();
		mailInfoEntity.setInfoValue(String.format("%s", amount));
		int val = dao.save(mailInfoEntity);
		if (val <= 0)
			throw new BlAppException(-1, "修改邮件发送数量失败");
		// 扣次数
		logger.info("开始扣邮件次数");
		/**
		 * 老版本的扣除邮件次数，1.5版本修改 SearchEmailQuantityByUser condition = null;
		 * condition = new SearchEmailQuantityByUser();
		 * condition.setUserId(userId); Entity entity = dao.query(condition,
		 * false); EmailRightEntity emailRightEntity = null; if (entity
		 * instanceof EmailRightEntity) emailRightEntity = (EmailRightEntity)
		 * entity; if (emailRightEntity == null) throw new
		 * BlAppException(CodeTable.BL_EMAIL_ERROR.getValue(), "扣除发送邮件次数出错");
		 * Integer tmp = emailRightEntity.getRemainTimes(); int sendSize =
		 * req.getSendType() == 2 ? toGroup.size() : groupContractList.size();
		 * logger.info("sendType:" + req.getSendType()); logger.info("sendSize:"
		 * + sendSize); if (tmp == null || tmp < sendSize) throw new
		 * BlAppException(CodeTable.BL_EMAIL_ERROR.getValue(), "发送邮件次数不够");
		 * emailRightEntity.setRemainTimes(tmp - sendSize);
		 * AddEmailQuantityEntity addEmailQuantityEntity = null;
		 * addEmailQuantityEntity = new AddEmailQuantityEntity();
		 * addEmailQuantityEntity.setUserId(userId);
		 * addEmailQuantityEntity.setCreateTime(new Date());
		 * addEmailQuantityEntity.setQuantity(emailRightEntity.getRemainTimes());
		 * addEmailQuantityEntity.setUpdateTime(new Date());
		 * addEmailQuantityEntity.setTotal(emailRightEntity.getTotal()); val =
		 * dao.save(addEmailQuantityEntity); if (val <= 0) throw new
		 * BlAppException(CodeTable.BL_EMAIL_ERROR.getValue(), "扣除发送邮件次数出错");
		 **/

		// 扣除发送邮件次数，payInfoServiceId
		MemberManager.useUserServiceResources(ctx, userId,
				PayInfoServiceInfoEntity.getServiceIdByType(PayInfoServiceInfoEntity.EMAIL), amount);

		QueueManager.getInstance().put(msg);
		return true;
	}

	private static String checkLabelId(RequestContext ctx, MailSendRequest req, Dao dao, String labelId)
			throws AppException, DaoAppException, BlAppException {
		if (Str.isNullOrEmpty(labelId)) {
			labelId = saveLabelId(ctx, req, dao);
			return labelId;
		}
		try {
			Integer.parseInt(labelId);
		} catch (Exception e) {
			labelId = saveLabelId(ctx, req, dao);
		}
		return labelId;
	}

	private static String saveLabelId(RequestContext ctx, MailSendRequest req, Dao dao)
			throws AppException, DaoAppException, BlAppException {
		String labelId;
		labelId = getLabelId(ctx, req.getEmailId());
		MailInfoEntity mailInfoEntity = null;
		if (mailInfoEntity == null) {
			mailInfoEntity = new MailInfoEntity();
			mailInfoEntity.setEmailId(req.getEmailId());
			mailInfoEntity.setEnable(true);
			mailInfoEntity.setInfoKey(MailInfoEntity.KEY_LABELID);
		}
		mailInfoEntity.setInfoValue(labelId);
		int val = dao.save(mailInfoEntity);
		if (val <= 0)
			throw new BlAppException(-1, "保存邮件labelId失败");
		return labelId;
	}

	private static List<String> getEmailContractValue(Iterator<Entity> iterator) {
		if (iterator == null || !iterator.hasNext())
			return null;
		List<String> list = new ArrayList<String>();

		while (iterator.hasNext()) {
			EmailContractEntity emailContractEntity = (EmailContractEntity) iterator.next();
			if (emailContractEntity == null || Str.isNullOrEmpty(emailContractEntity.getEmailAddr()))
				continue;
			list.add(emailContractEntity.getEmailAddr());

		}
		return list;
	}

	private static List<String> getEmailGroupValue(Iterator<Entity> iterator) {
		if (iterator == null || !iterator.hasNext())
			return null;
		List<String> list = new ArrayList<String>();

		while (iterator.hasNext()) {
			EmailGroupEntity emailGroupEntity = (EmailGroupEntity) iterator.next();
			if (emailGroupEntity == null || Str.isNullOrEmpty(emailGroupEntity.getGroupId()))
				continue;
			// TODO 拼接群组地址
			list.add(emailGroupEntity.getGroupId());

		}
		return list;
	}

	public static String getAttributeValue(List<MailInfoEntity> mailInfo, String key) {
		if (mailInfo == null || mailInfo.size() <= 0)
			return null;
		for (int i = 0; i < mailInfo.size(); i++)
			if (mailInfo.get(i).getInfoKey().equals(key))
				return mailInfo.get(i).getInfoValue();
		return null;
	}

	private static MailInfoEntity getMailInfoEntity(List<MailInfoEntity> mailInfo, String key) {
		if (mailInfo == null || mailInfo.size() <= 0)
			return null;
		for (int i = 0; i < mailInfo.size(); i++)
			if (mailInfo.get(i).getInfoKey().equals(key))
				return mailInfo.get(i);
		return null;
	}

	private static GeneralSendSohuMailConfig getGeneralSendConfig(RequestContext ctx) {
		GeneralSendSohuMailConfig config = new GeneralSendSohuMailConfig();
		Object tmp = null;
		tmp = BLContextUtil.getValue(ctx, "shouApiKey");
		if (tmp instanceof String)
			config.setApiKey((String) tmp);
		tmp = BLContextUtil.getValue(ctx, "shouApiUser");
		if (tmp instanceof String)
			config.setApiUser((String) tmp);
		tmp = BLContextUtil.getValue(ctx, "shouFrom");
		if (tmp instanceof String)
			config.setFrom((String) tmp);
		return config;

	}

	/**
	 * 保存邮件和群主
	 * 
	 * @param context
	 * @param userId
	 * @param groupId
	 * @param emailId
	 * @return
	 * @throws BlAppException
	 */
	public static boolean saveGroupCustomer(RequestContext context, String userId, String groupId, String emailId)
			throws BlAppException {
		SaveContractRequest saveRequest;
		Dao dao = null;
		CodeTable ct;
		try {
			List<String> groupList = new ArrayList<String>();
			groupList.add(groupId);
			List<EmailGroupEntity> groupts = null;
			if (groupList != null && groupList.size() >= 0) {
				// BLContextUtil.valiParaItemNumBetween(0, 5, groupList.size(),
				// "最多选择5个群组", false);
				// 获取群组
				groupts = ContactManager.bulidEmailGroupEntityByGroupId(context, groupList, userId);
				logger.info("封装群组:" + groupts);
			}
			if (groupts != null && groupts.size() > 0) {
				logger.info("封装群组:" + groupts);
				List<EmailContractEntity> groupContractList = CustomerManager.getEmailContractByGroupId(context,
						groupList, BLContextUtil.getSessionUserId(context));
				if (groupContractList == null || groupContractList.size() <= 0) {
					throw new BlAppException(-1, "请添加客户");
				}
				saveRequest = new SaveContractRequest();
				saveRequest.setGroupList(groupts);
				saveRequest.setEmailId(emailId);
				dao = BLContextUtil.getDao(context, EmailRightDao.class);
				logger.info("saveRequest:" + saveRequest);
				int val = dao.save(saveRequest);
				if (val == -100) {
					throw new BlAppException(-1, "邮件id：" + emailId + "不存在");
				}
				logger.info("val" + val);
				return true;
			} else {
				logger.info("群组为空");
				throw new BlAppException(-1, "保存客户异常");
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
}
