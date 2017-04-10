package cn.ilanhai.framework.app;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.*;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.context.*;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.ilanhai.framework.app.domain.*;
import cn.ilanhai.framework.common.cache.CacheManager;
import cn.ilanhai.framework.common.configuration.app.Configure;
import cn.ilanhai.framework.common.ds.DsManager;
import cn.ilanhai.framework.common.ds.DsManagerImpl;
import cn.ilanhai.framework.common.exception.*;
import cn.ilanhai.framework.common.session.Session;
import cn.ilanhai.framework.common.session.SessionManager;
import cn.ilanhai.framework.app.service.*;
import cn.ilanhai.framework.InterfaceUtil.AopTargetUtils;
import cn.ilanhai.framework.InterfaceUtil.InterfaceDocAnnotation;
import cn.ilanhai.framework.app.args.*;
import cn.ilanhai.framework.app.bl.DefaultBlProxyFactoryImpl;
import cn.ilanhai.framework.common.CT;
import cn.ilanhai.framework.uitl.*;

/**
 * 定义应用
 * 
 * @author he
 *
 */
public class ApplicationImpl implements Application {

	private static Logger logger = Logger.getLogger(ApplicationImpl.class);

	// 应用上下文
	private ApplicationContext applicationContext = null;

	// 服务代理
	private ServiceProxy serviceProxy = null;

	// 应用id
	private String id = "";

	// 应用
	private String name = "";
	// 配置
	private Configure configure = null;

	private SessionManager sessionManager = null;

	private Counter counter = null;

	private CacheManager cacheManager = null;

	private DsManager dsManager = null;

	public ApplicationImpl(Configure configure) {
		this.serviceProxy = DefaultServiceProxyImpl.getInstance();
		this.id = configure.getId();
		this.name = configure.getName();
		this.configure = configure;
		this.dsManager = new DsManagerImpl();
		this.counter = new Counter();
	}

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public ServiceProxy getServiceProxy() {
		return serviceProxy;
	}

	/**
	 * 初始化一个请求上下文
	 * 
	 * @param location
	 * @param args
	 * @return
	 */
	private RequestContext newRequestContextInstance(URI location, Args args) throws SessionContainerException {
		logger.info("new request location:" + location.toString());
		RequestContextImpl requestContext = null;
		Session session = null;
		String sessionId = null;
		String clientType = null;
		requestContext = new RequestContextImpl();
		requestContext.setLocation(location);
		requestContext.setApplication(this);
		requestContext.setArgs(args);
		if (!location.toString().contains("kem/session/gettoken")) {
			sessionId = requestContext.getQueryString("SessionId");
		}
		clientType = requestContext.getQueryString("ClientType");
		session = this.sessionManager.getSession(sessionId, clientType);
		requestContext.setSession(session);

		logger.info("construct new request success");
		return requestContext;
	}

	/**
	 * 服务调用
	 * 
	 * @param requestContext
	 * @return
	 * @throws AppException
	 */
	private Object invoke(RequestContext requestContext) throws ContainerException, AppException {
		logger.info("begin invoke");
		CT ct;
		Object obj = null;
		this.counter.totalCounter.getAndIncrement();
		this.counter.currentCounter.getAndIncrement();
		try {
			obj = this.serviceProxy.invoke(requestContext);
			this.counter.currentCounter.getAndDecrement();
			this.counter.successCounter.getAndIncrement();

			return obj;
		} catch (AppException e) {
			this.counter.currentCounter.getAndDecrement();
			this.counter.failureCounter.getAndIncrement();
			logger.info("app exception:" + e.toString());
			throw e;
		} catch (Throwable e) {
			this.counter.currentCounter.getAndDecrement();
			this.counter.failureCounter.getAndIncrement();
			logger.info("unexpect app exception:" + e.toString());
			ct = CT.APP_UNH_EXCEPTION;
			throw new AppException(ct.getVal(), ct.getDesc(), e);
		} finally {
			if (this.sessionManager != null)
				this.sessionManager.putSession(requestContext.getSession());
		}

	}

