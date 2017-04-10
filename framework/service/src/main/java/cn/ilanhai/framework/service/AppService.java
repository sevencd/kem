package cn.ilanhai.framework.service;

import java.net.*;
import java.util.*;

import cn.ilanhai.framework.app.Response;
import cn.ilanhai.framework.app.domain.*;
import cn.ilanhai.framework.common.exception.*;

/**
 * 定义远程服务接口
 * 
 * @author he
 *
 */
public interface AppService {
	Response service(URI location, Entity entity) throws ContainerException;

	String serviceJSON(URI location, String json);

	String serviceXML(URI location, String json);
}
