package cn.ilanhai.httpserver.remoteservice;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import cn.ilanhai.framework.service.AppService;

/**
 * 远程服务
 * 
 * @author he
 *
 */
public class AppServiceConsumer {
	private ClassPathXmlApplicationContext context = null;
	private AppService appService = null;
	private static AppServiceConsumer instance = null;
	private final String CONF_FILE_NAME = "consumer.xml";
	private boolean flag = false;

	private AppServiceConsumer() {

	}

	public final static AppServiceConsumer getInstance() {
		if (instance == null)
			instance = new AppServiceConsumer();
		return instance;

	}

	public void init() {
		if (this.flag)
			return;
		context = new ClassPathXmlApplicationContext(CONF_FILE_NAME);
		context.start();
		this.flag = true;
		System.out.println("remote service started");
	}

	public final AppService getAppService() {
		if (this.appService == null)
			this.appService = (AppService) context.getBean("appService");
		return this.appService;
	}
}
