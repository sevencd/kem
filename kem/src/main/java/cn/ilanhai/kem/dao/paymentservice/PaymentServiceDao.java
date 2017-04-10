package cn.ilanhai.kem.dao.paymentservice;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.kem.bl.BLContextUtil;
import cn.ilanhai.kem.dao.MybatisBaseDao;
import cn.ilanhai.kem.domain.BooleanEntity;
import cn.ilanhai.kem.domain.IdEntity;
import cn.ilanhai.kem.domain.paymentservice.PaymentOrderEntity;
import cn.ilanhai.kem.domain.paymentservice.PaymentOrderInfoEntity;
import cn.ilanhai.kem.domain.paymentservice.PayConfig.PayConfigDetailEntity;
import cn.ilanhai.kem.domain.paymentservice.PayConfig.PayConfigRequestEntity;
import cn.ilanhai.kem.domain.paymentservice.PayConfig.PayConfigResponseEntity;
import cn.ilanhai.kem.domain.paymentservice.PayConfig.PayConfigloadRequestEntity;
import cn.ilanhai.kem.domain.paymentservice.PayInfo.PayInfoLoadEntity;
import cn.ilanhai.kem.domain.paymentservice.PayInfo.PayInfoRequestEntity;
import cn.ilanhai.kem.domain.paymentservice.PayInfo.PayInfoResponseEntity;
import cn.ilanhai.kem.domain.paymentservice.PayInfo.PayInfoServiceEntity;
import cn.ilanhai.kem.domain.paymentservice.PayInfo.PayInfoServiceInfoEntity;
import cn.ilanhai.kem.domain.paymentservice.PayInfo.payInfoServiceInfoResponseEitity;
import cn.ilanhai.kem.domain.paymentservice.PayInfo.payInfoServiceResponseEntity;
import cn.ilanhai.kem.domain.paymentservice.QueryCondition.GetPackageServiceByIdQCondition;
import cn.ilanhai.kem.domain.paymentservice.QueryCondition.GetPaymentOrderByOrderIdQCondition;
import cn.ilanhai.kem.domain.paymentservice.QueryCondition.PackageServiceExistQCondtion;
import cn.ilanhai.kem.domain.paymentservice.QueryCondition.SearchOrderQCondition;
import cn.ilanhai.kem.domain.paymentservice.pricerange.GetpriveEntity;
import cn.ilanhai.kem.domain.paymentservice.pricerange.PriveRangeRequest;

@Component("paymentserviceDao")
public class PaymentServiceDao extends MybatisBaseDao {
	public PaymentServiceDao() throws DaoAppException {
		super();
	}

	private Logger logger = Logger.getLogger(PaymentServiceDao.class);

	@Override
	public int save(Entity entity) throws DaoAppException {
		if (entity instanceof PaymentOrderEntity) {
			// 保存订单
			saveOne((PaymentOrderEntity) entity);
		} else if (entity instanceof PayInfoRequestEntity) {
			// 保存付费配置
			saveOne((PayInfoRequestEntity) entity);
		} else if (entity instanceof PayConfigRequestEntity) {
			// 保存支付配置
			saveOne((PayConfigRequestEntity) entity);
		} else if (entity instanceof PriveRangeRequest) {
			return savePriceRange((PriveRangeRequest) entity);
		}
		return super.save(entity);
	}

	private int saveOne(PaymentOrderEntity entity) throws DaoAppException {
		logger.info("保存订单：" + entity.toString());
		SqlSession sqlSession = null;
		try {
			sqlSession = this.getSqlSession();
			int result =0;
			if(!orderIsExist(entity.getOrderId())){
			// 保存订单
				result= sqlSession.insert("paymentService.insertPaymentOrderEntity", entity);
				// 保存详情
				for (PaymentOrderInfoEntity info : entity.getOrderInfo()) {
					sqlSession.insert("paymentService.savePaymentOrderInfoEntity", info);
				}
			}else {
				result= sqlSession.insert("paymentService.updatePaymentOrderEntity", entity);
			}
			
			return result;
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		} finally {
		}
	}
	
	private boolean orderIsExist(String orderId) throws DaoAppException{
		GetPaymentOrderByOrderIdQCondition condition=new GetPaymentOrderByOrderIdQCondition();
		condition.setOrderId(orderId);
		
		Entity order=this.getPaymentOrderByOrderId(condition);
		if(order==null){
			return false;
		}else{
			return true;
		}
	}

