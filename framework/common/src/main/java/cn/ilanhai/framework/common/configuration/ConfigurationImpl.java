package cn.ilanhai.framework.common.configuration;

import java.io.*;
import java.net.*;
import java.util.*;

import javax.swing.border.EtchedBorder;
import javax.xml.parsers.*;

import org.apache.log4j.Logger;
import org.w3c.dom.*;

import cn.ilanhai.framework.common.configuration.app.Configure;
import cn.ilanhai.framework.common.configuration.app.ConfigureImpl;
import cn.ilanhai.framework.common.configuration.app.Context;
import cn.ilanhai.framework.common.configuration.app.mq.MQConf;
import cn.ilanhai.framework.common.configuration.app.mq.MQConfImpl;
import cn.ilanhai.framework.common.configuration.cache.CacheConf;
import cn.ilanhai.framework.common.configuration.cache.CacheConfImpl;
import cn.ilanhai.framework.common.configuration.ds.DsConf;
import cn.ilanhai.framework.common.configuration.ds.DsConfImpl;
import cn.ilanhai.framework.common.exception.*;
import cn.ilanhai.framework.uitl.*;

/**
 * 定义配置
 * 
 * @author he
 *
 */
public class ConfigurationImpl implements Configuration {

	private static Logger logger = Logger.getLogger(ConfigurationImpl.class);

	private static ConfigurationImpl configurationImpl = null;
	private final String fileName = "container.xml";
	private Map settings = null;
	private List<Configure> configure = null;
	private List<CacheConf> cacheConf = null;
	private Map<String, Context> context = null;

	public ConfigurationImpl() {
		this.settings = new HashMap<String, Object>();
		this.configure = new ArrayList<Configure>();
		this.cacheConf = new ArrayList<CacheConf>();
		this.context = new HashMap<String, Context>();
	}

	public static Configuration getInstance() {
		if (configurationImpl == null)
			configurationImpl = new ConfigurationImpl();
		return configurationImpl;
	}

	/**
	 * 加载配置
	 */
	public final void load() throws ConfigurationContainerException {

		logger.info("start load config");
		File f = null;
		DocumentBuilderFactory factory = null;
		DocumentBuilder builder = null;
		Document doc = null;
		NodeList nl = null;
		Element root = null;
		String nodeName = "";
		Node node = null;
		String tmp = "";
		Element ele = null;
		try {
			// 获取用户目录文件夹，用户目录下的conf文件夹
			tmp = String.format("%s%sconf%s%s", System.getProperty("user.dir"), File.separatorChar, File.separatorChar,
					this.fileName);
			// 获取配置文件
			f = new File(tmp);
			// 如果配置文件不存在，则报错
			if (!f.exists())
				throw new ConfigurationContainerException(
						"Error : Config file doesn't exist! Path:" + f.getAbsolutePath());
			// 读取配置文件内容
			factory = DocumentBuilderFactory.newInstance();
			builder = factory.newDocumentBuilder();
			doc = builder.parse(f);
			root = doc.getDocumentElement();
			if (root == null)
				throw new ConfigurationContainerException("config file content format error");
			nl = root.getChildNodes();
			if (nl == null || nl.getLength() <= 0)
				return;
			for (int i = 0; i < nl.getLength(); i++) {
				node = nl.item(i);
				if (!(node instanceof Element))
					continue;
				ele = (Element) node;
				nodeName = ele.getTagName();
				if (nodeName.equals("settings")) {
					this.paserSetting(this.settings, ele);
				} else if (nodeName.equals("host")) {
					this.paserHost(ele);
				} else if (nodeName.equals("caches")) {
					this.paserCaches(ele);
				} else {
				}
			}
			this.paserContext();
		} catch (ConfigurationContainerException e) {
			throw e;
		} catch (Exception e) {
			throw new ConfigurationContainerException(e);
		} finally {
			logger.info("end load config");
		}
	}

	private final void paserSetting(Map map, Node element) throws ConfigurationContainerException {
		Node node = null;
		NodeList nl = null;
		String key = "";
		String value = "";
		Element ele = null;
		try {
			if (map == null)
				return;
			if (element == null)
				return;
			nl = element.getChildNodes();
			if (nl == null || nl.getLength() <= 0)
				return;
			for (int i = 0; i < nl.getLength(); i++) {
				node = nl.item(i);
				if (!(node instanceof Element))
					continue;
				ele = (Element) node;
				if (ele == null)
					continue;
				key = ele.getAttribute("key");
				if (Str.isNullOrEmpty(key))
					continue;
				if (map.containsKey(key))
					continue;
				value = ele.getAttribute("value");
				map.put(key, value);
			}
		} catch (Exception e) {
			throw new ConfigurationContainerException(e);
		}
	}

