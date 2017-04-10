package cn.ilanhai.kem.mail.sohu.address;

import java.util.Map;

import org.apache.log4j.Logger;

import cn.ilanhai.framework.common.exception.AppException;
import cn.ilanhai.framework.uitl.Str;
import cn.ilanhai.kem.mail.protocol.Result;
import cn.ilanhai.kem.mail.protocol.sohu.address.AddAddressRequest;
import cn.ilanhai.kem.mail.protocol.sohu.address.AddAddressResponse;
import cn.ilanhai.kem.mail.protocol.sohu.address.StatdayListRequest;
import cn.ilanhai.kem.mail.protocol.sohu.address.StatdayListResponse;
import cn.ilanhai.kem.mail.sohu.SohuMailConfig;
import cn.ilanhai.kem.util.HttpHelper;

/**
 * 数据统计
 * @author Nature
 *
 */
public class StatdayList {
	public StatdayListResponse getData(SohuMailConfig cfg,
			StatdayListRequest req) throws AppException {
		String doc = null;
		Map<String, String> params = null;
		StatdayListResponse resp = null;
		if (cfg == null)
			throw new AppException(-1, "配置参数出错");
		if (Str.isNullOrEmpty(cfg.getApiUser()))
			throw new AppException(-1, "配置ApiUser出错");
		if (Str.isNullOrEmpty(cfg.getApiKey()))
			throw new AppException(-1, "配置ApiKey出错");
		if (req == null)
			throw new AppException(-1, "请求参数错误");
//		if(req.getLabelIdList()==null){
//			throw new AppException(-1,"无法识别的邮件");
//		}
		params = cfg.toMap();
		params = req.map(params, req.toMap());
		doc = HttpHelper.sendPost(URL, params, CHARSET);
		LOGGER.info(doc);
		if (Str.isNullOrEmpty(doc))
			throw new AppException(-1, "调用远程接口失败");
		resp = Result.jsonToObject(doc, StatdayListResponse.class);
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
	private final Logger LOGGER = Logger.getLogger(StatdayList.class);
	/**
	 * 请求的url
	 */
	private final String URL = "http://api.sendcloud.net/apiv2/statday/list";
}