	@Override
	public Entity query(Entity entity, boolean flag) throws DaoAppException {
		if (entity instanceof PackageServiceExistQCondtion) {
			// 查询套餐是否存在
			return queryOne((PackageServiceExistQCondtion) entity);
		} else if (entity instanceof GetPackageServiceByIdQCondition) {
			// 根据服务套餐ID查询服务套餐内容
			return queryOne((GetPackageServiceByIdQCondition) entity);
		} else if (entity instanceof PayInfoLoadEntity) {
			//加载套餐内容
			return loadPayInfo((PayInfoLoadEntity) entity);
		} else if (entity instanceof SearchOrderQCondition) {
			// 获取查询订单总条数
			return countSearchOrder((SearchOrderQCondition) entity);
		} else if (entity instanceof GetPaymentOrderByOrderIdQCondition) {
			// 根据订单ID查询订单
			return getPaymentOrderByOrderId((GetPaymentOrderByOrderIdQCondition) entity);
		} else
			return super.query(entity, flag);
	}

	@Override
	public Iterator<Entity> query(Entity entity) throws DaoAppException {
		if (entity instanceof PayConfigloadRequestEntity) {
			return loadPayConfig((PayConfigloadRequestEntity) entity).iterator();
		} else if (entity instanceof SearchOrderQCondition) {
			return searchOrder((SearchOrderQCondition) entity).iterator();
		} else if (entity instanceof IdEntity) {
			return loadPriceRange((IdEntity<Integer>) entity).iterator();
		} else if (entity instanceof GetpriveEntity) {
			//查询付费配置的价格
			return queryPriceRange((GetpriveEntity) entity).iterator();
		}
		return null;
	}

	/**
	 * 查询订单
	 * 
	 * @param condition
	 * @return
	 */
	private List<Entity> searchOrder(SearchOrderQCondition condition) throws DaoAppException {
		logger.info("查询订单：" + condition);
		SqlSession sqlSession = null;
		try {
			sqlSession = this.getSqlSession();
			return sqlSession.selectList("paymentService.searchOrder", condition);
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		} finally {

		}
	}

	private Entity countSearchOrder(SearchOrderQCondition condition) throws DaoAppException {
		logger.info("查询订单总条数：" + condition);
		SqlSession sqlSession = null;
		try {
			sqlSession = this.getSqlSession();
			return sqlSession.selectOne("paymentService.countSearchOrder", condition);
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		} finally {

		}
	}

	// 保存支付配置
	private int saveOne(PayConfigRequestEntity entity) throws DaoAppException {
		logger.info("进入支付配置信息保存方法");
		SqlSession sqlSession = null;
		try {
			sqlSession = this.getSqlSession();
			List<PayConfigDetailEntity> payConfig = entity.getInfo();
			for (int i = 0; i < payConfig.size(); i++) {
				PayConfigDetailEntity configDetail = payConfig.get(i);
				BLContextUtil.valiParaItemIntegerNull(configDetail.getType(), "类型不能为空");
				if (this.isExists(configDetail.getType(), configDetail.getSysKey())) {
					// 保存新的信息
					int status = sqlSession.insert("paymentService.savepayconfig", configDetail);
					if (status <= 0) {
						throw new DaoAppException(-1, "保存支付配置失败");
					}
				} else {
					// 更新信息
					int status = sqlSession.insert("paymentService.updatepayconfig", configDetail);
					if (status <= 0) {
						throw new DaoAppException(-1, "更新支付配置失败");
					}
				}
			}
			return 1;
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		} finally {

		}
	}

	// 遍历数据库，查看支付配置key是否存在
	private boolean isExists(Integer type, String key) throws DaoAppException {
		PayConfigloadRequestEntity paytype = new PayConfigloadRequestEntity();
		paytype.setType(type);
		List<Entity> keys = this.loadPayConfig(paytype);
		for (int i = 0; i < keys.size(); i++) {
			PayConfigResponseEntity syskey = (PayConfigResponseEntity) keys.get(i);
			if (syskey.getSysKey().equals(key)) {
				return false;
			}
		}
		return true;
	}

