package cn.ilanhai.kem.domain.statistic.dto;

import java.util.Calendar;
import java.util.Date;

/**
 * 数据传播数据,客户端请求
 * 
 * @author he
 *
 */
public class ManuscriptStatisticPropagateDtoRequest extends
		ManuscriptStatisticTotalDtoRequest {
	public ManuscriptStatisticPropagateDtoRequest() {

	}

	public int getDateRangeType() {
		return dateRangeType;
	}

	public void setDateRangeType(int dateRangeType) {
		this.dateRangeType = dateRangeType;
	}

	public Date getBeginDateTime() {

		if (this.getDateRange() == DateRange.Last7Day
				|| this.getDateRange() == DateRange.Last30Day)
			this.initBeginDateTime(new Date());
		return beginDateTime;
	}

	private void initBeginDateTime(Date beginDatetime) {

		DateRange dateRange = this.getDateRange();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(beginDatetime);
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		if (dateRange == DateRange.Last7Day) {
			calendar.add(Calendar.DATE, LAST_7DAY);
		} else if (dateRange == DateRange.Last30Day) {
			calendar.add(Calendar.DATE, LAST_30DAY);
		} else if (dateRange == DateRange.Range) {

		} else {

		}
		this.beginDateTime = calendar.getTime();
	}

	public void setBeginDateTime(Date beginDatetime) {

		this.beginDateTime = beginDatetime;
	}

	public Date getEndDateTime() {
		if (this.getDateRange() == DateRange.Last7Day
				|| this.getDateRange() == DateRange.Last30Day)
			this.initEndDateTime(new Date());
		return endDateTime;
	}

	private void initEndDateTime(Date endDateTime) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(endDateTime);
		calendar.set(Calendar.HOUR, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);

		this.endDateTime = calendar.getTime();
	}

	public void setEndDateTime(Date endDateTime) {

		this.endDateTime = endDateTime;
	}

	/**
	 * 将条件类型转成DateRange枚举
	 * 
	 * @return
	 */
	public DateRange getDateRange() {
		return DateRange.valueOf(this.dateRangeType);
	}

	private static final long serialVersionUID = 1L;
	/**
	 * 7天
	 */
	private final int LAST_7DAY = -7;
	/**
	 * 30天
	 */
	private final int LAST_30DAY = -30;
	/**
	 * 条件类型 必需 0表示最近7天(从当天算起) 1表示最近30天(从当天算起) 2表示时间范围
	 */
	private int dateRangeType = -1;
	/**
	 * 开始时间 非必需 条件类型为3必需 (格式 yyyy-MM-dd 时间从00:00:00)
	 */
	private Date beginDateTime;
	/**
	 * 结束时间 非必需 条件类型为3必需 (格式 yyyy-MM-dd 时间从23:59:59)
	 */
	private Date endDateTime;

	/**
	 * 条件类型
	 * 
	 * @author he
	 *
	 */
	public enum DateRange {
		/**
		 * 0表示最近7天(从当天算起)
		 */
		Last7Day,
		/**
		 * 1表示最近30天(从当天算起)
		 */
		Last30Day,
		/**
		 * 2表示时间范围
		 */
		Range;
		/**
		 * @param val
		 * @return
		 */
		public static DateRange valueOf(int val) {
			switch (val) {
			case 0:
				return Last7Day;
			case 1:
				return Last30Day;
			case 2:
				return Range;
			default:
				return null;
			}
		}
	}
}
