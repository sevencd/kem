package cn.ilanhai.framework.host;

import org.apache.log4j.Logger;
import org.springframework.context.support.*;

import cn.ilanhai.framework.app.*;
import cn.ilanhai.framework.container.*;
import cn.ilanhai.framework.common.exception.*;
import cn.ilanhai.framework.common.parameter.ParameterManager;
import cn.ilanhai.framework.uitl.Str;

import java.io.File;
import java.net.*;

/**
 * 定义服务器
 * 
 * @author he
 *
 */
public class ServerImpl implements Server {

	private static final Logger logger = Logger.getLogger(ServerImpl.class);

	private static ServerImpl instance = null;
	private final String SERVICE_PROVIDER_CONF_FILE_NAME="provider.xml";
	private FileSystemXmlApplicationContext  applicationContext = null;
	private boolean flag = false;
	private ApplicationManager applicationManager = null;
	
	private ServerImpl() {
		this.applicationManager = ApplicationManagerImpl.getInstance();
	}

	public static Server getInstance() {
		if (instance == null)
			instance = new ServerImpl();
		return instance;
	}

	public void start() throws ContainerException {
		try {
			logger.info("begin start server");
			if (this.flag)
				return;

			this.initApplicationManager();
			
			this.applicationContext = new FileSystemXmlApplicationContext (
					this.getProviderPath());
			this.applicationContext.start();
			this.flag = true;
			logger.info("end start server");
		} catch (Exception e) {
			logger.error(e.toString());
			throw new ContainerException(e);
		}
	}
	private String getProviderPath(){
		
		//String tmp="conf"+File.separatorChar;//+"provider.xml";
		//String file="provider.xml";

		//ParameterManager
		//if(ParameterManager.getInstance().getParameterValue(ParameterManager.KEY_NAME)!=null){
			//file=ParameterManager.getInstance().getParameterValue(ParameterManager.KEY_NAME)+File.separatorChar+file;
		//}
		String tmp = String.format("file:%s%sconf%s%s", 
				System.getProperty("user.dir"),
				File.separatorChar,
				File.separatorChar,
				SERVICE_PROVIDER_CONF_FILE_NAME);
		//tmp+=file;
		logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>");
		logger.info(tmp);
		return tmp;
	}

	/**
	 * 初始化应用容器
	 * 
	 * @throws ContainerException
	 */
	private void initApplicationManager() throws ContainerException {

		logger.info("start initial appalication manager");

		try {

			applicationManager.init();
			applicationManager.start();

		} catch (ContainerException e) {
			throw e;
		} catch (Exception e) {
			throw new ContainerException(e);
		}
		logger.info("end initial appalication manager");
	}

	public void stop() throws ContainerException {
		try {
			if (!this.flag)
				return;
			if (this.applicationContext != null)
				this.applicationContext.stop();
			this.flag = false;
		} catch (Exception e) {
			throw new ContainerException(e);
		}
	}

	public void close() throws ContainerException {
		try {
			if (!this.flag)
				return;
			if (applicationManager != null)
				applicationContext.close();
			if (this.applicationContext != null)
				this.applicationContext.close();
			this.flag = false;

		} catch (Exception e) {
			throw new ContainerException(e);
		}
	}

}
