package cn.ilanhai.kem.bl.plugin.activeplugin;

import cn.ilanhai.framework.app.RequestContext;
import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.exception.BlAppException;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.framework.common.exception.SessionContainerException;
import cn.ilanhai.kem.domain.plugin.activeplugin.dto.DrawPrizeResponseDto;

/**
 * 活动插件接口
 * 
 * @author Nature
 *
 */
public interface ActivePlugin {

	/**
	 * 创建活动插件
	 * 
	 * @param context
	 * @return 返回插件ID
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	Integer create(RequestContext context) throws SessionContainerException, BlAppException, DaoAppException;

	/**
	 * 加载活动插件设置
	 * 
	 * @param context
	 * @return 活动插件设置
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	Entity loadsetting(RequestContext context) throws SessionContainerException,BlAppException, DaoAppException;

	/**
	 * 保存活动插件设置
	 * 
	 * @param context
	 * @return
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	String savesetting(RequestContext context) throws SessionContainerException,BlAppException, DaoAppException;

	/**
	 * 填写中奖信息
	 * 
	 * @param context
	 * @return 抽奖记录ID
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	boolean addprizeinfo(RequestContext context) throws SessionContainerException,BlAppException, DaoAppException;

	/**
	 * 抽奖
	 * 
	 * @param context
	 * @return 抽奖记录ID
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	DrawPrizeResponseDto drawprize(RequestContext context) throws SessionContainerException,BlAppException, DaoAppException;

	/**
	 * 兑奖
	 * 
	 * @param context
	 * @return
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	boolean exchangeprize(RequestContext context) throws BlAppException, DaoAppException;

	/**
	 * 获取抽奖信息
	 * 
	 * @param context
	 * @return
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	Entity loadoptions(RequestContext context) throws SessionContainerException,BlAppException, DaoAppException;

	/**
	 * 查询中奖信息
	 * 
	 * @param context
	 * @return
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	Entity searchWinTrafficUser(RequestContext context) throws BlAppException, DaoAppException;
	/**
	 * 删除中奖信息
	 * @param context
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	void deleteWinTrafficUser(RequestContext context) throws BlAppException, DaoAppException;
}
