package cn.ilanhai.kem.bl.tag;

import java.util.List;

import cn.ilanhai.framework.app.RequestContext;
import cn.ilanhai.framework.app.dao.Dao;
import cn.ilanhai.framework.app.dao.DaoProxyFactory;
import cn.ilanhai.framework.app.dao.DefaultDaoProxyFactoryImpl;
import cn.ilanhai.framework.common.exception.BlAppException;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.kem.common.CodeTable;
import cn.ilanhai.kem.dao.tag.MyBatisSysTagDao;
import cn.ilanhai.kem.domain.IdEntity;

public class SysTagManager {
	private static Class<?> currentclass = MyBatisSysTagDao.class;
	private static DaoProxyFactory daoFactory = DefaultDaoProxyFactoryImpl.getInstance();

	public static void quoteNumAdd(RequestContext requestContext, List<String> tageNames) throws BlAppException {
		Dao dao;
		try {
			dao = getDao(requestContext);
			for (String string : tageNames) {
				IdEntity<String> tagName = new IdEntity<String>();
				tagName.setId(string);
				int val = dao.save(tagName);
				if (val < 0) {
					CodeTable ct = CodeTable.BL_COMMON_SAVE_DOAMIN;
					String tmp = ct.getDesc();
					tmp = String.format(tmp, "标签引用数");
					throw new BlAppException(ct.getValue(), tmp);
				}
			}
		} catch (DaoAppException e) {
			CodeTable ct = CodeTable.BL_DATA_ERROR;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		}
	}

	private static Dao getDao(RequestContext requestContext) throws DaoAppException, BlAppException {
		CodeTable ct;
		Dao dao = daoFactory.getDao(requestContext, currentclass);
		if (dao == null) {
			ct = CodeTable.BL_COMMON_GET_DAO;
			String tmp = ct.getDesc();
			tmp = String.format(tmp, "标签");
			throw new BlAppException(ct.getValue(), tmp);
		}
		return dao;
	}
}
