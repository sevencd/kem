package cn.ilanhai.kem.mail.sohu;

import org.apache.log4j.Logger;

import cn.ilanhai.framework.common.exception.AppException;
import cn.ilanhai.kem.mail.AbstractMail;
import cn.ilanhai.kem.mail.MailConfig;
import cn.ilanhai.kem.mail.protocol.MailInfo;
import cn.ilanhai.kem.mail.protocol.Result;
import cn.ilanhai.kem.mail.protocol.sohu.address.AddAddressRequest;
import cn.ilanhai.kem.mail.protocol.sohu.address.AddMemberRequest;
import cn.ilanhai.kem.mail.protocol.sohu.address.DeleteAddressRequest;
import cn.ilanhai.kem.mail.protocol.sohu.address.DeleteMemberRequest;
import cn.ilanhai.kem.mail.protocol.sohu.address.QueryAddressListRequest;
import cn.ilanhai.kem.mail.protocol.sohu.address.QueryMemberListRequest;
import cn.ilanhai.kem.mail.protocol.sohu.address.QueryMemberRequest;
import cn.ilanhai.kem.mail.protocol.sohu.address.StatdayListRequest;
import cn.ilanhai.kem.mail.protocol.sohu.address.UpdateAddressRequest;
import cn.ilanhai.kem.mail.protocol.sohu.address.UpdateMemberRequest;
import cn.ilanhai.kem.mail.protocol.sohu.label.AddLabelRequest;
import cn.ilanhai.kem.mail.sohu.address.AddAddress;
import cn.ilanhai.kem.mail.sohu.address.AddMember;
import cn.ilanhai.kem.mail.sohu.address.DeleteAddress;
import cn.ilanhai.kem.mail.sohu.address.DeteteMember;
import cn.ilanhai.kem.mail.sohu.address.QueryAddressList;
import cn.ilanhai.kem.mail.sohu.address.QueryMember;
import cn.ilanhai.kem.mail.sohu.address.QueryMemberList;
import cn.ilanhai.kem.mail.sohu.address.StatdayList;
import cn.ilanhai.kem.mail.sohu.address.UpdateAddress;
import cn.ilanhai.kem.mail.sohu.address.UpdateMember;
import cn.ilanhai.kem.mail.sohu.label.AddLabel;

/**
 * sohu邮件
 * 
 * @author he
 *
 */
public class SohuMail extends AbstractMail {

	@Override
	public Result sendTo(MailConfig config, MailInfo info) throws AppException {
		return new GeneralSendSohuMail().sendTo(config, info);
	}

	@Override
	public Result pageQueryAddress(MailConfig config, MailInfo info)
			throws AppException {
		QueryAddressListRequest req = null;
		SohuMailConfig cfg = null;
		if (!(info instanceof QueryAddressListRequest))
			throw new AppException(-1, "请求参数类型错误");
		if (!(config instanceof SohuMailConfig))
			throw new AppException(-1, "配置参数类型错误");
		req = (QueryAddressListRequest) info;
		cfg = (SohuMailConfig) config;
		return new QueryAddressList().query(cfg, req);
	}

	@Override
	public Result addAddress(MailConfig config, MailInfo info)
			throws AppException {
		AddAddressRequest req = null;
		SohuMailConfig cfg = null;
		if (!(info instanceof AddAddressRequest))
			throw new AppException(-1, "请求参数类型错误");
		if (!(config instanceof SohuMailConfig))
			throw new AppException(-1, "配置参数类型错误");
		req = (AddAddressRequest) info;
		cfg = (SohuMailConfig) config;
		return new AddAddress().add(cfg, req);
	}

	@Override
	public Result deleteAddress(MailConfig config, MailInfo info)
			throws AppException {

		DeleteAddressRequest req = null;
		SohuMailConfig cfg = null;
		if (!(info instanceof DeleteAddressRequest))
			throw new AppException(-1, "请求参数类型错误");
		if (!(config instanceof SohuMailConfig))
			throw new AppException(-1, "配置参数类型错误");
		req = (DeleteAddressRequest) info;
		cfg = (SohuMailConfig) config;
		return new DeleteAddress().delete(cfg, req);
	}

