package cn.ilanhai.kem.mail.protocol.sohu.label;

import java.util.Date;

/**
 * 标签
 * 
 * @author he
 *
 */
public class Label {
	public Label() {

	}

	public String getLabelId() {
		return labelId;
	}

	public void setLabelId(String labelId) {
		this.labelId = labelId;
	}

	public String getLabelName() {
		return labelName;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}

	public Date getGmtCreated() {
		return gmtCreated;
	}

	public void setGmtCreated(Date gmtCreated) {
		this.gmtCreated = gmtCreated;
	}

	public Date getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	/**
	 * 标签ID
	 */
	private String labelId;
	/**
	 * 标签名称
	 */
	private String labelName;
	/**
	 * 标签创建时间
	 */
	private Date gmtCreated;
	/**
	 * 标签修改时间
	 */
	private Date gmtModified;

}
