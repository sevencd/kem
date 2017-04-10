package cn.ilanhai.kem.domain.statistic;

import java.util.Date;

import cn.ilanhai.framework.app.domain.AbstractEntity;
import cn.ilanhai.kem.domain.statistic.ManuscriptDataStatisticsEntity.StatisticsType;

/**
 * 稿件渠道统计实体
 * 
 * @author he
 *
 */
public class ManuscriptChannelStatisticsEntity extends AbstractEntity {
	public ManuscriptChannelStatisticsEntity() {

	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public ChannelType getChannelType() {
		return channelType;
	}

	public void setChannelType(ChannelType channelType) {
		this.channelType = channelType;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
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

	public String getVisitUrl() {
		return visitUrl;
	}

	public void setVisitUrl(String visitUrl) {
		this.visitUrl = visitUrl;
	}

	private static final long serialVersionUID = 1L;
	/**
	 * 编号
	 */
	private long id;
	/**
	 * 渠道类型
	 */
	private ChannelType channelType;
	/**
	 * 数量
	 */
	private int quantity;
	/**
	 * 添加时间
	 */
	private Date addTime;
	/**
	 * 修改时间
	 */
	private Date updateTime;
	/**
	 * 浏览稿件URL
	 */
	private String visitUrl;

	/**
	 * 渠道类型
	 * 
	 * @author he
	 *
	 */
	public enum ChannelType {
		/**
		 * 0 qq
		 */
		Qq,
		/**
		 * 1 qq空间
		 */
		QqZone,
		/**
		 * 2 微信
		 */
		Wx,
		/**
		 * 3 微博
		 */
		SinaMicroBlog;
		public static ChannelType valueOf(int val) {
			switch (val) {
			case 0:
				return Qq;
			case 1:
				return QqZone;
			case 2:
				return Wx;
			case 3:
				return SinaMicroBlog;

			default:
				return null;
			}
		}
	}
}
