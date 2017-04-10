package cn.ilanhai.httpserver.modules.resource;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 定义资源接口
 * 
 * @author he
 *
 */
public interface Resource {
	ResourceEntry save(String path,String fileName, InputStream inputStream)
			throws IOException;

	// ResourceEntry delete(OutputStream outputStream) throws IOException;
}
