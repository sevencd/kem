package cn.ilanhai.httpserver.servlet;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.ilanhai.framework.uitl.FastJson;
import cn.ilanhai.framework.uitl.Str;
import cn.ilanhai.httpserver.consts.BaseResponse;
import cn.ilanhai.httpserver.consts.ResponseCode;
import cn.ilanhai.httpserver.modules.requestoverlap.DefaultRequestOverlap;
import cn.ilanhai.httpserver.modules.resource.Resource;
import cn.ilanhai.httpserver.modules.resource.ResourceEntry;
import cn.ilanhai.httpserver.modules.resource.ResourceFactory;
import cn.ilanhai.httpserver.util.Configuration;
import cn.ilanhai.httpserver.util.ConfigurationImpl;
import cn.ilanhai.httpserver.util.ImgMD5;
import cn.ilanhai.httpserver.util.RequestHelp;
import cn.ilanhai.httpserver.util.ServletHelper;

/**
 * 用户图片上传
 * 
 * @author hy
 *
 */
public class UserImgUploadServlet extends HttpServlet {
	private static final long serialVersionUID = -2278759440118013077L;
	private static final SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd");
	private Logger logger = Logger.getLogger(UserImgUploadServlet.class);

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		logger.info("进入图片上传servlet");
		Configuration configuration = ConfigurationImpl.getInstance();
		String serviceName = (String) configuration.getSettings().get("serviceName");
		String savePath = (String) configuration.getSettings().get("savePath");
		String imgFile = (String) configuration.getSettings().get("imgFile");
		String inputName = (String) configuration.getSettings().get("inputName");
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload fileUpload = new ServletFileUpload(factory);
		fileUpload.setHeaderEncoding("utf-8");
		// 设置上传文件大小的上限，-1表示无上限
		fileUpload.setSizeMax(-1);
		List<FileItem> items = new ArrayList<FileItem>();
		// 上传文件，解析表单中包含的文件字段和普通字段
		String json = "";
		List<String> imgPaths = new ArrayList<String>();
		List<String> imgNames = new ArrayList<String>();
		List<String> abstratPaths = new ArrayList<String>();
		List<String> imgMd5s = new ArrayList<String>();
		int imgSize = 0;
		Map<String, Object> pathMap = new HashMap<String, Object>();
		pathMap.put("imgPath", imgPaths);
		pathMap.put("imgName", imgNames);
		pathMap.put("absolutePath", abstratPaths);
		pathMap.put("imgMd5", imgMd5s);
		String materialId = "";
		List<FileItem> fileItems = new ArrayList<FileItem>();
		try {
			items = fileUpload.parseRequest(req);
			// 遍历字段进行处理
			Iterator<FileItem> iterator = items.iterator();
			while (iterator.hasNext()) {
				FileItem fileItem = iterator.next();
				if (fileItem.isFormField()) {
					if (!Str.isNullOrEmpty(fileItem.getString()) && "materialId".equals(fileItem.getFieldName())) {
						materialId = fileItem.getString();
					}
				} else {
					imgSize++;
				}
				fileItems.add(fileItem);
			}
			// 验证长度
			if (checkImgLength(req, resp, imgSize)) {
				return;
			}
			iterator = fileItems.iterator();
			while (iterator.hasNext()) {
				String dataURL = File.separator + simpleDate.format(new Date(System.currentTimeMillis()));
				FileItem fileItem = iterator.next();
				if (!fileItem.isFormField()) {
					if (inputName.equals(fileItem.getFieldName())) {
						if (checkFileIsImg(req, resp, fileItem)) {
							return;
						}
						if (checkImgSize(req, resp, fileItem)) {
							return;
						}
						byte[] b = new byte[(int) fileItem.getSize()];
						fileItem.getInputStream().read(b);
						// 判断是否重复
						String imgMd5 = ImgMD5.MD5(b);
						logger.info("验证图片是否重复");
						Map<String, Object> imgMd5Map = new HashMap<String, Object>();
						imgMd5Map.put("imgMd5Search", imgMd5);
						imgMd5Map.put("startCount", 0);
						imgMd5Map.put("pageSize", 1);
						imgMd5Map.put("materialId", materialId);
						json = RequestHelp.serviceJson(req, imgMd5Map);
						JSONObject jsonObject = JSON.parseObject(json);
						if (!jsonObject.get("code").equals(0)) {
							// 响应信息
							sendResponse(req, resp, json);
							return;
						}
						JSONArray JArray = jsonObject.getJSONObject("data").getJSONArray("list");

						String imgPath = "";
						String abstratPath = "";
						String pathHead = serviceName;
						if (JArray.size() > 0) {
							JSONObject jobject = JArray.getJSONObject(0);
							imgPath = jobject.getString("imgPath");
							abstratPath = imgPath;
							imgPath = imgPath.replaceAll(pathHead, "");
							logger.info("文件获取成功：" + imgPath);
							abstratPaths.add(abstratPath);
						} else {
							Resource resource = ResourceFactory.getResource();
							// 保存路径
							String nowSavePath = savePath + File.separator + imgFile + dataURL;
							// 访问路径
							logger.info("存储路径：" + nowSavePath);
							ResourceEntry entry = resource.save(nowSavePath, fileItem.getName(),
									fileItem.getInputStream());
							imgPath = dataURL + File.separator + entry.getNewName();
							logger.info("文件存储成功：" + imgPath);
							abstratPath = pathHead + imgPath;
							String imgName = fileItem.getName();
							imgPaths.add(imgPath);
							imgNames.add(imgName);
							abstratPaths.add(abstratPath);
							imgMd5s.add(imgMd5);
						}
					}
				} else {
					if (!Str.isNullOrEmpty(fileItem.getString())) {
						pathMap.put(fileItem.getFieldName(), fileItem.getString());
					}
				}
			}
			// 后台请求
			json = RequestHelp.serviceJson(req, pathMap);
			// 响应信息
			sendResponse(req, resp, json);
		} catch (FileUploadException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 返回响应信息
	 * 
	 * @param req
	 * @param resp
	 * @param json
	 * @throws IOException
	 */
	protected void sendResponse(HttpServletRequest req, HttpServletResponse resp, String json)
			throws ServletException, IOException {
		String sessionIdValue = JSON.parseObject(json).getString(ServletHelper.KEY_SESSION_ID_X);
		this.setHeader(req, resp, sessionIdValue);
		write(json, req, resp);
	}

	/**
	 * 验证图片大小
	 * 
	 * @param resp
	 * @param fileItem
	 * @throws IOException
	 */
	private boolean checkImgSize(HttpServletRequest req, HttpServletResponse resp, FileItem fileItem)
			throws ServletException, IOException {
		BaseResponse<String> response;
		if (RequestHelp.checkSize(fileItem.getSize())) { // 文件不是图片
			response = new BaseResponse<String>();
			response.setResponseInfo(ResponseCode.REQUEST_DATA_ERROR);
			response.setDesc("图片太大了,不能超过:" + RequestHelp.sizeMB + "MB哦");
			write(FastJson.bean2Json(response), req, resp);
			return true;
		}
		return false;
	}

	private boolean checkImgLength(HttpServletRequest req, HttpServletResponse resp, int size)
			throws ServletException, IOException {
		BaseResponse<String> response;
		if (size > RequestHelp.sizeImg) { // 文件不是图片
			response = new BaseResponse<String>();
			response.setResponseInfo(ResponseCode.REQUEST_DATA_ERROR);
			response.setDesc("上传素材过多,一次最多上传" + RequestHelp.sizeImg + "份哦");
			write(FastJson.bean2Json(response), req, resp);
			return true;
		}
		return false;
	}

	/**
	 * 验证是否为图片
	 * 
	 * @param resp
	 * @param fileItem
	 * @throws IOException
	 */
	private boolean checkFileIsImg(HttpServletRequest req, HttpServletResponse resp, FileItem fileItem)
			throws ServletException, IOException {
		BaseResponse<String> response;
		BufferedImage bi = ImageIO.read(fileItem.getInputStream());
		if (bi == null) { // 文件不是图片
			response = new BaseResponse<String>();
			response.setResponseInfo(ResponseCode.REQUEST_DATA_ERROR);
			response.setDesc("只能上传图片");
			write(FastJson.bean2Json(response), req, resp);
			return true;
		}
		return false;
	}

	private void write(String result, HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		DefaultRequestOverlap.getInstance().write(req, resp, result);
		resp.getWriter().write(result);
	}

	/**
	 * 设置头信息
	 * 
	 * @param request
	 * @param response
	 * @param value
	 */
	protected void setHeader(HttpServletRequest request, HttpServletResponse response, String value) {
		Cookie cookie = null;
		String contentType = request.getHeader(ServletHelper.KEY_CONTENT_TYPE);
		if (contentType != null && contentType.length() > 0)
			response.addHeader(ServletHelper.KEY_CONTENT_TYPE, contentType);
		response.addHeader(ServletHelper.KEY_SESSION_ID, value);
		cookie = new Cookie(ServletHelper.KEY_SESSION_ID, value);
		response.addCookie(cookie);
	}
}
