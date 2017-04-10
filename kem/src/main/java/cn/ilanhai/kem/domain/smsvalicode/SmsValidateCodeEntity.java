package cn.ilanhai.kem.domain.smsvalicode;

import java.util.Date;

import cn.ilanhai.framework.app.domain.AbstractEntity;
import cn.ilanhai.framework.common.exception.BlAppException;
import cn.ilanhai.kem.common.CodeTable;
import cn.ilanhai.kem.domain.enums.UserType;
import cn.ilanhai.kem.domain.enums.ValidateStatus;

public class SmsValidateCodeEntity extends AbstractEntity {

	private static final long serialVersionUID = -169059318293436049L;

	// 验证码编号
	private Integer id;
	// 验证码内容
	private String identityCode;
	// 用户编号
	private String userId;
	// 用户类型
	private UserType userType;
	// 流程编码
	private String ModuleCode;
	// 业务编码
	private String workId;
	// 创建时间
	private Date createtime;
	// 过期时间
	private Date deadline;
	// 验证状态
	private ValidateStatus status;

	public SmsValidateCodeEntity() {
		userType = UserType.ANONYMOUS_USER;
		status = status.NOT_VALIDATE;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIdentityCode() {
		return identityCode;
	}

	public void setIdentityCode(String identityCode) {
		this.identityCode = identityCode;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	public String getModuleCode() {
		return ModuleCode;
	}

	public void setModuleCode(String moduleCode) {
		ModuleCode = moduleCode;
	}

	public String getWorkId() {
		return workId;
	}

	public void setWorkId(String workId) {
		this.workId = workId;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

	public ValidateStatus getStatus() {
		return status;
	}

	public void setStatus(ValidateStatus status) {
		this.status = status;
	}

	public boolean verify(String smsCode) throws BlAppException {
		// 校验记录是否已经经过校验
		if (this.getStatus().equals(ValidateStatus.VERIFIED)) {
			throw new BlAppException(
					CodeTable.BL_SMSVALICODE_VALIDATED.getValue(),
					CodeTable.BL_SMSVALICODE_VALIDATED.getDesc());
		}
		// 校验是否已超时
		if ((new Date()).after(this.getDeadline())) {
			throw new BlAppException(
					CodeTable.BL_SMSVALICODE_TIMTOUT.getValue(),
					CodeTable.BL_SMSVALICODE_TIMTOUT.getDesc());
		}
		// 校验code是否匹配
		if (!this.getIdentityCode().equals(smsCode)) {
			throw new BlAppException(
					CodeTable.BL_SMSVALICODE_WRONGCODE.getValue(),
					CodeTable.BL_SMSVALICODE_WRONGCODE.getDesc());
		}
		return true;
	}
}
