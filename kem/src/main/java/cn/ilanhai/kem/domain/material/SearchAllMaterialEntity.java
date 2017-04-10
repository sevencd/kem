package cn.ilanhai.kem.domain.material;

import java.util.Date;

import cn.ilanhai.framework.app.domain.AbstractEntity;
/**
 * 用于查询素材的实体
 * @author dgj
 *
 */
public class SearchAllMaterialEntity  extends AbstractEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7125219535858349207L;

	private String materialId;//
	private String thumbnail;//图片路径
	private Integer terminalType;//终端类型
	private String materialCategory;//素材分类
	private String materialCategoryId;//素材分类id
	private Integer materialType;//用于类型分类
	private Date createtime;
	private String keyWord;
	private Integer materialStatus;//状态
	private String remark;
	private String materialTemplate;//svg模板
	private String clientType;
	private String compositeCategory;
	private String prepareThumbnail;//svg待修改后台使用的缩略图
	
	public String getPrepareThumbnail() {
		return prepareThumbnail;
	}
	public void setPrepareThumbnail(String prepareThumbnail) {
		this.prepareThumbnail = prepareThumbnail;
	}
	public String getClientType() {
		return clientType;
	}
	public void setClientType(String clientType) {
		this.clientType = clientType;
	}
	public String getCompositeCategory() {
		return compositeCategory;
	}
	public void setCompositeCategory(String compositeCategory) {
		this.compositeCategory = compositeCategory;
	}
	public String getMaterialTemplate() {
		return materialTemplate;
	}
	public void setMaterialTemplate(String materialTemplate) {
		this.materialTemplate = materialTemplate;
	}
	public String getMaterialId() {
		return materialId;
	}
	public void setMaterialId(String materialId) {
		this.materialId = materialId;
	}
	public String getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	public Integer getTerminalType() {
		return terminalType;
	}
	public void setTerminalType(Integer terminalType) {
		this.terminalType = terminalType;
	}
	public String getMaterialCategory() {
		return materialCategory;
	}
	public void setMaterialCategory(String materialCategory) {
		this.materialCategory = materialCategory;
	}
	public String getMaterialCategoryId() {
		return materialCategoryId;
	}
	public void setMaterialCategoryId(String materialCategoryId) {
		this.materialCategoryId = materialCategoryId;
	}
	public Integer getMaterialType() {
		return materialType;
	}
	public void setMaterialType(Integer materialType) {
		this.materialType = materialType;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public String getKeyWord() {
		return keyWord;
	}
	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}
	public Integer getMaterialStatus() {
		return materialStatus;
	}
	public void setMaterialStatus(Integer materialStatus) {
		this.materialStatus = materialStatus;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	

}
