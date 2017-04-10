package cn.ilanhai.kem.domain;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * 一个实现了Map接口的实体
 * @author Nature
 *
 */
public class MapDto extends AbstractEntity implements Map<String,Object>{

	private Map<String,Object> mapValue=new HashMap<String,Object>();
	
	@Override
	public int size() {
		return mapValue.size();
	}

	@Override
	public boolean isEmpty() {
		return mapValue.isEmpty();
	}

	@Override
	public boolean containsKey(Object key) {
		return mapValue.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return mapValue.containsValue(value);
	}

	@Override
	public Object get(Object key) {
		return mapValue.get(key);
	}

	@Override
	public Object put(String key, Object value) {
		return mapValue.put(key, value);
	}

	@Override
	public Object remove(Object key) {
		return mapValue.remove(key);
	}

	@Override
	public void putAll(Map m) {
		mapValue.putAll(m);
	}

	@Override
	public void clear() {
		mapValue.clear();
	}

	@Override
	public Set keySet() {
		return mapValue.keySet();
	}

	@Override
	public Collection values() {
		return mapValue.values();
	}

	@Override
	public Set entrySet() {
		return mapValue.entrySet();
	}
	
	@Override
	public String toString() {
		return mapValue.toString();
	}
	
}
