package cn.ilanhai.httpserver.modules.resource;


import cn.ilanhai.httpserver.modules.resource.localresource.LocalResource;

public class ResourceFactory {

	public static Resource getResource(){
		return new LocalResource();
	}
}