	private final void paserCaches(Node element) throws ConfigurationContainerException {
		Node node = null;
		Element ele = null;
		NodeList nl = null;
		CacheConf cacheConf = null;
		try {
			if (element == null)
				return;
			nl = element.getChildNodes();
			if (nl == null || nl.getLength() <= 0)
				return;
			for (int i = 0; i < nl.getLength(); i++) {
				node = nl.item(i);
				if (!(node instanceof Element))
					continue;
				ele = (Element) node;
				if (ele == null)
					continue;
				cacheConf = paserCache(ele);
				if (cacheConf != null)
					this.cacheConf.add(cacheConf);
			}
		} catch (ConfigurationContainerException e) {
			throw e;
		} catch (Exception e) {
			throw new ConfigurationContainerException(e);
		}

	}

	private final CacheConf paserCache(Node element) throws ConfigurationContainerException {
		Node node = null;
		Element ele = null;
		NodeList nl = null;
		CacheConf cacheConf = null;
		String nodeName = "";
		String tmp = "";
		try {
			if (element == null)
				return null;
			nl = element.getChildNodes();
			if (nl == null || nl.getLength() <= 0)
				return null;
			cacheConf = new CacheConfImpl();
			for (int i = 0; i < nl.getLength(); i++) {
				node = nl.item(i);
				if (!(node instanceof Element))
					continue;
				ele = (Element) node;
				if (ele == null)
					continue;
				nodeName = ele.getTagName();
				if (nodeName.equals("id")) {
					tmp = ele.getTextContent();
					cacheConf.setId(tmp);
				} else if (nodeName.equals("host")) {
					tmp = ele.getTextContent();
					cacheConf.setHost(tmp);
				} else if (nodeName.equals("port")) {
					tmp = ele.getTextContent();
					cacheConf.setPort(Integer.parseInt(tmp));
				} else if (nodeName.equals("password")) {
					tmp = ele.getTextContent();
					cacheConf.setPassword(tmp);
				} else if (nodeName.equals("connectionTimeout")) {
					tmp = ele.getTextContent();
					cacheConf.setConnectionTimeout(Integer.parseInt(tmp));
				} else if (nodeName.equals("osTimeout")) {
					tmp = ele.getTextContent();
					cacheConf.setOsTimeout(Integer.parseInt(tmp));
				} else if (nodeName.equals("ssl")) {
					tmp = ele.getTextContent();
					cacheConf.setSsl(false);
					if (tmp.equals("true"))
						cacheConf.setSsl(true);
				} else if (nodeName.equals("type")) {
					tmp = ele.getTextContent();
					cacheConf.setType(tmp);
				} else if (nodeName.equals("quantity")) {
					tmp = ele.getTextContent();
					cacheConf.setQuantity(Integer.parseInt(tmp));
				} else if (nodeName.equals("sha1")) {
					tmp = ele.getTextContent();
					cacheConf.setSha1(tmp);
				} else {

				}
			}
			return cacheConf;

		} catch (Exception e) {
			throw new ConfigurationContainerException(e);
		}
	}

	private final void paserHost(Node element) throws ConfigurationContainerException {
		Node node = null;
		Element ele = null;
		NodeList nl = null;
		Context ctx = null;
		String tmp = null;
		try {
			if (element == null)
				return;
			nl = element.getChildNodes();
			if (nl == null || nl.getLength() <= 0)
				return;
			for (int i = 0; i < nl.getLength(); i++) {
				node = nl.item(i);
				if (!(node instanceof Element))
					continue;
				ele = (Element) node;
				if (ele == null)
					continue;
				ctx = new Context();
				if (!ele.hasAttribute("name"))
					throw new ConfigurationContainerException("context element name attribute error");
				tmp = ele.getAttribute("name");
				if (Str.isNullOrEmpty(tmp))
					throw new ConfigurationContainerException("context element name attribute value not empty");
				if (this.context.containsKey(tmp))
					throw new ConfigurationContainerException("context element name attribute value exists");
				ctx.setName(tmp);
				ctx.setConfFile("context.xml");
				if (ele.hasAttribute("confFile")) {
					tmp = ele.getAttribute("confFile");
					if (tmp != null && tmp.length() > 0)
						ctx.setConfFile(tmp);
				}
				this.context.put(ctx.getName(), ctx);
			}
		} catch (ConfigurationContainerException e) {
			throw e;
		} catch (Exception e) {
			throw new ConfigurationContainerException(e);
		}

	}

