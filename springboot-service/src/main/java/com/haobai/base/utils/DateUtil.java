package com.haobai.base.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import com.haobai.base.constants.CommonConstants;

/**
 * 日期工具类。
 * 
 * @author Origin
 *
 */
public class DateUtil {

	/**
	 * 获取系统日期
	 * 
	 * @return
	 */
	public static Date getSystemDate() {
		return new Date();
	}

	/**
	 * 是否是正确的日期格式,默认日期格式是yyyy/MM/dd HH:mm:ss。
	 * 
	 * @param argDate
	 *            日期
	 * @param argFormat
	 *            日期格式
	 * @return
	 */
	public static boolean isDate(String argDate, String argFormat) {
		boolean bResult = true;
		if (argDate == null) {
			bResult = false;
		} else {
			String strDateFormat = argFormat;
			if ((argFormat == null) || (argFormat.isEmpty())) {
				strDateFormat = "yyyy-MM-dd HH:mm:ss";
			}
			try {
				SimpleDateFormat simpleDataFormat = new SimpleDateFormat(strDateFormat);
				// 严格限制日期转换
				// 例：防止把1996-2-31等错误日期是转换为1996-6-1
				simpleDataFormat.setLenient(false);

				Date dCheck = simpleDataFormat.parse(argDate);
				if (dCheck == null) {
					bResult = false;
					return bResult;
				}
				bResult = true;
			} catch (Exception e) {
				bResult = false;
			}
		}
		return bResult;
	}

