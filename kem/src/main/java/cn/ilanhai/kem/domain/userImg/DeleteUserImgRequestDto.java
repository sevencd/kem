package cn.ilanhai.kem.domain.userImg;

import java.util.List;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class DeleteUserImgRequestDto extends AbstractEntity {
	private static final long serialVersionUID = -8303831414270395411L;
	private List<DeleteUserImgDataDto> data;

	public List<DeleteUserImgDataDto> getData() {
		return data;
	}

	public void setData(List<DeleteUserImgDataDto> data) {
		this.data = data;
	}
}
