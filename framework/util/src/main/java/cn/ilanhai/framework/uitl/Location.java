package cn.ilanhai.framework.uitl;

import java.net.*;

/**
 * 服务定位助手
 * 
 * @author he
 *
 */
public final class Location {

	/**
	 * 服务定们格式是否正确
	 * 
	 * @param location
	 * @return
	 */
	public static boolean isLocation(URI location) {
		if (location == null)
			return false;
		return true;
	}
}
