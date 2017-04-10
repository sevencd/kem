package cn.ilanhai.kem.bl.statistic.iparea;

import cn.ilanhai.framework.common.exception.BlAppException;

public interface IpToArea {
	/**
	 * 获取区域实体
	 * 
	 * @param ip
	 * @return
	 * @throws BlAppException
	 */
	public IpAreaEntity getAreaEntity(String ip) throws BlAppException;

	/**
	 * 获取区域名称
	 * 
	 * @param ip
	 * @return
	 * @throws BlAppException
	 */
	public String getAreaName(String ip) throws BlAppException;
}
