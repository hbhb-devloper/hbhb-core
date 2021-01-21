package com.hbhb.core.utils;

import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static java.util.Calendar.getInstance;

/**
 * Created by baiqian@myweimai.com on 2016/11/8.
 */
public class DateUtil {

    private DateUtil() {
    }

    public static final String FORMAT_PATTERN_COMM = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT_PATTERN_yyyy_MM_dd = "yyyy-MM-dd";
    public static final String FORMAT_PATTERN_yyyy = "yyyy";
    public static final String FORMAT_PATTERN_yyyyMM = "yyyyMM";
    public static final String FORMAT_PATTERN_yyyyMMdd = "yyyy/MM/dd";


    /**
     * 获取当前日期
     *
     * @return
     */
    public static String getCurrentDate() {
        return getCurrentDate(FORMAT_PATTERN_COMM);
    }

    public static String getCurrentMonth() {
        return getCurrentDate(FORMAT_PATTERN_yyyyMM);
    }

    public static String getCurrentYear() {
        return getCurrentDate(FORMAT_PATTERN_yyyy);
    }

    public static String getCurrentDate(String formatPattern) {
        SimpleDateFormat format = new SimpleDateFormat(formatPattern);
        return format.format(new Date());
    }


    /**
     * 根据完整的日期字符串截取只有年月日的日期字符串
     *
     * @param str
     * @return
     */
    public static String getCutYMDDate(String str) {
        if (null == str || "".equals(str)) {
            return "";
        }
        Date date = stringToDate(str);
        SimpleDateFormat format = new SimpleDateFormat(FORMAT_PATTERN_yyyy_MM_dd);
        return format.format(date);
    }

