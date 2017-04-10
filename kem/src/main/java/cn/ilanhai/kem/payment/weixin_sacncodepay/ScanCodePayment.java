package cn.ilanhai.kem.payment.weixin_sacncodepay;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;

import cn.ilanhai.framework.common.exception.AppException;
import cn.ilanhai.framework.uitl.Str;
import cn.ilanhai.kem.common.CodeTable;
import cn.ilanhai.kem.payment.AbstractPayment;
import cn.ilanhai.kem.payment.PaymentConfig;
import cn.ilanhai.kem.payment.PaymentInfo;
import cn.ilanhai.kem.payment.protocol.weixin_scancodepay.AsynNodifyResult;
import cn.ilanhai.kem.payment.protocol.weixin_scancodepay.ResultState;
import cn.ilanhai.kem.payment.protocol.weixin_scancodepay.ResultState.ReturnCode;
import cn.ilanhai.kem.payment.protocol.weixin_scancodepay.UnifiedOrder;
import cn.ilanhai.kem.payment.protocol.weixin_scancodepay.UnifiedOrderResult;
import cn.ilanhai.kem.util.HttpHelper;

public class ScanCodePayment extends AbstractPayment {
	/**
	 * 统一定单API
	 */
	private final String UNIFIED_ORDER_API = "https://api.mch.weixin.qq.com/pay/unifiedorder";

	/**
	 * 编码
	 */
	private final String ENCODING = "UTF-8";
	/**
	 * 二维码宽
	 */
	private final int QRCODE_WIDTH = 300;
	/**
	 * 二维码高
	 */
	private final int QRCODE_HEIGHT = 300;
	/**
	 * 二维码格式
	 */
	private final String QRCODE_FORMAT = "png";

	@Override
	public String pay(PaymentConfig config, PaymentInfo info)
			throws AppException {
		CodeTable ct;
		String tmp = null;
		ScanCodePaymentConfig cfg = null;
		UnifiedOrder entity = null;
		ct = CodeTable.BL_PAYMENT_ERROR;
		try {
			if (!(config instanceof ScanCodePaymentConfig))
				throw new AppException(ct.getValue(), "微信参数配置错误");
			cfg = (ScanCodePaymentConfig) config;
			if (cfg == null)
				throw new AppException(ct.getValue(), "微信参数配置错误");
			tmp = cfg.getAppKey();
			if (Str.isNullOrEmpty(tmp))
				throw new AppException(ct.getValue(), "微信参数配置错误");
			entity = new UnifiedOrder(tmp);
			tmp = cfg.getAppId();
			if (Str.isNullOrEmpty(tmp))
				throw new AppException(ct.getValue(), "微信参数配置错误");
			entity.setAppId(tmp);
			tmp = cfg.getAppMchId();
			if (Str.isNullOrEmpty(tmp))
				throw new AppException(ct.getValue(), "微信参数配置错误");
			entity.setMchId(tmp);
			tmp = cfg.getNotifyUrl();
			if (Str.isNullOrEmpty(tmp))
				throw new AppException(ct.getValue(), "微信参数配置错误");
			entity.setNotifyUrl(tmp);
			tmp = cfg.getKey();
			if (Str.isNullOrEmpty(tmp))
				throw new AppException(ct.getValue(), "微信参数配置错误");
			entity.setKey(tmp);
			if (info == null)
				throw new AppException(ct.getValue(), "定单信息错误");
			tmp = info.getOrderId();
			if (Str.isNullOrEmpty(tmp))
				throw new AppException(ct.getValue(), "定单信息错误");
			entity.setOutTradeNo(tmp);
			tmp = info.getName();
			if (Str.isNullOrEmpty(tmp))
				throw new AppException(ct.getValue(), "定单信息错误");
			entity.setBody(tmp);
			tmp = info.getTotalFee();
			if (Str.isNullOrEmpty(tmp))
				throw new AppException(ct.getValue(), "定单信息错误");
			double tf = Double.valueOf(tmp);
			tmp = String.format("%s", (int) (tf * 100));
			entity.setTotalFee(tmp);
			tmp = info.getDescription();
			if (!Str.isNullOrEmpty(tmp))
				entity.setDetail(tmp);
			return this.createUnifiedOrder(entity);
		} catch (IOException e) {
			throw new AppException(ct.getValue(), e.getMessage(), e);
		}
	}

