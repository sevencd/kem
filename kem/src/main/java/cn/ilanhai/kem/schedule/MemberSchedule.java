package cn.ilanhai.kem.schedule;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import cn.ilanhai.framework.app.RequestContext;
import cn.ilanhai.framework.app.dao.Dao;
import cn.ilanhai.framework.app.dao.DaoProxyFactory;
import cn.ilanhai.framework.app.dao.DefaultDaoProxyFactoryImpl;
import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.exception.AppException;
import cn.ilanhai.framework.common.exception.BlAppException;
import cn.ilanhai.framework.common.exception.CacheContainerException;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.framework.common.exception.SessionContainerException;
import cn.ilanhai.kem.bl.BLContextUtil;
import cn.ilanhai.kem.bl.bindhost.BindHostManager;
import cn.ilanhai.kem.bl.member.MemberManager;
import cn.ilanhai.kem.bl.paymentservice.OrderManager;
import cn.ilanhai.kem.dao.member.MemberDao;
import cn.ilanhai.kem.dao.paymentservice.PaymentServiceDao;
import cn.ilanhai.kem.domain.member.MemberEntity;
import cn.ilanhai.kem.domain.member.QueryCondition.QueryNewExpiredMemberQCondition;
import cn.ilanhai.kem.domain.paymentservice.PayInfo.PayInfoServiceEntity;
import cn.ilanhai.kem.domain.paymentservice.PayInfo.PayInfoServiceInfoEntity;

/**
 * 会员状态更新定时服务
 * 
 * @author Nature
 *
 */
public class MemberSchedule implements Job {

	private Logger logger = Logger.getLogger(MemberSchedule.class);

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		logger.info("开始更新会员状态");
		// 获取新的dao
		Dao dao = ScheduleContext.getDao(MemberDao.class);
		if (dao == null) {
			logger.error("更新会员状态失败：dao获取失败");
			return;
		}
		// 查询出所有过期会员并且为标识为过期的会员
		QueryNewExpiredMemberQCondition condition = new QueryNewExpiredMemberQCondition();
		// 将过期时间设置为当天的0点0分0秒
		Calendar date = Calendar.getInstance();

		date.setTime(new Date());
		date.set(date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DATE), 0, 0);
		// 测试
		// date.set(
		// 2017,11,23,
		// 0, 0);

		condition.setCutoffTime(date.getTime());
		Iterator<Entity> members = null;
		try {
			members = dao.query(condition);
			if (members == null) {
				return;
			}
		} catch (DaoAppException e) {
			logger.error("查询新过期会员失败:" + e.getMessage());
			return;
		}
		// 遍历所有检索出的会员，更新其状态
		List<Entity> memberList = new ArrayList<Entity>();
		while (members.hasNext()) {
			MemberEntity member = (MemberEntity) members.next();
			member.setStatus(MemberEntity.DISABLE);
			try {
				dao.save(member);
			} catch (DaoAppException e) {
				logger.error("更新会员状态失败：" + e.getMessage());
				continue;
			}
			memberList.add(member);
		}
		// 更新服务
		this.updateServices(ScheduleContext.getRequestContext(), memberList.iterator());
		// 推送服务
		this.putUserResouse(dao);
	}

	private void putUserResouse(Dao dao) {
		Iterator<Entity> oneMonthmember = null;
		try {
			oneMonthmember = dao.query(null);
			if (oneMonthmember == null) {
				return;
			}
		} catch (DaoAppException e) {
			logger.error("查询该月会员失败:" + e.getMessage());
			return;
		}
		PayInfoServiceEntity payInfoServiceEntity = null;
		Dao packageDao = ScheduleContext.getDao(PaymentServiceDao.class);
		while (oneMonthmember.hasNext()) {
			MemberEntity member = (MemberEntity) oneMonthmember.next();
			Integer packageId = member.getPackageServiceId();
			try {
				payInfoServiceEntity = OrderManager.getPackageServiceInfoById(packageId, packageDao);
			} catch (Exception e) {
				logger.info("1推送任务错误:" + packageId + "-----" + e.getMessage());
				continue;
			}
			if (payInfoServiceEntity == null || payInfoServiceEntity.getInfo() == null) {
				logger.info("2推送任务错误:payInfoServiceEntity==null||payInfoServiceEntity.getInfo() == null");
				continue;
			}
			for (PayInfoServiceInfoEntity entity : payInfoServiceEntity.getInfo()) {
				if (!entity.getType().equals(PayInfoServiceInfoEntity.MEMBER) && entity.getTimeMode().equals(1)
						&& !entity.getType().equals(PayInfoServiceInfoEntity.SUBACCOUNT)) {
					try {
						MemberManager.createPackageUserResource(ScheduleContext.getRequestContext(), entity,
								member.getUserId(), member);
					} catch (Exception e) {
						logger.info("3推送任务错误:" + packageId + "-----" + e.getMessage());
						continue;
					}
				}
			}
		}
	}

	/**
	 * 会员状态已更新，更新相关服务
	 * 
	 * @param context
	 * @param members
	 */
	private void updateServices(RequestContext context, Iterator<Entity> members) {
		// 去掉redis中推广对应域名
		try {
			MemberManager m = new MemberManager(ScheduleContext.getRequestContext());
			logger.info("进入会员更新方法," + members.hasNext());
			BindHostManager manager = new BindHostManager(context);
			logger.info("BindHostManager construct sucess");
			while (members.hasNext()) {
				logger.info("进入循环");
				MemberEntity member = (MemberEntity) members.next();
				logger.info("获取member成功------" + member);
				String userId = member.getUserId();
				logger.info("userid=" + userId + "调用绑定域名去除方法");
				manager.disableHost(userId);
				// 清除资源
				m.deleteUserServiceResources(member.getUserId());
			}
			logger.info("循环结束啦！！！");
		} catch (Throwable e) {
			logger.info("报错了！！！" + e.getMessage());
		}
	}
}
