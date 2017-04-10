package cn.ilanhai.kem.domain.user.frontuser;

public class RegistFrontUserEntity extends FrontUserEntity {
	private static final long serialVersionUID = 231785568608583232L;
	/**
	 * 短信验证码内容
	 */
	private String smsCode;

	/**
	 * 图片证码内容
	 */
	private String imgCode;
	/**
	 * token
	 */
	private String token;

	/**
	 * 功能模块
	 */
	private String moduleCode;

	/**
	 * 具体流程编码
	 */
	private String workId;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getImgCode() {
		return imgCode;
	}

	public void setImgCode(String imgCode) {
		this.imgCode = imgCode;
	}

	public String getSmsCode() {
		return smsCode;
	}

	public void setSmsCode(String smsCode) {
		this.smsCode = smsCode;
	}

	public String getModuleCode() {
		return moduleCode;
	}

	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
	}

	public String getWorkId() {
		return workId;
	}

	public void setWorkId(String workId) {
		this.workId = workId;
	}

}
