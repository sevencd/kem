package cn.ilanhai.kem.domain.special;

import java.util.Date;
import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * 查询专题响应数据Dto
 * 
 * @author hy
 *
 */
public class SearchSpecialResponseDataDto extends AbstractEntity {
	private static final long serialVersionUID = 3661317252295662432L;
	/**
	 * 专题id
	 */
	private String specialId;
	/**
	 * 专题名
	 */
	private String specialName;
	/**
	 * 专题封面
	 */
	private String specialImg;
	/**
	 * 专题类型
	 */
	private Integer specialType;
	/**
	 * 创建时间
	 */
	private Date createtime;
	/**
	 * 专题状态
	 */
	private Integer specialState;
	/**
	 * 发布状态
	 */
	private Integer publishState;
	/**
	 * 作者
	 */
	private String user;
	/**
	 * 简介
	 */
	private String summary;

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

	public String getSpecialImg() {
		return specialImg;
	}

	public void setSpecialImg(String specialImg) {
		this.specialImg = specialImg;
	}

	public Integer getSpecialType() {
		return specialType;
	}

	public void setSpecialType(Integer specialType) {
		this.specialType = specialType;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Integer getSpecialState() {
		return specialState;
	}

	public void setSpecialState(Integer specialState) {
		this.specialState = specialState;
	}

	public Integer getPublishState() {
		return publishState;
	}

	public void setPublishState(Integer publishState) {
		this.publishState = publishState;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}
}
