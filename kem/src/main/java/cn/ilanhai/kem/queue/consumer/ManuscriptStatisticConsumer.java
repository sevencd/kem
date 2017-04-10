package cn.ilanhai.kem.queue.consumer;

import org.apache.log4j.Logger;

import cn.ilanhai.framework.common.exception.AppException;
import cn.ilanhai.kem.App;
import cn.ilanhai.kem.bl.statistic.impl.AbstractManuscriptStatistic;
import cn.ilanhai.kem.queue.QueueConsumer;
import cn.ilanhai.kem.queue.msg.ManuscriptStatisticMsg;

/**
 * 稿件统计定阅者
 * 
 * @author he
 *
 */
@SuppressWarnings("rawtypes")
public class ManuscriptStatisticConsumer extends
		QueueConsumer<ManuscriptStatisticMsg> {

	public ManuscriptStatisticConsumer(App app) {
		super(app, ManuscriptStatisticMsg.class);
	}

	@Override
	protected String getId() {
		return ManuscriptStatisticMsg.class.getName();
	}

	@Override
	protected void recieveMsg(ManuscriptStatisticMsg msg) throws AppException {
		if (msg.getMsgContent() == null)
			return;
		AbstractManuscriptStatistic.generateFactory(app, msg.getMsgContent());
	}

	/**
	 * 日志
	 */
	private Logger logger = Logger.getLogger(QueueConsumer.class);

}
