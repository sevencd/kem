package cn.ilanhai.httpserver.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.IllegalFormatCodePointException;
import java.util.Map;
import java.util.Map.Entry;

import javassist.expr.NewArray;

import javax.management.ValueExp;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;

import cn.ilanhai.framework.uitl.FastJson;

public class ServletHelper {
	private static final Logger logger = Logger.getLogger(ServletHelper.class);
	public final static String KEY_CLIENT_TYPE = "ClientType";
	public final static boolean IS_INCLUDE_SITE_NAME = true;
	public final static String KEY_SESSION_ID = "SessionId";
	public final static String KEY_SESSION_ID_X = "sessionId";
	public final static String KEY_CONTENT_TYPE = "Content-Type";

	/**
	 * 将请求路经转成服务定位
	 * 
	 * @param isIncludeSiteName
	 * @param uri
	 * @return
	 */
	public static URI getURIFromString(boolean isIncludeSiteName, HttpServletRequest request) {
		String[] segment = null;
		String url = "";
		String uri = null;
		try {
			uri = request.getRequestURI();
			if (uri == null || uri.length() <= 0)
				return null;
			segment = uri.split("/");
			if (segment == null)
				return null;
			if (segment.length < 4)
				return null;
			url = "http://%s/%s/%s";
			if (isIncludeSiteName) {
				if (segment.length < 5)
					return null;
				url = String.format(url, segment[2], segment[3], segment[4]);
			} else {
				url = String.format(url, segment[1], segment[2], segment[3]);
			}
			url = String.format("%s?%s=%s&%s=%s&%s=%s&%s=%s&%s=%s", url, KEY_SESSION_ID,
					getHeader(KEY_SESSION_ID, request), KEY_CLIENT_TYPE, getHeader(KEY_CLIENT_TYPE, request),
					"RemoteAddr", getRemoteAddr(request), "Referer", getReferer(request), "RequestTime",
					getRequestTime());
			return new URI(url);
		} catch (URISyntaxException e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * 读取http请求 get方法url上的数据
	 * 
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String getJsonStringFromHttpServletRequest(HttpServletRequest request)
			throws UnsupportedEncodingException {

		Map<String, String[]> parameterMap = request.getParameterMap();
		Map<String, String> returnMap = new HashMap<String, String>();
		java.util.Iterator<Entry<String, String[]>> entries = parameterMap.entrySet().iterator();
		Map.Entry<String, String[]> entry;
		String name = "";
		String value = "";
		Object valueObj = null;
		while (entries.hasNext()) {
			entry = (Entry<String, String[]>) entries.next();
			name = (String) entry.getKey();
			valueObj = entry.getValue();
			if (null == valueObj) {
				value = "";
			} else if (valueObj instanceof String[]) {
				String[] values = (String[]) valueObj;
				for (int i = 0; i < values.length; i++) {
					value = values[i] + ",";
				}
				value = value.substring(0, value.length() - 1);
			} else {
				value = valueObj.toString();
			}
			returnMap.put(name, new String(value.getBytes("iso8859-1"), "UTF-8"));
		}
		if (returnMap.isEmpty()) {
			return "";
		} else if (returnMap.size() == 1) {
			String obj = null;
			for (Entry<String, String> e : returnMap.entrySet()) {
				obj = e.getValue();
				break;
			}
			return FastJson.bean2Json(obj);
		} else {
			return FastJson.bean2Json(returnMap);
		}
	}

	/**
	 * 读取http请求 post的数据
	 * 
	 * @param request
	 * @param encoding
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public static String readJsonStringFromHttpServletRequest(HttpServletRequest request, String encoding)
			throws ServletException, IOException {
		String tmp = null;
		tmp = request.getContentType();
		if (tmp.toLowerCase().contains("application/json".toLowerCase()))
			return getJsonDataFromRequest(request, encoding);
		else if (tmp.equalsIgnoreCase("text/xml"))
			return getXmlDataFromRequest(request, encoding);
		else
			return getFormDataFromRequest(request, encoding);
	}

	private static String getFormDataFromRequest(HttpServletRequest request, String encoding)
			throws ServletException, IOException {
		return getJsonStringFromHttpServletRequest(request);
	}

	private static String getXmlDataFromRequest(HttpServletRequest request, String encoding)
			throws ServletException, IOException {
		BufferedReader br = null;
		InputStreamReader reader = null;
		StringBuffer sb = null;
		String xml = null;
		try {
			reader = new InputStreamReader(request.getInputStream());
			br = new BufferedReader(reader);
			sb = new StringBuffer();
			String temp;
			while ((temp = br.readLine()) != null) {
				sb.append(temp);
			}
			br.close();
			xml = sb.toString();
			return FastJson.bean2Json(xml);
		} catch (Exception e) {
			throw new ServletException(e);
		} finally {
			if (reader != null)
				reader.close();
			if (br != null)
				br.close();
		}
	}

	private static String getJsonDataFromRequest(HttpServletRequest request, String encoding)
			throws ServletException, IOException {
		BufferedReader br = null;
		InputStreamReader reader = null;
		StringBuffer sb = null;
		try {
			reader = new InputStreamReader(request.getInputStream(), encoding);
			br = new BufferedReader(reader);
			sb = new StringBuffer();
			String temp;
			while ((temp = br.readLine()) != null) {
				sb.append(temp);
			}
			br.close();
			return sb.toString();
		} catch (Exception e) {
			throw new ServletException(e);
		} finally {
			if (reader != null)
				reader.close();
			if (br != null)
				br.close();
		}
	}

	/**
	 * 从Http头中获取指定key的值，如果找不到就从cookie中找
	 * 
	 * @param key
	 * @param request
	 * @return
	 */
	public static String getHeader(String key, HttpServletRequest request) {
		String value = null;
		Cookie[] cookies = null;
		Cookie cookie = null;
		try {
			value = request.getHeader(key);
			if (value == null || value.length() <= 0) {
				cookies = request.getCookies();
				if (cookies == null || cookies.length <= 0)
					return value;
				for (int i = 0; i < cookies.length; i++) {
					cookie = cookies[i];
					if (cookie.getName().equalsIgnoreCase(key)) {
						value = cookie.getValue();
						break;
					}
				}
			}
			if (value == null)
				value = "none";
			value = URLEncoder.encode(value, "utf-8");
			return value;
		} catch (UnsupportedEncodingException e) {
			return "none";
		}
	}

	/**
	 * @param response
	 * @param key
	 * @param value
	 */
	public static void setHeader(HttpServletResponse response, String key, String value, boolean isWriteCookie) {
		Cookie cookie = null;
		if (key == null || key.length() <= 0)
			return;
		response.addHeader(key, value);
		if (!isWriteCookie)
			return;
		cookie = new Cookie(key, value);
		response.addCookie(cookie);
	}

	public static String getStringFromJsonString(String jsonString, String key) {
		if (jsonString == null || jsonString.length() <= 0)
			return null;
		if (key == null || key.length() <= 0)
			return null;
		return JSON.parseObject(jsonString).getString(key);
	}

	/**
	 * 获取远程地址
	 * 
	 * @param request
	 * @return
	 */
	private static String getRemoteAddr(HttpServletRequest request) {
		String ipAddress = null;
		ipAddress = request.getHeader("x-forwarded-for");
		logger.info("获取x-forwarded-for到的ip为：" + ipAddress);
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("Proxy-Client-IP");
			logger.info("获取Proxy-Client-IP到的ip为：" + ipAddress);
		}
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("WL-Proxy-Client-IP");
			logger.info("获取WL-Proxy-Client-IP到的ip为：" + ipAddress);
		}
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getRemoteAddr();
			logger.info("获取request.getRemoteAddr();到的ip为：" + ipAddress);
			if (ipAddress.equals("127.0.0.1")) {
				// 根据网卡取本机配置的IP
				InetAddress inet = null;
				try {
					inet = InetAddress.getLocalHost();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
				ipAddress = inet.getHostAddress();
				logger.info("获取inet.getHostAddress()到的ip为：" + ipAddress);
			}

		}
		// 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
		if (ipAddress != null && ipAddress.length() > 15) { // "***.***.***.***".length()
															// = 15
			if (ipAddress.indexOf(",") > 0) {
				ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
				logger.info("获取ipAddress.substring(0, ipAddress.indexOf(\",\"))到的ip为：" + ipAddress);
			}
		}
		try {
			ipAddress = ipAddress.substring(ipAddress.lastIndexOf(":") + 1, ipAddress.length());
			String reluet = URLEncoder.encode(ipAddress, "utf-8");
			logger.info("获取reluet到的ip为：" + reluet);
			return reluet;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "none";
		}

	}
	
	private static String getReferer(HttpServletRequest request) {
		String tmp = null;
		try {
			tmp = request.getHeader("Referer");
			if (tmp == null || tmp.length() <= 0)
				return "none";
			return URLEncoder.encode(tmp, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "none";
		}
	}

	private static String getRequestTime() {
		try {
			return URLEncoder.encode(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "none";
		}
	}
}
