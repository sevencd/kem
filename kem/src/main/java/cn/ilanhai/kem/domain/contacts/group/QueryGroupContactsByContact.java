package cn.ilanhai.kem.domain.contacts.group;

import java.util.List;

import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * 通过联系人查询群组
 * 
 * @author dgj
 *
 */
public class QueryGroupContactsByContact extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8588991558020428041L;

	private List<String> list;

	public List<String> getList() {
		return list;
	}

	public void setList(List<String> list) {
		this.list = list;
	}
}
