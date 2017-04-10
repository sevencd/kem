package cn.ilanhai.kem.bl.user.manageuser.impl;

import java.util.ArrayList;
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
import cn.ilanhai.framework.common.exception.CacheContainerException;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.framework.common.exception.SessionContainerException;
import cn.ilanhai.framework.common.session.enums.SessionStateType;
import cn.ilanhai.kem.bl.BLContextUtil;
import cn.ilanhai.kem.bl.BaseBl;
import cn.ilanhai.kem.bl.bindhost.BindHostManager;
import cn.ilanhai.kem.bl.paymentservice.OrderManager;
import cn.ilanhai.kem.bl.user.frontuser.UserRelationManger;
import cn.ilanhai.kem.bl.user.manageuser.ManageUser;
import cn.ilanhai.kem.dao.user.backuser.BackuserDao;
import cn.ilanhai.kem.dao.user.backuser.frontuser.BackuserManagerFrontDao;
import cn.ilanhai.kem.domain.CountDto;
import cn.ilanhai.kem.domain.MapEntity;
import cn.ilanhai.kem.domain.enums.MemberType;
import cn.ilanhai.kem.domain.enums.UserRelationType;
import cn.ilanhai.kem.domain.enums.UserStatus;
import cn.ilanhai.kem.domain.paymentservice.PayInfo.PayInfoServiceEntity;
import cn.ilanhai.kem.domain.user.frontuser.BackuserManageFromtUserInfoEntity;
import cn.ilanhai.kem.domain.user.frontuser.LoadReturnUserInfoByEdtion;
import cn.ilanhai.kem.domain.user.frontuser.LoadUserInfoByEditionDto;
import cn.ilanhai.kem.domain.user.frontuser.SearchFrontuserResponseDto;
import cn.ilanhai.kem.domain.user.manageuser.QueryCondition.GetUserDetailQCondition;

/**
 * 用户管理
 * 
 * @author Nature
 *
 */
@Component("manageuser")
public class ManageUserImpl extends BaseBl implements ManageUser {
	private Logger logger = Logger.getLogger(ManageUserImpl.class);

	@Override
	@InterfaceDocAnnotation(methodVersion = "1.0.0")
	public boolean setstate(RequestContext context) throws BlAppException, DaoAppException {
		// 判断后台用户是否已登陆
		BLContextUtil.checkBackUserLogined(context);
		// 获取参数
		BackuserManageFromtUserInfoEntity request = context.getDomain(BackuserManageFromtUserInfoEntity.class);
		// 验证入参是否为空
		valiPara(request);
		BLContextUtil.valiParaNotNull(request.getStatus(), "状态");
		BLContextUtil.valiParaItemStrNullOrEmpty(request.getUser_id(), "用户id");

		logger.info("开始修改会员状态");
		Dao dao = null;
		dao = BLContextUtil.getDao(context, BackuserManagerFrontDao.class);
		int val = dao.save(request);
		if (val == -1) {
			return false;
		}
		logger.info("修改会员状态成功");
		if (request.getStatus() == 0) {
			// 去掉redis中推广对应域名
			BindHostManager manager = new BindHostManager(context);
			try {
				manager.disableHost(request.getUser_id());
			} catch (AppException | CacheContainerException e) {
				e.printStackTrace();
			} catch (SessionContainerException e) {
				e.printStackTrace();
			}
			;
		}
		return true;
	}

