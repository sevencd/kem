package cn.ilanhai.kem.queue.consumer;

import java.util.Map;

import org.apache.log4j.Logger;

import cn.ilanhai.framework.common.exception.AppException;
import cn.ilanhai.framework.uitl.FastJson;

import cn.ilanhai.kem.App;
import cn.ilanhai.kem.mail.protocol.MailInfo;
import cn.ilanhai.kem.mail.protocol.sohu.SohuMailInfo;
import cn.ilanhai.kem.mail.protocol.sohu.address.AddAddressRequest;
import cn.ilanhai.kem.mail.protocol.sohu.address.AddMemberRequest;
import cn.ilanhai.kem.mail.protocol.sohu.address.DeleteAddressRequest;
import cn.ilanhai.kem.mail.protocol.sohu.address.DeleteMemberRequest;
import cn.ilanhai.kem.mail.protocol.sohu.address.QueryAddressListRequest;
import cn.ilanhai.kem.mail.protocol.sohu.address.QueryMemberListRequest;
import cn.ilanhai.kem.mail.protocol.sohu.address.QueryMemberRequest;
import cn.ilanhai.kem.mail.protocol.sohu.address.UpdateAddressRequest;
import cn.ilanhai.kem.mail.protocol.sohu.address.UpdateMemberRequest;
import cn.ilanhai.kem.mail.sohu.SohuMail;
import cn.ilanhai.kem.mail.sohu.GeneralSendSohuMailConfig;
import cn.ilanhai.kem.mail.sohu.SohuMailConfig;
import cn.ilanhai.kem.queue.QueueConsumer;
import cn.ilanhai.kem.queue.msg.MailMsg;

/**
 * 邮件定阅者
 * 
 * @author he
 *
 */
@SuppressWarnings("rawtypes")
public class MailConsumer extends QueueConsumer<MailMsg> {

	public MailConsumer(App app) {
		super(app, MailMsg.class);
	}

	@Override
	protected String getId() {
		return MailMsg.class.getName();
	}

	@Override
	protected void recieveMsg(MailMsg msg) throws AppException {
		SohuMail sohuMail = null;
		MailInfo info = null;
		info = msg.getMsgContent();

		if (info == null) {
			logger.error("消息错误");
			return;
		}
		if (info.getDataType() == null) {
			logger.error("消息数据类型错误");
			return;
		}
		sohuMail = new SohuMail();
		String jsonText = msg.getRawMsg();
		switch (info.getDataType()) {
		case GeneralSendSohuMail:
			SohuMailInfo sohuMailInfo = null;
			sohuMailInfo = FastJson.json2Bean(jsonText, SohuMailInfo.class);
			sohuMail.sendTo(this.getGeneralSendConfig(), sohuMailInfo);
			break;
		case AddAddress:
			AddAddressRequest addAddressRequest = null;
			addAddressRequest = FastJson.json2Bean(jsonText,
					AddAddressRequest.class);
			sohuMail.addAddress(this.getConfig(), addAddressRequest);
			break;
		case AddMember:
			AddMemberRequest addMemberRequest = null;
			addMemberRequest = FastJson.json2Bean(jsonText,
					AddMemberRequest.class);
			sohuMail.addMember(this.getConfig(), addMemberRequest);
			break;

		case DeleteAddress:
			DeleteAddressRequest deleteAddressRequest = null;
			deleteAddressRequest = FastJson.json2Bean(jsonText,
					DeleteAddressRequest.class);
			sohuMail.deleteAddress(this.getConfig(), deleteAddressRequest);
			break;

		case DeleteMember:
			DeleteMemberRequest deleteMemberRequest = null;
			deleteMemberRequest = FastJson.json2Bean(jsonText,
					DeleteMemberRequest.class);
			sohuMail.deleteMember(this.getConfig(), deleteMemberRequest);
			break;

		case QueryAddressList:
			QueryAddressListRequest queryAddressListRequest = null;
			queryAddressListRequest = FastJson.json2Bean(jsonText,
					QueryAddressListRequest.class);
			sohuMail.pageQueryAddress(this.getConfig(), queryAddressListRequest);
			break;

		case QueryMemberList:
			QueryMemberListRequest queryMemberListRequest = null;
			queryMemberListRequest = FastJson.json2Bean(jsonText,
					QueryMemberListRequest.class);
			sohuMail.pageQueryMember(this.getConfig(), queryMemberListRequest);
			break;

		case QueryMember:
			QueryMemberRequest queryMemberRequest = null;
			queryMemberRequest = FastJson.json2Bean(jsonText,
					QueryMemberRequest.class);
			sohuMail.queryMember(this.getConfig(), queryMemberRequest);
			break;

		case UpdateAddress:
			UpdateAddressRequest updateAddressRequest = null;
			updateAddressRequest = FastJson.json2Bean(jsonText,
					UpdateAddressRequest.class);
			sohuMail.updateAddress(this.getConfig(), updateAddressRequest);
			break;
		case UpdateMember:
			UpdateMemberRequest updateMemberRequest = null;
			updateMemberRequest = FastJson.json2Bean(jsonText,
					UpdateMemberRequest.class);
			sohuMail.updateMember(this.getConfig(), updateMemberRequest);
			break;
		default:
			break;
		}

	}

	private SohuMailConfig getConfig() {
		SohuMailConfig config = new SohuMailConfig();
		Object tmp = null;
		Map<String, String> setting = this.app.getConfigure().getSettings();
		tmp = setting.get("shouApiKey");
		if (tmp instanceof String)
			config.setApiKey((String) tmp);
		tmp = setting.get("shouApiUser");
		if (tmp instanceof String)
			config.setApiUser((String) tmp);
		return config;

	}

	private GeneralSendSohuMailConfig getGeneralSendConfig() {
		GeneralSendSohuMailConfig config = new GeneralSendSohuMailConfig();
		Object tmp = null;
		Map<String, String> setting = this.app.getConfigure().getSettings();
		tmp = setting.get("shouApiKey");
		if (tmp instanceof String)
			config.setApiKey((String) tmp);
		tmp = setting.get("shouApiUser");
		if (tmp instanceof String)
			config.setApiUser((String) tmp);
		tmp = setting.get("shouFrom");
		if (tmp instanceof String)
			config.setFrom((String) tmp);
		return config;

	}

	private String rawMsg;
	/**
	 * 日志
	 */
	private Logger logger = Logger.getLogger(QueueConsumer.class);

}
