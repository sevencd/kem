package cn.ilanhai.kem.domain.user.frontuser;

import java.util.List;

import cn.ilanhai.framework.app.domain.AbstractEntity;
/**
 * 会员列表
 * @author csz
 * @time 2017-03-22 15:18
 *
 */
public class SearchFrontuserResponseDto extends AbstractEntity {

	private static final long serialVersionUID = 2250616505772955553L;
	
	/**
	 * 开始条数
	 */
	private Integer startCount;
	/**
	 * 页面个数
	 */
	private Integer pageSize;
	/**
	 * 总数
	 */
	private Integer totalCount;
	/**
	 * 数据集合
	 */
	private List<LoadReturnUserInfoByEdtion> list;
	
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
	public Integer getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public List<LoadReturnUserInfoByEdtion> getList() {
		return list;
	}
	public void setList(List<LoadReturnUserInfoByEdtion> list) {
		this.list = list;
	}
	
}
