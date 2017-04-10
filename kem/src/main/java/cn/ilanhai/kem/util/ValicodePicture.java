package cn.ilanhai.kem.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.util.Random;

public class ValicodePicture {

	/**
	 * 创建图片
	 * 
	 * @param height
	 *            图片高
	 * @param width
	 *            图片宽
	 * @param lineCount
	 *            干扰线条数
	 * @param value
	 *            生成值
	 * @return
	 */
	public static final BufferedImage createPicture(int height, int width,
			int lineCount, String value) {
		int x = 0, fontHeight = 0, codeY = 0;
		int red = 0, green = 0, blue = 0;
		BufferedImage image = null;
		ImgFontByte imgFont = null;
		char c = ' ';
		Random random = null;
		Graphics2D g = null;
		x = width / (value.length() + 2);// 每个字符的宽度
		fontHeight = height - 2;// 字体的高度
		codeY = height - 4;
		// 图像buffer
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		g = image.createGraphics();
		// 生成随机数
		random = new Random();
		// 将图像填充为白色
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, width, height);
		// 创建字体
		imgFont = new ImgFontByte();
		Font font = imgFont.getFont(fontHeight);
		g.setFont(font);
		for (int i = 0; i < lineCount; i++) {
			int xs = random.nextInt(width);
			int ys = random.nextInt(height);
			int xe = xs + random.nextInt(width / 8);
			int ye = ys + random.nextInt(height / 8);
			red = random.nextInt(255);
			green = random.nextInt(255);
			blue = random.nextInt(255);
			g.setColor(new Color(red, green, blue));
			g.drawLine(xs, ys, xe, ye);
		}
		for (int i = 0; i < value.length(); i++) {
			c = value.charAt(i);
			red = random.nextInt(255);
			green = random.nextInt(255);
			blue = random.nextInt(255);
			g.setColor(new Color(red, green, blue));
			g.drawString(String.format("%s", c), (i + 1) * x, codeY);
		}
		return image;

	}

}
