package cn.ilanhai.kem.domain.userImg;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class SearchUserImgRequestDto extends AbstractEntity {
	private static final long serialVersionUID = -8303831414270395411L;
	/**
	 * 排序方式
	 */
	private Integer orderType;
	/**
	 * 图片名称
	 */
	private String imgName;
	/**
	 * 起始数
	 */
	private Integer startCount;
	/**
	 * 多少
	 */
	private Integer pageSize;

	private String imgMd5Search;

	private String materialId;

	private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	public String getImgName() {
		return imgName;
	}

	public void setImgName(String imgName) {
		this.imgName = imgName;
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

	public String getImgMd5Search() {
		return imgMd5Search;
	}

	public void setImgMd5Search(String imgMd5Search) {
		this.imgMd5Search = imgMd5Search;
	}

	public String getMaterialId() {
		return materialId;
	}

	public void setMaterialId(String materialId) {
		this.materialId = materialId;
	}
}
