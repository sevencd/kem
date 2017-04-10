package cn.ilanhai.httpserver.modules.requestoverlap;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import cn.ilanhai.framework.common.exception.CacheContainerException;

public interface RequestOverlap {
	int ExpireTime = 3000;

	boolean overlap(ServletRequest request, ServletResponse response)
			throws IOException, ServletException;

	void write(ServletRequest request, ServletResponse response, String data)
			throws IOException, ServletException;

	void setExpireTime(int expireTime);

	int getExpireTime();
}
