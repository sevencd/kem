package cn.ilanhai.kem.util;

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

import com.alibaba.fastjson.JSONObject;

import cn.ilanhai.framework.app.dao.Dao;
import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.cache.Cache;
import cn.ilanhai.framework.common.exception.BlAppException;
import cn.ilanhai.framework.common.exception.CacheContainerException;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.framework.common.exception.SessionContainerException;
import cn.ilanhai.kem.cache.CacheUtil;
import cn.ilanhai.kem.dao.collectionType.CollectionTypeDao;

public class CollectionTypeUtil {
	private static Logger logger = Logger.getLogger(CollectionTypeUtil.class);
	private static Cache c;
	private final static int CRAWLER_INDEX=3;

	public static void initCollectionType(ApplicationContext applicationContext) {
		try {
			c = CacheUtil.getInstance().getCache(CRAWLER_INDEX);
			Dao dao = applicationContext.getBean(CollectionTypeDao.class);
			List<Entity> typeList = dao.queryList(null);
			c.set("collectiontype", typeList, -1);
			logger.info("初始化行业typeNum和typeName成功");
		} catch (DaoAppException e) {
			e.printStackTrace();
			logger.info("初始化行业typeNum和typeName失败");
		} catch (CacheContainerException e) {
			e.printStackTrace();
			logger.info("初始化行业typeNum和typeName失败");

		}

	}

	/**
	 * @Description 通过行业编码得到行业名称
	 * @param industryTwo 二级行业编号
	 * @author csz
	 * @time 2017年3月14日  18:05:21
	 */
	public static String translateTypeNum(String industryTwo) {
		try {
			@SuppressWarnings("unchecked")
			List<JSONObject> collectionTypeEntityList = c.get("collectiontype", List.class);
			if (collectionTypeEntityList != null && collectionTypeEntityList.size() > 0) {
				for (Iterator<JSONObject> iterator = collectionTypeEntityList.iterator(); iterator.hasNext();) {
					JSONObject jsonObject = iterator.next();
					if (industryTwo.equals(jsonObject.get("typeNum").toString())) {
						return jsonObject.get("typeName").toString();
					}

				}
			}
		} catch (CacheContainerException e) {
			e.printStackTrace();
		}
		return industryTwo;
	}

}