	/**
	 * 处理以get/post的json方式调用正常时的返回值
	 * 
	 * @param state
	 * @param data
	 * @param location
	 * @return
	 */
	private String handlerResult(boolean state, Object data, RequestContext context, String methodVersion) {
		JsonResponseImpl response = null;
		CT ct;
		response = new JsonResponseImpl();
		ct = CT.APP_FAIL;
		if (state)
			ct = CT.APP_SUCCESS;
		response.setCode(ct.getVal());
		response.setDesc(ct.getDesc());
		response.setData(data);
		response.setLocation(context.getLocation());
		response.setSessionId(context.getSession().getSessionId());
		response.setInterfaceDocUrl(methodVersion);
		return FastJson.bean2Json(response);
	}

	/**
	 * 处理以get/post的json方式调用异常时的返回值
	 * 
	 * @param e
	 * @param location
	 * @return
	 */
	private String handlerException(ContainerException e, RequestContext context, String methodVersion) {
		JsonResponseImpl response = null;
		Session session = null;
		ByteArrayOutputStream outputStream = null;
		PrintWriter printWriter = null;
		try {
			outputStream = new ByteArrayOutputStream();
			printWriter = new PrintWriter(outputStream);
			e.printStackTrace(printWriter);
			printWriter.flush();
			this.logger.error(outputStream.toString());
			response = new JsonResponseImpl();
			response.setData("");
			response.setDesc(e.getErrorDesc());
			response.setCode(e.getErrorCode());
			response.setInterfaceDocUrl(methodVersion);
			if (context != null) {
				response.setLocation(context.getLocation());
				session = context.getSession();
				if (session != null)
					response.setSessionId(session.getSessionId());
			}

			return FastJson.bean2Json(response);
		} finally {
			if (outputStream != null)
				try {
					outputStream.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			if (printWriter != null)
				printWriter.close();
		}
	}

	/**
	 * 检查服务定位格式
	 * 
	 * @param location
	 * @throws AppException
	 */
	private void checkLocation(URI location) throws AppException {
		CT ct;
		if (location == null) {
			ct = CT.APP_REQ_LOC_ERROR;
			throw new AppException(ct.getVal(), ct.getDesc());
		}
		if (!Location.isLocation(location)) {
			ct = CT.APP_REQ_LOC_FORMAT_ERROR;
			throw new AppException(ct.getVal(), ct.getDesc());
		}
	}

	public void init() throws AppException {
		CT ct;
		String bcp = "";
		try {
			if (this.configure == null) {
				ct = CT.APP_CONFIG_ERROR;
				throw new AppException(ct.getVal(), ct.getDesc());
			}
			bcp = this.configure.getBeansConfPath();
			if (Str.isNullOrEmpty(bcp)) {
				ct = CT.APP_CONTEXT_CONFIG_FILE_PATH_ERROR;
				throw new AppException(ct.getVal(), ct.getDesc());
			}
			this.dsManager.init(this.getConfigure().getDsConf());
			SyncApplication.setApplication(this);
			this.applicationContext = new ClassPathXmlApplicationContext(bcp);
			SyncApplication.removeApplication();

		} catch (Exception e) {
			ct = CT.APP_INIT_FAIL;
			throw new AppException(ct.getVal(), ct.getDesc(), e);
		}
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Configure getConfigure() {
		return this.configure;
	}

	public void setConfigure(Configure configure) {
		this.configure = configure;
	}

	public Response service(URI location, Entity entity) throws ContainerException {
		logger.info(this.counter.toString());
		RequestContext requestContext = null;
		EntityArgs args = null;
		Object obj = null;
		ResponseImpl response = null;
		try {
			this.checkLocation(location);
			args = new EntityArgs();
			args.setEntity(entity);
			requestContext = this.newRequestContextInstance(location, args);
			obj = this.invoke(requestContext);
			response = new ResponseImpl();
			response.setData(obj);
			response.setSessionId(requestContext.getSession().getSessionId());
			return response;
		} catch (AppException e) {
			throw e;
		} catch (ContainerException e) {
			throw e;
		}
	}

	public String serviceJSON(URI location, String json) {
		logger.info(this.counter.toString());
		RequestContext requestContext = null;
		JSONArgs args = null;
		Object result = null;
		boolean flag = true;
		String interfaceDoc = "";
		try {
			//校验location是否合法
			this.checkLocation(location);
			//设置json
			args = new JSONArgs();
			args.setJsonString(json);
			//创建新的请求
			requestContext = this.newRequestContextInstance(location, args);
			//获取是否启用了接口文档配置
			boolean isInterface = Boolean.parseBoolean(getValue(requestContext, "dointerface"));
			//处理文档地址
			if (isInterface) {
				interfaceDoc = buildInterfaceDoc(requestContext);
			}

			//处理请求
			result = this.invoke(requestContext);
			if (result != null) {
				if (result.getClass() == Boolean.class) {
					flag = (Boolean) result;
					result = null;
				}
			}
			logger.info(this.counter.toString());
			return this.handlerResult(flag, result, requestContext, interfaceDoc);
		} catch (AppException e) {
			return this.handlerException(e, requestContext, interfaceDoc);
		} catch (ContainerException e) {
			return this.handlerException(e, requestContext, interfaceDoc);
		} catch (NoSuchMethodException e) {
			return this.handlerException(new ContainerException(e.getMessage()), requestContext, interfaceDoc);
		} catch (SecurityException e) {
			return this.handlerException(new ContainerException(e.getMessage()), requestContext, interfaceDoc);
		} catch (Exception e) {
			return this.handlerException(new ContainerException(e.getMessage()), requestContext, interfaceDoc);
		}
	}

	private String buildInterfaceDoc(RequestContext requestContext)
			throws BlAppException, Exception, NoSuchMethodException {
		Object target = DefaultBlProxyFactoryImpl.getInstance().getBl(requestContext);
		String actionName = requestContext.getApplicationActionNameFromLocation();
		String Url = getValue(requestContext, "interfaceurl");
		Class<? extends Object> c = AopTargetUtils.getTarget(target).getClass();
		InterfaceDocAnnotation interfaceDoc = c.getMethod(actionName, RequestContext.class)
				.getAnnotation(InterfaceDocAnnotation.class);
		StringBuilder result = new StringBuilder();
		result.append(Url);
		result.append("&projectName=" + requestContext.getLocation().getAuthority());
		if (interfaceDoc == null) {
			return result.toString();
		}
		result.append("&methodVersion=" + interfaceDoc.methodVersion());
		result.append("&url=HOST/" + requestContext.getLocation().getAuthority() + "/"
				+ getLocationPathSection(1, requestContext.getLocation()) + "/"
				+ getLocationPathSection(2, requestContext.getLocation()));
		return result.toString();
	}

	@SuppressWarnings("unchecked")
	protected String getValue(RequestContext context, String key) {
		Map<String, Object> settings = null;
		if (key == null || key.length() <= 0)
			return null;
		settings = context.getApplication().getConfigure().getSettings();
		if (settings == null)
			return null;
		if (!settings.containsKey(key))
			return null;
		return (String) settings.get(key);
	}

	private String getLocationPathSection(int index, URI location) {
		String path = "";
		String[] sectionArray = null;

		if (location == null)
			return null;
		path = location.getPath();
		location.getAuthority();
		logger.info(path);
		if (Str.isNullOrEmpty(path))
			return null;
		sectionArray = path.split("/");
		if (sectionArray == null || sectionArray.length <= 0)
			return null;
		if (index >= sectionArray.length)
			return null;
		return sectionArray[index];

	}

	public String serviceXML(URI location, String json) {
		logger.info(this.counter.toString());
		return null;
	}

	public void close() throws AppException {

	}

	public void setCacheManager(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}

	public CacheManager getCacheManager() {
		return this.cacheManager;
	}

	public void setSessionManager(SessionManager sessionManager) {
		this.sessionManager = sessionManager;
	}

	public DsManager getDs() {
		return this.dsManager;
	}

}
