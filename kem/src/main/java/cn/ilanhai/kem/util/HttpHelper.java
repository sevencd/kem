package cn.ilanhai.kem.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;
import org.omg.CORBA.DataInputStream;

/**
 * @author he
 * 
 */
public class HttpHelper {

	private static Logger logger = Logger.getLogger(HttpHelper.class);

	/**
	 * 使用Get方式获取数据
	 * 
	 * @param url
	 *            URL包括参数，http://HOST/XX?XX=XX&XXX=XXX
	 * @param charset
	 * @return
	 */
	public static String sendGet(String url, String charset) {
		String result = "";
		BufferedReader in = null;
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 建立实际的连接
			connection.connect();
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(
					connection.getInputStream(), charset));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送GET请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}
	/**
	 * 使用put方式更新数据
	 * 
	 * @param url
	 * @param param
	 * @param charset
	 * @author csz
	 * @return
	 */
	public static String sendPut(String url, String param,String charset) {
		OutputStream os = null;
		BufferedReader in = null;
		StringBuilder sb = new StringBuilder();
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			HttpURLConnection  conn = (HttpURLConnection )realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 发送PUT请求必须设置如下三行
			conn.setRequestMethod("PUT");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			os = conn.getOutputStream();     
			// 发送请求参数
            os.write(param.getBytes("utf-8")); 
        	// flush输出流的缓冲
            os.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), charset));
			String line;
			while ((line = in.readLine()) != null) {
				sb.append(line);
			}
		} catch (Exception e) {
			System.out.println("发送 PUT 请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (os != null) {
					os.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return sb.toString();
	}

	private static void outputParams(Map<String, String> params) {
		if (params == null || params.size() <= 0)
			logger.info("提交参数为空");
		logger.info("提交参数:");
		Iterator<String> iterator = params.keySet().iterator();
		while (iterator.hasNext()) {
			String key = iterator.next();
			logger.info(String.format("%s=%s", key, params.get(key)));
		}

	}

	/**
	 * POST请求，字符串形式数据
	 * 
	 * @param url
	 *            请求地址
	 * @param param
	 *            请求数据
	 * @param charset
	 *            编码方式
	 */
	public static String sendPostUrl(String url, String param, String charset) {

		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 发送POST请求必须设置如下三行
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), charset));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送 POST 请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * POST请求，Map形式数据
	 * 
	 * @param url
	 *            请求地址
	 * @param param
	 *            请求数据
	 * @param charset
	 *            编码方式
	 */
	public static String sendPost(String url, Map<String, String> param,
			String charset) {
		outputParams(param);
		StringBuffer buffer = new StringBuffer();
		if (param != null && !param.isEmpty()) {
			for (Map.Entry<String, String> entry : param.entrySet()) {
				buffer.append(entry.getKey()).append("=")
						.append(entry.getValue()).append("&");

			}
		}
		buffer.deleteCharAt(buffer.length() - 1);

		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			logger.info("url:"+url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(buffer);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), charset));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送 POST 请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 上传文件
	 * 
	 * @param strUrl
	 * @param filePath
	 * @return
	 * @throws Exception
	 */
	public static String postFile(String strUrl, String filePath)
			throws Exception {
		String boundary = null;
		String enter = null;
		File f = null;
		FileInputStream fis = null;
		URL url = null;
		HttpURLConnection conn = null;
		DataOutputStream dos = null;
		InputStream inputStream = null;
		byte[] buf = null;
		StringBuilder sb = null;
		try {
			if (strUrl == null || strUrl.length() <= 0 || filePath == null
					|| strUrl.length() <= 0)
				throw new Exception("参数错误");
			boundary = String.format("----WebKitFormBoundary%s",
					System.currentTimeMillis());
			enter = "\r\n";
			f = new File(filePath);
			if (!f.isFile())
				throw new Exception("文件不存在");
			fis = new FileInputStream(f);
			url = new URL(strUrl);
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setRequestMethod("POST");
			conn.setUseCaches(false);
			conn.setInstanceFollowRedirects(true);
			conn.setRequestProperty("Content-Type",
					"multipart/form-data;boundary=" + boundary);
			conn.connect();
			dos = new DataOutputStream(conn.getOutputStream());
			sb = new StringBuilder();
			sb.append(enter);
			sb.append(enter);
			sb.append("--");
			sb.append(boundary);
			sb.append(enter);

			sb.append("Content-Disposition:form-data; name=\"f\"; filename=\"");
			sb.append(f.getName());
			sb.append("\"");
			sb.append(enter);
			sb.append("Content-Type:application/octet-stream");
			sb.append(enter);
			sb.append(enter);

			dos.writeBytes(sb.toString());
			buf = new byte[fis.available()];
			fis.read(buf);
			dos.write(buf);
			sb.delete(0, sb.length());

			sb.append(enter);
			sb.append("--");
			sb.append(boundary);
			sb.append("--");

			dos.writeBytes(sb.toString());
			dos.flush();
			if (conn.getResponseCode() != 200)
				throw new Exception("http响应错误");
			inputStream = conn.getInputStream();
			buf = new byte[inputStream.available()];
			inputStream.read(buf);
			return new String(buf);

		} catch (Exception e) {
			throw e;
		} finally {
			if (dos != null)
				dos.close();
			if (fis != null)
				fis.close();
			if (inputStream != null)
				inputStream.close();
			if (conn != null)
				conn.disconnect();
		}
	}
}