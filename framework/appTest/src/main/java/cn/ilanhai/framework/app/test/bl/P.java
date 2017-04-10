package cn.ilanhai.framework.app.test.bl;

import cn.ilanhai.framework.app.RequestContext;

public interface P {

	void getVoid(RequestContext context);

	boolean getBool(RequestContext context);

	byte getByte(RequestContext context);

	char getChar(RequestContext context);

	short getShort(RequestContext context);

	int getInt(RequestContext context);

	long getLong(RequestContext context);

	float getFloat(RequestContext context);

	double getDouble(RequestContext context);

	Object getObject(RequestContext context);

}