	/**
	 * 创建一个统一订单
	 * 
	 * @param unifiedOrderEntity
	 * @return
	 * @throws AppException
	 * @throws IOException
	 */
	private String createUnifiedOrder(UnifiedOrder unifiedOrderEntity)
			throws AppException, IOException {
		String tmp = null;
		Map<String, String> rv = null;
		CodeTable ct;
		ct = CodeTable.BL_PAYMENT_ERROR;
		tmp = unifiedOrderEntity.toXmlString();
		System.out.println(tmp);
		tmp = HttpHelper.sendPostUrl(UNIFIED_ORDER_API, tmp, ENCODING);
		System.out.println(tmp);
		if (tmp == null || tmp.length() <= 0)
			throw new AppException(ct.getValue(), "请求生成定单失败");
		return this.response(tmp);
	}

	/**
	 * 处理成生统一定单结果
	 * 
	 * @param xmlString
	 * @return
	 * @throws AppException
	 * @throws IOException
	 */
	private String response(String xmlString) throws AppException, IOException {
		Map<String, String> result = null;
		ResultState uos = null;
		UnifiedOrderResult uore = null;
		ResultState.ReturnCode rc;
		UnifiedOrderResult.ResultCode resc;
		CodeTable ct = CodeTable.BL_PAYMENT_ERROR;
		String tmp = null;
		result = this.paserXml(xmlString);
		if (result == null || result.size() <= 0)
			throw new AppException(ct.getValue(), "");
		uos = new ResultState();
		tmp = result.get("return_code");
		tmp = tmp.toUpperCase();
		rc = tmp.equals(ReturnCode.FAIL.toString()) ? ReturnCode.FAIL
				: ReturnCode.SUCCESS;
		uos.setReturnCode(rc);
		tmp = result.get("return_msg");
		uos.setReturnMsg(tmp);
		if (uos.getReturnCode() == ReturnCode.FAIL)
			throw new AppException(ct.getValue(), uos.getReturnMsg());
		uore = new UnifiedOrderResult();
		tmp = result.get("appid");
		uore.setAppId(tmp);
		tmp = result.get("mch_id");
		uore.setMchId(tmp);
		tmp = result.get("device_info");
		uore.setDeviceInfo(tmp);
		tmp = result.get("nonce_str");
		// uore.
		tmp = result.get("sign");
		uore.setSign(tmp);
		tmp = result.get("result_code");
		tmp = tmp.toUpperCase();
		resc = tmp.equals(UnifiedOrderResult.ResultCode.FAIL.toString()) ? UnifiedOrderResult.ResultCode.FAIL
				: UnifiedOrderResult.ResultCode.SUCCESS;
		uore.setResultCode(resc);
		if (uore.getResultCode() == UnifiedOrderResult.ResultCode.FAIL) {
			tmp = result.get("err_code");
			uore.setErrCode(tmp);
			tmp = result.get("err_code_des");
			uore.setErrCodeDesc(tmp);
			tmp = String.format("错误码:%s 原因:%s", uore.getResultCode(),
					uore.getErrCodeDesc());
			throw new AppException(ct.getValue(), tmp);
		}
		tmp = result.get("trade_type");
		tmp = result.get("prepay_id");
		uore.setPrepayId(tmp);
		tmp = result.get("code_url");
		uore.setCodeUrl(tmp);
		tmp = this.createPicture(tmp);
		if (Str.isNullOrEmpty(tmp))
			throw new AppException(ct.getValue(), "");
		return tmp;
	}

