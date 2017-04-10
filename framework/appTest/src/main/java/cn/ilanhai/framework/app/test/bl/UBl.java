package cn.ilanhai.framework.app.test.bl;

import java.util.Iterator;

import cn.ilanhai.framework.app.RequestContext;
import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.common.exception.BlAppException;

public interface UBl {
	boolean add(RequestContext context);

	boolean update(RequestContext context);

	boolean delete(RequestContext context);

	Entity getUser(RequestContext context);

	Iterator<Entity> getUsers(RequestContext context);

	Iterator<Entity> getExc(RequestContext context) throws BlAppException;
}
