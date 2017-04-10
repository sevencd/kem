package cn.ilanhai.framework.app.bl;

import cn.ilanhai.framework.app.Application;

/**
 * 定义业务逻辑接口
 * 
 * @author he
 *
 */
public interface Bl {
	void setApplication(Application application);
	Application getApplication();
}
