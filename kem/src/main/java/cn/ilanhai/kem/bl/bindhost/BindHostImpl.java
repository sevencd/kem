package cn.ilanhai.kem.bl.bindhost;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import cn.ilanhai.framework.InterfaceUtil.InterfaceDocAnnotation;
import cn.ilanhai.framework.app.RequestContext;
import cn.ilanhai.framework.app.dao.Dao;
import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.exception.AppException;
import cn.ilanhai.framework.common.exception.BlAppException;
import cn.ilanhai.framework.common.exception.CacheContainerException;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.framework.common.exception.SessionContainerException;
import cn.ilanhai.kem.bl.BaseBl;
import cn.ilanhai.kem.bl.paymentservice.OrderManager;
import cn.ilanhai.kem.common.CodeTable;
import cn.ilanhai.kem.dao.bindhost.BindHostDao;
import cn.ilanhai.kem.domain.bindhost.BindHostRequestEntity;
import cn.ilanhai.kem.domain.bindhost.QueryBindHostRequestEntity;
import cn.ilanhai.kem.domain.bindhost.QueryBindHostResponseEntity;
import cn.ilanhai.kem.bl.BLContextUtil;

@Component("bindhost")
public class BindHostImpl extends BaseBl implements BindHost {

	private Logger logger = Logger.getLogger(BindHostImpl.class);

	@Override
	@InterfaceDocAnnotation(methodVersion = "1.0.0")
	public boolean bind(RequestContext context) throws BlAppException, DaoAppException {
		BindHostRequestEntity request = null;
		CodeTable ct;
		try {
			BindHostManager manager = new BindHostManager(context);
			request = context.getDomain(BindHostRequestEntity.class);
			if (request.getHost().length() > 0) {
				if (request.getHost().startsWith("http://")) {
					request.setHost(request.getHost().substring(7));
				}else if (request.getHost().startsWith("https://")) {
					request.setHost(request.getHost().substring(8));
				}
				Pattern patt = Pattern.compile("[a-z0-9-]+(\\.[a-z0-9-]+)+",Pattern.CASE_INSENSITIVE);
				Matcher matcher = patt.matcher(request.getHost());
				boolean isMatch = matcher.matches();
				if (!isMatch) {
					throw new BlAppException(-1, "请输入合法域名");
				}
			} else {
				logger.info("域名为空则刷新用户绑定状态");
				manager.updateBindStatus(request);
				return true;
			}
			// 保存用户域名信息
			String user = manager.saveUserHost(request);
			// 从数据库获取用户的推广
			// 将域名添加进redis
			manager.queryentensionbyuser(user, request.getHost());
			return true;
		} catch (BlAppException e) {
			throw e;
		} catch (CacheContainerException e) {
			e.printStackTrace();
			throw new BlAppException(-1, e.getMessage(), e);
		} catch (SessionContainerException e) {
			e.printStackTrace();
			throw new BlAppException(-1, e.getMessage(), e);
		} catch (AppException e) {
			e.printStackTrace();
			throw new BlAppException(-1, e.getMessage(), e);
		} catch (Exception e) {
			ct = CodeTable.BL_UNHANDLED_EXCEPTION;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		}
	}

	@Override
	@InterfaceDocAnnotation(methodVersion = "1.0.0")
	public Entity loaduserhost(RequestContext context) throws BlAppException, DaoAppException {
		QueryBindHostRequestEntity request = new QueryBindHostRequestEntity();
		Dao dao = null;
		checkFrontUserLogined(context);
		String userId;
		try {
			userId = BLContextUtil.getSessionUserId(context);
			BLContextUtil.valiParaItemStrNullOrEmpty(userId, "用户id");
			request.setUserId(userId);
			dao = BLContextUtil.getDao(context, BindHostDao.class);
			BLContextUtil.valiDaoIsNull(dao, "加载用户绑定域名");
			QueryBindHostResponseEntity queryBindHostResponseEntity = (QueryBindHostResponseEntity) dao.query(request, false);
			if (queryBindHostResponseEntity == null) {
				queryBindHostResponseEntity = new QueryBindHostResponseEntity();
				queryBindHostResponseEntity.setUserId(userId);
			}
			return queryBindHostResponseEntity;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new BlAppException(-1,e.getMessage());
		}
		
	}
}
