package cn.ilanhai.httpserver.modules.requestoverlap;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import cn.ilanhai.framework.common.cache.redis.RedisCacheImpl;
import cn.ilanhai.framework.common.exception.CacheContainerException;
import cn.ilanhai.httpserver.filters.RequestOverlapFilter;
import cn.ilanhai.httpserver.modules.cache.Caches;

public class DefaultRequestOverlap implements RequestOverlap {
	private final String KEY_SESSION_ID = "SessionId";
	private final String DATA_ENCODEING = "UTF-8";
	private RedisCacheImpl cache = null;
	private int expireTime = 15000;
	private Logger logger = Logger.getLogger(DefaultRequestOverlap.class);
	private static DefaultRequestOverlap instance = null;

	private DefaultRequestOverlap() throws ServletException {
		try {
			this.cache = (RedisCacheImpl) Caches.getInstance()
					.getRequestOverlapCache();
		} catch (CacheContainerException e) {
			throw new ServletException(e.getMessage(), e);
		}
	}

	public static RequestOverlap getInstance() throws ServletException {
		if (instance == null)
			instance = new DefaultRequestOverlap();
		return instance;
	}

	public int getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(int expireTime) {
		this.expireTime = expireTime;
	}

	@Override
	public boolean overlap(ServletRequest request, ServletResponse response)
			throws IOException, ServletException {
		HttpServletRequest req = null;
		byte[] keyBytes = null;
		byte[] valueBytes = null;
		try {
			this.logger.info("enter overlap");
			req = (HttpServletRequest) request;
			keyBytes = generateKey(req);
			valueBytes = cache.get(keyBytes);
			if (valueBytes != null) {
				response.getWriter().write(
						new String(valueBytes, DATA_ENCODEING));
				return false;
			}
			return true;
		} catch (NoSuchAlgorithmException e) {
			this.logger.error(e);
			throw new ServletException(e.getMessage(), e);
		} catch (CacheContainerException e) {
			this.logger.error(e);
			throw new ServletException(e.getMessage(), e);
		} finally {
			this.logger.info("leave overlap");
		}
	}

	@Override
	public void write(ServletRequest request, ServletResponse response,
			String data) throws IOException, ServletException {
		if (data == null || data.length() <= 0)
			return;
		this.write(request, response, data.getBytes(DATA_ENCODEING));
	}

	private void write(ServletRequest request, ServletResponse response,
			byte[] bytes) throws IOException, ServletException {
		HttpServletRequest req = null;
		req = (HttpServletRequest) request;
		byte[] keyBytes = null;
		try {
			this.logger.info("enter write");
			if (bytes == null || bytes.length <= 0)
				return;
			keyBytes = generateKey(req);
			cache.set(keyBytes, bytes, this.expireTime / 1000);
		} catch (NoSuchAlgorithmException e) {
			this.logger.error(e);
			throw new ServletException(e.getMessage(), e);
		} catch (CacheContainerException e) {
			this.logger.error(e);
			throw new ServletException(e.getMessage(), e);
		} finally {
			this.logger.info("leave write");
		}
	}

	private byte[] generateKey(HttpServletRequest req) throws IOException,
			NoSuchAlgorithmException {
		StringBuilder sb = null;
		InputStream inputStream = null;
		byte[] bytes = null;
		byte[] bytes1 = null;
		String tmp = null;
		ByteBuffer buffer = null;
		int len = -1;
		sb = new StringBuilder();
		sb.append(req.getMethod());
		sb.append(req.getContextPath());
		sb.append(req.getPathInfo());
		sb.append(req.getQueryString());
		tmp = req.getHeader(KEY_SESSION_ID);
		if (tmp != null && tmp.length() > 0)
			sb.append(tmp);
		inputStream = req.getInputStream();
		if (inputStream != null) {
			len = inputStream.available();
			if (len > 0) {
				bytes1 = new byte[len];
				inputStream.read(bytes1);
				inputStream.reset();
			}
		}
		bytes = sb.toString().getBytes();
		len = bytes.length;
		if (bytes1 != null && bytes1.length > 0)
			len += bytes1.length;
		buffer = ByteBuffer.allocate(len);
		buffer.put(bytes);
		if (bytes1 != null && bytes1.length > 0)
			buffer.put(bytes1);
		System.out.println(len);
		System.out.println(buffer.capacity());
		System.out.println(new String(buffer.array()));
		return this.md5(buffer.array());
	}

	private byte[] md5(byte[] bytes) throws NoSuchAlgorithmException {
		MessageDigest messageDigest = MessageDigest.getInstance("MD5");
		messageDigest.update(bytes);
		return messageDigest.digest();
	}

}
