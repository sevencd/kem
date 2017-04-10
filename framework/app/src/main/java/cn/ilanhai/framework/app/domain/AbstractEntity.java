package cn.ilanhai.framework.app.domain;

import java.util.ArrayList;
import java.util.Iterator;

import cn.ilanhai.framework.uitl.FastJson;

/**
 * 定义抽象领域对象
 * 
 * @author he
 *
 */
public class AbstractEntity implements Entity {



	@Override
	public String toString() {
		return FastJson.bean2Json(this);
	}
}
