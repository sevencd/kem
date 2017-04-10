package cn.ilanhai.kem.domain.email;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * 邮件加载响应
 * 
 * @author he
 *
 */
public class MailLoadDtoResponse extends AbstractEntity {
	
	private static final long serialVersionUID = -3324567491815455469L;

	public MailLoadDtoResponse() {
		this.info = new HashMap<String,String>();
	}

	public Map<String,String> getInfo() {
		return info;
	}

	public void setInfo(Map<String,String> info) {
		this.info = info;
	}

	private Map<String,String> info;
}
