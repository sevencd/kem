package cn.ilanhai.kem.domain.ver;

import java.util.Date;

/**
 * @author he
 *
 */
public class DbVerEntity extends VerEntity {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Date addTime;
	private Date updateTime;
	private boolean verChange = false;

	public DbVerEntity() {
		addTime = new Date(System.currentTimeMillis());
		updateTime = new Date(System.currentTimeMillis());
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public boolean isVerChange() {
		return verChange;
	}

	public void setVerChange(boolean verChange) {
		this.verChange = verChange;
	}

}
