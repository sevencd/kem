package cn.ilanhai.kem.bl.user.trafficuser.impl;

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
import cn.ilanhai.framework.common.session.Session;
import cn.ilanhai.framework.uitl.ExpressionMatchUtil;
import cn.ilanhai.kem.bl.BaseBl;
import cn.ilanhai.kem.bl.customer.CustomerManager;
import cn.ilanhai.kem.bl.extension.ExtensionManager;
import cn.ilanhai.kem.bl.manuscript.ManuscriptManager;
import cn.ilanhai.kem.bl.plugin.PluginManager;
import cn.ilanhai.kem.bl.user.trafficuser.Trafficuser;
import cn.ilanhai.kem.bl.user.trafficuser.TrafficuserManager;
import cn.ilanhai.kem.common.CodeTable;
import cn.ilanhai.kem.common.Constant;
import cn.ilanhai.kem.dao.user.trafficuser.MyBatisTrafficuserDao;
import cn.ilanhai.kem.dao.user.trafficuser.TrafficuserPluginDao;
import cn.ilanhai.kem.domain.CountDto;
import cn.ilanhai.kem.domain.IdEntity;
import cn.ilanhai.kem.domain.enums.ManuscriptParameterType;
import cn.ilanhai.kem.domain.enums.TrafficuserType;
import cn.ilanhai.kem.domain.enums.UserType;
import cn.ilanhai.kem.domain.extension.ExtensionEntity;
import cn.ilanhai.kem.domain.manuscript.ManuscriptEntity;
import cn.ilanhai.kem.domain.plugin.activeplugin.dto.DrawPrizeRequestDto;
import cn.ilanhai.kem.domain.plugin.activeplugin.dto.QueryActivePluginByRelationId;
import cn.ilanhai.kem.domain.plugin.activeplugin.dto.QueryDrawprizeDataDto;
import cn.ilanhai.kem.domain.plugin.activeplugin.entity.ActivePluginEntity;
import cn.ilanhai.kem.domain.plugin.activeplugin.entity.DrawOptionEntity;
import cn.ilanhai.kem.domain.user.trafficuser.QueryTrafficUserEntity;
import cn.ilanhai.kem.domain.user.trafficuser.QueryTrafficUserResponseDto;
import cn.ilanhai.kem.domain.user.trafficuser.QueryTrafficUserTypeEntity;
import cn.ilanhai.kem.domain.user.trafficuser.TrafficuserEntity;

@Component("trafficuser")
public class TrafficuserImpl extends BaseBl implements Trafficuser {

