package cn.ilanhai.kem.domain.plugin.activeplugin.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import java.util.Random;

import cn.ilanhai.framework.common.exception.BlAppException;
import cn.ilanhai.kem.bl.plugin.activeplugin.ActivePluginImpl;
import cn.ilanhai.kem.common.CodeTable;
import cn.ilanhai.kem.domain.enums.ActivePluginType;
import cn.ilanhai.kem.domain.enums.PluginType;
import cn.ilanhai.kem.domain.plugin.PluginEntity;
import cn.ilanhai.kem.domain.plugin.activeplugin.dto.QueryDrawPrizeRecordByRecordIdDto;

public class ActivePluginEntity extends PluginEntity {
	private static Logger logger = Logger.getLogger(ActivePluginEntity.class);
	private static final long serialVersionUID = 6722457941409343999L;

	// 活动插件类型
	protected ActivePluginType activePluginType;
	// 奖项设置
	protected List<ActivePluginPrizeSettingEntity> activePluginPrizeSettings;
	// 抽奖次数
	protected Integer drawTime;
	// 中奖次数
	protected Integer winTime;
	// 间隔时间
	protected Integer intervalTime;
	// 间隔时间单位，1：分钟；2：小时；3：天
	protected Integer intervalTimeType;
	// 中奖收集信息
	protected Map<String, Boolean> prizeCollectInfo = new HashMap<String, Boolean>();
	// 中奖收集信息必填项
	protected Map<String, Boolean> prizeCollectRequiredInfo = new HashMap<String, Boolean>();
	// 网址链接
	protected String outerUrl;
	// 商家电话
	protected String merchantPhone;

	public ActivePluginEntity() {
		this.pluginType = PluginType.ACTIVEPLUGIN;
	}

	public List<ActivePluginPrizeSettingEntity> getActivePluginPrizeSettings() {
		return activePluginPrizeSettings;
	}

	public void setActivePluginPrizeSettings(List<ActivePluginPrizeSettingEntity> activePluginPrizeSettings) {
		this.activePluginPrizeSettings = activePluginPrizeSettings;
	}

	public ActivePluginType getActivePluginType() {
		return activePluginType;
	}

	public void setActivePluginType(ActivePluginType activePluginType) {
		this.activePluginType = activePluginType;
	}

	public Integer getDrawTime() {
		return drawTime;
	}

	public void setDrawTime(Integer drawTime) {
		this.drawTime = drawTime;
	}

	public Integer getWinTime() {
		return winTime;
	}

	public void setWinTime(Integer winTime) {
		this.winTime = winTime;
	}

	public Integer getIntervalTime() {
		return intervalTime;
	}

	public void setIntervalTime(Integer intervalTime) {
		this.intervalTime = intervalTime;
	}

	public Integer getIntervalTimeType() {
		return intervalTimeType;
	}

	public void setIntervalTimeType(Integer intervalTimeType) {
		this.intervalTimeType = intervalTimeType;
	}

	public Map<String, Boolean> getPrizeCollectInfo() {
		return prizeCollectInfo;
	}

	public void setPrizeCollectInfo(Map<String, Boolean> prizeCollectInfo) {
		this.prizeCollectInfo = prizeCollectInfo;
	}

	public Map<String, Boolean> getPrizeCollectRequiredInfo() {
		return prizeCollectRequiredInfo;
	}

	public void setPrizeCollectRequiredInfo(Map<String, Boolean> prizeCollectRequiredInfo) {
		this.prizeCollectRequiredInfo = prizeCollectRequiredInfo;
	}

	public String getOuterUrl() {
		return outerUrl;
	}

	public void setOuterUrl(String outerUrl) {
		this.outerUrl = outerUrl;
	}

	public String getMerchantPhone() {
		return merchantPhone;
	}

	public void setMerchantPhone(String merchantPhone) {
		this.merchantPhone = merchantPhone;
	}

