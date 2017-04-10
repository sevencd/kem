package cn.ilanhai.kem.domain.extension;

import java.util.Date;

import cn.ilanhai.framework.app.domain.AbstractEntity;
import cn.ilanhai.kem.domain.tag.SearchTagRequestEntity;

/**
 * 查询推广请求dto
 * 
 * @author hy
 *
 */
public class SearchExtensionRequestDto extends AbstractEntity {
	private static final long serialVersionUID = 1345591773431720654L;
	public static final String DATEASC = "date-asc";
	public static final String DATEDESC = "date-desc";
	public static final String DESC = "desc";
	public static final String ASC = "asc";
	/**
	 * 用户id 可空
	 */
	private String user_id;
	/**
	 * 专题名称 模糊查询
	 */
	private String extensionName;
	/**
	 * 推广状态 0:已发布，1：已查看 2：已禁用 如果为空 则为当前用户所有推广 如果为后台用户则不能为空
	 */
	private Integer extensionState;
	/**
	 * 推广类型 1：PC端；2：移动端
	 */
	private Integer extensionType;
	/**
	 * 可空，不为空时则筛选结果的创建时间将会大于等于该值
	 */
	private Date timeStart;
	/**
	 * 可空，不为空时则筛选结果的创建时间将会小于该值
	 */
	private Date timeEnd;
	/**
	 * 开始条数，不可空，正整数
	 */
	private Integer startCount;
	/**
	 * 取用条数，不可空，正整数
	 */
	private Integer pageSize;

	private String order;
	
	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getExtensionName() {
		return extensionName;
	}

	public void setExtensionName(String extensionName) {
		this.extensionName = extensionName;
	}

	public Integer getExtensionState() {
		return extensionState;
	}

	public void setExtensionState(Integer extensionState) {
		this.extensionState = extensionState;
	}

	public Integer getExtensionType() {
		return extensionType;
	}

	public void setExtensionType(Integer extensionType) {
		this.extensionType = extensionType;
	}

	public Date getTimeStart() {
		return timeStart;
	}

	public void setTimeStart(Date timeStart) {
		this.timeStart = timeStart;
	}

	public Date getTimeEnd() {
		return timeEnd;
	}

	public void setTimeEnd(Date timeEnd) {
		this.timeEnd = timeEnd;
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

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}
}
