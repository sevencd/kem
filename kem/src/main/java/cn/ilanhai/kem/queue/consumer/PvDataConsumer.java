package cn.ilanhai.kem.queue.consumer;

import org.apache.log4j.Logger;

import cn.ilanhai.framework.common.exception.AppException;

import cn.ilanhai.kem.App;

import cn.ilanhai.kem.queue.QueueConsumer;
import cn.ilanhai.kem.queue.msg.PvDataMsg;

/**
 * PV数据的消费者
 * 
 * @author Nature
 *
 */
public class PvDataConsumer extends QueueConsumer<PvDataMsg> {

	private Logger logger = Logger.getLogger(QueueConsumer.class);

	/**
	 * 构造函数，需要注入应用上下文
	 * 
	 * @param app
	 */
	public PvDataConsumer(App app) {
		super(app, PvDataMsg.class);
	}

	@Override
	protected String getId() {
		return PvDataMsg.class.getName();
	}

	@Override
	protected void recieveMsg(PvDataMsg msg) throws AppException {
		logger.info("接收到消息：" + msg.getTestMsg());

	}

}