	private Map<String, String> paserXml(String xmlString) throws IOException {
		DocumentBuilderFactory factory = null;
		DocumentBuilder builder = null;
		InputStream is = null;
		Document document = null;
		Map<String, String> map = null;
		NodeList nodes = null;
		Node node = null;
		try {
			factory = DocumentBuilderFactory.newInstance();
			builder = factory.newDocumentBuilder();
			if (xmlString == null || xmlString.length() <= 0)
				return null;
			map = new HashMap<String, String>();
			is = new ByteArrayInputStream(xmlString.getBytes());
			document = builder.parse(is);
			nodes = document.getFirstChild().getChildNodes();
			for (int i = 0; i < nodes.getLength(); i++) {
				node = nodes.item(i);
				if (!(node instanceof Element))
					continue;
				map.put(node.getNodeName(), node.getTextContent());
			}
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (is != null)
				is.close();
		}

	}

	/**
	 * 生成二维码图片
	 * 
	 * @param url
	 * @return
	 * @throws IOException
	 */
	private String createPicture(String url) throws IOException {
		HashMap<EncodeHintType, String> hints = null;
		BitMatrix bitMatrix = null;
		ByteArrayOutputStream os = null;
		String base64String = null;
		BufferedImage image = null;
		try {
			if (Str.isNullOrEmpty(url))
				return null;

			hints = new HashMap<EncodeHintType, String>();
			hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
			bitMatrix = new MultiFormatWriter().encode(url,
					BarcodeFormat.QR_CODE, QRCODE_WIDTH, QRCODE_HEIGHT, hints);
			os = new ByteArrayOutputStream();
			image = toBufferedImage(bitMatrix);
			ImageIO.write(image, QRCODE_FORMAT, os);
			base64String = org.apache.commons.codec.binary.Base64
					.encodeBase64String(os.toByteArray());
			return String.format("data:image/png;base64,%s", base64String);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (os != null)
				os.close();
		}
	}

	/**
	 * 数据转换
	 * 
	 * @param matrix
	 * @return
	 */
	private BufferedImage toBufferedImage(BitMatrix matrix) {
		int BLACK = 0xFF000000;
		int WHITE = 0xFFFFFFFF;
		int width = matrix.getWidth();
		int height = matrix.getHeight();
		BufferedImage image = null;
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
			}
		}
		return image;
	}

	@Override
	public String nodify(Map<String, String> params) {
		ResultState rs = null;
		AsynNodifyResult anr = null;
		ResultState.ReturnCode rc;
		UnifiedOrderResult.ResultCode resc;
		String tmp = null;
		try {
			if (params == null || params.size() <= 0)
				return null;
			if (!params.containsKey("xml"))
				return null;
			tmp = params.get("xml");
			params = this.paserXml(tmp);
			if (params == null || params.size() <= 0)
				return null;
			tmp = params.get("return_code");
			if (Str.isNullOrEmpty(tmp))
				return null;
			rc = tmp.equalsIgnoreCase(ReturnCode.SUCCESS.toString()) ? ReturnCode.SUCCESS
					: ReturnCode.FAIL;
			if (rc != ReturnCode.SUCCESS)
				return null;
			rs = new ResultState();
			rs.setReturnCode(rc);
			tmp = params.get("return_msg");
			if (!Str.isNullOrEmpty(tmp))
				rs.setReturnMsg(tmp);
			tmp = params.get("result_code");
			resc = tmp.equalsIgnoreCase(UnifiedOrderResult.ResultCode.SUCCESS
					.toString()) ? UnifiedOrderResult.ResultCode.SUCCESS
					: UnifiedOrderResult.ResultCode.FAIL;
			if (resc != UnifiedOrderResult.ResultCode.SUCCESS)
				return null;
			anr = new AsynNodifyResult();
			// anr.setResultCode(resc);
			tmp = params.get("out_trade_no");
			anr.setOutTradeNo(tmp);
			return tmp;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
