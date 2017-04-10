package cn.ilanhai.kem.mail.sohu.address;

import java.util.Map;

import org.apache.log4j.Logger;

import cn.ilanhai.framework.common.exception.AppException;
import cn.ilanhai.framework.uitl.Str;
import cn.ilanhai.kem.mail.protocol.Result;
import cn.ilanhai.kem.mail.protocol.sohu.address.UpdateMemberRequest;
import cn.ilanhai.kem.mail.protocol.sohu.address.UpdateMemberResponse;
import cn.ilanhai.kem.mail.sohu.SohuMailConfig;
import cn.ilanhai.kem.util.HttpHelper;

public class UpdateMember {
	public UpdateMember() {

	}

	public UpdateMemberResponse update(SohuMailConfig cfg,
			UpdateMemberRequest req) throws AppException {
		String doc = null;
		Map<String, String> params = null;
		UpdateMemberResponse resp = null;
		if (cfg == null)
			throw new AppException(-1, "配置参数出错");
		if (Str.isNullOrEmpty(cfg.getApiUser()))
			throw new AppException(-1, "配置ApiUser出错");
		if (Str.isNullOrEmpty(cfg.getApiKey()))
			throw new AppException(-1, "配置ApiKey出错");
		if (req == null)
			throw new AppException(-1, "请求参数错误");
		if (Str.isNullOrEmpty(req.getAddress()))
			throw new AppException(-1, "请求参数地址错误");
		if (req.getMember() == null || req.getMember().size() <= 0)
			throw new AppException(-1, "请求参数成员地址错误");
		if (req.getName() == null || req.getName().size() <= 0)
			throw new AppException(-1, "请求参数成员名称错误");
		if (req.getName().size() > req.getMember().size())
			throw new AppException(-1, "请求参数成员名称不能大于地址数");
		if (!req.mailMaxMemberIsOk(MAX_MEMBER_NUMBER))
			throw new AppException(-1, String.format("请求参数成员数不能大于%s",
					MAX_MEMBER_NUMBER));
		if (!req.mailAddressFormatIsOk(req.getMember()))
			throw new AppException(-1, "请求参数成员地址格式错误");
		if (!req.mailAddressFormatIsOk(req.getNewMember()))
			throw new AppException(-1, "请求参数成员地址格式错误");
		if (req.getMember().size() != req.getNewMember().size())
			throw new AppException(-1, "请求参数成员地址不等于新成员地址数");
		if (req.getName().size() > req.getMember().size())
			throw new AppException(-1, "请求参数成员名称不大于成员地址数");
		params = cfg.toMap();
		params = req.map(params, req.toMap());
		doc = HttpHelper.sendPost(URL, params, CHARSET);
		LOGGER.info(doc);
		if (Str.isNullOrEmpty(doc))
			throw new AppException(-1, "调用远程接口失败");
		resp = Result.jsonToObject(doc, UpdateMemberResponse.class);
		if (resp == null)
			throw new AppException(-1, "调用远程返回值格式错误");
		return resp;
	}

	/**
	 * 编码
	 */
	private final String CHARSET = "utf-8";
	/**
	 * 日志
	 */
	private final Logger LOGGER = Logger.getLogger(UpdateMember.class);
	/**
	 * 请求的url
	 */
	private final String URL = "http://api.sendcloud.net/apiv2/addressmember/update";
	private final int MAX_MEMBER_NUMBER = 1000;
}
