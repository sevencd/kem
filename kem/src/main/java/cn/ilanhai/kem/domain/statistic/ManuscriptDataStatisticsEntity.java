package cn.ilanhai.kem.domain.statistic;

import java.util.Calendar;
import java.util.Date;

import org.apache.ibatis.annotations.Case;
import org.apache.ibatis.jdbc.Null;

import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * 稿件数据统计实体
 * 
 * @author he
 *
 */
public class ManuscriptDataStatisticsEntity extends AbstractEntity {
	public ManuscriptDataStatisticsEntity() {

	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getVisitUrl() {
		return visitUrl;
	}

	public void setVisitUrl(String visitUrl) {
		this.visitUrl = visitUrl;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public StatisticsType getStatisticsType() {
		return statisticsType;
	}

	public void setStatisticsType(StatisticsType statisticsType) {
		this.statisticsType = statisticsType;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	/**
	 * 是否是今天
	 * 
	 * @return
	 */
	public boolean isToday() {
		Calendar addTime = Calendar.getInstance();
		addTime.setTime(this.addTime);
		Calendar sysTime = Calendar.getInstance();
		if (sysTime.get(Calendar.YEAR) != addTime.get(Calendar.YEAR))
			return false;
		if (sysTime.get(Calendar.MONTH) != addTime.get(Calendar.MONTH))
			return false;
		if (sysTime.get(Calendar.DAY_OF_MONTH) != addTime
				.get(Calendar.DAY_OF_MONTH))
			return false;
		return true;
	}

	/**
	 * 是否是昨天
	 * 
	 * @return
	 */
	public boolean isYesterday() {
		Calendar addTime = Calendar.getInstance();
		addTime.setTime(this.addTime);
		Calendar sysTime = Calendar.getInstance();
		if (sysTime.get(Calendar.YEAR) != addTime.get(Calendar.YEAR))
			return false;
		if (sysTime.get(Calendar.MONTH) != addTime.get(Calendar.MONTH))
			return false;
		sysTime.add(Calendar.DAY_OF_MONTH, -1);
		if (sysTime.get(Calendar.DAY_OF_MONTH) != addTime
				.get(Calendar.DAY_OF_MONTH))
			return false;
		return true;
	}

	private static final long serialVersionUID = 1L;
	/**
	 * 编号
	 */
	private long id;
	/**
	 * 浏览稿件URL
	 */
	private String visitUrl;
	/**
	 * 数量
	 */
	private int quantity;
	/**
	 * 统计类型
	 */
	private StatisticsType statisticsType;
	/**
	 * 添加时间
	 */
	private Date addTime;
	/**
	 * 修改时间
	 */
	private Date updateTime;

	/**
	 * 统计类型
	 * 
	 * @author he
	 *
	 */
	public enum StatisticsType {

		/**
		 * 0 浏览量
		 */
		Pv,
		/**
		 * 1 访客量
		 */
		Uv,
		/**
		 * 2 分享量
		 */
		Share,
		/**
		 * 3 停留时间
		 */
		ResidenceTime,
		/**
		 * 4 分享率
		 */
		ShareRate;
		public static StatisticsType valueOf(int val) {
			switch (val) {
			case 0:
				return Pv;
			case 1:
				return Uv;
			case 2:
				return Share;
			case 3:
				return ResidenceTime;
			case 4:
				return ShareRate;
			default:
				return null;
			}
		}

	}
}
