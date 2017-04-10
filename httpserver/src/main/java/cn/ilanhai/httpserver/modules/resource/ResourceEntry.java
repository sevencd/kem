package cn.ilanhai.httpserver.modules.resource;

import java.util.HashMap;
import java.util.Map;
import java.util.zip.CRC32;

/**
 * 资源实体
 * 
 * @author he
 *
 */
public class ResourceEntry {
	public final String KEY_RAW = "raw";
	// 原名称
	private String rawName = null;
	// 新名称
	private String newName = null;
	// 大小
	private long size = 0;
	// 类型
	private String type = null;
	// 路经
	private String path = null;

	public ResourceEntry() {
		
	}
	public String getRawName() {
		return rawName;
	}

	public void setRawName(String rawName) {
		this.rawName = rawName;
	}

	public String getNewName() {
		return newName;
	}

	public void setNewName(String newName) {
		this.newName = newName;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}
