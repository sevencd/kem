package cn.ilanhai.kem.bl.rights;

import cn.ilanhai.framework.app.RequestContext;
import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.exception.BlAppException;
import cn.ilanhai.framework.common.exception.DaoAppException;

/**
 * 去版权接口
 * 
 * @author hy
 *
 */
public interface Rights {
	/**
	 * 查询去版权次数
	 * 
	 * @param context
	 * @return
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	Entity searchrightstimes(RequestContext context) throws BlAppException, DaoAppException;
}