	private final void paserContext() throws ConfigurationContainerException {
		File f = null;
		DocumentBuilderFactory factory = null;
		DocumentBuilder builder = null;
		Document doc = null;
		Element root = null;
		Context ctx = null;
		Iterator<Context> ctxs = null;
		String tmp = null;
		Configure configure = null;
		try {
			logger.info("start load context");
			if (this.context.isEmpty())
				return;
			ctxs = this.context.values().iterator();
			while (ctxs.hasNext()) {
				ctx = ctxs.next();
				tmp = String.format("%s%sconf%s%s%s%s", System.getProperty("user.dir"), File.separatorChar,
						File.separatorChar, ctx.getName(), File.separatorChar, ctx.getConfFile());
				f = new File(tmp);
				if (!f.exists())
					throw new ConfigurationContainerException(
							"Error : context file doesn't exist! Path:" + f.getAbsolutePath());
				factory = DocumentBuilderFactory.newInstance();
				builder = factory.newDocumentBuilder();
				doc = builder.parse(f);
				root = doc.getDocumentElement();
				if (root == null)
					throw new ConfigurationContainerException("context file content format error");
				configure = paserApplication(root);
				if (configure == null)
					continue;
				ctx.setConfigure(configure);
				this.configure.add(configure);
			}
		} catch (ConfigurationContainerException e) {
			throw e;
		} catch (Exception e) {
			throw new ConfigurationContainerException(e);
		} finally {
			logger.info("end load context");
		}
	}

	private final Configure paserApplication(Node element) throws ConfigurationContainerException {
		Node node = null;
		Element ele = null;
		NodeList nl = null;
		Configure configure = null;
		String nodeName = "";
		String tmp = "";
		try {
			if (element == null)
				return null;
			nl = element.getChildNodes();
			if (nl == null || nl.getLength() <= 0)
				return null;
			configure = new ConfigureImpl();
			for (int i = 0; i < nl.getLength(); i++) {
				node = nl.item(i);
				if (!(node instanceof Element))
					continue;
				ele = (Element) node;
				if (ele == null)
					continue;
				nodeName = ele.getTagName();
				if (nodeName.equals("id")) {
					tmp = ele.getTextContent();
					configure.setId(tmp);
				} else if (nodeName.equals("name")) {
					tmp = ele.getTextContent();
					configure.setName(tmp);
				} else if (nodeName.equals("jarFileName")) {
					tmp = ele.getTextContent();
					configure.setJarFileName(tmp);
				} else if (nodeName.equals("packageName")) {
					tmp = ele.getTextContent();
					configure.setPackageName(tmp);
				} else if (nodeName.equals("enable")) {
					tmp = ele.getTextContent();
					configure.setEnable(false);
					if (tmp.equals("true"))
						configure.setEnable(true);
				} else if (nodeName.equals("beansConfPath")) {
					tmp = ele.getTextContent();
					configure.setBeansConfPath(tmp);
				} else if (nodeName.equals("startupClassName")) {
					tmp = ele.getTextContent();
					configure.setStartupClassName(tmp);
				} else if (nodeName.equals("settings")) {
					this.paserSetting(configure.getSettings(), ele);
				} else if (nodeName.equals("mqs")) {
					this.paserApplicationMQs(configure, ele);
				} else if (nodeName.equals("dss")) {
					this.paserApplicationDss(configure, ele);
				} else {

				}
			}
			return configure;

		} catch (ConfigurationContainerException e) {
			throw e;
		} catch (Exception e) {
			throw new ConfigurationContainerException(e);
		}
	}

	private final void paserApplicationMQs(Configure appConfigure, Node element)
			throws ConfigurationContainerException {
		Node node = null;
		Element ele = null;
		NodeList nl = null;
		MQConf mqConf = null;
		try {
			if (element == null)
				return;
			nl = element.getChildNodes();
			if (nl == null || nl.getLength() <= 0)
				return;
			for (int i = 0; i < nl.getLength(); i++) {
				node = nl.item(i);
				if (!(node instanceof Element))
					continue;
				ele = (Element) node;
				if (ele == null)
					continue;
				mqConf = paserApplicationMQ(ele);
				if (mqConf != null)
					appConfigure.getMQConf().add(mqConf);
			}
		} catch (ConfigurationContainerException e) {
			throw e;
		} catch (Exception e) {
			throw new ConfigurationContainerException(e);
		}

	}

