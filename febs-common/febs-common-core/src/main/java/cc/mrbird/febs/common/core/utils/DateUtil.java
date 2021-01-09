package cc.mrbird.febs.common.core.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 时间工具类
 *
 * @author MrBird
 */
public class DateUtil {

    public static final String FULL_TIME_PATTERN = "yyyyMMddHHmmss";

    public static final String FULL_TIME_SPLIT_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static final String CST_TIME_PATTERN = "EEE MMM dd HH:mm:ss zzz yyyy";

    public static final String FULL_TIME_SPLIT_PATTERN_4 = "HH:mm:ss";
    /**
     * 格式化时间，格式为 yyyyMMddHHmmss
     *
     * @param localDateTime LocalDateTime
     * @return 格式化后的字符串
     */
    public static String formatFullTime(LocalDateTime localDateTime) {
        return formatFullTime(localDateTime, FULL_TIME_PATTERN);
    }

    /**
     * 根据传入的格式，格式化时间
     *
     * @param localDateTime LocalDateTime
     * @param format        格式
     * @return 格式化后的字符串
     */
    public static String formatFullTime(LocalDateTime localDateTime, String format) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
        return localDateTime.format(dateTimeFormatter);
    }

    /**
     * 根据传入的格式，格式化时间
     *
     * @param date   Date
     * @param format 格式
     * @return 格式化后的字符串
     */
    public static String getDateFormat(Date date, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.CHINA);
        return simpleDateFormat.format(date);
    }

    /**
     * 格式化 CST类型的时间字符串
     *
     * @param date   CST类型的时间字符串
     * @param format 格式
     * @return 格式化后的字符串
     * @throws ParseException 异常
     */
    public static String formatCstTime(String date, String format) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(CST_TIME_PATTERN, Locale.US);
        Date usDate = simpleDateFormat.parse(date);
        return getDateFormat(usDate, format);
    }

    /**
     * 格式化 Instant
     *
     * @param instant Instant
     * @param format  格式
     * @return 格式化后的字符串
     */
    public static String formatInstant(Instant instant, String format) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        return localDateTime.format(DateTimeFormatter.ofPattern(format));
    }



    public static Date getDateParse(String date) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(FULL_TIME_SPLIT_PATTERN, Locale.CHINA);
        return simpleDateFormat.parse(date);
    }
    public static Date getDateParse(String date, String format) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.CHINA);
        return simpleDateFormat.parse(date);
    }
    /**
     * 判断当前时间是否在指定时间范围
     *
     * @param from 开始时间
     * @param to   结束时间
     * @return 结果
     */
    public static boolean between(LocalTime from, LocalTime to) {
        LocalTime now = LocalTime.now();
        return now.isAfter(from) && now.isBefore(to);
    }


    public static Date getMonthBeginPar(String dateStr) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM", Locale.CHINA);
        Date dateParse = format.parse(dateStr);
        Calendar calendar = Calendar.getInstance();// 获取当前日期
        calendar.setTime(dateParse);
        calendar.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Long time = calendar.getTimeInMillis();
        return calendar.getTime();
    }

    public static Date getMonthEndPar(String dateStr) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM", Locale.CHINA);
        Date dateParse = format.parse(dateStr);
        Calendar calendar = Calendar.getInstance();// 获取当前日期
        calendar.setTime(dateParse);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime();
    }

    public static Date restTimePing(String workDateStr, Date restTime, int across) throws ParseException {
        SimpleDateFormat sdf4 = new SimpleDateFormat(FULL_TIME_SPLIT_PATTERN_4);
        String formatRestTime = sdf4.format(restTime);
        SimpleDateFormat sdf = new SimpleDateFormat(FULL_TIME_SPLIT_PATTERN);
        Date workDate = sdf.parse(workDateStr + " " + formatRestTime);
        Calendar cal = Calendar.getInstance();
        cal.setTime(workDate);
        cal.add(Calendar.HOUR_OF_DAY, across);
        return cal.getTime();
    }

    public static Date restTimePing(Date workDate, Date restTime, int across) throws ParseException {
        Calendar calWorkDate = Calendar.getInstance();
        calWorkDate.setTime(workDate);
        Calendar cal = Calendar.getInstance();
        cal.setTime(restTime);

        calWorkDate.set(Calendar.HOUR_OF_DAY,cal.get(Calendar.HOUR_OF_DAY));
        calWorkDate.set(Calendar.MINUTE,cal.get(Calendar.MINUTE));
        calWorkDate.set(Calendar.SECOND,cal.get(Calendar.SECOND));
        calWorkDate.add(Calendar.HOUR_OF_DAY, across);
        return calWorkDate.getTime();
    }
}
