package cn.ilanhai.kem.domain.collectionType;

import java.util.Date;
import java.util.List;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class CollectionTypeEntity extends AbstractEntity {
	private static final long serialVersionUID = -6687504826747471003L;
	private Integer typeId;
	private String typeNum;
	private String typeName;
	private String typeParentNum;
	private String typeParentName;
	private Integer typeParentId;
	private Date createtime;
	private List<CollectionTypeEntity> childrenType;
	public Integer getTypeId() {
		return typeId;
	}
	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}
	public String getTypeNum() {
		return typeNum;
	}
	public void setTypeNum(String typeNum) {
		this.typeNum = typeNum;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getTypeParentNum() {
		return typeParentNum;
	}
	public void setTypeParentNum(String typeParentNum) {
		this.typeParentNum = typeParentNum;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public List<CollectionTypeEntity> getChildrenType() {
		return childrenType;
	}
	public void setChildrenType(List<CollectionTypeEntity> childrenType) {
		this.childrenType = childrenType;
	}
	public String getTypeParentName() {
		return typeParentName;
	}
	public void setTypeParentName(String typeParentName) {
		this.typeParentName = typeParentName;
	}
	public Integer getTypeParentId() {
		return typeParentId;
	}
	public void setTypeParentId(Integer typeParentId) {
		this.typeParentId = typeParentId;
	}
}
