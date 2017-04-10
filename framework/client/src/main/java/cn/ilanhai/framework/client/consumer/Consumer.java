package cn.ilanhai.framework.client.consumer;

import org.springframework.context.support.*;

import cn.ilanhai.framework.service.*;

public class Consumer {
	private ClassPathXmlApplicationContext context = null;
	private AppService appService = null;

	public Consumer() {
		context = new ClassPathXmlApplicationContext("consumer.xml");
		context.start();

		appService = (AppService) context.getBean("appService");
	}

	public AppService getAppService() {
		return this.appService;
	}
}
