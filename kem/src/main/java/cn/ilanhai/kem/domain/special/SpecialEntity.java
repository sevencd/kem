package cn.ilanhai.kem.domain.special;

import java.util.Date;
import java.util.List;

import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * 专题实体
 * 
 * @author hy
 *
 */
public class SpecialEntity extends AbstractEntity {
	private static final long serialVersionUID = 7325501131810788379L;
	/**
	 * 专题id
	 */
	private String specialId;
	/**
	 * 专题名
	 */
	private String specialName;
	/**
	 * 模版id
	 */
	private String templateId;
	/**
	 * 用户
	 */
	private String userId;

	/**
	 * 创建时间
	 */
	private Date createtime;

	/**
	 * 封面
	 */
	private String coverImg;

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
	 * 发布名称
	 */
	private String publishName;

	/**
	 * 发布状态
	 */
	private Integer publishState;

	/**
	 * 连接设置id
	 */
	private Integer modelConfigId;
	/**
	 * 活动类型
	 */
	private String activeType;

	/**
	 * 活动配置
	 */
	private Integer activeConfig;

	/**
	 * 专题状态
	 */
	private Integer specialState;

	/**
	 * 专题类型
	 */
	private Integer specialType;

	/**
	 * 推广id
	 */
	private String extensionId;
	
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
	/**
	 * 审核记录
	 */
	private List<AuditLogEntity> auditLog;
	/**
	 * 用户名或手机
	 */
	private String user;

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
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

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
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

	public String getPublishName() {
		return publishName;
	}

	public void setPublishName(String publishName) {
		this.publishName = publishName;
	}

	public Integer getPublishState() {
		return publishState;
	}

	public void setPublishState(Integer publishState) {
		this.publishState = publishState;
	}

	public Integer getModelConfigId() {
		return modelConfigId;
	}

	public void setModelConfigId(Integer modelConfigId) {
		this.modelConfigId = modelConfigId;
	}

	public String getActiveType() {
		return activeType;
	}

	public void setActiveType(String activeType) {
		this.activeType = activeType;
	}

	public Integer getActiveConfig() {
		return activeConfig;
	}

	public void setActiveConfig(Integer activeConfig) {
		this.activeConfig = activeConfig;
	}

	public Integer getSpecialState() {
		return specialState;
	}

	public void setSpecialState(Integer specialState) {
		this.specialState = specialState;
	}

	public Integer getSpecialType() {
		return specialType;
	}

	public void setSpecialType(Integer specialType) {
		this.specialType = specialType;
	}

	public String getExtensionId() {
		return extensionId;
	}

	public void setExtensionId(String extensionId) {
		this.extensionId = extensionId;
	}

	public String getCoverImg() {
		return coverImg;
	}

	public void setCoverImg(String coverImg) {
		this.coverImg = coverImg;
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

	public List<AuditLogEntity> getAuditLog() {
		return auditLog;
	}

	public void setAuditLog(List<AuditLogEntity> auditLog) {
		this.auditLog = auditLog;
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
