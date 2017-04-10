package cn.ilanhai.kem.bl.crawler.impl;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.ilanhai.framework.InterfaceUtil.InterfaceDocAnnotation;
import cn.ilanhai.framework.app.RequestContext;
import cn.ilanhai.framework.app.dao.Dao;
import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.exception.AppException;
import cn.ilanhai.framework.common.exception.BlAppException;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.framework.common.session.enums.SessionStateType;
import cn.ilanhai.kem.bl.BLContextUtil;
import cn.ilanhai.kem.bl.BaseBl;
import cn.ilanhai.kem.bl.crawler.CrawlerRule;
import cn.ilanhai.kem.common.CodeTable;
import cn.ilanhai.kem.dao.crawler.CrawlerRuleDao;
import cn.ilanhai.kem.domain.CountDto;
import cn.ilanhai.kem.domain.crawler.CrawlerRuleDeleteDto;
import cn.ilanhai.kem.domain.crawler.CrawlerRuleDto;
import cn.ilanhai.kem.domain.crawler.CrawlerRuleRequestDto;
import cn.ilanhai.kem.domain.crawler.CrawlerRuleResponseDto;

/**
 * 
 * 爬虫规则业务逻辑
 * 
 * @TypeName CrawlerRuleImpl
 * @time 2017-03-01 10:05
 * @author csz
 */

@Component("CrawlerRule")
public class CrawlerRuleImpl extends BaseBl implements CrawlerRule {
	private static String desc = "采集规则";

	@Override
	@InterfaceDocAnnotation(methodVersion = "1.4.0")
	@Transactional(rollbackFor = { Throwable.class }, propagation = Propagation.REQUIRED)
	public void saveTask(RequestContext context) throws BlAppException, DaoAppException {
		// 验证后台用户是否已登录
		this.checkBackUserLogined(context);

		CodeTable ct;
		// 获取入参
		CrawlerRuleDto request = context.getDomain(CrawlerRuleDto.class);
		// 验证入参是否为空
		valiPara(request);
		// 验证taskid
		BLContextUtil.valiTaskId(request.getTaskId());

		try {
			// 获取数据连接资源
			Dao dao = this.daoProxyFactory.getDao(context, CrawlerRuleDao.class);
			valiDaoIsNull(dao, desc);
			// 返回结果
			int result = dao.save(request);
			valiSaveDomain(result, desc);
		} catch (BlAppException e) {
			ct = CodeTable.BL_UNHANDLED_EXCEPTION;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		}
	}

	@Override
	@InterfaceDocAnnotation(methodVersion = "1.4.0")
	@Transactional(rollbackFor = { Throwable.class }, propagation = Propagation.REQUIRED)
	public void removeTask(RequestContext context) throws BlAppException, DaoAppException {
		this.checkBackUserLogined(context);
		// 获取入参
		CrawlerRuleDeleteDto request = context.getDomain(CrawlerRuleDeleteDto.class);
		valiPara(request);
		// 验证请求ids是否为空
		valiParaItemListNull(request.getIds(), "taskid");
		// 获取数据了连接资源
		Dao dao = this.daoProxyFactory.getDao(context, CrawlerRuleDao.class);
		valiDaoIsNull(dao, desc);
		// 删除
		int deleteCount = dao.delete(request);
		valiDeleteDomain(deleteCount, desc);
	}

	@Override
	@InterfaceDocAnnotation(methodVersion = "1.4.0")
	@Transactional(rollbackFor = { Throwable.class }, propagation = Propagation.REQUIRED)
	public void updateTask(RequestContext context) throws BlAppException, DaoAppException {
		CodeTable ct;
		try {
			this.checkBackUserLogined(context);
			// 获取入参
			CrawlerRuleDto request = context.getDomain(CrawlerRuleDto.class);
			valiPara(request);
			// 获取数据了连接资源
			Dao dao = this.daoProxyFactory.getDao(context, CrawlerRuleDao.class);
			valiDaoIsNull(dao, desc);
			// 更新数据
			int updateCount = dao.save(request);
			valiUpdateDomain(updateCount, desc);
		} catch (BlAppException e) {
			throw e;
		} catch (Exception e) {
			ct = CodeTable.BL_UNHANDLED_EXCEPTION;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		}
	}

	@Override
	@InterfaceDocAnnotation(methodVersion = "1.4.0")
	public Entity listTask(RequestContext context) throws BlAppException {
		try {
			// 获取入参
			CrawlerRuleRequestDto request = context.getDomain(CrawlerRuleRequestDto.class);
			valiPara(request);
			// 验证后台用户是否已登录
			this.checkBackUserLogined(context);
			// 获取数据了连接资源
			Dao dao = this.daoProxyFactory.getDao(context, CrawlerRuleDao.class);
			valiDaoIsNull(dao, desc);
			// 查询结果
			List<Entity> list = dao.queryList(request);
			CountDto count = (CountDto) dao.query(request, false);
			// 返回结果
			CrawlerRuleResponseDto result = new CrawlerRuleResponseDto();
			result.setDetail(list);
			result.setTotalCount(count.getCount());
			result.setStartCount(request.getStartCount());
			result.setPageSize(request.getPageSize());
			return result;
		} catch (DaoAppException e) {
			throw new BlAppException(e.getErrorCode(), e.getErrorDesc(), e);
		}
	}

}
