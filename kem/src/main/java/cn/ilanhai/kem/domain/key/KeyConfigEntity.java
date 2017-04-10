package cn.ilanhai.kem.domain.key;

import java.util.Date;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class KeyConfigEntity extends AbstractEntity {
	private static final long serialVersionUID = 1L;
	private int id;
	private String prefix;
	private long seed;
	private boolean isEnable;
	private Date addTime;
	private Date updateTime;
	private int len;
	private int step=0;
	public KeyConfigEntity() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public long getSeed() {
		return seed;
	}

	public void setSeed(long seed) {
		this.seed = seed;
	}

	public boolean isEnable() {
		return isEnable;
	}

	public void setEnable(boolean isEnable) {
		this.isEnable = isEnable;
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

	public int getLen() {
		return len;
	}

	public void setLen(int len) {
		this.len = len;
	}

	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}
	
}
