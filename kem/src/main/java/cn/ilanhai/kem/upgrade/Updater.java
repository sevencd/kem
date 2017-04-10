package cn.ilanhai.kem.upgrade;

import cn.ilanhai.framework.app.Application;
import cn.ilanhai.framework.common.exception.AppException;

/**
 * 升级接口
 * @author Nature
 *
 */
public abstract class Updater {
	public abstract boolean init(Application app) throws AppException;

	public abstract boolean update() throws AppException;
}
