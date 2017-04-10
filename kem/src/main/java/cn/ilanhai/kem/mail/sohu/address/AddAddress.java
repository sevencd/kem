package cn.ilanhai.kem.mail.sohu.address;

import java.util.Map;

import org.apache.log4j.Logger;

import cn.ilanhai.framework.common.exception.AppException;
import cn.ilanhai.framework.uitl.Str;
import cn.ilanhai.kem.mail.protocol.Result;
import cn.ilanhai.kem.mail.protocol.sohu.address.AddAddressRequest;
import cn.ilanhai.kem.mail.protocol.sohu.address.AddAddressResponse;
import cn.ilanhai.kem.mail.sohu.SohuMailConfig;
import cn.ilanhai.kem.util.HttpHelper;

public class AddAddress {

	public AddAddress() {

	}

	public AddAddressResponse add(SohuMailConfig cfg,
			AddAddressRequest req) throws AppException {
		String doc = null;
		Map<String, String> params = null;
		AddAddressResponse resp = null;
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
		if (Str.isNullOrEmpty(req.getName()))
			throw new AppException(-1, "请求参数名称错误");
		params = cfg.toMap();
		params = req.map(params, req.toMap());
		doc = HttpHelper.sendPost(URL, params, CHARSET);
		LOGGER.info(doc);
		if (Str.isNullOrEmpty(doc))
			throw new AppException(-1, "调用远程接口失败");
		resp = Result.jsonToObject(doc, AddAddressResponse.class);
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
	private final Logger LOGGER = Logger.getLogger(AddAddress.class);
	/**
	 * 请求的url
	 */
	private final String URL = "http://api.sendcloud.net/apiv2/addresslist/add";
}
