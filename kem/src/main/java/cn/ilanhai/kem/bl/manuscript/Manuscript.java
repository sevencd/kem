package cn.ilanhai.kem.bl.manuscript;

import java.text.ParseException;

import cn.ilanhai.framework.app.RequestContext;
import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.exception.BlAppException;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.kem.domain.MapDto;

public interface Manuscript {
	/**
	 * 加载稿件内容
	 * 
	 * @param context
	 * @return
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	public Entity load(RequestContext context) throws BlAppException, DaoAppException;

	/**
	 * 优秀案例查询功能
	 * 
	 * @param context
	 * @return
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	public Entity searchmanuscript(RequestContext context) throws BlAppException, DaoAppException;

	/**
	 * 启用/取消优秀案例
	 * 
	 * @param context
	 * @return
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	public Entity create(RequestContext context) throws BlAppException, DaoAppException;

	/**
	 * 加载标签
	 * 
	 * @param context
	 * @return
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	public Entity loadtags(RequestContext context) throws BlAppException, DaoAppException;

	/**
	 * 预览稿件
	 * 
	 * @param context
	 * @return
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	public String preview(RequestContext context) throws BlAppException, DaoAppException;

	/**
	 * 查询收藏
	 * 
	 * @param context
	 * @return
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	public Entity searchcollection(RequestContext context) throws BlAppException, DaoAppException;

	/**
	 * 保存稿件内容
	 * 
	 * @param context
	 * @return
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	public Entity save(RequestContext context) throws BlAppException, DaoAppException;

	/**
	 * 保存稿件名称
	 * 
	 * @param context
	 * @return
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	public Entity savemanuscriptname(RequestContext context) throws BlAppException, DaoAppException;

	/**
	 * 加载稿件名称
	 * 
	 * @param context
	 * @return
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	public String loadname(RequestContext context) throws BlAppException, DaoAppException;

	/**
	 * 获取稿件是否做发布设置
	 * 
	 * @param context
	 * @return
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	public String loadpublishstate(RequestContext context) throws BlAppException, DaoAppException;

	/**
	 * 稿件收藏
	 * 
	 * @param context
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	public void collection(RequestContext context) throws BlAppException, DaoAppException;

	/**
	 * 稿件复制
	 * 
	 * @param context
	 * @return
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	public String copy(RequestContext context) throws BlAppException, DaoAppException;

	/**
	 * 稿件删除
	 * 
	 * @param context
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	public void delete(RequestContext context) throws BlAppException, DaoAppException;

	/**
	 * 保存发布信息
	 * 
	 * @param context
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	public void savepublishinfo(RequestContext context) throws BlAppException, DaoAppException;

	/**
	 * 查询发布设置
	 * 
	 * @param context
	 * @return
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	Entity searchpublishsetting(RequestContext context) throws BlAppException, DaoAppException;

	/**
	 * 获取稿件的状态
	 * 
	 * @param context
	 * @return
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	public MapDto loadstate(RequestContext context) throws BlAppException, DaoAppException;
/**
	 * 加载发布设置
	 * @param context
	 * @return
	 * @throws BlAppException
	 * @throws DaoAppException
	 * @throws ParseException 
	 */
	Entity loadpublishinfo(RequestContext context) throws BlAppException, DaoAppException, ParseException;

	/**
	 * 查询稿件
	 * 
	 * @param context
	 * @return
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	public Entity search(RequestContext context) throws BlAppException, DaoAppException;
}
