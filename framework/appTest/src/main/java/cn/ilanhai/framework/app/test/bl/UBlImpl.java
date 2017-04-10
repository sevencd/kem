package cn.ilanhai.framework.app.test.bl;

import java.util.ArrayList;
import java.util.Iterator;

import org.springframework.stereotype.Component;

import cn.ilanhai.framework.app.RequestContext;
import cn.ilanhai.framework.app.bl.AbstractBl;
import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.app.test.domain.UEntity;
import cn.ilanhai.framework.common.exception.BlAppException;

@Component("user")
public class UBlImpl extends AbstractBl implements UBl {

	public boolean add(RequestContext context) {
		Entity e = null;
		System.out.println("add");
		e = context.<UEntity> getDomain(UEntity.class);
		System.out.println(e);
		return true;
	}

	public boolean update(RequestContext context) {
		Entity e = null;
		System.out.println("update");
		e = context.<UEntity> getDomain(UEntity.class);
		System.out.println(e);
		return true;
	}

	public boolean delete(RequestContext context) {
		Entity e = null;
		System.out.println("delete");
		e = context.<UEntity> getDomain(UEntity.class);
		System.out.println(e);
		return false;
	}

	public Entity getUser(RequestContext context) {
		Entity e = null;

		System.out.println("getUesr");
		e = context.<UEntity> getDomain(UEntity.class);
		System.out.println(e);

		UEntity u = null;
		u = new UEntity();
		u.setId(888);
		u.setName("he");
		return u;
	}

	public Iterator<Entity> getUsers(RequestContext context) {
		System.out.println("getUsers");
		Entity e = null;
		e = context.<UEntity> getDomain(UEntity.class);
		System.out.println(e);
		Iterator<Entity> ret = null;
		UEntity u = null;
		ArrayList<Entity> us = null;
		us = new ArrayList<Entity>();
		u = new UEntity();
		u.setId(888);
		u.setName("he");
		us.add(u);
		u = new UEntity();
		u.setId(999);
		u.setName("hehe");
		us.add(u);
		return us.iterator();
	}

	public Iterator<Entity> getExc(RequestContext context)
			throws BlAppException {

		throw new BlAppException(999, "999");
	}

}
