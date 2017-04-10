package cn.ilanhai.kem.domain.material;

import java.util.Date;

import cn.ilanhai.framework.app.domain.AbstractEntity;
import cn.ilanhai.kem.domain.enums.TerminalType;

/**
 * 查询素材入参
 * @author dgj
 *
 */
public class SearchMaterailRequest extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1760765765575697836L;
	
	private Integer terminalType;
	private String materialId;
	private String materialCategoryId;
	private String sort;//排序
	private Date startDate;
	private Date endDate;
	private Integer startCount;
	private Integer pageSize;
	private Integer count;
	private String key;
	private String templateId;
	private String serviceName;
	private Integer materialType;
	
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getTemplateId() {
		return templateId;
	}
	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
	public String getKeys() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public Integer getTerminalType() {
		return terminalType;
	}
	public void setTerminalType(Integer terminalType) {
		this.terminalType = terminalType;
	}
	public String getMaterialId() {
		return materialId;
	}
	public void setMaterialId(String materialId) {
		this.materialId = materialId;
	}
	public String getMaterialCategoryId() {
		return materialCategoryId;
	}
	public void setMaterialCategoryId(String materialCategoryId) {
		this.materialCategoryId = materialCategoryId;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Integer getStartCount() {
		return startCount;
	}
	public void setStartCount(Integer startCount) {
		this.startCount = startCount;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	/**
	 * @return the materialType
	 */
	public Integer getMaterialType() {
		return materialType;
	}
	/**
	 * @param materialType the materialType to set
	 */
	public void setMaterialType(Integer materialType) {
		this.materialType = materialType;
	}
	
}
