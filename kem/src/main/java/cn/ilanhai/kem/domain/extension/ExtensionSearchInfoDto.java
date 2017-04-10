package cn.ilanhai.kem.domain.extension;

import java.util.Date;
import java.util.List;

import cn.ilanhai.framework.app.domain.AbstractEntity;
import cn.ilanhai.kem.domain.special.ConfigActiveEntity;
import cn.ilanhai.kem.domain.special.ConfigKeywordEntity;
import cn.ilanhai.kem.domain.special.ConfigTagEntity;

public class ExtensionSearchInfoDto extends AbstractEntity {

	private static final long serialVersionUID = 8353190578473841508L;

	private String extension_id;
	private String extension_name;
	private String extension_img;
	private String special_id;
	private String special_name;
	private Date publish_time;
	private String extension_url;
	private Integer extension_type;
	private Integer extension_state;
	private Date createtime;
	private String user_id;
	private String summary;
	private String model_config_id;
	private String disable_reason;
	private String context;
	private String user_name;
	private String user_phone;
	/**
	 * 案例id
	 */
	private String manuscriptId;

	private Integer manuscriptEnable;
	/**
	 * 关键字
	 */
	private List<ConfigKeywordEntity> configKeywords;
	/**
	 * 标签
	 */
	private List<ConfigTagEntity> configTags;
	/**
	 * 活动设置
	 */
	private ConfigActiveEntity configActive;
	/**
	 * id
	 */
	private Integer configId;
	/**
	 * 模块连接id
	 */
	private Integer modelConfigId;
	/**
	 * 设置开始时间
	 */
	private Date start_time;
	/**
	 * 设置结束时间
	 */
	private Date end_time;
	/**
	 * 主色调
	 */
	private String main_color;
	
	
	
	public Date getStart_time() {
		return start_time;
	}

	public void setStart_time(Date start_time) {
		this.start_time = start_time;
	}

	public String getUser_phone() {
		return user_phone;
	}

	public void setUser_phone(String user_phone) {
		this.user_phone = user_phone;
	}

	public String getExtension_id() {
		return extension_id;
	}

	public void setExtension_id(String extension_id) {
		this.extension_id = extension_id;
	}

	public String getExtension_name() {
		return extension_name;
	}

	public void setExtension_name(String extension_name) {
		this.extension_name = extension_name;
	}

	public String getExtension_img() {
		return extension_img;
	}

	public void setExtension_img(String extension_img) {
		this.extension_img = extension_img;
	}

	public String getSpecial_id() {
		return special_id;
	}

	public void setSpecial_id(String special_id) {
		this.special_id = special_id;
	}

	public String getSpecial_name() {
		return special_name;
	}

	public void setSpecial_name(String special_name) {
		this.special_name = special_name;
	}

	public Date getPublish_time() {
		return publish_time;
	}

	public void setPublish_time(Date publish_time) {
		this.publish_time = publish_time;
	}

	public String getExtension_url() {
		return extension_url;
	}

	public void setExtension_url(String extension_url) {
		this.extension_url = extension_url;
	}

	public Integer getExtension_type() {
		return extension_type;
	}

	public void setExtension_type(Integer extension_type) {
		this.extension_type = extension_type;
	}

	public Integer getExtension_state() {
		return extension_state;
	}

	public void setExtension_state(Integer extension_state) {
		this.extension_state = extension_state;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getModel_config_id() {
		return model_config_id;
	}

	public void setModel_config_id(String model_config_id) {
		this.model_config_id = model_config_id;
	}

	public String getDisable_reason() {
		return disable_reason;
	}

	public void setDisable_reason(String disable_reason) {
		this.disable_reason = disable_reason;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
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

	public ConfigActiveEntity getConfigActive() {
		return configActive;
	}

	public void setConfigActive(ConfigActiveEntity configActive) {
		this.configActive = configActive;
	}

	public Integer getConfigId() {
		return configId;
	}

	public void setConfigId(Integer configId) {
		this.configId = configId;
	}

	public Integer getModelConfigId() {
		return modelConfigId;
	}

	public void setModelConfigId(Integer modelConfigId) {
		this.modelConfigId = modelConfigId;
	}

	public Date getEnd_time() {
		return end_time;
	}

	public void setEnd_time(Date end_time) {
		this.end_time = end_time;
	}

	public String getMain_color() {
		return main_color;
	}

	public void setMain_color(String main_color) {
		this.main_color = main_color;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getManuscriptId() {
		return manuscriptId;
	}

	public void setManuscriptId(String manuscriptId) {
		this.manuscriptId = manuscriptId;
	}

	public Integer getManuscriptEnable() {
		return manuscriptEnable;
	}

	public void setManuscriptEnable(Integer manuscriptEnable) {
		this.manuscriptEnable = manuscriptEnable;
	}
}
