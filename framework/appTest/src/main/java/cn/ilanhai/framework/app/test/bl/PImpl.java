package cn.ilanhai.framework.app.test.bl;

import java.util.ArrayList;
import java.util.Iterator;

import org.springframework.objenesis.instantiator.basic.NewInstanceInstantiator;
import org.springframework.stereotype.Component;

import cn.ilanhai.framework.app.RequestContext;
import cn.ilanhai.framework.app.bl.AbstractBl;
import cn.ilanhai.framework.app.domain.Entity;
import cn.ilanhai.framework.app.test.domain.UEntity;
import cn.ilanhai.framework.common.exception.BlAppException;
import cn.ilanhai.framework.common.session.Session;

@Component("ppppp")
public class PImpl extends AbstractBl implements P {

	public void getVoid(RequestContext context) {

	}

	public boolean getBool(RequestContext context) {

		return false;
	}

	public byte getByte(RequestContext context) {

		return 1;
	}

	public char getChar(RequestContext context) {

		return 2;
	}

	public short getShort(RequestContext context) {

		return 3;
	}

	public int getInt(RequestContext context) {

		Session session = null;
		session = context.getSession();
		Integer sessionCounter = null;
		int tmp = 0;

		sessionCounter = (Integer) session.getParameter("sessionCounter");
		// if(session==null)

		session.setParameter("logg", 1);

		if (sessionCounter != null)
			tmp = sessionCounter + 1;

		session.setParameter("sessionCounter", new Integer(tmp));

		if (true) {
			session.setParameter("aa", new Integer(tmp));

		}

		return tmp;

	}

	public long getLong(RequestContext context) {
		return 5;
	}

	public float getFloat(RequestContext context) {

		return 6.0f;
	}

	public double getDouble(RequestContext context) {

		return 7.0;
	}

	public Object getObject(RequestContext context) {
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

}
