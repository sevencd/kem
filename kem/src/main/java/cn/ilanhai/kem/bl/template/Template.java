package cn.ilanhai.kem.bl.template;

import cn.ilanhai.framework.app.RequestContext;
import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.exception.BlAppException;
import cn.ilanhai.framework.common.exception.DaoAppException;

/**
 * 定认模版接口
 * 
 * @author he
 *
 */
public interface Template {
	/**
	 * 加载用户模板
	 * 
	 * @param context
	 * @return
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	Entity load(RequestContext context) throws BlAppException, DaoAppException;

	/**
	 * 保存用户模板(不存在的不进行保存)
	 * 
	 * @param context
	 * @return
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	boolean save(RequestContext context) throws BlAppException, DaoAppException;

	/**
	 * 新建模板接口
	 * 
	 * @param context
	 * @return
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	Entity create(RequestContext context) throws BlAppException, DaoAppException;

	/**
	 * 复制模板
	 * 
	 * @param context
	 * @return
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	String copy(RequestContext context) throws BlAppException, DaoAppException;

	/**
	 * 提交发布
	 * 
	 * @param context
	 * @return
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	Entity publish(RequestContext context) throws BlAppException, DaoAppException;

	/**
	 * 查询模版
	 * 
	 * @param context
	 * @return
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	Entity search(RequestContext context) throws BlAppException, DaoAppException;
	
	/**
	 * 随机查询模版
	 * 
	 * @param context
	 * @return
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	Entity searchbyrand(RequestContext context) throws BlAppException, DaoAppException;

	/**
	 * 审核模版
	 * 
	 * @param context
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	void verify(RequestContext context) throws BlAppException, DaoAppException;

	/**
	 * 加载所有当前用户自己的模板中存在的标签
	 * 
	 * @param context
	 * @return
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	Entity loadtags(RequestContext context) throws BlAppException, DaoAppException;

	/**
	 * 保存发布设置
	 * 
	 * @param context
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	void savepublishinfo(RequestContext context) throws BlAppException, DaoAppException;

	/**
	 * 加载发布设置
	 * 
	 * @param context
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	Entity loadpublishinfo(RequestContext context) throws BlAppException, DaoAppException;

	/**
	 * 查询用户使用过的模板
	 * 
	 * @param context
	 * @return
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	Entity searchusedtemplate(RequestContext context) throws BlAppException, DaoAppException;

	/**
	 * 删除模版
	 * 
	 * @param context
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	void delete(RequestContext context) throws BlAppException, DaoAppException;

	/**
	 * 预览模版
	 * 
	 * @param context
	 * @return
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	String preview(RequestContext context) throws BlAppException, DaoAppException;

	/**
	 * 查询收藏
	 * 
	 * @param context
	 * @return
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	Entity searchCollection(RequestContext context) throws BlAppException, DaoAppException;

	/**
	 * 收藏
	 * 
	 * @param context
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	void collection(RequestContext context) throws BlAppException, DaoAppException;

	/**
	 * 模版下架
	 * 
	 * @param context
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	void shelf(RequestContext context) throws BlAppException, DaoAppException;
}
