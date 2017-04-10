package cn.ilanhai.kem.util;

import java.util.Random;

/**
 * 生成验证码数据助手
 * 
 * @author he
 *
 */
public final class ValicodeHepler {
	private final static char[] codeSequence = { 'A', 'B', 'C', 'D', 'E', 'F',
			'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T',
			'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9' };
	private final static char[] codeSequenceOnlyNum = { '0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9' };

	/**
	 * 生成指定长度字符串
	 * 包含字母及数字
	 * @param count
	 * @return
	 */
	public final static String generate(int count) {
		StringBuffer code = null;
		Random random = null;
		char c = ' ';
		code = new StringBuffer();
		random = new Random();
		for (int i = 0; i < count; i++) {
			c = codeSequence[random.nextInt(codeSequence.length)];
			code.append(c);
		}
		return code.toString();
	}
	/**
	 * 
	 * @param count
	 * @return
	 */
	public final static String generateOnlyNum(int count) {
		StringBuffer code = null;
		Random random = null;
		char c = ' ';
		code = new StringBuffer();
		random = new Random();
		for (int i = 0; i < count; i++) {
			c = codeSequenceOnlyNum[random.nextInt(codeSequenceOnlyNum.length)];
			code.append(c);
		}
		return code.toString();
	}
}
