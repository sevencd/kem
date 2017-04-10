package cn.ilanhai.kem.bl.user.frontuser;

import java.util.Date;

import org.apache.log4j.Logger;

import cn.ilanhai.framework.app.RequestContext;
import cn.ilanhai.framework.app.dao.Dao;
import cn.ilanhai.framework.app.dao.DaoProxyFactory;
import cn.ilanhai.framework.app.dao.DefaultDaoProxyFactoryImpl;
import cn.ilanhai.framework.common.exception.BlAppException;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.framework.common.exception.SessionContainerException;
import cn.ilanhai.framework.common.session.BaseSessionState;
import cn.ilanhai.framework.common.session.RedisSession;
import cn.ilanhai.framework.common.session.SessionFactory;
import cn.ilanhai.framework.common.session.enums.SessionStateType;
import cn.ilanhai.framework.uitl.ExpressionMatchUtil;
import cn.ilanhai.framework.uitl.Str;
import cn.ilanhai.kem.bl.BLContextUtil;
import cn.ilanhai.kem.bl.integrate.ykd.YkdIntegrateManager;
import cn.ilanhai.kem.bl.member.MemberManager;
import cn.ilanhai.kem.common.CodeTable;
import cn.ilanhai.kem.dao.user.frontuser.FrontuserDao;
import cn.ilanhai.kem.domain.IdEntity;
import cn.ilanhai.kem.domain.enums.UserInfoType;
import cn.ilanhai.kem.domain.enums.UserStatus;
import cn.ilanhai.kem.domain.member.MemberEntity;
import cn.ilanhai.kem.domain.user.frontuser.FrontUserEntity;
import cn.ilanhai.kem.domain.user.frontuser.FrontUserInfoEntity;
import cn.ilanhai.kem.domain.user.frontuser.QueryByPhoneNoConditionEntity;
import cn.ilanhai.kem.domain.user.frontuser.UserLoginRequestDto;
import cn.ilanhai.kem.domain.user.frontuser.dto.QueryFrontUserByCompany;
import cn.ilanhai.kem.domain.user.frontuser.dto.SearchFrontUserInfoDto;
import cn.ilanhai.kem.domain.userRelation.UserRelationEntity;
import cn.ilanhai.kem.util.TimeUtil;

public class FrontuserManger {
	private static Logger logger = Logger.getLogger(FrontuserManger.class);
	private static Class<?> currentclass = FrontuserDao.class;
	private static DaoProxyFactory daoFactory = DefaultDaoProxyFactoryImpl.getInstance();

	public static boolean isExist(RequestContext context, String phoneNo) throws BlAppException, DaoAppException {
		CodeTable ct;
		if (phoneNo == null) {
			ct = CodeTable.BL_UNHANDLED_EXCEPTION;
			throw new BlAppException(ct.getValue(), ct.getDesc());
		}
		if (!ExpressionMatchUtil.isPhoneNo(phoneNo)) {
			throw new BlAppException(-1, "电话号码错误");
		}
		// 获取数据库资源
		Dao dao = daoFactory.getDao(context, currentclass);
		FrontUserEntity request = new FrontUserEntity();
		request.setPhoneNo(phoneNo);
		FrontUserEntity frontUserEntity = (FrontUserEntity) dao.query(request, true);
		if (frontUserEntity != null) {
			return true;
		} else {
			return false;
		}
	}

	public static FrontUserEntity getUser(RequestContext context, String userId)
			throws BlAppException, DaoAppException {
		// 获取数据库资源
		CodeTable ct = null;
		try {
			Dao dao = daoFactory.getDao(context, FrontuserDao.class);
			IdEntity<String> idDto = new IdEntity<String>();
			idDto.setId(userId);
			FrontUserEntity frontUserEntity = (FrontUserEntity) dao.query(idDto, false);
			return frontUserEntity;
		} catch (DaoAppException e) {
			ct = CodeTable.BL_DATA_ERROR;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		} catch (Exception e) {
			ct = CodeTable.BL_UNHANDLED_EXCEPTION;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		}
	}

	/**
	 * 获取info
	 * 
	 * @param context
	 * @param type
	 * @return
	 * @throws DaoAppException
	 * @throws BlAppException
	 * @throws SessionContainerException
	 */
	public static String getUserInfoByType(RequestContext context, UserInfoType type, String userId)
			throws DaoAppException, BlAppException {
		if (type == null || Str.isNullOrEmpty(userId)) {
			return "";
		}
		SearchFrontUserInfoDto searchFrontUserInfoDto = new SearchFrontUserInfoDto();
		searchFrontUserInfoDto.setUserID(userId);
		searchFrontUserInfoDto.setInfoType(type.getValue());
		Dao dao = daoFactory.getDao(context, currentclass);
		FrontUserInfoEntity frontUserEntity = (FrontUserInfoEntity) dao.query(searchFrontUserInfoDto, true);
		return frontUserEntity == null ? "" : frontUserEntity.getInfo();
	}

	public static int saveUserInfoByType(RequestContext context, UserInfoType type, String infoString, String userId)
			throws DaoAppException, BlAppException {
		if (type == null) {
			return -1;
		}
		FrontUserInfoEntity info = new FrontUserInfoEntity();
		info.setUserID(userId);
		info.setInfo(infoString);
		info.setInfoType(type.getValue());
		info.setInfoState(UserStatus.ENABLE.getValue());
		Dao dao = daoFactory.getDao(context, currentclass);
		return dao.save(info);
	}

	/**
	 * 获取会员到期时间
	 * 
	 * @param context
	 * @param user
	 * @return
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	public static Date getMemberEndTime(RequestContext context, FrontUserEntity user)
			throws BlAppException, DaoAppException {
		UserRelationEntity userRelationEntity = UserRelationManger.getUserRelation(context, user.getUserID());
		String parentId = userRelationEntity.getFatherUserId();
		MemberEntity memberEntity = MemberManager.getMemberInfo(context, parentId);
		Date time = new Date();
		if (memberEntity == null) {
			time = user.getCreatetime();
		} else if (MemberEntity.DISABLE == memberEntity.getStatus()) {
			time = memberEntity.getEndtime();
		}
		return time;
	}
}
