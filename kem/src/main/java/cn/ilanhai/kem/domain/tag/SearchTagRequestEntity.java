package cn.ilanhai.kem.domain.tag;

import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * 查询标签请求对象
 * 
 * @author hy
 *
 */
public class SearchTagRequestEntity extends AbstractEntity {
	private static final long serialVersionUID = 6453827601685933221L;

	public static final String QUOTEASC = "quoteAsc";
	public static final String QUOTEDESC = "quoteDesc";
	public static final String DESC = "desc";
	public static final String ASC = "asc";
	private String orderModeSQL;//实际排序方式

	public String getOrderModeSQL() {
		return orderModeSQL;
	}

	public void setOrderModeSQL(String orderModeSQL) {
		this.orderModeSQL = orderModeSQL;
	}

	/**
	 * 排序方式 quoteAsc引用数升序,quoteDesc引用数降序
	 */
	private String orderMode;
	/**
	 * 标签名
	 */
	private String tagName;
	/**
	 * 开始条数
	 */
	private Integer startCount;
	/**
	 * 取用条数
	 */
	private Integer pageSize;
	/**
	 * 是否精选，为null则不筛选此条件
	 */
	private Boolean isSelection;

	private String userId;

	public String getOrderMode() {
		return orderMode;
	}

	public void setOrderMode(String orderMode) {
		this.orderMode = orderMode;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
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

	public Boolean getIsSelection() {
		return isSelection;
	}

	public void setIsSelection(Boolean isSelection) {
		this.isSelection = isSelection;
	}

	public static String choseModle(String model) {
		if (SearchTagRequestEntity.QUOTEDESC.equals(model)) {
			return SearchTagRequestEntity.DESC;
		} else if (SearchTagRequestEntity.QUOTEASC.equals(model)) {
			return SearchTagRequestEntity.ASC;
		} else {
			return null;
		}
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
