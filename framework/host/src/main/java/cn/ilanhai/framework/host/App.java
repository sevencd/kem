package cn.ilanhai.framework.host;

import java.io.File;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.spi.Configurator;

import cn.ilanhai.framework.common.parameter.ParameterManager;

public class App {
	static {
		String tmp = null;
		tmp = String.format("%s%sconf%s%s", System.getProperty("user.dir"),
				File.separatorChar, File.separatorChar, "log4j.properties");
		PropertyConfigurator.configure(tmp);
	}
	private static final Logger logger = Logger.getLogger(App.class);
	private static final int WAITE_TIME = 180 * 1000;

	public static void main(String[] args) {

		Server server = null;
		// Scanner sc = null;
		try {
			ParameterManager.getInstance().init(args);
			logger.info("service starting");
			server = ServerImpl.getInstance();
			server.start();
			logger.info("service started");
			while (true) {
				Thread.sleep(WAITE_TIME);
			}
			/*
			 * sc = new Scanner(System.in); while (sc.hasNext()) { if
			 * (sc.next().equalsIgnoreCase("exit")) break;
			 */
			// server.stop();
			// server.close();
		} catch (Exception e) {
			logger.error(e.toString());
			e.printStackTrace();
		}

	}
}
