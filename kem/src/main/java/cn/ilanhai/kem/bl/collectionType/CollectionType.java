package cn.ilanhai.kem.bl.collectionType;


import cn.ilanhai.framework.app.RequestContext;
import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.exception.BlAppException;
import cn.ilanhai.framework.common.exception.DaoAppException;

public interface CollectionType {

	/**
	 * 
	 * 
	 * @param context
	 * @return
	 * @throws BlAppException
	 * @throws DaoAppException
	 */
	Entity loadcollectiontype(RequestContext context) throws BlAppException, DaoAppException;
}
