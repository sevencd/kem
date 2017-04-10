package cn.ilanhai.kem.domain.statistic.dto;

/**
 * 查询统计
 * 
 * @author he
 *
 */
public class QueryLimitManuscriptStatisticDto extends
		QueryManuscriptStatisticDto {
	public QueryLimitManuscriptStatisticDto() {

	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	/**
	 * 数量
	 */
	private int limit;
}
