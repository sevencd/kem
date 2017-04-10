package cn.ilanhai.framework.host.provider;

import java.net.*;
import java.util.*;

import org.apache.log4j.Logger;

import cn.ilanhai.framework.app.Application;
import cn.ilanhai.framework.app.Response;
import cn.ilanhai.framework.app.domain.*;
import cn.ilanhai.framework.common.exception.*;
import cn.ilanhai.framework.service.*;
import cn.ilanhai.framework.container.*;

/**
 * 定义远程服务
 * 
 * @author he
 *
 */
public class AppServiceImpl implements AppService {
	private Logger logger = Logger.getLogger(AppServiceImpl.class);

	/**
	 * 从容器中获取应用
	 * 
	 * @param location
	 * @return
	 * @throws ContainerException
	 */
	private Application getApplication(URI location) throws ContainerException {
		ApplicationManager applicationManager = null;

		if (location == null)
			return null;
		applicationManager = ApplicationManagerImpl.getInstance();
		try {
			return applicationManager.getApplication(location.getHost());
		} catch (ContainerException e) {
			throw e;
		}
	}

	public Response service(URI location, Entity entity)
			throws ContainerException {
		Application application = null;
		try {
			logger.info("Response service(URI location, Entity entity)");
			logger.info(String.format("location:%s", location));
			location = new URI(location.toString());
			application = this.getApplication(location);
			return application.service(location, entity);
		} catch (AppException e) {
			throw e;
		} catch (Exception e) {

			throw new ContainerException(e);
		}
	}

	public String serviceJSON(URI location, String json) {

		Application application = null;
		String tmp = null;
		try {
			logger.info("String serviceJSON(URI location, String json)");
			logger.info(String.format("location:%s", location));
			location = new URI(location.toString());
			application = this.getApplication(location);
			tmp = application.serviceJSON(location, json);
			return tmp;
		} catch (Exception e) {
			return e.getMessage();
		}
	}

	public String serviceXML(URI location, String json) {

		Application application = null;
		try {
			logger.info("String serviceXML(URI location, String json)");
			logger.info(String.format("location:%s", location));
			location = new URI(location.toString());
			application = this.getApplication(location);
			return application.serviceXML(location, json);
		} catch (Exception e) {
			return e.getMessage();
		}
	}

}