	private int saveOne(PayInfoRequestEntity entity) throws DaoAppException {

		// 保存或更新套餐
//		updatepayprice((PayInfoRequestEntity) entity);//1.5方法调整，不对prod_service进行修改
		//1.5版本规定套餐type都为0,所以只需要在for循环外面做一次删除操作
		if (entity.getPackageService().size() > 0) {
			PayInfoServiceEntity payInfoServiceEntity = entity.getPackageService().get(0);
			updateIsDelete(payInfoServiceEntity.getType());
		} else {
			updateIsDelete(0);
		}
		
		PayInfoRequestEntity request = (PayInfoRequestEntity) entity;
		List<PayInfoServiceEntity> payInfoList = request.getPackageService();
		for (int j = 0; j < payInfoList.size(); j++) {
			
			PayInfoServiceEntity payInfo = payInfoList.get(j);
			
			// 保存套餐
			if (payInfo.getId() == null || payInfo.getId() == 0) {
				if (saveInfo(payInfo) > 0 && payInfo.getId() != 0) {
					// 遍历套餐详情，并保存
					for (int i = 0; i < payInfo.getInfo().size(); i++) {
						PayInfoServiceInfoEntity info = payInfo.getInfo().get(i);
						info.setPackageServiceId(payInfo.getId());
						info.setServiceId(PayInfoServiceInfoEntity.getServiceIdByType(info.getType()));
						saveInfoDetails(info);
					}
				} else {
					throw new DaoAppException(-1, "保存套餐失败");
				}
				// 更新套餐
			} else {
				if (updateInfo(payInfo) > 0 && payInfo.getId() != 0) {
					// 遍历套餐详情，并保存
					for (int i = 0; i < payInfo.getInfo().size(); i++) {
						PayInfoServiceInfoEntity info = payInfo.getInfo().get(i);
						info.setPackageServiceId(payInfo.getId());
						if (info.getId() == 0 || info.getId() == null) {
							saveInfoDetails(info);
						} else {
							updateInfoDetails(info);
						}
					}
				} else {
					throw new DaoAppException(-1, "更新套餐失败");
				}
			}
		}
		return 1;
	}

	// 判断套餐是否存在
	private Entity queryOne(PackageServiceExistQCondtion condition) throws DaoAppException {
		logger.info("进入套餐是否存在查询方法");
		SqlSession sqlSession;
		try {
			sqlSession = this.getSqlSession();
			BooleanEntity result = sqlSession.selectOne("paymentService.packageServiceExist", condition);
			logger.info("获得查询结果:" + result);
			return result;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new DaoAppException(e.getMessage(), e);
		}
	}

	// 根据服务套餐ID查询服务套餐内容
	private Entity queryOne(GetPackageServiceByIdQCondition condition) throws DaoAppException {
		logger.info("进入根据服务套餐ID查询服务套餐内容方法");
		SqlSession sqlSession;
		try {
			sqlSession = this.getSqlSession();
			PayInfoServiceEntity result = sqlSession.selectOne("paymentService.getPackageServiceById", condition);
			logger.info("获得查询结果:" + result);
			return result;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new DaoAppException(e.getMessage(), e);
		}
	}

	// 保存套餐
	private int saveInfo(PayInfoServiceEntity entity) throws DaoAppException {
		logger.info("进入套餐保存方法");
		SqlSession sqlSession = null;
		try {
			sqlSession = this.getSqlSession();
			return sqlSession.insert("paymentService.savepayinfo", entity);
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		} finally {

		}
	}

	// 保存套餐详细信息
	private int saveInfoDetails(PayInfoServiceInfoEntity entity) throws DaoAppException {
		logger.info("进入套餐保存方法");
		SqlSession sqlSession = null;
		try {
			sqlSession = this.getSqlSession();
			logger.info("查询套餐详情单位");
			PayInfoLoadEntity searchUnit = new PayInfoLoadEntity();
			searchUnit.setType(entity.getType());
			/**
			 * 1.5版本修改
			 */
//			PayInfoResponseEntity unit = sqlSession.selectOne("paymentService.loadpayinfo", searchUnit);
//			entity.setUnit(unit.getUnit());
			return sqlSession.insert("paymentService.savepayinfodetials", entity);
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		} finally {

		}
	}

	// 更新套餐信息
	private int updateInfo(PayInfoServiceEntity entity) throws DaoAppException {
		logger.info("进入套餐更新方法");
		SqlSession sqlSession = null;
		try {
			sqlSession = this.getSqlSession();
			return sqlSession.update("paymentService.updatepayinfo", entity);
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		} finally {

		}
	}

	// 更新套餐详情
	private int updateInfoDetails(PayInfoServiceInfoEntity entity) throws DaoAppException {
		logger.info("进入套餐详情更新方法");
		SqlSession sqlSession = null;
		try {
			sqlSession = this.getSqlSession();
			return sqlSession.update("paymentService.updatepayinfodetials", entity);
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		} finally {

		}
	}

