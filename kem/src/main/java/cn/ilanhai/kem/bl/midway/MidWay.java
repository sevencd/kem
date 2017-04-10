package cn.ilanhai.kem.bl.midway;

import java.util.List;

import cn.ilanhai.framework.app.RequestContext;
import cn.ilanhai.framework.common.exception.BlAppException;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.kem.domain.midway.PullPublishedResponseDto;


public interface MidWay {

	/**
	 * midway获取所有发布内容列表
	 * @param context
	 * @return
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	List<PullPublishedResponseDto> pullpublished(RequestContext context) throws BlAppException,
	DaoAppException;
}
