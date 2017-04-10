package cn.ilanhai.kem.event;

/**
 * 消息队列配置
 * @author Nature
 *
 */
public class MQConfig {

	private static String brokeUrl;//队列地址
	private static String username;//用户名
	private static String password;//密码
	public static String getBrokeUrl() {
		return brokeUrl;
	}
	public static void setBrokeUrl(String brokeUrl) {
		MQConfig.brokeUrl = brokeUrl;
	}
	public static String getUsername() {
		return username;
	}
	public static void setUsername(String username) {
		MQConfig.username = username;
	}
	public static String getPassword() {
		return password;
	}
	public static void setPassword(String password) {
		MQConfig.password = password;
	}
	
	
}
