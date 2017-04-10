package cn.ilanhai.kem.bl.collectionType.impl;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import cn.ilanhai.framework.InterfaceUtil.InterfaceDocAnnotation;
import cn.ilanhai.framework.app.RequestContext;
import cn.ilanhai.framework.app.dao.Dao;
import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.exception.BlAppException;
import cn.ilanhai.framework.common.exception.DaoAppException;
import cn.ilanhai.kem.bl.BLContextUtil;
import cn.ilanhai.kem.bl.BaseBl;
import cn.ilanhai.kem.bl.collectionType.CollectionType;
import cn.ilanhai.kem.common.CodeTable;
import cn.ilanhai.kem.dao.collectionType.CollectionTypeDao;
import cn.ilanhai.kem.domain.collectionType.dto.LoadAllCollectionTypeDto;

@Component("collectiontype")
public class CollectionTypeImpl extends BaseBl implements CollectionType {
	Logger logger = Logger.getLogger(CollectionTypeImpl.class);
	@Override
	@InterfaceDocAnnotation(methodVersion = "1.4.0")
	public Entity loadcollectiontype(RequestContext context) throws BlAppException, DaoAppException {
		CodeTable ct;
		try {
			LoadAllCollectionTypeDto loadAllCollectionTypeDto = context.getDomain(LoadAllCollectionTypeDto.class);
			this.valiPara(loadAllCollectionTypeDto);
			Dao dao = BLContextUtil.getDao(context, CollectionTypeDao.class);
			if (new Integer(1).equals(loadAllCollectionTypeDto.getLoadType())) {
				return dao.query(null, false);
			}
			this.checkLimit(loadAllCollectionTypeDto.getStartCount(), loadAllCollectionTypeDto.getPageSize());
			return dao.query(loadAllCollectionTypeDto, false);
		} catch (BlAppException e) {
			throw e;
		} catch (DaoAppException e) {
			ct = CodeTable.BL_DATA_ERROR;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		} catch (Exception e) {
			ct = CodeTable.BL_UNHANDLED_EXCEPTION;
			throw new BlAppException(ct.getValue(), ct.getDesc(), e);
		}
	}

}
