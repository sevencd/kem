package cn.ilanhai.kem.domain.template;

import java.util.Date;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class searchTemplateBackmanageDto extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3854173146428840325L;
	private String template_id;
	private String user_id;
	private String cover_img;
	private String template_name;
	private String main_color;
	private String summary;
	private Date createtime; 
	private Integer template_state;
	private Integer publish_state;
	private Integer template_type;
	private String verify_name;
	private Date verify_time;
	private Date shelf_time;
	private String bounced_reason;
	private String user_name;
	private String user_type;
	private String user_phone;
	private String user_email;
	public String getTemplate_id() {
		return template_id;
	}
	public void setTemplate_id(String template_id) {
		this.template_id = template_id;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getCover_img() {
		return cover_img;
	}
	public void setCover_img(String cover_img) {
		this.cover_img = cover_img;
	}
	public String getTemplate_name() {
		return template_name;
	}
	public void setTemplate_name(String template_name) {
		this.template_name = template_name;
	}
	public String getMain_color() {
		return main_color;
	}
	public void setMain_color(String main_color) {
		this.main_color = main_color;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public Integer getTemplate_state() {
		return template_state;
	}
	public void setTemplate_state(Integer template_state) {
		this.template_state = template_state;
	}
	public Integer getPublish_state() {
		return publish_state;
	}
	public void setPublish_state(Integer publish_state) {
		this.publish_state = publish_state;
	}
	public Integer getTemplate_type() {
		return template_type;
	}
	public void setTemplate_type(Integer template_type) {
		this.template_type = template_type;
	}
	public String getVerify_name() {
		return verify_name;
	}
	public void setVerify_name(String verify_name) {
		this.verify_name = verify_name;
	}
	public Date getVerify_time() {
		return verify_time;
	}
	public void setVerify_time(Date verify_time) {
		this.verify_time = verify_time;
	}
	public Date getShelf_time() {
		return shelf_time;
	}
	public void setShelf_time(Date shelf_time) {
		this.shelf_time = shelf_time;
	}
	public String getBounced_reason() {
		return bounced_reason;
	}
	public void setBounced_reason(String bounced_reason) {
		this.bounced_reason = bounced_reason;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getUser_type() {
		return user_type;
	}
	public void setUser_type(String user_type) {
		this.user_type = user_type;
	}
	public String getUser_phone() {
		return user_phone;
	}
	public void setUser_phone(String user_phone) {
		this.user_phone = user_phone;
	}
	public String getUser_email() {
		return user_email;
	}
	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
