package cn.ilanhai.framework.host;

import java.net.URI;

import cn.ilanhai.framework.app.Application;
import cn.ilanhai.framework.common.exception.ContainerException;

/**
 * 定义服务器接口
 * 
 * @author he
 *
 */
public interface Server {
	/**
	 * 打开
	 * 
	 * @throws ContainerException
	 */
	void start() throws ContainerException;

	/**
	 * 停止
	 * 
	 * @throws ContainerException
	 */
	void stop() throws ContainerException;

	/**
	 * 关闭
	 * 
	 * @throws ContainerException
	 */
	void close() throws ContainerException;

}