	public DrawPrizeResultEntity drawPrize(Map<Integer, DrawOptionEntity> options, Date canWinTim, Integer winTime) {
		DrawPrizeResultEntity entity = new DrawPrizeResultEntity();
		entity.setOptionId(0);
		entity.setOptionName("未中奖");
		if (canWinTim != null && !new Date().after(canWinTim)) {
			logger.info("中奖时间未到：" + canWinTim);
			return entity;
		}
		if (winTime >= this.winTime) {
			logger.info("超过中奖次数：" + winTime+",中奖次数："+this.winTime);
			return entity;
		}
		List<DrawPrizeData> drawDatas = new ArrayList<DrawPrizeData>();
		Integer currentMaxRang = 0;
		Iterator<Entry<Integer, DrawOptionEntity>> iterator = options.entrySet().iterator();
		// 初始化抽奖数据
		while (iterator.hasNext()) {
			DrawOptionEntity option = iterator.next().getValue();
			DrawPrizeData drawData = new DrawPrizeData();
			drawData.setOptionId(option.getOptionId());
			drawData.setPrizeRecordId(option.getPrizeRecordId());
			drawData.setOptionName(option.getOptionName());
			drawData.setStartRang(currentMaxRang + 1);
			drawData.setPrizeName(option.getPrizeName());
			currentMaxRang += option.getHeavy();
			drawData.setEndRang(currentMaxRang);
			drawDatas.add(drawData);
		}
		logger.info("[");
		for (DrawPrizeData drawPrizeData : drawDatas) {
			logger.info("奖项："+drawPrizeData.optionName);
			logger.info("奖品名称："+drawPrizeData.prizeName);
			logger.info("奖品id："+drawPrizeData.optionId);
		}
		logger.info("]");
		// 获取1到currentMaxRang内的随机数
		Random rand = new Random(System.currentTimeMillis());
		int random = rand.nextInt(100) + 1;
		for (DrawPrizeData data : drawDatas) {
			if (data.getStartRang() <= random && data.getEndRang() >= random) {
				// 检查库存是否足够
				logger.info("[");
				for (ActivePluginPrizeSettingEntity setting : this.getActivePluginPrizeSettings()) {
					logger.info("setting:"+setting.getRecordId());
					if (setting.getRecordId().equals(data.getOptionId())) {
						logger.info("匹配奖项设置:"+setting.getRecordId());
						if (setting.getAmount() <= 0) {
							logger.info("库存不足:"+setting.getAmount());
							logger.info("]");
							return entity;
						}
					}
				}
				logger.info("]");

				entity.setOptionId(data.getOptionId());
				entity.setOptionName(data.getOptionName());
				entity.setPrizeName(data.getPrizeName());
				logger.info("random:"+random+"start:"+data.getStartRang()+"start:"+data.getEndRang());
				break;
			}
		}

		return entity;
	}

	protected class DrawPrizeData {

		private Integer optionId;
		private Integer prizeRecordId;// 奖项记录ID，如果无奖项，则为null
		private String optionName;
		private String prizeName;
		private Integer startRang;// 大于等于该值
		private Integer endRang;// 小于等于该值

		public Integer getOptionId() {
			return optionId;
		}

		public void setOptionId(Integer optionId) {
			this.optionId = optionId;
		}

		public Integer getStartRang() {
			return startRang;
		}

		public void setStartRang(Integer startRang) {
			this.startRang = startRang;
		}

		public Integer getEndRang() {
			return endRang;
		}

		public void setEndRang(Integer endRang) {
			this.endRang = endRang;
		}

		public Integer getPrizeRecordId() {
			return prizeRecordId;
		}

		public void setPrizeRecordId(Integer prizeRecordId) {
			this.prizeRecordId = prizeRecordId;
		}

		public String getOptionName() {
			return optionName;
		}

		public void setOptionName(String optionName) {
			this.optionName = optionName;
		}

		public String getPrizeName() {
			return prizeName;
		}

		public void setPrizeName(String prizeName) {
			this.prizeName = prizeName;
		}

	}

}
