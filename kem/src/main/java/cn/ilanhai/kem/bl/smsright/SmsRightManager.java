package cn.ilanhai.kem.bl.smsright;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.ilanhai.framework.app.RequestContext;
import cn.ilanhai.framework.app.dao.Dao;
import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.exception.BlAppException;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.framework.uitl.Str;
import cn.ilanhai.kem.bl.BLContextUtil;
import cn.ilanhai.kem.bl.contacts.ContactManager;
import cn.ilanhai.kem.bl.customer.CustomerManager;
import cn.ilanhai.kem.bl.member.MemberManager;
import cn.ilanhai.kem.common.CodeTable;
import cn.ilanhai.kem.dao.smsright.SmsRightDao;
import cn.ilanhai.kem.domain.paymentservice.PayInfo.PayInfoServiceInfoEntity;
import cn.ilanhai.kem.domain.smsright.AddSmsQuantityEntity;
import cn.ilanhai.kem.domain.smsright.SearchSmsQuantityByUser;
import cn.ilanhai.kem.domain.smsright.SmsEntity;
import cn.ilanhai.kem.domain.smsright.SmsInfoEntity;
import cn.ilanhai.kem.domain.smsright.SmsRightEntity;
import cn.ilanhai.kem.domain.smsright.dto.QueryOneSmsEntityDto;
import cn.ilanhai.kem.domain.smsright.dto.QuerySmsInfoEntityDto;
import cn.ilanhai.kem.domain.smsright.dto.SmsInfoDto;
import cn.ilanhai.kem.domain.smsright.dto.SmsLoadDtoRequest;
import cn.ilanhai.kem.domain.smsright.dto.SmsLoadDtoResponse;
import cn.ilanhai.kem.domain.smsright.dto.SmsSendDtoRequest;
import cn.ilanhai.kem.domain.smsright.dto.SmsSendDtoResponse;
import cn.ilanhai.kem.keyfac.KeyFactory;
import cn.ilanhai.kem.queue.QueueManager;
import cn.ilanhai.kem.queue.msg.SmsMsg;
import cn.ilanhai.kem.sms.protocol.tosms.ToSmsInfo;
import cn.ilanhai.kem.sms.protocol.tosms.TosmsSmsInfo;

public class SmsRightManager {
	private static Logger logger = Logger.getLogger(SmsRightManager.class);
	private static Class<?> currentclass = SmsRightDao.class;

	public static void buysmsmember(RequestContext context, PayInfoServiceInfoEntity info, String userId)
			throws BlAppException, DaoAppException {
		logger.info("用户：" + userId + "购买邮件数量：" + info.getQuantity());
		Integer quantity = 0;
		Integer total = 0;
		SmsRightEntity smsRightEntity = searchResidualQuantity(context, userId);
		if (smsRightEntity != null) {
			quantity = smsRightEntity.getQuantity();
			total = smsRightEntity.getTotal();
		}
		quantity = quantity + info.getQuantity();
		total = total + info.getQuantity();
		logger.info("购买前数量：" + quantity);
		addSmsQuantity(context, userId, quantity, total);
		logger.info("购买后数量：" + searchResidualQuantity(context, userId).getQuantity());
		logger.info("完成添加email数量");
	}

	private static void addSmsQuantity(RequestContext context, String userId, Integer quantity, Integer total)
			throws BlAppException, DaoAppException {
		BLContextUtil.valiParaItemIntegerNull(quantity, "短信数量");
		BLContextUtil.valiParaItemStrNullOrEmpty(userId, "用户编号");
		logger.info("当前用户[" + userId + "] 购买短信数量为:[" + quantity + "]");
		Dao dao = BLContextUtil.getDao(context, currentclass);
		AddSmsQuantityEntity smsQuantity = new AddSmsQuantityEntity();
		Date now = new Date();
		smsQuantity.setUserId(userId);
		smsQuantity.setQuantity(quantity);
		smsQuantity.setCreateTime(now);
		smsQuantity.setUpdateTime(now);
		smsQuantity.setTotal(total);
		dao.save(smsQuantity);
	}

	private static SmsRightEntity searchResidualQuantity(RequestContext context, String userId)
			throws BlAppException, DaoAppException {
		BLContextUtil.valiParaItemStrNullOrEmpty(userId, "用户编号");
		Dao dao = BLContextUtil.getDao(context, currentclass);
		SearchSmsQuantityByUser searchSms = new SearchSmsQuantityByUser();
		searchSms.setUserId(userId);
		SmsRightEntity sms = (SmsRightEntity) dao.query(searchSms, false);
		return sms;
	}

