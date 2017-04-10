package cn.ilanhai.kem.bl.user.trafficuser;

import cn.ilanhai.framework.app.RequestContext;
import cn.ilanhai.framework.app.dao.Dao;
import cn.ilanhai.framework.app.dao.DaoProxyFactory;
import cn.ilanhai.framework.app.dao.DefaultDaoProxyFactoryImpl;
import cn.ilanhai.framework.common.exception.BlAppException;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.kem.common.CodeTable;
import cn.ilanhai.kem.dao.user.trafficuser.MyBatisTrafficuserDao;
import cn.ilanhai.kem.domain.IdEntity;
import cn.ilanhai.kem.domain.user.trafficuser.TrafficuserEntity;

public class TrafficuserManager {
	//private static Class<?> currentclass = TrafficuserDao.class;
	private static Class<?> currentclass = MyBatisTrafficuserDao.class;

	private static DaoProxyFactory daoFactory = DefaultDaoProxyFactoryImpl.getInstance();

	public static Integer saveTrafficuser(RequestContext context, TrafficuserEntity trafficuserEntity)
			throws BlAppException {
		CodeTable ct;
		try {
			Dao dao = getDao(context);
			int val = dao.save(trafficuserEntity);
			if (val < 0) {
				ct = CodeTable.BL_COMMON_SAVE_DOAMIN;
				String tmp = ct.getDesc();
				tmp = String.format(tmp, "流量用户信息");
				throw new BlAppException(ct.getValue(), tmp);
			}
			return trafficuserEntity.getTrafficuserId();
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

	public static TrafficuserEntity getTrafficuser(RequestContext context, Integer trafficuserId)
			throws BlAppException {
		CodeTable ct;
		try {
			IdEntity<Integer> id = new IdEntity<Integer>();
			id.setId(trafficuserId);
			Dao dao = getDao(context);
			return (TrafficuserEntity) dao.query(id, false);
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

	public static TrafficuserEntity getTrafficuserByExtensionAndPhone(RequestContext context, String extensionId,
			String phoneNo) throws BlAppException {
		CodeTable ct;
		try {
			TrafficuserEntity trafficuserEntity = new TrafficuserEntity();
			trafficuserEntity.setExtensionId(extensionId);
			trafficuserEntity.setPhoneNo(phoneNo);
			Dao dao = getDao(context);
			return (TrafficuserEntity) dao.query(trafficuserEntity, false);
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

	private static Dao getDao(RequestContext requestContext) throws DaoAppException, BlAppException {
		CodeTable ct;
		Dao dao = daoFactory.getDao(requestContext, currentclass);
		if (dao == null) {
			ct = CodeTable.BL_COMMON_GET_DAO;
			String tmp = ct.getDesc();
			tmp = String.format(tmp, "专题");
			throw new BlAppException(ct.getValue(), tmp);
		}
		return dao;
	}
}