	@Override
	@InterfaceDocAnnotation(methodVersion = "1.4")
	public Entity loaduserinfo(RequestContext context) throws BlAppException, DaoAppException {
		// 判断后台用户是否已登陆
		this.checkBackUserLogined(context);
		// 获取请求对象
		LoadUserInfoByEditionDto request = context.getDomain(LoadUserInfoByEditionDto.class);
		// 请求不可为空
		BLContextUtil.valiDomainIsNull(request, "请求对象");
		BLContextUtil.valiParaNotNull(request.getPageSize(), "读取条数");
		BLContextUtil.valiParaNotNull(request.getStartCount(), "开始条数");
		// 获取dao
		Dao dao = BLContextUtil.getDao(context, BackuserManagerFrontDao.class);
		this.valiDaoIsNull(dao, "加载用户信息");
		//只查主账号
		request.setRelationType(UserRelationType.MAINUSER.getValue());
		// 查询结果
		Iterator<Entity> pagedData = dao.query(request);
		CountDto count = (CountDto) dao.query(request, false);
		// 将迭代器转换为List<LoadReturnUserInfoByEdtion>
		List<LoadReturnUserInfoByEdtion> list = this.transformationForType(pagedData);
		for (LoadReturnUserInfoByEdtion info : list) {
			String memberType = info.getPackageServiceId();
			if (memberType == null) {
				if (Integer.valueOf(info.getStatus()) == UserStatus.Trial.getValue()) {
					info.setMemberType(MemberType.ApplyTrial.getName());
				} else {
					info.setMemberType(MemberType.TrialEdition.getName());
				}
			} 
			if (info.getStatus()==2) {
				info.setStatus(UserStatus.ENABLE.getValue());
			}

		}
		// 组织返回值
		SearchFrontuserResponseDto response = new SearchFrontuserResponseDto();
		response.setPageSize(request.getPageSize());
		response.setStartCount(request.getStartCount());
		response.setList(list);
		response.setTotalCount(count.getCount());
		return response;
	}

	/**
	 * 迭代器按类型转换为list
	 * 
	 * @param datas
	 * @return
	 */
	private List<LoadReturnUserInfoByEdtion> transformationForType(Iterator<Entity> datas) {
		List<LoadReturnUserInfoByEdtion> result = new ArrayList<LoadReturnUserInfoByEdtion>();
		while (datas.hasNext()) {
			result.add((LoadReturnUserInfoByEdtion) datas.next());
		}
		return result;
	}

	@Override
	@InterfaceDocAnnotation(methodVersion = "1.4")
	public Map<String, Object> getuserdetail(RequestContext context) throws BlAppException, DaoAppException {
		// 获取请求对象
		String request;
		try {

			if (context.getSession().getSessionState().getSessionStateType()
					.equals(SessionStateType.BACK_USER_LOGINED_STATE)) {
				request = context.getDomain(String.class);
			} else if (context.getSession().getSessionState().getSessionStateType()
					.equals(SessionStateType.FRONT_USER_LOGINED_STATE)) {
				request = BLContextUtil.getSessionUserId(context);
			} else {
				throw new BlAppException(-1, "用户没有登录");
			}

			// 参数校验
			BLContextUtil.valiParaNotNull(request, "用户ID");

			// 获取dao
			Dao dao = null;
			dao = BLContextUtil.getDao(context, BackuserDao.class);
			this.valiDaoIsNull(dao, "加载用户信息");

			GetUserDetailQCondition condition = new GetUserDetailQCondition();
			condition.setUserId(request);

			// 组织返回值
			MapEntity response = (MapEntity) dao.query(condition, false);
			// 返回值不可为空
			BLContextUtil.valiDomainIsNull(response, "用户信息");
			Map<String, Object> data = response.getData();
			Object memberType = data.get("packageServiceId");
			if (memberType == null) {
				if ((int) data.get("status") == UserStatus.Trial.getValue()) {
					data.put("memberType", MemberType.ApplyTrial.getName());
				} else {
					data.put("memberType", MemberType.TrialEdition.getName());
				}
			} 
			return data;
		} catch (Exception e) {
			throw new BlAppException(-1, e.getMessage(), e);
		}

	}

	@Override
	@InterfaceDocAnnotation(methodVersion = "1.4")
	@Transactional(rollbackFor = { Throwable.class }, propagation = Propagation.REQUIRED)
	public void updateTrialState(RequestContext context) throws BlAppException {
		// 判断后台用户是否已登陆
		this.checkBackUserLogined(context);
		// 获取参数
		BackuserManageFromtUserInfoEntity request = context.getDomain(BackuserManageFromtUserInfoEntity.class);
		// 验证入参是否为空
		valiPara(request);
		this.valiParaItemStrNullOrEmpty(request.getUser_id(), "用户id");
		request.setStatus(UserStatus.ENABLE.getValue());
		logger.info("开始修改会员状态");
		Dao dao = null;
		int val;
		try {
			dao = BLContextUtil.getDao(context, BackuserManagerFrontDao.class);
			val = dao.save(request);
			this.valiSaveDomain(val, "修改会员状态");
		} catch (DaoAppException e) {
			throw new BlAppException(-1, e.getMessage(), e);
		}
		logger.info("修改会员状态成功");
	}
}
