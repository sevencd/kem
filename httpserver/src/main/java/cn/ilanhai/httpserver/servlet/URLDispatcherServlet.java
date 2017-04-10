package cn.ilanhai.httpserver.servlet;

import java.io.*;
import java.net.URI;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import org.apache.log4j.Logger;

import cn.ilanhai.httpserver.modules.cache.Caches;
import cn.ilanhai.httpserver.modules.requestoverlap.DefaultRequestOverlap;
import cn.ilanhai.httpserver.remoteservice.AppServiceConsumer;
import cn.ilanhai.httpserver.util.ServletHelper;

public class URLDispatcherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(URLDispatcherServlet.class);

	public URLDispatcherServlet() {
		super();
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String jsonString = null;
		jsonString = ServletHelper.getJsonStringFromHttpServletRequest(request);
		this.handler(request, response, jsonString);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String jsonSting = null;
		

		jsonSting = ServletHelper.readJsonStringFromHttpServletRequest(request,
				"utf-8");
		this.handler(request, response, jsonSting);
	}

	private void handler(HttpServletRequest request,
			HttpServletResponse response, String jsonString)
			throws ServletException, IOException {
		URI location = null;
		String tmp = null;
		logger.info("处理开始...");
		logger.info(request.getContentType());
		location = ServletHelper.getURIFromString(
				ServletHelper.IS_INCLUDE_SITE_NAME, request);
		if (location == null)
			throw new ServletException("服务定位出错");
		logger.info("远程服务定位：" + location);
		logger.info("远程服务参数：" + jsonString);
		
		jsonString = AppServiceConsumer.getInstance().getAppService()
				.serviceJSON(location, jsonString);
		logger.info("远程服务结果：" + jsonString);
		tmp = ServletHelper.getStringFromJsonString(jsonString,
				ServletHelper.KEY_SESSION_ID_X);
		ServletHelper.setHeader(response, ServletHelper.KEY_SESSION_ID, tmp,
				true);
		tmp = ServletHelper.getHeader(ServletHelper.KEY_CONTENT_TYPE, request);
		ServletHelper.setHeader(response, ServletHelper.KEY_CONTENT_TYPE, tmp,
				false);
		response.getWriter().write(jsonString);
		DefaultRequestOverlap.getInstance()
				.write(request, response, jsonString);
		logger.info("处理完成...");
	}
}
