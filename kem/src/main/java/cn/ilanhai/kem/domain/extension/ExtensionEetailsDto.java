package cn.ilanhai.kem.domain.extension;

import java.util.Date;
import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * 推广详情实体
 * 
 * @author hy
 *
 */
public class ExtensionEetailsDto extends AbstractEntity {
	private static final long serialVersionUID = 7325501131810788379L;
	/**
	 * 推广id
	 */
	private String extensionId;
	/**
	 * 专题编号
	 */
	private String specialId;

	/**
	 * 专题名
	 */
	private String specialName;

	/**
	 * 专题名
	 */
	private String extensionName;
	/**
	 * 封面
	 */
	private String extensionImg;

	/**
	 * 创建时间
	 */
	private Date createtime;


	/**
	 * 简介
	 */
	private String summary;
	/**
	 * 发布时间
	 */
	private Date publishTime;

	/**
	 * 专题状态
	 */
	private Integer extensionState;

	/**
	 * 专题类型
	 */
	private Integer extensionType;
	/**
	 * 用户名或手机
	 */
	private String user;

	private String extensionUrl;
	
	private Integer isHasActive;

	public String getExtensionId() {
		return extensionId;
	}

	public void setExtensionId(String extensionId) {
		this.extensionId = extensionId;
	}

	public String getSpecialId() {
		return specialId;
	}

	public void setSpecialId(String specialId) {
		this.specialId = specialId;
	}

	public String getSpecialName() {
		return specialName;
	}

	public void setSpecialName(String specialName) {
		this.specialName = specialName;
	}

	public String getExtensionName() {
		return extensionName;
	}

	public void setExtensionName(String extensionName) {
		this.extensionName = extensionName;
	}

	public String getExtensionImg() {
		return extensionImg;
	}

	public void setExtensionImg(String extensionImg) {
		this.extensionImg = extensionImg;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public Date getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}

	public Integer getExtensionState() {
		return extensionState;
	}

	public void setExtensionState(Integer extensionState) {
		this.extensionState = extensionState;
	}

	public Integer getExtensionType() {
		return extensionType;
	}

	public void setExtensionType(Integer extensionType) {
		this.extensionType = extensionType;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getExtensionUrl() {
		return extensionUrl;
	}

	public void setExtensionUrl(String extensionUrl) {
		this.extensionUrl = extensionUrl;
	}

	public Integer isHasActive() {
		return isHasActive;
	}

	public void setHasActive(Integer isHasActive) {
		this.isHasActive = isHasActive;
	}
}
