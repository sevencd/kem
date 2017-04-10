package cn.ilanhai.kem.bl.special;

import cn.ilanhai.framework.app.RequestContext;
import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.exception.BlAppException;
import cn.ilanhai.framework.common.exception.DaoAppException;

/**
 * 专题接口
 * 
 * @author hy
 *
 */
public interface Special {
	/**
	 * 查询专题
	 * 
	 * @param context
	 * @return
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	Entity search(RequestContext context) throws BlAppException,
			DaoAppException;

	/**
	 * 使用模版生成专题
	 * 
	 * @param context
	 * @return
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	Entity create(RequestContext context) throws BlAppException,
			DaoAppException;

	/**
	 * 加载用户专题
	 * 
	 * @param context
	 * @return
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	Entity load(RequestContext context) throws BlAppException, DaoAppException;

	/**
	 * 复制专题
	 * 
	 * @param context
	 * @return
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	String copy(RequestContext context) throws BlAppException, DaoAppException;

	/**
	 * 删除专题
	 * 
	 * @param context
	 * @return
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	void delete(RequestContext context) throws BlAppException, DaoAppException;

	/**
	 * 发布推广
	 * 
	 * @param context
	 * @return
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	String publish(RequestContext context) throws BlAppException,
			DaoAppException;

	/**
	 * 加载筛选标签
	 * 
	 * @param context
	 * @return
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	Entity loadtags(RequestContext context) throws BlAppException,
			DaoAppException;

	/**
	 * 保存专题
	 * 
	 * @param context
	 * @return
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	Entity save(RequestContext context) throws BlAppException, DaoAppException;
	
	/**
	 * 保存专题名称
	 * 
	 * @param context
	 * @return
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	Entity savespecialname(RequestContext context) throws BlAppException, DaoAppException;

	/**
	 * 保存推广设置
	 * 
	 * @param context
	 * @return
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	void savepublishinfo(RequestContext context) throws BlAppException,
			DaoAppException;

	/**
	 * 加载发布设置
	 * 
	 * @param context
	 * @return
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	Entity loadpublishinfo(RequestContext context) throws BlAppException,
			DaoAppException;

	/**
	 * 预览专题
	 * 
	 * @param context
	 * @return
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	String preview(RequestContext context) throws BlAppException,
			DaoAppException;

}
