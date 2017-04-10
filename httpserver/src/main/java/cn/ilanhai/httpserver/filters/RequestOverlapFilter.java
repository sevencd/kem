package cn.ilanhai.httpserver.filters;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.annotation.Retention;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import cn.ilanhai.framework.common.exception.CacheContainerException;
import cn.ilanhai.httpserver.modules.requestoverlap.DefaultRequestOverlap;
import cn.ilanhai.httpserver.modules.requestoverlap.FiltersRule;
import cn.ilanhai.httpserver.modules.requestoverlap.RequestOverlap;

public class RequestOverlapFilter implements Filter {
	private RequestOverlap requestOverlap = null;
	private FiltersRule filtersRule = null;
	private Logger logger = Logger.getLogger(RequestOverlapFilter.class);

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		String temp = null;
		int iTemp = -1;
		try {
			this.requestOverlap = DefaultRequestOverlap.getInstance();
			temp = filterConfig.getInitParameter("expireTime");
			this.requestOverlap.setExpireTime(RequestOverlap.ExpireTime);
			if (temp != null && temp.length() > 0)
				iTemp = Integer.valueOf(temp);
			if (iTemp > 0)
				this.requestOverlap.setExpireTime(iTemp);
			// 初始化过滤规则
			this.filtersRule = FiltersRule.getFiltersRule();
		} catch (IOException e) {
			throw new ServletException(e);
		}

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		String tmp = null;
		HttpServletRequest httpServletRequest = null;
		httpServletRequest = (HttpServletRequest) request;
		Enumeration<String> enumeration = request.getParameterNames();
		while (enumeration.hasMoreElements()) {
			String key = (String) enumeration.nextElement();
			this.logger.info(String.format("%s=%s", key,
					request.getParameter(key)));
		}
		this.logger.info("enter ...");
		tmp = httpServletRequest.getRequestURI();
		this.logger.info(tmp);
		// 验证过滤
		if (this.filtersRule != null)
			if (this.filtersRule.isMatching(tmp)) {
				this.logger.info("filter data overlap");
				this.logger.info("leave ...");
			}

		if (!this.requestOverlap.overlap(request, response)) {
			this.logger.info("submit data overlap...");
			this.logger.info("leave ...");
			return;
		}
		chain.doFilter(request, response);
		this.logger.info("leave ...");
	}

	@Override
	public void destroy() {

	}

}
