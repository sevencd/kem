package cn.ilanhai.kem.domain.email;

/**
 * 邮件条目
 * 
 * @author he
 *
 */
public class MailInfoItemDto {
	public MailInfoItemDto() {

	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * 内容类型
	 */
	private String key;

	/**
	 * 具体内容
	 */
	private String content;
}
