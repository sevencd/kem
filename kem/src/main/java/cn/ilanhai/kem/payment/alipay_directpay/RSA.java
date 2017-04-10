package cn.ilanhai.kem.payment.alipay_directpay;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

public class RSA {

	public static final String SIGN_ALGORITHMS = "SHA1WithRSA";

	/**
	 * RSA签名
	 * 
	 * @param content
	 *            待签名数据
	 * @param privateKey
	 *            商户私钥
	 * @param input_charset
	 *            编码格式
	 * @return 签名值
	 */
	public static String sign(String content, String privateKey,
			String input_charset) {
		PKCS8EncodedKeySpec priPKCS8 = null;
		KeyFactory keyf = null;
		PrivateKey priKey = null;
		java.security.Signature signature = null;
		byte[] signed = null;
		try {
			priPKCS8 = new PKCS8EncodedKeySpec(Base64.decode(privateKey));
			keyf = KeyFactory.getInstance("RSA");
			priKey = keyf.generatePrivate(priPKCS8);
			signature = java.security.Signature.getInstance(SIGN_ALGORITHMS);
			signature.initSign(priKey);
			signature.update(content.getBytes(input_charset));
			signed = signature.sign();
			return Base64.encode(signed);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * RSA验签名检查
	 * 
	 * @param content
	 *            待签名数据
	 * @param sign
	 *            签名值
	 * @param ali_public_key
	 *            支付宝公钥
	 * @param input_charset
	 *            编码格式
	 * @return 布尔值
	 */
	public static boolean verify(String content, String sign,
			String ali_public_key, String input_charset) {
		KeyFactory keyFactory = null;
		byte[] encodedKey = null;
		PublicKey pubKey = null;
		java.security.Signature signature = null;
		boolean bverify = false;
		try {
			keyFactory = KeyFactory.getInstance("RSA");
			encodedKey = Base64.decode(ali_public_key);
			pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(
					encodedKey));
			signature = java.security.Signature.getInstance(SIGN_ALGORITHMS);
			signature.initVerify(pubKey);
			signature.update(content.getBytes(input_charset));
			bverify = signature.verify(Base64.decode(sign));
			return bverify;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 解密
	 * 
	 * @param content
	 *            密文
	 * @param private_key
	 *            商户私钥
	 * @param input_charset
	 *            编码格式
	 * @return 解密后的字符串
	 */
	public static String decrypt(String content, String private_key,
			String input_charset) throws Exception {
		PrivateKey prikey = null;
		Cipher cipher = null;
		InputStream ins = null;
		ByteArrayOutputStream writer = null;
		byte[] buf = null;
		int bufl;
		byte[] block = null;
		prikey = getPrivateKey(private_key);
		cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, prikey);
		ins = new ByteArrayInputStream(Base64.decode(content));
		writer = new ByteArrayOutputStream();
		// rsa解密的字节大小最多是128，将需要解密的内容，按128位拆开解密
		buf = new byte[128];
		while ((bufl = ins.read(buf)) != -1) {
			if (buf.length == bufl) {
				block = buf;
			} else {
				block = new byte[bufl];
				for (int i = 0; i < bufl; i++) {
					block[i] = buf[i];
				}
			}
			writer.write(cipher.doFinal(block));
		}
		return new String(writer.toByteArray(), input_charset);
	}

	/**
	 * 得到私钥
	 * 
	 * @param key
	 *            密钥字符串（经过base64编码）
	 * @throws Exception
	 */
	public static PrivateKey getPrivateKey(String key) throws Exception {
		byte[] keyBytes;
		PKCS8EncodedKeySpec keySpec = null;
		KeyFactory keyFactory = null;
		PrivateKey privateKey = null;
		keyBytes = Base64.decode(key);
		keySpec = new PKCS8EncodedKeySpec(keyBytes);
		keyFactory = KeyFactory.getInstance("RSA");
		privateKey = keyFactory.generatePrivate(keySpec);
		return privateKey;
	}
}