	@Override
	public Result updateAddress(MailConfig config, MailInfo info)
			throws AppException {

		UpdateAddressRequest req = null;
		SohuMailConfig cfg = null;
		if (!(info instanceof UpdateAddressRequest))
			throw new AppException(-1, "请求参数类型错误");
		if (!(config instanceof SohuMailConfig))
			throw new AppException(-1, "配置参数类型错误");
		req = (UpdateAddressRequest) info;
		cfg = (SohuMailConfig) config;
		return new UpdateAddress().update(cfg, req);
	}

	@Override
	public Result pageQueryMember(MailConfig config, MailInfo info)
			throws AppException {
		QueryMemberListRequest req = null;
		SohuMailConfig cfg = null;
		if (!(info instanceof QueryMemberListRequest))
			throw new AppException(-1, "请求参数类型错误");
		if (!(config instanceof SohuMailConfig))
			throw new AppException(-1, "配置参数类型错误");
		req = (QueryMemberListRequest) info;
		cfg = (SohuMailConfig) config;
		return new QueryMemberList().query(cfg, req);
	}

	@Override
	public Result queryMember(MailConfig config, MailInfo info)
			throws AppException {
		QueryMemberRequest req = null;
		SohuMailConfig cfg = null;
		if (!(info instanceof QueryMemberRequest))
			throw new AppException(-1, "请求参数类型错误");
		if (!(config instanceof SohuMailConfig))
			throw new AppException(-1, "配置参数类型错误");
		req = (QueryMemberRequest) info;
		cfg = (SohuMailConfig) config;
		return new QueryMember().query(cfg, req);
	}

	@Override
	public Result addMember(MailConfig config, MailInfo info)
			throws AppException {

		AddMemberRequest req = null;
		SohuMailConfig cfg = null;
		if (!(info instanceof AddMemberRequest))
			throw new AppException(-1, "请求参数类型错误");
		if (!(config instanceof SohuMailConfig))
			throw new AppException(-1, "配置参数类型错误");
		req = (AddMemberRequest) info;
		cfg = (SohuMailConfig) config;
		return new AddMember().add(cfg, req);
	}

	@Override
	public Result deleteMember(MailConfig config, MailInfo info)
			throws AppException {
		DeleteMemberRequest req = null;
		SohuMailConfig cfg = null;
		if (!(info instanceof DeleteMemberRequest))
			throw new AppException(-1, "请求参数类型错误");
		if (!(config instanceof SohuMailConfig))
			throw new AppException(-1, "配置参数类型错误");
		req = (DeleteMemberRequest) info;
		cfg = (SohuMailConfig) config;
		return new DeteteMember().detete(cfg, req);
	}

	@Override
	public Result updateMember(MailConfig config, MailInfo info)
			throws AppException {
		UpdateMemberRequest req = null;
		SohuMailConfig cfg = null;
		if (!(info instanceof UpdateMemberRequest))
			throw new AppException(-1, "请求参数类型错误");
		if (!(config instanceof SohuMailConfig))
			throw new AppException(-1, "配置参数类型错误");
		req = (UpdateMemberRequest) info;
		cfg = (SohuMailConfig) config;
		return new UpdateMember().update(cfg, req);
	}

	

	@Override
	public Result statdayList(MailConfig config, MailInfo info) throws AppException {
		StatdayListRequest req=null;
		SohuMailConfig cfg = null;
		if (!(info instanceof StatdayListRequest))
			throw new AppException(-1, "请求参数类型错误");
		if (!(config instanceof SohuMailConfig))
			throw new AppException(-1, "配置参数类型错误");
		req = (StatdayListRequest) info;
		cfg = (SohuMailConfig) config;
		return new StatdayList().getData(cfg, req);
	}
	
	@Override
	public Result addLabel(MailConfig config, MailInfo info)
			throws AppException {
		AddLabelRequest req = null;
		SohuMailConfig cfg = null;
		if (!(info instanceof AddLabelRequest))
			throw new AppException(-1, "请求参数类型错误");
		if (!(config instanceof SohuMailConfig))
			throw new AppException(-1, "配置参数类型错误");
		req = (AddLabelRequest) info;
		cfg = (SohuMailConfig) config;
		return new AddLabel().add(cfg, req);
	}

	private final Logger logger = Logger.getLogger(SohuMail.class);
}
