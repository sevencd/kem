package cn.ilanhai.kem;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.quartz.SchedulerException;

import cn.ilanhai.framework.app.ApplicationImpl;
import cn.ilanhai.framework.app.dao.Dao;
import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.cache.Cache;
import cn.ilanhai.framework.common.configuration.app.Configure;
import cn.ilanhai.framework.common.exception.AppException;
import cn.ilanhai.framework.common.exception.CacheContainerException;
import cn.ilanhai.kem.bl.BLContextUtil;
import cn.ilanhai.kem.bl.statistic.impl.ApplicationStatisticImpl;
import cn.ilanhai.kem.cache.CacheUtil;
import cn.ilanhai.kem.dao.collectionType.CollectionTypeDao;
import cn.ilanhai.kem.dao.ver.MyBatisDbVerDao;
import cn.ilanhai.kem.event.BaseEventSubscriber;
import cn.ilanhai.kem.event.DomainEventManager;
import cn.ilanhai.kem.keyfac.KeyFactory;
import cn.ilanhai.kem.mail.protocol.MailInfo;
import cn.ilanhai.kem.mail.protocol.sohu.address.AddAddressRequest;
import cn.ilanhai.kem.mail.protocol.sohu.address.DeleteAddressRequest;
import cn.ilanhai.kem.mail.protocol.sohu.address.UpdateAddressRequest;
import cn.ilanhai.kem.modules.work.WorkFactory;
import cn.ilanhai.kem.queue.QueueManager;
import cn.ilanhai.kem.queue.msg.MailMsg;
import cn.ilanhai.kem.schedule.ScheduleContext;
import cn.ilanhai.kem.upgrade.DbUpdater;
import cn.ilanhai.kem.util.CollectionTypeUtil;

public class App extends ApplicationImpl {

	public App(Configure configure) {
		super(configure);

	}

	@Override
	public void init() throws AppException {
		DbUpdater dbUpdater = null;
		super.init();
		// 升级
		dbUpdater = new DbUpdater();
		dbUpdater.init(this);
		dbUpdater.update();
		// 初始化缓存工具
		CacheUtil.Init(this.getCacheManager());
		// 初始化KEY生成品
		KeyFactory.getInstance().init(this);

		// for (int i = 0; i < 1000; i++) {
		// System.out.println(KeyFactory.getInstance().newKey(
		// KeyFactory.KEY_USER));
		// }
		WorkFactory.init(this.getApplicationContext());


		try {
			ScheduleContext.init(this);
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 初始化领域事件模块
		DomainEventManager.getInstance().init(this);
		// 初始化消息队列
		QueueManager.getInstance().init(this);
		// String group = "test001";
		//
		// MailMsg<MailInfo> msg = null;
		//
		// AddAddressRequest addAddressRequest = null;
		// addAddressRequest = new AddAddressRequest();
		// addAddressRequest.setAddress(group);
		// addAddressRequest.setName(group);
		// msg = new MailMsg<MailInfo>();
		// msg.setMsgContent(addAddressRequesFt);
		// QueueManager.getInstance().put(msg);
		//
		// UpdateAddressRequest updateAddressRequest = null;
		// updateAddressRequest = new UpdateAddressRequest();
		// updateAddressRequest.setAddress(group);
		// updateAddressRequest.setName("he");
		// msg = new MailMsg<MailInfo>();
		// msg.setMsgContent(updateAddressRequest);
		// QueueManager.getInstance().put(msg);
		//

		// DeleteAddressRequest deleteAddressRequest = null;
		// deleteAddressRequest = new DeleteAddressRequest();
		// deleteAddressRequest.setAddress(group);
		// msg = new MailMsg<MailInfo>();
		// msg.setMsgContent(deleteAddressRequest);
		// QueueManager.getInstance().put(msg);

		// 将二级行业typenum和typename数据放入redis by csz
		CollectionTypeUtil.initCollectionType(this.getApplicationContext());
	}

	@Override
	public void close() throws AppException {

	}
}