	public static SmsLoadDtoResponse load(RequestContext ctx, SmsLoadDtoRequest req)
			throws BlAppException, DaoAppException {
		if (req == null)
			throw new BlAppException(-1, "请求参数错误");
		if (Str.isNullOrEmpty(req.getSmsId()))
			throw new BlAppException(-1, "请求参数短信编号错误");
		Dao dao = ctx.getApplication().getApplicationContext().getBean(SmsRightDao.class);
		if (dao == null)
			throw new BlAppException(-1, "短信数据访问出错");
		QueryOneSmsEntityDto queryOneSmsEntityDto = null;
		queryOneSmsEntityDto = new QueryOneSmsEntityDto();
		queryOneSmsEntityDto.setSmsId(req.getSmsId());
		SmsEntity smsEntity = null;
		Entity entity = dao.query(queryOneSmsEntityDto, false);
		if (entity instanceof SmsEntity)
			smsEntity = (SmsEntity) entity;
		if (smsEntity == null)
			throw new BlAppException(-1, "短信数据不存在");
		QuerySmsInfoEntityDto querySmsInfoEntityDto = null;
		querySmsInfoEntityDto = new QuerySmsInfoEntityDto();
		querySmsInfoEntityDto.setSmsId(req.getSmsId());
		Iterator<Entity> iterator = null;
		iterator = dao.query(querySmsInfoEntityDto);
		SmsLoadDtoResponse response = null;
		response = new SmsLoadDtoResponse();
		if (iterator == null || !iterator.hasNext())
			return response;
		while (iterator.hasNext()) {
			SmsInfoEntity smsInfoEntity = (SmsInfoEntity) iterator.next();
			if (smsInfoEntity == null)
				continue;
			SmsInfoDto smsInfoDto = null;
			smsInfoDto = new SmsInfoDto();
			smsInfoDto.setKey(smsInfoEntity.getInfoKey());
			smsInfoDto.setContent(smsInfoEntity.getInfoValue());
			response.getInfo().add(smsInfoDto);
		}
		return response;
	}

	public static SmsSendDtoResponse send(RequestContext ctx, SmsSendDtoRequest req, String userId)
			throws BlAppException, DaoAppException {
		String contnet = null;
		String customers = null;
		Date sendDate = null;
		if (req == null)
			throw new BlAppException(-1, "请求参数错误");
		if (req.checkIsContainsOtherKey())
			throw new BlAppException(-1, "请求参数短信信息键错误");
		SmsInfoDto smsInfoDto = req.getOperate(SmsInfoEntity.KEY_CONTENT);
		if (smsInfoDto == null || Str.isNullOrEmpty(smsInfoDto.getContent()))
			throw new BlAppException(-1, "请求参数短信信息内容错误");
		contnet = smsInfoDto.getContent();

		if (contnet.length() > 300) {
			throw new BlAppException(-1, "短信内容太长了哦,最多只允许300个字哦");
		}

		req.getOperates().add(smsInfoDto);
		smsInfoDto = req.getOperate(SmsInfoEntity.KEY_SENDTYPE);
		String tmp = null;
		if (smsInfoDto == null || Str.isNullOrEmpty(smsInfoDto.getContent()))
			throw new BlAppException(-1, "请求参数短信信息发送类型错误");
		tmp = smsInfoDto.getContent();
		int v = 0;
		try {
			v = Integer.valueOf(tmp);
		} catch (Exception e) {
			throw new BlAppException(-1, "请求参数短信信息发送类型值只能为数字");
		}
		if (v <= 0 || v > 2)
			throw new BlAppException(-1, "请求参数短信信息发送类型值错误");
		smsInfoDto = req.getOperate(SmsInfoEntity.KEY_SENDTIME);
		if (smsInfoDto == null || Str.isNullOrEmpty(smsInfoDto.getContent()))
			throw new BlAppException(-1, "请设置发送时间");
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
			sendDate = format.parse(smsInfoDto.getContent());
		} catch (Exception e) {
			throw new BlAppException(-1, "请设置正确的发送时间");
		}
		smsInfoDto = req.getOperate(SmsInfoEntity.KEY_CUSTOMER);
		if (smsInfoDto == null || Str.isNullOrEmpty(smsInfoDto.getContent()))
			throw new BlAppException(-1, "请求参数短信信息联系人错误");
		if (!req.checkContracts(smsInfoDto.getContent()))
			throw new BlAppException(-1, "请求参数短信信息联系人数据错误");
		customers = smsInfoDto.getContent();
		if (Str.isNullOrEmpty(userId))
			throw new BlAppException(-1, "用户编号错误");
		String id = null;
		id = KeyFactory.newKey(KeyFactory.KEY_SMS);
		if (Str.isNullOrEmpty(id))
			throw new BlAppException(-1, "生成key出错");
		Dao dao = ctx.getApplication().getApplicationContext().getBean(SmsRightDao.class);
		if (dao == null)
			throw new BlAppException(-1, "短信数据访问出错");
		SmsEntity smsEntity = null;
		smsEntity = new SmsEntity();
		smsEntity.setCreateTime(new Date());
		smsEntity.setDelete(false);
		smsEntity.setUpdateTime(new Date());
		smsEntity.setUserId(userId);
		smsEntity.setSmsId(id);

