package cn.ilanhai.kem.domain.imgvalicode;

import cn.ilanhai.framework.app.domain.AbstractEntity;
import cn.ilanhai.framework.common.exception.BlAppException;
import cn.ilanhai.kem.domain.enums.ValidateStatus;
import cn.ilanhai.kem.common.CodeTable;
import cn.ilanhai.kem.domain.enums.UserType;
import java.util.*;

/**
 * 验证码实体
 * 
 * @author he
 *
 */
public class ImgVailcodeEntity extends AbstractEntity {
	// 编号
	private Integer id;
	// 唯一码
	private String identityCode;
	// 用户编号
	private String userId;
	// 用户类型
	private UserType userType;
	// 模块代码
	private String moduleCode;
	//
	private String workId;
	// 创建时间
	private Date createtime;
	// 过期时间
	private Date deadline;
	// 状态
	private ValidateStatus status;

	public ImgVailcodeEntity() {
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

	public boolean verify(String imgCode) throws BlAppException {
		// 校验记录是否已经经过校验
		if (this.getStatus().equals(ValidateStatus.VERIFIED)) {
			throw new BlAppException(CodeTable.BL_IMGVALICODE_VALIDATED.getValue(),
					CodeTable.BL_IMGVALICODE_VALIDATED.getDesc());
		}
		// 校验是否已超时
		if ((new Date()).after(this.getDeadline())) {
			throw new BlAppException(CodeTable.BL_IMGVALICODE_EXPIRE.getValue(),
					CodeTable.BL_IMGVALICODE_EXPIRE.getDesc());
		}
		// 校验code是否匹配
		if (!this.getIdentityCode().toUpperCase().equals(imgCode.toUpperCase())) {
			throw new BlAppException(CodeTable.BL_IMGVALICODE_ERROR.getValue(),
					CodeTable.BL_IMGVALICODE_ERROR.getDesc());
		}
		return true;
	}
}
