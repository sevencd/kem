package cn.ilanhai.kem.domain.userImg;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class SearchUserImgRequestEntity extends AbstractEntity {
	private static final long serialVersionUID = -8303831414270395411L;
	private Integer orderType;
	private String imgName;
	private Integer startCount;
	private Integer pageSize;
	private String userId;
	private String serviceName;
	private String imgMd5;
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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public SearchUserImgRequestEntity buildSearchUserImgRequestEntity(SearchUserImgRequestDto searchUserImgRequestDto) {
		if (searchUserImgRequestDto == null) {
			return this;
		}
		this.imgName = searchUserImgRequestDto.getImgName();
		this.orderType = searchUserImgRequestDto.getOrderType();
		this.pageSize = searchUserImgRequestDto.getPageSize();
		this.startCount = searchUserImgRequestDto.getStartCount();
		this.imgMd5 = searchUserImgRequestDto.getImgMd5Search();
		this.materialId = searchUserImgRequestDto.getMaterialId();
		this.type = searchUserImgRequestDto.getType();
		return this;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getImgMd5() {
		return imgMd5;
	}

	public void setImgMd5(String imgMd5) {
		this.imgMd5 = imgMd5;
	}

	public String getMaterialId() {
		return materialId;
	}

	public void setMaterialId(String materialId) {
		this.materialId = materialId;
	}
}
