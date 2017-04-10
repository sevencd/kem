package cn.ilanhai.kem.bl.material;

import cn.ilanhai.framework.app.RequestContext;
import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.exception.BlAppException;
import cn.ilanhai.framework.common.exception.DaoAppException;

public interface Material {
	/**
	 * 后台添加素材管理类型
	 * 
	 * @param context
	 *            全局上下文
	 * @return 查询返回对象
	 * @throws BlAppException
	 * @throws DaoAppException
	 *             Version V:1.1.1
	 */
	public Entity addMaterialType(RequestContext context) throws BlAppException, DaoAppException;

	/**
	 * 后台管理员 管理素材类型
	 * 
	 * @param context
	 * @throws BlAppException
	 * @throws DaoAppException
	 *             Version V:1.1.1
	 */
	public void shelfMaterialType(RequestContext context) throws BlAppException, DaoAppException;

	/**
	 * 后台用户查询素材管理
	 * 
	 * @param context
	 * @throws BlAppException
	 * @throws DaoAppException
	 *             Version V:1.1.1
	 */
	public Entity searchMaterialType(RequestContext context) throws BlAppException, DaoAppException;

	/**
	 * 上传svg
	 * 
	 * @param context
	 * @return
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	public Entity uploadsvg(RequestContext context) throws BlAppException, DaoAppException;

	/**
	 * 保存svg
	 * 
	 * @param context
	 * @return
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	public void savesvg(RequestContext context) throws BlAppException, DaoAppException;

	/**
	 * 查询素材
	 * 
	 * @param context
	 * @return
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	public Entity search(RequestContext context) throws BlAppException, DaoAppException;

	/**
	 * 新增关键字
	 * 
	 * @param context
	 * @return
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	public boolean setmaterialskeyword(RequestContext context) throws BlAppException, DaoAppException;

	/**
	 * 修改素材关键字
	 * 
	 * @param context
	 * @return
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	public boolean setkeyword(RequestContext context) throws BlAppException, DaoAppException;

	/**
	 * 设置备注
	 * 
	 * @param context
	 * @return
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	public boolean setremark(RequestContext context) throws BlAppException, DaoAppException;

	/**
	 * 设置分类
	 * 
	 * @param context
	 * @return
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	public boolean setmaterialtype(RequestContext context) throws BlAppException, DaoAppException;

	/**
	 * 删除 素材
	 * 
	 * @param context
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	public void deletematerial(RequestContext context) throws BlAppException, DaoAppException;

	/**
	 * 删除 素材
	 * 
	 * @param context
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	public Entity downloadmaterial(RequestContext context) throws BlAppException, DaoAppException;
}