		int val = dao.save(smsEntity);
		if (val <= 0)
			throw new BlAppException(-1, "保存短信出错");
		List<SmsInfoDto> items = req.getOperates();
		SmsInfoEntity smsInfoEntity = null;
		smsInfoEntity = new SmsInfoEntity();
		for (int i = 0; i < items.size(); i++) {
			smsInfoDto = items.get(i);
			if (smsInfoDto == null)
				continue;
			smsInfoEntity.setEnable(true);
			smsInfoEntity.setSmsId(id);
			smsInfoEntity.setInfoKey(smsInfoDto.getKey());
			smsInfoEntity.setInfoValue(smsInfoDto.getContent());
			val = dao.save(smsInfoEntity);
			if (val <= 0)
				throw new BlAppException(-1, "保存短信信息出错");
		}
		TosmsSmsInfo tosmsSmsInfo = new TosmsSmsInfo();
		tosmsSmsInfo.setContent(contnet);
		// 获取群组的电话
		tosmsSmsInfo
				.setPhone(CustomerManager.getPhoneNumberByGroupId(ctx, Arrays.asList(customers.split(",")), userId));
		tosmsSmsInfo.setSendTime(sendDate);
		if (tosmsSmsInfo.getPhone().size() <= 0) {
			throw new BlAppException(-1, "请添加发送客户");
		}
		SmsMsg<TosmsSmsInfo> msg = null;
		msg = new SmsMsg<TosmsSmsInfo>();
		msg.setMsgContent(tosmsSmsInfo);

		int count = calSmsCount(contnet);
		// 扣次次数
		/**
		 * 1.5版本前的短信次数扣除 SearchSmsQuantityByUser searchSms = null; searchSms =
		 * new SearchSmsQuantityByUser(); searchSms.setUserId(userId);
		 * SmsRightEntity smsRightEntity = null; Entity entity =
		 * dao.query(searchSms, false); if (entity instanceof SmsRightEntity)
		 * smsRightEntity = (SmsRightEntity) entity; if (smsRightEntity == null)
		 * throw new BlAppException(CodeTable.BL_SMS_ERROR.getValue(),
		 * "扣除发送短信次数出错"); Integer quantity = smsRightEntity.getQuantity();
		 * 
		 * if (quantity == null || quantity < count *
		 * tosmsSmsInfo.getPhone().size()) throw new
		 * BlAppException(CodeTable.BL_SMS_ERROR.getValue(), "发送短信次数不够");
		 * smsRightEntity.setQuantity(quantity - count *
		 * tosmsSmsInfo.getPhone().size()); AddSmsQuantityEntity
		 * addSmsQuantityEntity = null; addSmsQuantityEntity = new
		 * AddSmsQuantityEntity();
		 * addSmsQuantityEntity.setUserId(smsRightEntity.getUserId());
		 * addSmsQuantityEntity.setQuantity(smsRightEntity.getQuantity());
		 * addSmsQuantityEntity.setUpdateTime(new Date());
		 * addSmsQuantityEntity.setCreateTime(new Date());
		 * addSmsQuantityEntity.setTotal(smsRightEntity.getTotal());
		 * 
		 * val = dao.save(addSmsQuantityEntity); if (val <= 0) throw new
		 * BlAppException(CodeTable.BL_SMS_ERROR.getValue(), "扣除发送短信次数出错");
		 **/
		// 扣除发送邮件次数，payInfoServiceId
		MemberManager.useUserServiceResources(ctx, userId,
				PayInfoServiceInfoEntity.getServiceIdByType(PayInfoServiceInfoEntity.SMS),
				count * tosmsSmsInfo.getPhone().size());

		// 添加发送数量条目
		addSendAmount(id, dao, tosmsSmsInfo, count);