	// 更新服务单价
	private int updatepayprice(PayInfoRequestEntity entity) throws DaoAppException {
		logger.info("进入套餐更新方法");
		SqlSession sqlSession = null;
		try {
			sqlSession = this.getSqlSession();
			return sqlSession.update("paymentService.updatepayprice", entity);
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		} finally {

		}
	}

	// 更新套餐状态
	private int updateIsDelete(Integer entity) throws DaoAppException {
		logger.info("进入更新套餐是否删除掉状态");
		SqlSession sqlSession = null;
		try {
			sqlSession = this.getSqlSession();
			return sqlSession.update("paymentService.updateisdelete",entity);
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		} finally {

		}
	}

	private List<Entity> loadPayConfig(PayConfigloadRequestEntity entity) throws DaoAppException {
		logger.info("进入更新加载支付配置");
		SqlSession sqlSession = null;
		try {
			sqlSession = this.getSqlSession();
			return sqlSession.selectList("paymentService.loadpayconfig", entity);
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		} finally {

		}
	}

	// 加载支付配置信息
	private Entity loadPayInfo(PayInfoLoadEntity entity) throws DaoAppException {

		logger.info("进入加载支付信息配置");
		SqlSession sqlSession = null;
		try {
			sqlSession = this.getSqlSession();
			// 查询可用套餐
			PayInfoResponseEntity payInfo = new PayInfoResponseEntity();
			//sqlSession.selectOne("paymentService.loadpayinfo", entity);//1.5修改，不用查询
			List<payInfoServiceResponseEntity> packageService = sqlSession
					.selectList("paymentService.loadpackageService", entity);
			List<payInfoServiceResponseEntity> packageServicepage = new ArrayList<payInfoServiceResponseEntity>();
			// 遍历套餐
			for (int i = 0; i < packageService.size(); i++) {
				payInfoServiceResponseEntity packageServiceInfo = packageService.get(i);
				entity.setId(packageServiceInfo.getId());
				// 查询套餐详情
				List<payInfoServiceInfoResponseEitity> info = sqlSession
						.selectList("paymentService.loadpackageServiceinfo", entity);
				packageServiceInfo.setInfo(info);
				packageServicepage.add(packageServiceInfo);
			}
			payInfo.setPackageService(packageServicepage);
			return payInfo;
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		} finally {

		}

	}

	private Entity getPaymentOrderByOrderId(GetPaymentOrderByOrderIdQCondition condition) throws DaoAppException {
		logger.info("根据订单ID查询订单:" + condition);
		SqlSession sqlSession = null;
		try {
			sqlSession = this.getSqlSession();
			return sqlSession.selectOne("paymentService.getPaymentOrderByOrderId", condition);
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		} finally {

		}
	}
	//保存价格区间
	private int savePriceRange(PriveRangeRequest entity) throws DaoAppException {
		if(entity == null) {
			return -1;
		}
		if (deleteOldprice(entity.getType())) {
			return savePrice(entity);
		}
		return 0;
	}

	private int savePrice(PriveRangeRequest entity) throws DaoAppException {
		logger.info("保存服务为:" + entity.getType() + " 的价格区间");
		SqlSession sqlSession = null;
		try {
			sqlSession = this.getSqlSession();
			return sqlSession.insert("paymentService.insertnewpricerange", entity);
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		} finally {

		}
	}

	private boolean deleteOldprice(Integer type) throws DaoAppException {
		logger.info("删除服务为:" + type + " 的价格");
		SqlSession sqlSession = null;
		try {
			sqlSession = this.getSqlSession();
			Integer val = sqlSession.delete("paymentService.deleteoldpricerange", type);
			if (val == null || val < 0) {
				return false;
			}
			return true;
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		} finally {

		}
		
	}
	//加载付费配置
	private List<Entity> loadPriceRange(IdEntity<Integer> entity) throws DaoAppException {
		
		logger.info("加载服务id为:" + entity.getId() + " 的价格");
		SqlSession sqlSession = null;
		try {
			sqlSession = this.getSqlSession();
			return sqlSession.selectList("paymentService.loadpricerange", entity.getId());
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		} finally {

		}
	}
	
	//查询套餐价格
	private List<Entity> queryPriceRange(GetpriveEntity entity) throws DaoAppException {
		logger.info("查询服务id为:" + entity.getPackageServiceId() + " 的价格");
		SqlSession sqlSession = null;
		try {
			sqlSession = this.getSqlSession();
			return sqlSession.selectList("paymentService.querypricerange", entity);
		} catch (Exception e) {
			throw new DaoAppException(e.getMessage(), e);
		} finally {

		}
	}
}