	private final MQConf paserApplicationMQ(Node element) throws ConfigurationContainerException {

		Node node = null;
		Element ele = null;
		NodeList nl = null;
		MQConf mqConf = null;
		String nodeName = "";
		String tmp = "";
		try {
			if (element == null)
				return null;
			nl = element.getChildNodes();
			if (nl == null || nl.getLength() <= 0)
				return null;
			mqConf = new MQConfImpl();
			for (int i = 0; i < nl.getLength(); i++) {
				node = nl.item(i);
				if (!(node instanceof Element))
					continue;
				ele = (Element) node;
				if (ele == null)
					continue;
				nodeName = ele.getTagName();
				if (nodeName.equals("id")) {
					tmp = ele.getTextContent();
					mqConf.setId(tmp);
				} else if (nodeName.equals("userName")) {
					tmp = ele.getTextContent();
					mqConf.setUserName(tmp);
				} else if (nodeName.equals("password")) {
					tmp = ele.getTextContent();
					mqConf.setPassword(tmp);
				} else if (nodeName.equals("brokerUrl")) {
					tmp = ele.getTextContent();
					mqConf.setBrokerUrl(tmp);
				} else if (nodeName.equals("producerClassName")) {
					tmp = ele.getTextContent();
					mqConf.setProducerClassName(tmp);
				} else if (nodeName.equals("consumerClassName")) {
					tmp = ele.getTextContent();
					mqConf.setConsumerClassName(tmp);
				} else {

				}
			}
			return mqConf;

		} catch (Exception e) {
			throw new ConfigurationContainerException(e);
		}

	}

	private final void paserApplicationDss(Configure appConfigure, Node element)
			throws ConfigurationContainerException {
		Node node = null;
		Element ele = null;
		NodeList nl = null;
		DsConf dsConf = null;
		try {
			if (element == null)
				return;
			nl = element.getChildNodes();
			if (nl == null || nl.getLength() <= 0)
				return;
			for (int i = 0; i < nl.getLength(); i++) {
				node = nl.item(i);
				if (!(node instanceof Element))
					continue;
				ele = (Element) node;
				if (ele == null)
					continue;
				dsConf = paserApplicationDs(ele);
				if (dsConf != null)
					appConfigure.getDsConf().add(dsConf);
			}
		} catch (ConfigurationContainerException e) {
			throw e;
		} catch (Exception e) {
			throw new ConfigurationContainerException(e);
		}

	}

	private final DsConf paserApplicationDs(Node element) throws ConfigurationContainerException {

		Node node = null;
		Element ele = null;
		NodeList nl = null;
		DsConf dsConf = null;
		String nodeName = "";
		String tmp = "";
		try {
			if (element == null)
				return null;
			nl = element.getChildNodes();
			if (nl == null || nl.getLength() <= 0)
				return null;
			dsConf = new DsConfImpl();
			for (int i = 0; i < nl.getLength(); i++) {
				node = nl.item(i);
				if (!(node instanceof Element))
					continue;
				ele = (Element) node;
				if (ele == null)
					continue;
				nodeName = ele.getTagName();
				if (nodeName.equals("id")) {
					tmp = ele.getTextContent();
					dsConf.setId(tmp);
				} else if (nodeName.equals("name")) {
					tmp = ele.getTextContent();
					dsConf.setName(tmp);
				} else if (nodeName.equals("className")) {
					tmp = ele.getTextContent();
					dsConf.setClassName(tmp);
				} else if (nodeName.equals("driverClassName")) {
					tmp = ele.getTextContent();
					dsConf.setDriverClassName(tmp);
				} else if (nodeName.equals("url")) {
					tmp = ele.getTextContent();
					dsConf.setUrl(tmp);
				} else if (nodeName.equals("dataBaseName")) {
					tmp = ele.getTextContent();
					dsConf.setDataBaseName(tmp);
				} else {

				}
			}
			return dsConf;

		} catch (Exception e) {
			throw new ConfigurationContainerException(e);
		}

	}

	public final Map getSettings() {
		return this.settings;
	}

	public final List<Configure> getApplicationConf() {
		return this.configure;
	}

	public final List<CacheConf> getCacheConf() {
		return this.cacheConf;
	}

	public final Map getContext() {
		return this.context;
	}
}
