package com.dcits.ftp.dateUtils;

import com.dcits.ftp.exception.BusinessException ;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

	// 每一天的毫秒数
	private static final long MS_EVERY_DAY = 1000 * 60 * 60 * 24;
	// 默认的pattern
	private static final String PATTERN = "yyyyMMdd";

	public static final String PATTERN_TIME = "yyyy-MM-dd HH:mm:ss.S";

	public static final String PATTERN_TIME2 = "yyyy-MM-dd";

	public static final String PATTERN_TIME4 = "yyyyMMddHHmm";

	public static final String PATTERN_TIME5 = "yyyy-MM-dd HH:mm:ss";

	public static final String PATTERN_TIME6 = "yyyy-MM-dd HH:mm:ss,SSS";

	public static final String PATTERN_TIME7 = "yyyyMMddHHmmss";

	private static final String PATTERN_DATE = "yyyy-MM-dd";

	private static final String PATTERN_DATETIME = "yyyy-MM-dd HH:mm:ss";

	// 日期格式化
	private static SimpleDateFormat dateFormat;

	static {
		dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	}


	/**
	 * 由日期型转化为"yyyyMMdd"形式的String类型
	 * 
	 * @param date Date
	 * @return String
	 */
	public static String dateToString7(Date date) {

		SimpleDateFormat dateFmt = new SimpleDateFormat(PATTERN_TIME7);
		return dateFmt.format(date);
	}

	/**
	 * 由日期型转化为"yyyyMMdd"形式的String类型
	 *
	 * @param date Date
	 * @return String
	 */
	public static String dateToString(Date date) {

		SimpleDateFormat dateFmt = new SimpleDateFormat(PATTERN);
		return dateFmt.format(date);
	}

	
	/**
	 * 由日期型转化为"yyyyMMdd"形式的String类型
	 * @param date Date
	 * @param pattern String
	 * @return String
	 */
	public static String dateToString(Date date, String pattern) {

		SimpleDateFormat dateFmt = new SimpleDateFormat(pattern);
		return dateFmt.format(date);
	}

	public static String getNowDate() {
		SimpleDateFormat dateFmt = new SimpleDateFormat(PATTERN_DATE);
		return dateFmt.format(new Date());
	}

	public static String getNowDateTime() {
		SimpleDateFormat dateFmt = new SimpleDateFormat(PATTERN_DATETIME);
		return dateFmt.format(new Date());
	}


	/**
	 * 由String类型，转化为日期型
	 * 
	 * @param strDate String
	 * @return Date
	 * @throws BusinessException 基本异常
	 */
	public static Date stringToDate(String strDate) throws BusinessException {
		DateFormat df = new SimpleDateFormat(PATTERN);
		Date date = null;
		try {
			date = df.parse(strDate);
		} catch (ParseException e) {
			throw new BusinessException(e);
		}
		return date;
	}

	/**
	 * 由String类型，转化为日期型
	 *
	 * @param strDate String
	 * @return Date
	 * @throws BusinessException 基本异常
	 */
	public static Date stringToDatetime(String strDate) throws BusinessException {
		DateFormat df = new SimpleDateFormat(PATTERN_TIME);
		Date date = null;
		try {
			date = df.parse(strDate);
		} catch (ParseException e) {
			throw new BusinessException(e);
		}
		return date;
	}
	
	/**
	 * 由String类型，转化为日期型
	 * 
	 * @param strDate String
	 * @param pattern String
	 * @return Date
	 * @throws BusinessException 基本异常
	 */
	public static Date stringToDate(String strDate, String pattern) throws BusinessException {
		DateFormat df = new SimpleDateFormat(pattern);
		Date date = null;
		try {
			date = df.parse(strDate);
		} catch (ParseException e) {
			throw new BusinessException(e);
		}
		return date;
	}
	
	/**
	 * 
	 * 描述：字符串日期"yyyyMMdd"转成"yyyy-MM-dd"的Timestamp日期
	 * 
	 * @param strDate String
	 * @return Timestamp
	 * @throws BusinessException 基本异常
	 */
	public static Timestamp string2Timestamp(String strDate) throws BusinessException {
		String s = dateToString(stringToDate(strDate), PATTERN_TIME);
		Timestamp ts = Timestamp.valueOf(s);
		return ts;
	}

	/**
	 * 
	 * 描述：字符串日期"yyyyMMddHHmm"转成"yyyy-MM-dd HH:mm:ss.S"的Timestamp日期
	 * 
	 * @param strDate String
	 * @return Timestamp
	 * @throws BusinessException 基本异常
	 */
	public static Timestamp stringTime2Timestamp(String strDate) throws BusinessException {
		String s = dateToString(stringToDate(strDate, PATTERN_TIME7),
				PATTERN_TIME);
		Timestamp ts = Timestamp.valueOf(s);
		return ts;
	}
	
	/**
	 * 
	 * 描述：字符串日期"yyyyMMddHHmm"转成"yyyy-MM-dd HH:mm:ss.S"的Timestamp日期
	 * 
	 * @param strDate String
	 * @return Timestamp
	 * @throws BusinessException 基本异常
	 */
	public static Timestamp stringTime2Timestamp2(String strDate) throws BusinessException {
		String s = dateToString(stringToDate(strDate, PATTERN_TIME5),
				PATTERN_TIME);
		Timestamp ts = Timestamp.valueOf(s);
		return ts;
	}

	/**
	 * 
	 * 描述：date日期转成"yyyy-MM-dd HH:mm:ss"的Timestamp日期
	 * 
	 * @param date Date
	 * @return Timestamp
	 */
	public static Timestamp date2Timestamp(Date date) {
		String strDate = dateToString(date, PATTERN_TIME);
		Timestamp ts = Timestamp.valueOf(strDate);
		return ts;
	}

	/**
	 * 
	 * 描述：Timestamp日期转为字符型yyyyMMdd
	 * 
	 * @param timestamp Timestamp
	 * @return String
	 */
	public static String timestamp2String(Timestamp timestamp) {
		SimpleDateFormat df = new SimpleDateFormat(PATTERN);
		String str = df.format(timestamp);
		return str;
	}
	
	/**
	 * 
	 * 描述：Timestamp日期转为字符型yyyyMMdd
	 * 
	 * @param timestamp Timestamp
	 * @param pattern String
	 * @return String
	 */
	public static String timestamp2String(Timestamp timestamp, String pattern) {
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		String str = df.format(timestamp);
		return str;
	}
	
	/**
	 * 将10位的日期数据(yyyy-MM-dd)转换为8位的日期数据(yyyyMMdd)
	 * 
	 * @param date
	 *            10位的日期数据(yyyy-MM-dd)
	 * @return 8位的日期数据(yyyyMMdd)
	 */
	public static String convert10DateTo8Date(String date) {
		return date.replaceAll("-", "");
	}
	
	/**
	 * 将yyyy-mm-dd HH:mm:ss 转换为 yyyymmddhhmmss
	 * 
	 *
	 */
	public static String convertPattern5ToPattern7(String date) {
		String sj =  date.replaceAll("-", "").replaceAll(":", "").replaceAll(" ", "");
		return sj;
	}

    /**
     * 
     * 获取报表累计时间范围时，获取按日、旬、月、季度、年累计的起始日期
     * 时间：2015年6月24日 下午4:23:02
     * 作者：wangxhv
     * @param bbrq String
     * @param type String
     * @return Date
     */
	public static Date getDateBegin(String bbrq, String type) {
		
		Calendar calendar = Calendar.getInstance();
        calendar.setTime(DateUtil.stringToDate(bbrq));
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DATE);
	    if("day".equals(type)){
	    	calendar.set(Calendar.YEAR, year);
	    	calendar.set(Calendar.MONTH, month - 1);
	    	calendar.set(Calendar.DATE, day);
        } else if("tendays".equals(type)){
	    	calendar.set(Calendar.YEAR, year);
	    	calendar.set(Calendar.MONTH, month - 1);
	    	if(day <= 10){
            	calendar.set(Calendar.DATE, 1);
            }else if(day <= 20 && day > 10){
            	calendar.set(Calendar.DATE, 11);
            }else {
            	calendar.set(Calendar.DATE, 21);
            }
        } else if("month".equals(type)){
	    	calendar.set(Calendar.YEAR, year);
	    	calendar.set(Calendar.MONTH, month-1);
	    	calendar.set(Calendar.DATE, 1);
        } else if("quarter".equals(type)){

	    	calendar.set(Calendar.YEAR, year);
	    	if(month<=3){
		    	calendar.set(Calendar.MONTH, 0);
	    	}else if(month>3&&month<=6){
		    	calendar.set(Calendar.MONTH, 3);
	    	}else if(month>6&&month<=9){
		    	calendar.set(Calendar.MONTH, 6);
	    	}else if(month>9){
		    	calendar.set(Calendar.MONTH, 9);
	    	}
	    	calendar.set(Calendar.DATE, 1);
        } else if("year".equals(type)){
	    	calendar.set(Calendar.YEAR, year);
	    	calendar.set(Calendar.MONTH, 0);
	    	calendar.set(Calendar.DATE, 1);
        }
	    calendar.set(Calendar.HOUR, 0);
	    calendar.set(Calendar.MINUTE, 0);
	    calendar.set(Calendar.SECOND, 0);
	    calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
	}
	
	/**
	 * 获取date之后n天的日期（例如n=7，可以用于计算缴款期限）
	 * Simple To Introduction
	 * 时间：2015年6月24日 下午4:27:49
	 * 作者：wangxhv
	 * @param date Date
	 * @param n int
	 * @return Date
	 */
	public static Date getDateAfterN(Date date,int n){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		//得到7天后的日期（税票缴款期限）
        c.add(Calendar.DAY_OF_YEAR, n);
        return c.getTime();
	}
	
	/**
	 * 
	 * 获取上个月第一天0点（用于计算所属期起）
	 * 时间：2015年6月24日 下午4:28:29
	 * 作者：wangxhv
	 * @param date Date
	 * @return Date
	 */
	public static Date getLastMonthBegin(Date date){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		//获取上个月
	    c.add(Calendar.MONTH, -1);
	    c.set( c.get(Calendar.YEAR), c.get(Calendar.MONTH), 1, 0, 0, 0);
        return c.getTime();
	}
	
	/**
	 * 
	 * 获取上个月最后一天23:59:59（用于计算所属期止）
	 * 时间：2015年6月24日 下午4:29:56
	 * 作者：wangxhv
	 * @param date Date
	 * @return Date
	 */
	public static Date getLastMonthEnd(Date date){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		//获取上个月
	    c.add(Calendar.MONTH, -1);
	    //得到一个月最最后一天日期(31/30/29/28)  
	    int maxDay = c.getActualMaximum(Calendar.DAY_OF_MONTH);
	    c.set( c.get(Calendar.YEAR), c.get(Calendar.MONTH), maxDay, 23, 59, 59);
	    return c.getTime();
	}
	
	/**
	 * 
	 * 获取指定日期的前一天 
	 * 用于流水 
	 * 时间：2015年6月24日 下午8:17:46
	 * 作者：zhanghuiq
	 * @param date Date
	 * @return Date
	 */
	public static Date getDateBefore(Date date){
		Calendar calendar = Calendar.getInstance(); //得到日历
		calendar.setTime(date);//把当前时间赋给日历
		calendar.add(Calendar.DAY_OF_MONTH, -1);  //设置为前一天
		Date dateBefore = calendar.getTime();   //得到前一天的时间

		return  dateBefore;
	}

	/**
	 * 取date前第n天的Date
	 *
	 * @param date Date
	 * @param n int
	 * @return Date
	 */
	public static Date beforeDate(Date date, int n) {
		long day = n * MS_EVERY_DAY;
		Date d = new Date(date.getTime() - day);
		return d;
	}
	
	/**
	 * 
	 * 获取时间差（单位：毫秒）
	 * 时间：2015年6月25日 上午9:47:31
	 * 作者：wangsli
	 * @param endTime String
	 * @param startTime String
	 * @param pattern String
	 * @return long
	 */
	public static long compareTime(String endTime, String startTime, String pattern) {
		Date de = null;
		Date ds = null;
		SimpleDateFormat sf = new SimpleDateFormat(pattern);
		try {
			de = sf.parse(endTime);
			ds = sf.parse(startTime);
		} catch (ParseException e) {
			throw new BusinessException(e);
		}
		return de.getTime() - ds.getTime();
	}
	/**
	 * 获取当前时间，返回时间格式(如果调用参数为true时返回yyyy-MM-dd HH:mm:ss
	 * ，否则为false时返回yyyy-MM-DD不带日期格式)
	 *
	 * @param time
	 *            boolean
	 * @return String
	 *
	 */
	public static String getNowTime(boolean time) {
		Date now = new Date();
		String format = "";
		// yyyy-MM-dd HH:mm:ss:S 年月日时分秒毫杪
		if (time) {
			format = "yyyy-MM-dd";
		} else {
			format = "yyyy-MM-dd HH:mm:ss.s";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		String nowtime = sdf.format(now);
		return nowtime;
	}

	/**
	 *
	 * @return String
	 */
	public static String getNowTime(Date now) {
		//Date now = new Date();
		String format = "yyyyMMdd";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		String nowtime = sdf.format(now);
		return nowtime;
	}
	
}