	/**
	 * 把字符串类型的日期转换为日期类型的日期 。 例：yyyy/MM/dd HH:mm:ss yyyy/MM/dd
	 * 
	 * @param strDate
	 * @param format
	 * @return 格式为format的日期类型的日期
	 */
	public static Date convertStringToDate(String strDate, String format) {
		if ((StringUtils.isBlank(strDate)) || (StringUtils.isBlank(format)))
			// throw new IllegalArgumentException("日期要转换的格式不正确！");
			return null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat();
			sdf.setLenient(false);
			sdf.applyPattern(format);
			sdf.set2DigitYearStart(new Date(-32400000L));
			return sdf.parse(strDate);
		} catch (ParseException e) {
			throw new IllegalArgumentException(e);
		}
	}

	/**
	 * 日期转换成字符串。 例：yyyy/MM/dd HH:mm:ss yyyy/MM/dd
	 * 
	 * @param date
	 *            日期
	 * @param format
	 *            转换格式
	 * @return
	 */
	public static String convertDateToString(Date date, String format) {
		return convertDateToString(date, format, Locale.CHINESE);
	}

	/**
	 * 把日期转换成字符串。 例：yyyy/MM/dd HH:mm:ss yyyy/MM/dd
	 * 
	 * @param date
	 * @param format
	 * @param locale
	 * @return
	 */
	public static String convertDateToString(Date date, String format, Locale locale) {
		// 没有指定日期和格式
		if ((date == null) || (StringUtils.isBlank(format))) {
			return null;
		}

		// 没有指定本地化日期
		if (locale == null) {
			return null;
		}

		SimpleDateFormat sdf = new SimpleDateFormat(format, locale);
		sdf.setLenient(false);
		return sdf.format(date);
	}

	/**
	 * 获取参数日期的年。
	 * 
	 * @param date
	 * @return 年
	 */
	public static String getYear(Date date) {
		if (date == null) {
			return null;
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");

		return sdf.format(date);
	}

	/**
	 * 获取参数日期的月。
	 * 
	 * @param date
	 * @return 月
	 */
	public static String getMonth(Date date) {
		if (date == null) {
			return null;
		}

		SimpleDateFormat sdf = new SimpleDateFormat("MM");
		sdf.setLenient(false);
		return sdf.format(date);
	}

	public static String getHour(Date date) {
		if (date == null) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("HH");
		sdf.setLenient(false);
		return sdf.format(date);
	}

	/**
	 * 获取参数日期的日。去除07,06前的0
	 * 
	 * @param date
	 * @return 日
	 */
	public static String getDay(Date date) {
		if (date == null) {
			return null;
		}

		SimpleDateFormat sdf = new SimpleDateFormat("dd");
		sdf.setLenient(false);
		return sdf.format(date).replaceAll("^(0+)", "");
	}

	/**
	 * 比较baseDate是否小于targetDate。
	 * 
	 * @param baseDate
	 *            基础日期
	 * @param targetDate
	 *            对象日期
	 * @param field
	 *            比较级别 例：Calendar.MONTH 就是比较到月 Calendar.HOUR_OF_DAY 比较到时
	 *            Calendar.MINUTE 比较分 Calendar.SECOND 比较秒
	 * @return
	 */
	public static boolean isBefore(Date baseDate, Date targetDate, int field) {
		if (baseDate == null) {
			return (targetDate != null);
		}
		if (targetDate == null) {
			return false;
		}

		Date base = DateUtils.truncate(baseDate, field);

		Date target = DateUtils.truncate(targetDate, field);

		return base.before(target);
	}

	/**
	 * 比较baseDate是否小于targetDate。
	 * 
	 * @param baseDate
	 *            基础日期
	 * @param targetDate
	 *            对象日期
	 * @return
	 */
	public static boolean isBeforeByDate(Date baseDate, Date targetDate) {
		return isBefore(baseDate, targetDate, 5);
	}

	/**
	 * 比较baseDate是否小于等于targetDate。
	 * 
	 * @param baseDate
	 *            基础日期
	 * @param targetDate
	 *            对象日期
	 * @return
	 */
	public static boolean isBeforeIncludeTheDay(Date baseDate, Date targetDate) {
		if (baseDate == null) {
			return (targetDate != null);
		}

		if (targetDate == null) {
			return false;
		}

		Date base = DateUtils.truncate(baseDate, 5);

		Date target = DateUtils.truncate(targetDate, 5);

		if (base.compareTo(target) == 0) {
			return true;
		}

		return base.before(target);
	}

	/**
	 * 比较baseDate是否大于targetDate。
	 * 
	 * @param baseDate
	 *            基础日期
	 * @param targetDate
	 *            对象日期
	 * @param field
	 *            比较级别 例：Calendar.MONTH 就是比较到月 Calendar.HOUR_OF_DAY 比较到时
	 *            Calendar.MINUTE 比较分 Calendar.SECOND 比较秒
	 * @return
	 */
	public static boolean isAfter(Date baseDate, Date targetDate, int field) {
		return isBefore(targetDate, baseDate, field);
	}

	/**
	 * 比较baseDate是否大于targetDate。
	 * 
	 * @param baseDate
	 *            基础日期
	 * @param targetDate
	 *            对象日期
	 * @return
	 */
	public static boolean isAfterByDate(Date baseDate, Date targetDate) {
		return isAfter(baseDate, targetDate, 5);
	}

	/**
	 * 比较baseDate是否大于等于targetDate。
	 * 
	 * @param baseDate
	 *            基础日期
	 * @param targetDate
	 *            对象日期
	 * @return
	 */
	public static boolean isAfterIncludeTheDay(Date baseDate, Date targetDate) {
		if (targetDate == null) {
			return (baseDate != null);
		}

		if (baseDate == null) {
			return false;
		}

		Date base = DateUtils.truncate(baseDate, 5);

		Date target = DateUtils.truncate(targetDate, 5);

		if (base.compareTo(target) == 0) {
			return true;
		}

		return target.before(base);
	}

	/**
	 * 计算两个日期之间相差的月数
	 * 
	 * @param startDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return 月差数
	 */
	public static int getMonths(Date startDate, Date endDate) {
		int iMonth = 0;
		int flag = 0;
		if (startDate == null || endDate == null) {
			return iMonth;
		}
		try {
			Calendar objCalendarStartDate = Calendar.getInstance();
			objCalendarStartDate.setTime(startDate);

			Calendar objCalendarEndDate = Calendar.getInstance();
			objCalendarEndDate.setTime(endDate);

			if (objCalendarEndDate.equals(objCalendarStartDate)) {
				return 0;
			}
			if (objCalendarStartDate.after(objCalendarEndDate)) {
				Calendar temp = objCalendarStartDate;
				objCalendarStartDate = objCalendarEndDate;
				objCalendarEndDate = temp;
			}
			if (objCalendarEndDate.get(Calendar.DAY_OF_MONTH) < objCalendarStartDate.get(Calendar.DAY_OF_MONTH)) {
				flag = 1;
			}

			if (objCalendarEndDate.get(Calendar.YEAR) > objCalendarStartDate.get(Calendar.YEAR)) {
				iMonth = ((objCalendarEndDate.get(Calendar.YEAR) - objCalendarStartDate.get(Calendar.YEAR)) * 12
						+ objCalendarEndDate.get(Calendar.MONTH) - flag) - objCalendarStartDate.get(Calendar.MONTH);
			} else {
				iMonth = objCalendarEndDate.get(Calendar.MONTH) - objCalendarStartDate.get(Calendar.MONTH) - flag;
			}
		} catch (Exception e) {
			return iMonth;
		}
		return iMonth;
	}

	/**
	 * 计算两个日期之间相差的日数
	 * 
	 * @param startDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return 日差数
	 */
	public static int getDays(Date startDate, Date endDate) {
		int iDay = 0;
		if (startDate == null || endDate == null) {
			return iDay;
		}
		try {
			startDate = convertStringToDate(convertDateToString(startDate, CommonConstants.YYYY_MM_DD),
					CommonConstants.YYYY_MM_DD);
			endDate = convertStringToDate(convertDateToString(endDate, CommonConstants.YYYY_MM_DD),
					CommonConstants.YYYY_MM_DD);
			if (startDate.equals(endDate)) {
				return iDay;
			}
			if (startDate.after(endDate)) {
				// Date temp = startDate;
				// startDate = endDate;
				// endDate = temp;
				return iDay;
			}
			Long startDateLong = startDate.getTime();
			Long endDateLong = endDate.getTime();
			Long dayLong = endDateLong - startDateLong;
			// 计算成日
			dayLong = dayLong / 1000 / 60 / 60 / 24;
			iDay = dayLong.intValue();
		} catch (Exception e) {
			return iDay;
		}
		return iDay;
	}

	/**
	 * 计算两个日期之间相差的相差小时
	 * 
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @return 日差数
	 */
	public static int getHours(Date startTime, Date endTime) {
		int iHour = 0;
		if (startTime == null || endTime == null) {
			return iHour;
		}
		try {
			startTime = convertStringToDate(convertDateToString(startTime, CommonConstants.YYYY_MM_DD_HH),
					CommonConstants.YYYY_MM_DD_HH);
			endTime = convertStringToDate(convertDateToString(endTime, CommonConstants.YYYY_MM_DD_HH),
					CommonConstants.YYYY_MM_DD_HH);
			if (startTime.equals(endTime)) {
				return iHour;
			}
			if (startTime.after(endTime)) {
				Date temp = startTime;
				startTime = endTime;
				endTime = temp;
			}
			Long startDateLong = startTime.getTime();
			Long endDateLong = endTime.getTime();
			Long dayLong = endDateLong - startDateLong;
			// 计算成小时
			Long hourLong = dayLong / 1000 / 60 / 60;
			iHour = hourLong.intValue();
		} catch (Exception e) {
			return iHour;
		}
		return iHour;
	}

	/**
	 * 计算两个日期之间相差的相差小时
	 * 
	 * @param startDate
	 *            开始时间
	 * @param endDate
	 *            结束时间
	 * @return 日差数
	 */
	public static int getMinutes(Date startTime, Date endTime) {
		int iMinutes = 0;
		if (startTime == null || endTime == null) {
			return iMinutes;
		}
		try {
			startTime = convertStringToDate(convertDateToString(startTime, CommonConstants.YYYY_MM_DD_HH_MM),
					CommonConstants.YYYY_MM_DD_HH_MM);
			endTime = convertStringToDate(convertDateToString(endTime, CommonConstants.YYYY_MM_DD_HH_MM),
					CommonConstants.YYYY_MM_DD_HH_MM);
			if (startTime.equals(endTime)) {
				return iMinutes;
			}
			if (startTime.after(endTime)) {
				Date temp = startTime;
				startTime = endTime;
				endTime = temp;
			}
			Long startDateLong = startTime.getTime();
			Long endDateLong = endTime.getTime();
			Long dayLong = endDateLong - startDateLong;
			// 计算成分
			Long hourLong = dayLong / 1000 / 60;
			iMinutes = hourLong.intValue();
		} catch (Exception e) {
			return iMinutes;
		}
		return iMinutes;
	}

	/**
	 * 获取该日期所在月第一天的日期
	 * 
	 * @Date : 2014-5-27
	 * @param date
	 * @return
	 */
	public static Date getFirstDayOfDayMonth(Date date) {
		int m = date.getMonth();
		int y = date.getYear();
		Date firstDay = new Date(y, m, 1);
		return firstDay;
	}

	/**
	 * 获取该日期所在月最后天的日期
	 * 
	 * @Date : 2014-5-27
	 * @param date
	 * @return
	 */
	public static Date getLastDayOfDayMonth(Date date) {
		int m = date.getMonth();
		int y = date.getYear();
		Date nextMonthFirstDay = new Date(y, m + 1, 1);
		int min = 24 * 60 * 60 * 1000;
		Date toDay = new Date(nextMonthFirstDay.getTime() - min);
		return toDay;
	}

	/**
	 * 获取参数 日期 所在月天数
	 * 
	 * @Date : 2014-5-27
	 * @param date
	 * @return
	 */
	public static int getdayNumInMonthByDay(Date date) {
		Calendar cal = new GregorianCalendar();
		// 或者用Calendar cal = Calendar.getInstance();

		/** 设置date **/
		SimpleDateFormat oSdf = new SimpleDateFormat("", Locale.ENGLISH);
		oSdf.applyPattern("yyyyMM");

		/** 或者设置月份，注意月是从0开始计数的，所以用实际的月份-1才是你要的月份 **/
		// 一月份: cal.set( 2009, 1-1, 1 );

		/** 如果要获取上个月的 **/
		// cal.set(Calendar.DAY_OF_MONTH, 1);
		// 日期减一,取得上月最后一天时间对象
		// cal.add(Calendar.DAY_OF_MONTH, -1);
		// 输出上月最后一天日期
		/** 开始用的这个方法获取月的最大天数，总是得到是31天 **/
		// int num = cal.getMaximum(Calendar.DAY_OF_MONTH);
		/** 开始用的这个方法获取实际月的最大天数 **/
		int num2 = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		return num2;
	}

	/**
	 * 获得参数日期对应周六日期
	 * 
	 * @Date : 2014-5-27
	 * @param date
	 * @return
	 */
	public static Date getDateWeekSaturday(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		cal.add(Calendar.DAY_OF_MONTH, -1); // 解决周日会出现 并到下一周的情况
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);

		return cal.getTime();
	}

	/**
	 * 获得参数日期对应周日日期
	 * 
	 * @Date : 2014-5-27
	 * @param date
	 * @return
	 */
	public static Date getDateWeekSunday(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		cal.add(Calendar.DAY_OF_MONTH, 0); // 解决周日会出现 并到下一周的情况
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);

		return cal.getTime();
	}

	/**
	 * 获得参数日期对应周一日期
	 * 
	 * @Date : 2014-5-27
	 * @param date
	 * @return
	 */
	public static Date getDateWeekMonday(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		cal.add(Calendar.DAY_OF_MONTH, 0); // 解决周日会出现 并到下一周的情况
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

		return cal.getTime();
	}

	/**
	 * 获得参数日期对应周几,周一到周日对应0-6
	 * 
	 * @Date : 2014-5-27
	 * @param date
	 * @return
	 */
	public static int getDateWeekDay(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int dayForWeek = 0;
		dayForWeek = cal.get(Calendar.DAY_OF_WEEK) - 2;
		if (dayForWeek == -1) {
			dayForWeek = 6;
		}
		return dayForWeek;
	}

	/**
	 * 获得参数日期对应小时
	 * 
	 * @Date : 2014-5-28
	 * @param date
	 * @return
	 */
	public static int getDateHour(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int i = cal.get(Calendar.HOUR_OF_DAY);
		return cal.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * 得到某个日期的后一天日期
	 * 
	 * @param d
	 * @return
	 */
	public static Date getAfterDate(Date d) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(d);
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		d = calendar.getTime();
		return d;
	}

	/**
	 * 得到某个日期的前一天日期
	 * 
	 * @param d
	 * @return
	 */
	public static Date getBeforeDate(Date d) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(d);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		d = calendar.getTime();
		return d;
	}

	/**
	 * 得到某个日期的下一周这天日期
	 * 
	 * @param d
	 * @return
	 */
	public static Date getAfterSevenDate(Date d) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(d);
		calendar.add(Calendar.DAY_OF_MONTH, 7);
		d = calendar.getTime();
		return d;
	}

	/**
	 * 得到某个日期的六天后的日期
	 * 
	 * @param d
	 * @return
	 */
	public static Date getAfterSixDate(Date d) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(d);
		calendar.add(Calendar.DAY_OF_MONTH, 6);
		d = calendar.getTime();
		return d;
	}

	/**
	 * 判断当前日期是星期几
	 * 
	 * @param pTime
	 *            修要判断的时间
	 * @return dayForWeek 判断结果
	 * @Exception 发生异常
	 */
	public static int dayForWeek(String date) {
		SimpleDateFormat format = new SimpleDateFormat(CommonConstants.YYYY_MM_DD);
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(format.parse(date));
		} catch (ParseException e) {
			return 0;
		}
		int dayForWeek = 0;
		if (c.get(Calendar.DAY_OF_WEEK) == 1) {
			dayForWeek = 7;
		} else {
			dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
		}
		return dayForWeek;
	}

	/**
	 * 加上指定参数的年数
	 * 
	 * @Author : Origin
	 * @Date : 2014-6-5
	 * @param date
	 *            日期
	 * @param year
	 *            年数
	 * @return 返回加上后年数后的日期
	 */
	public static Date addYear(Date date, int year) {
		if (date == null) {
			return null;
		}

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.YEAR, year);
		return cal.getTime();
	}

	/**
	 * 加上指定参数的月数
	 * 
	 * @Author : Origin
	 * @Date : 2014-6-5
	 * @param date
	 *            日期
	 * @param month
	 *            月数
	 * @return 返回加上后月数后的日期
	 */
	public static Date addMonth(Date date, int month) {
		if (date == null) {
			return null;
		}

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, month);

		return cal.getTime();
	}

	/**
	 * 加上指定参数的日数
	 * 
	 * @Author : Origin
	 * @Date : 2014-6-5
	 * @param date
	 *            日期
	 * @param day
	 *            日数
	 * @return 返回加上后日数后的日期
	 */
	public static Date addDay(Date date, int day) {
		if (date == null) {
			return null;
		}

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_MONTH, day);
		return cal.getTime();
	}

	/**
	 * 加上指定参数的日数
	 * 
	 * @Author : Origin
	 * @Date : 2014-6-5
	 * @param date
	 *            日期
	 * @param minutes
	 *            分
	 * @return 返回加上后日数后的日期
	 */
	public static Date addMinutes(Date date, int minutes) {
		if (date == null) {
			return null;
		}

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MINUTE, minutes);

		return cal.getTime();
	}

	/**
	 * 得到几天前的时间
	 * 
	 * @param d
	 * @param day
	 * @return
	 */
	public static Date getDateBefore(Date d, int day) {
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
		return now.getTime();
	}

	// 格林威治时间戳转当前时间
	public static String getCurrentStringFromGMTStamp(long mTime) {
		mTime = mTime * 1000L;
		int offset = Calendar.getInstance().getTimeZone().getRawOffset();
		SimpleDateFormat fmt4 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar c = Calendar.getInstance();
		c.setTime(new Date(mTime + offset));
		String localDate = fmt4.format(c.getTime());
		return localDate;
	}

	/**
	 * 时间戳转换当前时间
	 * 
	 * @Author : priceless
	 * @Date : 2014-8-8
	 * @param mill
	 * @return
	 */
	public static String convertLongTimeToDate(long second, String format) {
		Date date = new Date(second * 1000);
		String strs = "";
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			strs = sdf.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strs;
	}

	public static Date convertStringTimeToDateTime(String time) {
		try {
			SimpleDateFormat dateformat1 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			String a1 = dateformat1.format(new Date());
			return dateformat1.parse(dateformat1.format(DateUtil.convertStringToDate(time, "yyyy/MM/dd HH:mm:ss")));
		} catch (ParseException e) {
			return null;
		}

	}

	public static Date convertStringTimeToDateTime2(String time) {
		try {
			SimpleDateFormat dateformat1 = new SimpleDateFormat("yyyy/MM/dd HH:mm");
			String a1 = dateformat1.format(new Date());
			return dateformat1.parse(dateformat1.format(DateUtil.convertStringToDate(time, "yyyy/MM/dd HH:mm")));
		} catch (ParseException e) {
			return null;
		}

	}

	public static int daysBetween(String smdate, String bdate) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(sdf.parse(smdate));
		long time1 = cal.getTimeInMillis();
		cal.setTime(sdf.parse(bdate));
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);

		return Integer.parseInt(String.valueOf(between_days));
	}

	// public static void main(String[] args) throws ParseException {
	// // TODO Auto-generated method stub
	// String d1="2014/09/08";
	// String d2=getNowSystemDate();
	// }

	public static String getNowSystemDate() throws ParseException {
		Calendar now = Calendar.getInstance();
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String dateNowStr = sdf.format(d);
		return dateNowStr;

	}

	public static String getNowSystemTime() throws ParseException {
		Calendar now = Calendar.getInstance();
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		String dateNowStr = sdf.format(d);
		return dateNowStr;

	}

	// public static void main(String args[]){
	// int a = dayForWeekDay("2014/11/15");
	// }

	/**
	 * 判断当前日期是星期几
	 * 
	 * @param pTime
	 *            修要判断的时间
	 * @return dayForWeek 判断结果
	 * @Exception 发生异常
	 */
	public static int dayForWeekDay(String date) {
		SimpleDateFormat format = new SimpleDateFormat(CommonConstants.STANDARD_FORMAT_DATE);
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(format.parse(date));
		} catch (ParseException e) {
			return 0;
		}
		int dayForWeek = 0;
		if (c.get(Calendar.DAY_OF_WEEK) == 1) {
			dayForWeek = 7;
		} else {
			dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
		}
		return dayForWeek;
	}

	/**
	 * 判断当前日期是星期几
	 * 
	 * @param pTime
	 *            修要判断的时间
	 * @return dayForWeek 判断结果
	 * @Exception 发生异常
	 */
	public static int dayWeekDay(String date) {
		SimpleDateFormat format = new SimpleDateFormat(CommonConstants.YYYY_MM_DD);
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(format.parse(date));
		} catch (ParseException e) {
			return 0;
		}
		int dayForWeek = 0;
		if (c.get(Calendar.DAY_OF_WEEK) == 1) {
			dayForWeek = 7;
		} else {
			dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
		}
		return dayForWeek;
	}
	public static void main(String[] args) throws ParseException {
		System.out.println(DateUtil.getNowSystemTime());
	}
}
