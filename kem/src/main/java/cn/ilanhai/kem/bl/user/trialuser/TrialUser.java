package cn.ilanhai.kem.bl.user.trialuser;

import cn.ilanhai.framework.app.RequestContext;
import cn.ilanhai.framework.common.exception.BlAppException;

/**
 * 申请试用接口
 * @author csz
 * @time 2017-03-20 14:54
 */
public interface TrialUser {
	/**
	 * 申请试用
	 * 
	 * @param context	请求上下文
	 * @throws BlAppException
	 */
	int saveTrialUser(RequestContext context) throws BlAppException;
}
