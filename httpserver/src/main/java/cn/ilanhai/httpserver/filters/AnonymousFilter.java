package cn.ilanhai.httpserver.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 匿名会话过滤器，需要确保用户至少是匿名用户的，需要此过滤器 如果用户未开启匿名会话，将会返回403错误码
 * 
 * @author Nature
 *
 */
public class AnonymousFilter implements Filter {

	public void init(FilterConfig filterConfig) throws ServletException {
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		// 获取token
		String token = req.getHeader("Token");

		// 如果会话不存在

		chain.doFilter(request, response);
	}

	public void destroy() {

	}

}