	private static String desc = "活动插件";
	private static Logger logger = Logger.getLogger(TrafficuserImpl.class);
	@Override
	@InterfaceDocAnnotation(methodVersion = "1.0.0")
	@Transactional(rollbackFor = { AppException.class, Exception.class,
			Throwable.class }, propagation = Propagation.REQUIRED)
	public Entity load(RequestContext context) throws BlAppException, DaoAppException {
		CodeTable ct;
		Dao dao;
		try {
			// 验证登录
			this.checkFrontUserLogined(context);
			// 验证普通用户
			this.checkFrontUserType(context, UserType.GENERAL_USER);

			Integer userId = context.getDomain(Integer.class);
			this.valiParaItemIntegerNull(userId, "流量用户编号");
			IdEntity<Integer> id = new IdEntity<Integer>();
			id.setId(userId);
			dao = this.daoProxyFactory.getDao(context, MyBatisTrafficuserDao.class);
			this.valiDaoIsNull(dao, "流量用户信息");
			TrafficuserEntity trafficuserEntity = (TrafficuserEntity) dao.query(id, false);
			this.valiDomainIsNull(trafficuserEntity, "用户不存在", false);
			// this.valiParaNotNull(trafficuserEntity, "流量用户信息");
			return trafficuserEntity;
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
	public void save(RequestContext context) throws BlAppException, DaoAppException {
		CodeTable ct;
		Dao dao;
		try {
			// 验证登录
			this.checkFrontUserLogined(context);
			// 验证普通用户
			this.checkFrontUserType(context, UserType.GENERAL_USER);
			TrafficuserEntity trafficuserEntity = context.getDomain(TrafficuserEntity.class);
			this.valiDomainIsNull(trafficuserEntity, "流量用户信息");
			this.valiParaItemStrNullOrEmpty(trafficuserEntity.getPhoneNo(), "客户手机号");

			this.valiParaItemStrNullOrEmpty(trafficuserEntity.getName(), "客户名");
			this.valiParaItemStrLength(trafficuserEntity.getName(), 20, "客户名");

			if (!ExpressionMatchUtil.isPhoneNo(trafficuserEntity.getPhoneNo())) {
				throw new BlAppException(CodeTable.BL_ACTIVEPLUGIN_DRAWPRIZE_ERROR.getValue(), "客户手机号错误");
			}

			if (!ExpressionMatchUtil.isEmailAddress(trafficuserEntity.getEmail())) {
				throw new BlAppException(CodeTable.BL_ACTIVEPLUGIN_DRAWPRIZE_ERROR.getValue(), "客户邮件地址错误");
			}

			if (!ExpressionMatchUtil.isQQ(trafficuserEntity.getQqNo())) {
				throw new BlAppException(CodeTable.BL_ACTIVEPLUGIN_DRAWPRIZE_ERROR.getValue(), "客户QQ号错误");
			}

			this.valiParaItemStrLength(trafficuserEntity.getRemark(), 50, "备注", false);
			this.valiParaItemStrLength(trafficuserEntity.getWechatNo(), 50, "微信号", false);
			this.valiParaItemStrLength(trafficuserEntity.getIndustry(), 50, "行业", false);
			this.valiParaItemStrLength(trafficuserEntity.getVocation(), 50, "职业", false);

			dao = this.daoProxyFactory.getDao(context, MyBatisTrafficuserDao.class);
			this.valiDaoIsNull(dao, "流量用户信息");
			int val = dao.save(trafficuserEntity);
			this.valiSaveDomain(val, "流量用户信息");
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
	public void saveuserinfo(RequestContext context) throws BlAppException, DaoAppException {
		// TODO Auto-generated method stub
		CodeTable ct;
		try {

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
			// 加载插件
			Dao dao = this.daoProxyFactory.getDao(context, TrafficuserPluginDao.class);

			List<Entity> response = transformation(dao.query(request));

			if (response.size() == 0) {
				QueryTrafficUserTypeEntity requestType = new QueryTrafficUserTypeEntity();
				requestType.setRelationId(request.getRelationId());
				Iterator<Entity> typeResponse = dao.query(requestType);
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
				
				Integer val = TrafficuserManager.saveTrafficuser(context, trafficuserEntity);
				if (val > 0) {
					ActivePluginEntity entity = PluginManager.getActivePlugin(context, request.getRelationId());
					this.valiActivePulginIsNull(entity);
					logger.info("插件信息为:" + entity);
					Session session = context.getSession();
					logger.info("封装客户信息");
					Map<String, String> infos = CustomerManager.buildInfo(context, request, entity);
					logger.info("客户信息：" + infos);
					ManuscriptEntity manuscriptEntity = ManuscriptManager.getManuscriptById(context, request.getRelationId());
					logger.info("向session中放入客户");
					session.setParameter(Constant.KEY_SESSION_ACTIVEPLUGIN_CUSTOMERID + entity.getActivePluginType().getValue(),
							CustomerManager.saveCustomer(context, infos, manuscriptEntity.getUserId(),
									ManuscriptManager.getTag(context, request.getRelationId(), ManuscriptParameterType.tag), null));
				}
			} else {
				ct = CodeTable.BL_COMMON_USER_REEXIST;
				throw new BlAppException(ct.getValue(), ct.getDesc());
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
	@InterfaceDocAnnotation(methodVersion = "1.0.0")
	@Transactional(rollbackFor = { AppException.class, Exception.class,
			Throwable.class }, propagation = Propagation.REQUIRED)
	public void delete(RequestContext context) throws BlAppException, DaoAppException {
		CodeTable ct;
		Dao dao;
		try {
			// 验证登录
			this.checkFrontUserLogined(context);
			// 验证普通用户
			this.checkFrontUserType(context, UserType.GENERAL_USER);

			TrafficuserEntity user = context.getDomain(TrafficuserEntity.class);
			this.valiDomainIsNull(user, "流量用户删除");
			this.valiParaItemIntegerNull(user.getTrafficuserId(), "流量用户编号");
			this.valiParaItemStrNullOrEmpty(user.getExtensionId(), "关联编号");

			ExtensionEntity extensionEntity = ExtensionManager.getExtensionEntityById(context, user.getExtensionId());
			this.valiDomainIsNull(extensionEntity, "关联编号所属推广为空");

			if (!this.getSessionUserId(context).equals(extensionEntity.getUserId())) {
				ct = CodeTable.BL_COMMON_USER_NOLIMIT;
				throw new BlAppException(ct.getValue(), ct.getDesc());
			}

			IdEntity<Integer> id = new IdEntity<Integer>();
			id.setId(user.getTrafficuserId());
			dao = this.daoProxyFactory.getDao(context, MyBatisTrafficuserDao.class);
			this.valiDaoIsNull(dao, "流量用户信息");
			int val = dao.delete(id);
			this.valiDeleteDomain(val, "流量用户信息");
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
	public Entity search(RequestContext context) throws BlAppException, DaoAppException {
		CodeTable ct;
		Dao dao;
		try {
			// 验证登录

			this.checkFrontUserLogined(context);
			// 验证普通用户
			this.checkFrontUserType(context, UserType.GENERAL_USER);

			QueryTrafficUserEntity queryTrafficUserEntity = context.getDomain(QueryTrafficUserEntity.class);
			this.valiDomainIsNull(queryTrafficUserEntity, "流量用户查询条件");
			dao = this.daoProxyFactory.getDao(context, MyBatisTrafficuserDao.class);
			this.valiDaoIsNull(dao, "流量用户信息");
			this.valiParaItemIntegerNull(queryTrafficUserEntity.getStartCount(), "起始值");
			this.valiParaItemIntegerNull(queryTrafficUserEntity.getPageSize(), "查询条数");
			this.valiParaItemObjectNull(queryTrafficUserEntity.getExtensionId(), "用户关联编号");
			ExtensionEntity extensionEntity = ExtensionManager.getExtensionEntityById(context,
					queryTrafficUserEntity.getExtensionId());
			this.valiDomainIsNull(extensionEntity, "推广编号所属推广为空");
			if (!this.getSessionUserId(context).equals(extensionEntity.getUserId())) {
				ct = CodeTable.BL_COMMON_USER_NOLIMIT;
				throw new BlAppException(ct.getValue(), ct.getDesc());
			}

			QueryTrafficUserResponseDto queryTrafficUserResponseDto = new QueryTrafficUserResponseDto();
			queryTrafficUserResponseDto.setExtensionId(queryTrafficUserEntity.getExtensionId());
			queryTrafficUserResponseDto.setList(dao.query(queryTrafficUserEntity));
			queryTrafficUserResponseDto.setPageSize(queryTrafficUserEntity.getPageSize());
			queryTrafficUserResponseDto.setStartCount(queryTrafficUserEntity.getStartCount());
			queryTrafficUserResponseDto.setTotalCount(((CountDto) dao.query(queryTrafficUserEntity, false)).getCount());
			return queryTrafficUserResponseDto;
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
