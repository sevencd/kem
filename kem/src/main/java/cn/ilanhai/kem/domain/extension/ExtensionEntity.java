package cn.ilanhai.kem.domain.extension;

import java.util.Date;
import java.util.List;

import cn.ilanhai.framework.app.domain.AbstractEntity;
import cn.ilanhai.kem.domain.special.ConfigActiveEntity;
import cn.ilanhai.kem.domain.special.ConfigEntity;
import cn.ilanhai.kem.domain.special.ConfigKeywordEntity;
import cn.ilanhai.kem.domain.special.ConfigTagEntity;

/**
 * 推广实体
 * 
 * @author hy
 *
 */
public class ExtensionEntity extends AbstractEntity {
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
	 * 用户
	 */
	private String userId;

	/**
	 * 创建时间
	 */
	private Date createtime;

	/**
	 * context
	 */

	private String context;
	/**
	 * 简介
	 */
	private String summary;
	/**
	 * 发布时间
	 */
	private Date publishTime;

	/**
	 * 连接设置id
	 */
	private Integer modelConfigId;
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

	private String disableReason;
	
	/**
	 * 案例id
	 */
	private String manuscriptId;
	
	
	private Boolean manuscriptEnable;

	/**
	 * 活动设置
	 */
	private ConfigActiveEntity configActive;
	/**
	 * 关键字
	 */
	private List<ConfigKeywordEntity> configKeywords;
	/**
	 * 标签
	 */
	private List<ConfigTagEntity> configTags;

	/**
	 * 发布设置
	 */
	private ConfigEntity config;

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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
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

	public Integer getModelConfigId() {
		return modelConfigId;
	}

	public void setModelConfigId(Integer modelConfigId) {
		this.modelConfigId = modelConfigId;
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

	public String getDisableReason() {
		return disableReason;
	}

	public void setDisableReason(String disableReason) {
		this.disableReason = disableReason;
	}

	public ConfigActiveEntity getConfigActive() {
		return configActive;
	}

	public void setConfigActive(ConfigActiveEntity configActive) {
		this.configActive = configActive;
	}

	public List<ConfigKeywordEntity> getConfigKeywords() {
		return configKeywords;
	}

	public void setConfigKeywords(List<ConfigKeywordEntity> configKeywords) {
		this.configKeywords = configKeywords;
	}

	public List<ConfigTagEntity> getConfigTags() {
		return configTags;
	}

	public void setConfigTags(List<ConfigTagEntity> configTags) {
		this.configTags = configTags;
	}

	public ConfigEntity getConfig() {
		return config;
	}

	public void setConfig(ConfigEntity config) {
		this.config = config;
	}

	public String getManuscriptId() {
		return manuscriptId;
	}

	public void setManuscriptId(String manuscriptId) {
		this.manuscriptId = manuscriptId;
	}

	public Boolean getManuscriptEnable() {
		return manuscriptEnable;
	}

	public void setManuscriptEnable(Boolean manuscriptEnable) {
		this.manuscriptEnable = manuscriptEnable;
	}
}
