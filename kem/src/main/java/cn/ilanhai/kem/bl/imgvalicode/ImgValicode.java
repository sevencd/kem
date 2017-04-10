package cn.ilanhai.kem.bl.imgvalicode;

import cn.ilanhai.framework.app.RequestContext;
import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.exception.BlAppException;
import cn.ilanhai.framework.common.exception.DaoAppException;

/**
 * 验证码业务逻辑接口
 * 
 * @author he
 *
 */
public interface ImgValicode {
	/**
	 * 验证长度
	 */
	int VAILCODE_COUNT = 4;
	/**
	 * 验证过期时间（单位：分钟）
	 */
	int VAILCODE_EXPIRED = 3;

	/**
	 * 生成验证码数据
	 * 
	 * @param context
	 * @return
	 * @throws BlAppException
	 */
	Entity generate(RequestContext context) throws BlAppException,
			DaoAppException;

	/**
	 * 校验验证码
	 * 
	 * @param context
	 * @return
	 * @throws BlAppException
	 */
	boolean vali(RequestContext context) throws BlAppException, DaoAppException;

	/**
	 * 验证码状态
	 * 
	 * @param context
	 * @return
	 * @throws BlAppException
	 */
	Entity status(RequestContext context) throws BlAppException,
			DaoAppException;

}
