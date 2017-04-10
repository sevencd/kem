package cn.ilanhai.kem.domain.rights;

import java.util.Date;

import cn.ilanhai.framework.app.domain.AbstractEntity;
/**
 * 去版权记录实体
 * @author hy
 *
 */
public class UnRightsLogEntity extends AbstractEntity {
	private static final long serialVersionUID = 5193827573363200758L;
	//记录id
	private Integer unrightsLogId;
	//用户id
	private String userId;
	
	private String manuscriptId;
	private Date createtime;
	public Integer getUnrightsLogId() {
		return unrightsLogId;
	}
	public void setUnrightsLogId(Integer unrightsLogId) {
		this.unrightsLogId = unrightsLogId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getManuscriptId() {
		return manuscriptId;
	}
	public void setManuscriptId(String manuscriptId) {
		this.manuscriptId = manuscriptId;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
}
