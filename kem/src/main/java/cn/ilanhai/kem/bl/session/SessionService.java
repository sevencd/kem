package cn.ilanhai.kem.bl.session;

import cn.ilanhai.framework.app.RequestContext;
import cn.ilanhai.framework.common.exception.BlAppException;

/**
 * 会话服务
 * 
 * @author Nature
 *
 */
public interface SessionService {

	String gettoken(RequestContext context) throws BlAppException;

	String logout(RequestContext context);

	String gethosturl(RequestContext context);

	Integer getsessionstate(RequestContext context) throws BlAppException;
}
