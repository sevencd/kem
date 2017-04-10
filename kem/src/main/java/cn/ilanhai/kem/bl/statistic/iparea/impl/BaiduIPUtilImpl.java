package cn.ilanhai.kem.bl.statistic.iparea.impl;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.ilanhai.framework.common.exception.BlAppException;
import cn.ilanhai.kem.bl.BLContextUtil;
import cn.ilanhai.kem.bl.statistic.iparea.IpAreaEntity;
import cn.ilanhai.kem.bl.statistic.iparea.IpToArea;
import cn.ilanhai.kem.common.CodeTable;

public class BaiduIPUtilImpl implements IpToArea {
	String httpUrl = "http://apis.baidu.com/apistore/iplookup/iplookup_paid";
	private static String apikey = "33e024a97b8d0a549fb8c906c78648d4";
	Logger logger = Logger.getLogger(BaiduIPUtilImpl.class);

	/**
	 * @param urlAll
	 *            :请求接口
	 * @param httpArg
	 *            :参数
	 * @return 返回结果
	 */
	public static String request(String httpUrl, String httpArg) {
		BufferedReader reader = null;
		String result = null;
		StringBuffer sbf = new StringBuffer();
		httpUrl = httpUrl + "?ip=" + httpArg;

		try {
			URL url = new URL(httpUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			// 填入apikey到HTTP header
			connection.setRequestProperty("apikey", apikey);
			connection.connect();
			InputStream is = connection.getInputStream();
			reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			String strRead = null;
			while ((strRead = reader.readLine()) != null) {
				sbf.append(strRead);
				sbf.append("\r\n");
			}
			reader.close();
			result = sbf.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public IpAreaEntity getAreaEntity(String ip) throws BlAppException {
		logger.info("开始访问百度ip查询接口,ip:" + ip);
		BLContextUtil.vailIpParam(ip);
		String jsonResult = request(httpUrl, ip);
		logger.info("jsonResult:" + jsonResult);
		JSONObject object = null;
		try{
		object = JSON.parseObject(jsonResult);
		}catch(Exception e){
			
		}
		logger.info("jsonObject:" + object);
		// 验证返回值是否为null
		if (object == null) {
			CodeTable ct = CodeTable.BL_IP_ERROR;
			logger.info("获取区域失败:" + object);
			throw new BlAppException(ct.getValue(), ct.getDesc());
		}
		IpAreaEntity ipAreaEntity = null;
		// 返回成功
		if (new Integer(0).equals(object.getInteger("error"))) {
			// 解析返回数据
			ipAreaEntity = JSON.parseObject(object.getJSONObject("data").toJSONString(), IpAreaEntity.class);
			logger.info("获取区域成功,ipAreaEntity:" + ipAreaEntity);
			// 非中国 记录为其他
			if (!"China".equals(ipAreaEntity.getCountry())) {
				logger.info("该ip为国外ip记录为其他");
				ipAreaEntity = new IpAreaEntity();
				ipAreaEntity.setProvince("其他");
				ipAreaEntity.setCity("");
			}
		} else {// 返回失败 记录为其他
			logger.info("获取区域失败记录为其他");
			ipAreaEntity = new IpAreaEntity();
			ipAreaEntity.setProvince("其他");
			ipAreaEntity.setCity("");
		}
		return ipAreaEntity;
	}

	@Override
	public String getAreaName(String ip) throws BlAppException {
		IpAreaEntity ipAreaEntity = getAreaEntity(ip);
		logger.info("ipAreaEntity:" + ipAreaEntity);
		if (ipAreaEntity == null) {
			CodeTable ct = CodeTable.BL_IP_ERROR;
			String tmp = ct.getDesc();
			throw new BlAppException(ct.getValue(), tmp);
		}
		return ipAreaEntity.getCountry();
	}

	public static void main(String[] args) throws BlAppException {
		System.out.println(new BaiduIPUtilImpl().getAreaEntity("218.89.222.137"));
	}
}
