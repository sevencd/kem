package cn.ilanhai.kem.util;

import cn.ilanhai.framework.common.exception.BlAppException;
import cn.ilanhai.kem.common.CodeTable;

public class ImgHelper {
	/**
	 * 获取图片格式类型
	 * 
	 * @param path 路径
	 * @return
	 * @throws BlAppException
	 */
	public static String getImgType(String path) throws BlAppException {
		String[] paths = path.split("\\.");// 表示用.去切割字符串
		if (paths.length <= 1) {
			throw new BlAppException(CodeTable.BL_IMG_PATH_ERROR.getValue(), CodeTable.BL_IMG_PATH_ERROR.getDesc());
		}
		return paths[paths.length - 1];// 用.连接最后一个字符串
	}
}
