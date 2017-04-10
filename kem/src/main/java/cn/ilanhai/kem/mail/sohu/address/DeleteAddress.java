package cn.ilanhai.kem.mail.sohu.address;

import java.util.Map;

import org.apache.log4j.Logger;

import cn.ilanhai.framework.common.exception.AppException;
import cn.ilanhai.framework.uitl.Str;
import cn.ilanhai.kem.mail.protocol.Result;
import cn.ilanhai.kem.mail.protocol.sohu.address.DeleteAddressRequest;
import cn.ilanhai.kem.mail.protocol.sohu.address.DeleteAddressResponse;
import cn.ilanhai.kem.mail.sohu.SohuMailConfig;
import cn.ilanhai.kem.util.HttpHelper;

public class DeleteAddress {
	public DeleteAddress() {

	}

	public DeleteAddressResponse  delete(SohuMailConfig cfg,
			DeleteAddressRequest req) throws AppException {
		String doc = null;
		Map<String, String> params = null;
		DeleteAddressResponse resp = null;
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
		params = cfg.toMap();
		params = req.map(params, req.toMap());
		doc = HttpHelper.sendPost(URL, params, CHARSET);
		LOGGER.info(doc);
		if (Str.isNullOrEmpty(doc))
			throw new AppException(-1, "调用远程接口失败");
		resp = Result.jsonToObject(doc, DeleteAddressResponse.class);
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
	private final Logger LOGGER = Logger.getLogger(DeleteAddress.class);
	/**
	 * 请求的url
	 */
	private final String URL = "http://api.sendcloud.net/apiv2/addresslist/delete";
}