		QueueManager.getInstance().put(msg);
		SmsSendDtoResponse response = null;
		response = new SmsSendDtoResponse();
		response.setSmsId(id);
		return response;
	}

	/**
	 * 申请试用发送账号密码到用户手机
	 * 
	 * @param id
	 * @param dao
	 * @param tosmsSmsInfo
	 * @param count
	 * @throws BlAppException
	 */
	@Transactional(rollbackFor = { Throwable.class }, propagation = Propagation.REQUIRED)
	public static void simpleSend(RequestContext ctx, ToSmsInfo smsInfo, String userId) throws BlAppException {
		String id = KeyFactory.newKey(KeyFactory.KEY_SMS);
		if (Str.isNullOrEmpty(id))
			throw new BlAppException(-1, "生成key出错");
		Dao dao = ctx.getApplication().getApplicationContext().getBean(SmsRightDao.class);
		if (dao == null)
			throw new BlAppException(-1, "短信数据访问出错");
		SmsEntity smsEntity = null;
		smsEntity = new SmsEntity();
		smsEntity.setCreateTime(new Date());
		smsEntity.setDelete(false);
		smsEntity.setUpdateTime(new Date());
		smsEntity.setUserId(userId);
		smsEntity.setSmsId(id);

		try {
			dao.save(smsEntity);
		} catch (DaoAppException e) {
			throw new BlAppException(-1, "保存短信信息出错");
		}
		List<SmsInfoDto> items = smsInfo.getList();
		SmsInfoEntity smsInfoEntity = null;
		SmsInfoDto smsInfoDto=null;
		smsInfoEntity = new SmsInfoEntity();
		for (int i = 0; i < items.size(); i++) {
			smsInfoDto = items.get(i);
			if (smsInfoDto == null)
				continue;
			smsInfoEntity.setEnable(true);
			smsInfoEntity.setSmsId(id);
			smsInfoEntity.setInfoKey(smsInfoDto.getKey());
			smsInfoEntity.setInfoValue(smsInfoDto.getContent());
			try {
				dao.save(smsInfoEntity);
			} catch (DaoAppException e) {
				throw new BlAppException(-1, "保存短信信息出错");
			}
		}

		TosmsSmsInfo tosmsSmsInfo = new TosmsSmsInfo();
		tosmsSmsInfo.setContent(smsInfo.getContent());
		// 获取群组的电话
		List<String> list = new ArrayList<String>();
		list.add(smsInfo.getPhone());
		tosmsSmsInfo.setPhone(list);
		tosmsSmsInfo.setSendTime(smsInfo.getSendTime());
		SmsMsg<TosmsSmsInfo> msg = null;
		msg = new SmsMsg<TosmsSmsInfo>();
		msg.setMsgContent(tosmsSmsInfo);

		int count = calSmsCount(smsInfo.getContent());
		// 添加发送数量条目
		try {
			addSendAmount(id, dao, tosmsSmsInfo, count);
		} catch (DaoAppException e) {
			throw new BlAppException(-1, "保存短信信息出错");
		}
		QueueManager.getInstance().put(msg);
	}

	/**
	 * 添加发送数量
	 * 
	 * @param id
	 * @param dao
	 * @param tosmsSmsInfo
	 * @param count
	 * @throws DaoAppException
	 * @throws BlAppException
	 */
	private static void addSendAmount(String id, Dao dao, TosmsSmsInfo tosmsSmsInfo, int count)
			throws DaoAppException, BlAppException {
		int val;
		SmsInfoEntity smsInfoEntity = new SmsInfoEntity();
		smsInfoEntity.setEnable(true);
		smsInfoEntity.setSmsId(id);
		smsInfoEntity.setInfoKey(SmsInfoEntity.KEY_SENDAMOUNT);
		smsInfoEntity.setInfoValue(String.format("%s", count * tosmsSmsInfo.getPhone().size()));
		val = dao.save(smsInfoEntity);
		if (val <= 0)
			throw new BlAppException(-1, "保存短信信息出错");
	}

	/**
	 * 将内容换算成短信条数
	 * 
	 * @param str
	 * @return
	 */
	private static int calSmsCount(String str) {
		if (Str.isNullOrEmpty(str))
			return 0;
		if (str.length() <= CONTENT_MAX_LENGTH)
			return 1;
		int count = str.length() / CONTENT_SEPARATES_MAX_LENTTH;
		if (str.length() % CONTENT_SEPARATES_MAX_LENTTH != 0)
			count++;
		return count;
	}

	/**
	 * 短信发送的最大长度
	 */
	private static final int CONTENT_MAX_LENGTH = 70;
	/**
	 * 超过了短信发送的最长度，分隔值
	 */
	private static final int CONTENT_SEPARATES_MAX_LENTTH = 67;
}
