package cn.ilanhai.kem.queue.consumer;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.ilanhai.framework.app.dao.Dao;
import cn.ilanhai.framework.common.exception.AppException;
import cn.ilanhai.kem.App;
import cn.ilanhai.kem.bl.contacts.ContactManager;
import cn.ilanhai.kem.dao.contacts.ContactsDao;
import cn.ilanhai.kem.domain.contacts.dto.ContactMsgDto;
import cn.ilanhai.kem.queue.QueueConsumer;
import cn.ilanhai.kem.queue.msg.ContactMsg;

/**
 * 联系人定阅者
 * 
 * @author hy
 *
 */
@SuppressWarnings("rawtypes")
public class ContactConsumer extends QueueConsumer<ContactMsg> {

	public ContactConsumer(App app) {
		super(app, ContactMsg.class);
	}

	@Override
	protected String getId() {
		return ContactMsg.class.getName();
	}

	@Override
	@Transactional(rollbackFor = { AppException.class, Exception.class,
			Throwable.class }, propagation = Propagation.REQUIRED)
	protected void recieveMsg(ContactMsg msg) throws AppException {
		if (msg.getMsgContent() == null)
			return;

		ContactMsgDto info = msg.getMsgContent();
		if (info == null) {
			logger.error("发送消息为空");
			return;
		}
		Dao dao = this.app.getApplicationContext().getBean(ContactsDao.class);
		// 创建联系人
		ContactManager.createContactByDao(dao, info.getUserId(), info.getName(), info.getContext(),
				info.getContactType());
	}

	/**
	 * 日志
	 */
	private Logger logger = Logger.getLogger(QueueConsumer.class);

}
