package cn.ilanhai.kem.bl.userImg;

import cn.ilanhai.framework.app.RequestContext;
import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.exception.BlAppException;
import cn.ilanhai.framework.common.exception.DaoAppException;
public interface UserImg {
	/**
	 * 查询图片
	 * 
	 * @param context
	 *            全局上下文
	 * @return 查询返回对象
	 * @throws BlAppException
	 * @throws DaoAppException
	 * Version V:1.1.1
	 */
	public Entity search(RequestContext context) throws BlAppException, DaoAppException;

	/**
	 * 查询我的素材
	 * 
	 * @param context
	 *            全局上下文
	 * @return 查询返回对象
	 * @throws BlAppException
	 * @throws DaoAppException
	 * Version V:1.1.1
	 */
	public Entity searchmyselfimg(RequestContext context) throws BlAppException, DaoAppException;

	/**
	 * 删除图片
	 * 
	 * @param context
	 * @throws BlAppException
	 * @throws DaoAppException
	 * Version V:1.1.1
	 */
	public void delete(RequestContext context) throws BlAppException, DaoAppException;
	
	/**
	 * 上传图片
	 * 
	 * @param context
	 * @throws BlAppException
	 * @throws DaoAppException
	 * Version V:1.1.1
	 */
	public Entity upload(RequestContext context) throws BlAppException, DaoAppException;
	
	
}
