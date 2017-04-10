package cn.ilanhai.kem.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.Logger;

public final class SmsHelper {
	private static final Logger logger = Logger.getLogger(SmsHelper.class);

	/**
	 * 
	 * @param phoneNo
	 * @param content
	 * @throws UnsupportedEncodingException 
	 */
	public static final void sendSms(String phoneNo, String content, String url) throws UnsupportedEncodingException {
		// String url =
		// //
		// "http://154584019.kid.iwanqi.cn/action/smssend.html?token=2f8242f0858bacde9c5defd0a0008e5a&ip=192.168.1.154&m=kshop&time=";
		// String url = this.getValue(context, "hostUrl");
		// "http://123558121.kid.ilhjy.cn/action/smssend.html?token=2f8242f0858bacde9c5defd0a0008e5a&ip=192.168.1.154&m=kshop&time=";
		// to=13880316877&content=短信内容【中国测试】&
		url = "http://" + url
				+ "/action/smssend.html?token=2f8242f0858bacde9c5defd0a0008e5a&ip=192.168.1.154&m=kshop&time=&to="
				+ phoneNo + "&content=" + URLEncoder.encode(content,"utf-8");
		//
		// String result = "";
		// BufferedReader in = null;
		// try {
		// URL realUrl = new URL(url);
		// // 打开和URL之间的连接
		// URLConnection connection = realUrl.openConnection();
		// // 设置通用的请求属性
		// connection.setRequestProperty("accept", "*/*");
		// connection.setRequestProperty("connection", "Keep-Alive");
		// connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible;
		// MSIE 6.0; Windows NT 5.1;SV1)");
		// // 建立实际的连接
		// connection.connect();
		// // 获取所有响应头字段
		// Map<String, List<String>> map = connection.getHeaderFields();
		// // 遍历所有的响应头字段
		// for (String key : map.keySet()) {
		// System.out.println(key + "--->" + map.get(key));
		// }
		// // 定义 BufferedReader输入流来读取URL的响应
		// in = new BufferedReader(new
		// InputStreamReader(connection.getInputStream()));
		// String line;
		// while ((line = in.readLine()) != null) {
		// result += line;
		// }
		// } catch (Exception e) {
		// System.out.println("发送GET请求出现异常！" + e);
		// e.printStackTrace();
		// }
		logger.info("发送GET请求:" + url);
		HttpGet httpGet = new HttpGet(url);
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse httpResponse = null;
		// System.out.println("请求结果为"+httpResponse.toString());
		// Thread.sleep(3000);
		HttpEntity httpEntity = null;
		try {
			httpResponse = httpClient.execute(httpGet);
		} catch (IOException e) {
			logger.info("发送GET请求出现异常:" + e);
			e.printStackTrace();
		}
		httpEntity = httpResponse.getEntity();

		BufferedReader reader = null;
		String line = null;
		try {
			reader = new BufferedReader(new InputStreamReader(httpEntity.getContent()));
			while ((line = reader.readLine()) != null) {
				logger.info(line);
			}
		} catch (UnsupportedOperationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (reader != null) {
					reader.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
}
