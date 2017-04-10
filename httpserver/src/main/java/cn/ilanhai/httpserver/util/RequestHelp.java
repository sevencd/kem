package cn.ilanhai.httpserver.util;

import java.net.URI;

import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import cn.ilanhai.httpserver.remoteservice.AppServiceConsumer;
import cn.ilanhai.httpserver.servlet.UserImgUploadServlet;

public class RequestHelp {

	private static Logger logger=Logger.getLogger(UserImgUploadServlet.class);
	
	public static final int sizeMB = 3;
	public static final int sizeImg = 10;

	public static String serviceJson(HttpServletRequest req,Map<String, Object> pathMap)
			throws ServletException {
		String json = null;
		URI location = null;
		json = JSON.toJSONString(pathMap);
		location = ServletHelper.getURIFromString(true, req);
		if (location == null)
			throw new ServletException("处理服务定位出错");
		logger.info("数据："+json);
		return AppServiceConsumer.getInstance().getAppService().serviceJSON(location, json);
	}

	public static String convertFileSize(long size) {
		long kb = 1024;
		long mb = kb * 1024;
		long gb = mb * 1024;
		if (size >= gb) {
			return String.format("%.1f GB", (float) size / gb);
		} else if (size >= mb) {
			float f = (float) size / mb;
			return String.format(f > 100 ? "%.0f MB" : "%.1f MB", f);
		} else if (size >= kb) {
			float f = (float) size / kb;
			return String.format(f > 100 ? "%.0f KB" : "%.1f KB", f);
		} else
			return String.format("%d B", size);
	}

	public static boolean checkSize(long size) {
		long sizeb = sizeMB * 1024 * 1024;
		if (size > sizeb) {
			return true;
		} else {
			return false;
		}

	}
}
