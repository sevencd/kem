package cn.ilanhai.kem.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import cn.ilanhai.framework.common.exception.BlAppException;
import cn.ilanhai.framework.uitl.Str;

public class TimeUtil {
	public static SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	/**
	 * 这种2017年03月23日格式的日期
	 */
	public static final SimpleDateFormat YYYY_MM_DD = new SimpleDateFormat("yyyy年MM月dd日");

	public static String format(Object obj) {
		if (obj == null) {
			return "";
		}
		return time.format(obj);
	}

	/**
	 * 取得某日期时间的特定表示格式的字符串
	 * 
	 * @param format
	 *            时间格式
	 * @param date
	 *            某日期（Date）
	 * @return 某日期的字符串
	 */
	public static synchronized String format(Date date, DateFormat format) {
		return format.format(date);
	}

	public static Date getDate(String str) throws BlAppException {
		if (Str.isNullOrEmpty(str)) {
			return null;
		}
		try {
			return time.parse(str);
		} catch (ParseException e) {
			throw new BlAppException(-1, "时间格式错误");
		}
	}

	public static int getDays(Date startdate, Date enddate) {

		Calendar calendar = Calendar.getInstance();

		calendar.setTime(startdate);
		long mintime = calendar.getTimeInMillis();
		calendar.setTime(enddate);
		long maxtime = calendar.getTimeInMillis();

		return Integer.parseInt(String.valueOf((maxtime - mintime) / (1000 * 3600 * 24)));
	}

	public static int getMonthSpace(Date date1, Date date2) {
		int result = 0;
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(date1);
		c2.setTime(date2);
		result = c2.get(Calendar.MONDAY) - c1.get(Calendar.MONTH);
		return result == 0 ? 1 : Math.abs(result);
	}

	public static int getYearSpace(Date date1, Date date2) {
		int result = 0;
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(date1);
		c2.setTime(date2);
		result = c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR);
		return result == 0 ? 1 : Math.abs(result);
	}

	/**
	 * 计算几天后的时间
	 * 
	 * @param starttime
	 *            开始时间
	 * @param quantity
	 *            天数
	 * @return
	 */
	public static Date calculateEndtime(Date starttime, int quantity) {
		Calendar now = Calendar.getInstance();
		now.setTime(starttime);
		now.set(Calendar.DATE, now.get(Calendar.DATE) + quantity);
		return now.getTime();
	}

}