    /**
     * 计算两个日期相隔多少月
     *
     * @param lowDate
     * @param bigDate
     * @return
     * @throws ParseException
     */
    public static int monthBetween(String lowDate, String bigDate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_PATTERN_yyyyMMdd);
        Calendar bef = Calendar.getInstance();
        Calendar aft = Calendar.getInstance();
        bef.setTime(sdf.parse(lowDate));
        aft.setTime(sdf.parse(bigDate));
        int surplus = aft.get(Calendar.DATE) - bef.get(Calendar.DATE);
        int result = aft.get(Calendar.MONTH) - bef.get(Calendar.MONTH);
        int month = (aft.get(Calendar.YEAR) - bef.get(Calendar.YEAR)) * 12;
        surplus = surplus <= 0 ? 1 : 0;
        Integer betweenMonth = (Math.abs(month + result) + surplus);
        return Integer.parseInt(String.valueOf(betweenMonth));
    }

    public static String dateToStringYmd(Date date) {
        return dateToString(date, FORMAT_PATTERN_yyyyMMdd);
    }

    /**
     * 计算两个日期相隔多少年
     *
     * @param lowDate
     * @param bigDate
     * @return
     * @throws ParseException
     */
    public static int yearBetween(String lowDate, String bigDate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_PATTERN_COMM);
        Calendar cal = Calendar.getInstance();
        cal.setTime(sdf.parse(lowDate));
        long time1 = cal.getTimeInMillis();
        cal.setTime(sdf.parse(bigDate));
        long time2 = cal.getTimeInMillis();
        long oneYearTime = 1000L * 3600L * 24L * 365L;
        long betweenYear = (time2 - time1) / oneYearTime;
        return Integer.parseInt(String.valueOf(betweenYear));
    }


    /**
     * 根据end时间字符串与当前时间相比,是否已过期
     *
     * @param end
     * @return
     */
    public static boolean isExpired(String end) {
        Date currTime = new Date();
        Date endTime = stringToDate(end);
        return currTime.getTime() > endTime.getTime();
    }

    /**
     * 根据开始时间和结束时间,比较开始时间是否已过期
     *
     * @param startTime: 开始时间
     * @param endTime:   结束时间
     * @return true: 已过期, false: 未过期
     */
    public static boolean isExpired(String startTime, String endTime) {
        Date stime = stringToDate(startTime);
        Date etime = stringToDate(endTime);
        return stime.getTime() > etime.getTime();
    }

    /**
     * 盘都某个时间是否在开始和结束时间的区间内如果在则未过期，不再则过期
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param time 需要比较的时间
     * @return true 未过期 false 过期
     */
    public static boolean isExpired(String startTime, String endTime, String time) {
        Date stime = stringToDate(startTime);
        Date etime = stringToDate(endTime);
        Date date = stringToDate(time);
        return date.getTime() > stime.getTime() && date.getTime() < etime.getTime();
    }

    public static boolean isExpiredYMD(String startTime, String endTime) {
        Date stime = string2DateYMD(startTime);
        Date etime = string2DateYMD(endTime);
        return stime.getTime() > etime.getTime();
    }

    /**
     * 通过间隔时间,使用startTime和当前时间比较是否已过期
     *
     * @param startTime 比较时间
     * @param minute    间隔时间
     * @return true: 已过去 false: 未过期
     */
    public static boolean isExpired(long startTime, int minute) {
        long currTime = System.currentTimeMillis();
        return (currTime - startTime) > minute * 60 * 1000;
    }

    /**
     * 根据开始时间和结束时间,计算出两者相差时间,然后和允许的间隔时间相比, 如果差值大于interval则为过期, 反之未过期
     *
     * @param startTime: 开始时间
     * @param endTime:   结束时间
     * @param interval:  间隔时间,单位为秒s
     * @return true: 已过期, false: 未过期
     */
    public static boolean isExpired(String startTime, String endTime, long interval) {
        if (StringUtils.isEmpty(startTime) || StringUtils.isEmpty(endTime)) {
            return true;
        }
        Date stime = stringToDate(startTime);
        Date etime = stringToDate(endTime);
        interval = interval * 1000;
        long diff = etime.getTime() - stime.getTime();
        if (diff < 0) {
            return true;
        }
        return diff - interval > 0;
    }

    /**
     * 日期转换成字符串
     *
     * @param date
     * @return
     */
    public static String dateToString(Date date) {
        return dateToString(date, FORMAT_PATTERN_COMM);
    }

    public static String dateToStringY(Date date) {
        return dateToString(date, FORMAT_PATTERN_yyyy);
    }


    /**
     * 日期转换成字符串，指定时间格式
     *
     * @param date
     * @return
     */
    public static String dateToString(Date date, String format) {
        if (date == null) {
            return "";
        }
        return formatDate(date, format);
    }

    /**
     * 字符串转换日期
     *
     * @param str
     * @return
     */
    public static Date stringToDate(String str) {
        //str =  " 2016-03-10 19:20:00 " 格式
        SimpleDateFormat format = new SimpleDateFormat(FORMAT_PATTERN_COMM);
        if (str != null && !"".equals(str)) {
            try {
                return format.parse(str);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 获取上一个月
     *
     * @return
     */
    public static String getLastMonth() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        // 设置为当前时间
        calendar.setTime(date);
        // 设置为上一个月
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1);
        date = calendar.getTime();
        return format.format(date);
    }


    public static Date string2DateYMD(String str) {
        //str =  " 2016-03-10" 格式
        SimpleDateFormat format = new SimpleDateFormat(FORMAT_PATTERN_yyyy_MM_dd);
        if (str != null && !"".equals(str)) {
            try {
                return format.parse(str);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static Date string3DateYMD(String str) {
        //str =  " 2016-03-10" 格式
        SimpleDateFormat format = new SimpleDateFormat(FORMAT_PATTERN_yyyyMMdd);
        if (str != null && !"".equals(str)) {
            try {
                return format.parse(str);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    public static Date string2DateY(String str) {
        //str =  " 2016" 格式
        SimpleDateFormat format = new SimpleDateFormat(FORMAT_PATTERN_yyyy);
        if (str != null && !"".equals(str)) {
            try {
                return format.parse(str);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 字符串转换日期
     *
     * @param str
     * @return
     */
    public static Date stringToDate(String str, String formatStr) {
        if (str != null && !"".equals(str) && !StringUtils.isEmpty(formatStr)) {
            try {
                SimpleDateFormat format = new SimpleDateFormat(formatStr);
                return format.parse(str);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    /**
     * 格式化为指定格式的时间字符串
     *
     * @param date
     * @param format
     * @return
     */
    public static String formatDate(Date date, String format) {
        String result = "";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        if (date != null) {
            result = sdf.format(date);
        }
        return result;
    }

    public static Date formatString(String str, String format) {
        if (null == str || "".equals(str)) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            return sdf.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 得到去年时间
     */
    public static String getLastYear() {
        Calendar dateYear = getInstance();
        dateYear.setTime(new Date());
        dateYear.add(Calendar.YEAR, -1);
        Date time = dateYear.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy");
        return format.format(time);
    }

    /**
     * 判断时间是否在时间段内
     *
     * @param nowTime
     * @param beginTime
     * @param endTime
     * @return
     */
    public static boolean belongCalendar(Date nowTime, Date beginTime, Date endTime) {
        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);

        Calendar begin = Calendar.getInstance();
        begin.setTime(beginTime);

        Calendar end = Calendar.getInstance();
        end.setTime(endTime);

        return date.after(begin) && date.before(end);
    }


}

