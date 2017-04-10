package cn.ilanhai.kem.mail;

import java.net.URLEncoder;

import cn.ilanhai.framework.uitl.Str;

/**
 * 邮件配置
 * 
 * @author he
 *
 */
public abstract class MailConfig {
	protected String encode(String str) {
		if (Str.isNullOrEmpty(str))
			return str;
		return URLEncoder.encode(str);
	}

}
