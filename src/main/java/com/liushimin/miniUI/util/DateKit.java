package com.liushimin.miniUI.util;

import org.apache.commons.collections.CollectionUtils;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

/**
 * 日期工具类
 * 
 * @author shiwp
 * 
 * @see org.apache.commons.lang3.time.DateUtils
 *
 * 
 */
public final class DateKit {

//    private static final Logger log = LoggerFactory.getLogger(DateKit.class);

    // ============================ 日期格式 ============================

    /** 标准日期格式：yyyy-MM-dd */
    public static final String DATE = "yyyy-MM-dd";

    /** 标准日期格式：yyyyMMdd */
    public static final String DATE_THIN = "yyyyMMdd";

    /** 标准日期格式：yyyy年MM月dd日 */
    public static final String DATE_CN = "yyyy年MM月dd日";
    
    /** 标准日期格式：yyyy/MM/dd */
    public static final String DATE_ROD = "yyyy/MM/dd";

    /** 标准时间格式：HH:mm:ss */
    public static final String TIME = "HH:mm:ss";

    /** 标准时间格式：HHmmss */
    public static final String TIME_THIN = "HHmmss";

    /** 标准日期时间格式，精确到秒：yyyy-MM-dd HH:mm:ss */
    public static final String DATETIME = "yyyy-MM-dd HH:mm:ss";

    /** 标准日期时间格式，精确到秒：yyyyMMddHHmmss */
    public static final String DATETIME_THIN = "yyyyMMddHHmmss";

    /** 标准日期时间格式，精确到秒：yyyy年MM月dd日 HH:mm:ss */
    public static final String DATETIME_CN = "yyyy年MM月dd日 HH:mm:ss";

    /** 标准日期时间格式，精确到毫秒：yyyy-MM-dd HH:mm:ss.SSS */
    public static final String MILLIS = "yyyy-MM-dd HH:mm:ss.SSS";

    /** 标准日期时间格式，精确到毫秒：yyyyMMddHHmmssSSS */
    public static final String MILLIS_THIN = "yyyyMMddHHmmssSSS";

    /** 标准月份格式，精确到月：yyyy-MM */
    public static final String MONTH = "yyyy-MM";

    /** 标准月份格式，精确到月：yyyyMM */
    public static final String MONTH_THIN = "yyyyMM";

    /** 标准月份格式，精确到月：yyyy年MM月 */
    public static final String MONTH_CN = "yyyy年MM月";

    /** 标准年度格式，精确到年：yyyy */
    public static final String YEAR = "yyyy";

    /** 标准日期时间格式，精确到秒：yyyy-MM-dd HH:mm:ss */
    public static final String DATETTIME = "yyyy-MM-dd'T'HH:mm:ss";

    /** 标准日期时间格式，精确到秒：yyyy-MM-dd HH:mm:ss */
    public static final String DATETTIME_THIN = "yyyyMMdd'T'HHmmss";

    public static ArrayList<String> datePatters = null;
    
    // ============================ 特殊格式 ============================

    private DateKit() {

    }

    static {
        if (CollectionUtils.isEmpty(datePatters)) {
            synchronized (DateKit.class) {
                if (CollectionUtils.isEmpty(datePatters)) {
                    datePatters = new ArrayList<String>();
                    datePatters.add(DATE);
                    datePatters.add(DATE_THIN);
                    datePatters.add(DATE_CN);
                    datePatters.add(DATE_ROD);
                    datePatters.add(DATETIME);
                    datePatters.add(DATETIME_THIN);
                    datePatters.add(DATETIME_CN);
                    datePatters.add(MILLIS);
                    datePatters.add(MILLIS_THIN);
                    datePatters.add(MONTH);
                    datePatters.add(MONTH_THIN);
                    datePatters.add(MONTH_CN);
                    datePatters.add(DATETTIME);
                    datePatters.add(DATETTIME_THIN);
                }
            }
        }
    }

    public static List<String> getDatePatters() {
        return datePatters;
    }

    // =============== base ===============

    /**
     * 获取当前系统日期
     */
    public static Date now() {
        return new Date();
    }

    /**
     * 校验字符串是否为合法日期
     * 
     * @param text
     *            字符串
     * @param pattern
     *            日期格式
     * @return
     */
    public static boolean isDate(String text, String pattern) {
        return toDate(text, pattern, null) != null;
    }

    /**
     * 字符串转日期，如果失败会抛出异常
     * 
     * @param text
     *            字符串
     * @param pattern
     *            日期格式
     * @return
     */
    public static Date toDate(String text, String pattern) {
        checkNotNull(text);
        checkNotNull(pattern);
        return LocalDateTime.parse(text, DateTimeFormat.forPattern(pattern)).toDate();
    }

    /**
     * 字符串转日期，如果失败返回默认值
     * 
     * @param text
     *            字符串
     * @param pattern
     *            日期格式
     * @param defaultDate
     *            默认值
     * @return
     */
    public static Date toDate(String text, String pattern, Date defaultDate) {
        if (text == null || pattern == null) {
            return defaultDate;
        }
        try {
            return toDate(text, pattern);
        } catch (Exception e) {
            System.out.println("日期转换异常" + e.getMessage());
        }
        return defaultDate;
    }

    /**
     * 日期转字符串，如果失败会抛出异常
     * 
     * @param date
     *            日期
     * @param pattern
     *            日期格式
     * @return
     */
    public static String toText(Date date, String pattern) {
        checkNotNull(date);
        checkNotNull(pattern);
        return TimeKit.toLocalDateTime(date).toString(pattern);
    }

    /**
     * 日期转字符串，如果失败返回默认字符串
     * 
     * @param date
     *            日期
     * @param pattern
     *            日期格式
     * @return
     */
    public static String toText(Date date, String pattern, String defaultText) {
        if (date == null || pattern == null) {
            return defaultText;
        }
        try {
            return toText(date, pattern);
        } catch (Exception e) {
//            log.debug("日期转换异常,{}", e.getMessage());
            System.out.println("日期转换异常" + e.getMessage());
        }
        return defaultText;
    }

    // =============== 范围判断 ===============

    /**
     * 判断日期是否小于系统日期
     * 
     * @param date
     *            需要判断的日期
     * @return
     */
    public static boolean before(Date date) {
        return before(date, now());
    }

    /**
     * 判断日期是否小于目标日期
     * 
     * @param date
     *            需要判断的日期
     * @param destDate
     *            目标日期
     * @return
     */
    public static boolean before(Date date, Date destDate) {
        checkNotNull(date);
        checkNotNull(destDate);
        return date.before(destDate);
    }

    /**
     * 判断日期是否大于系统日期
     * 
     * @param date
     *            需要判断的日期
     * @return
     */
    public static boolean after(Date date) {
        return after(date, now());
    }

    /**
     * 判断日期是否大于目标日期
     * 
     * @param date
     *            需要判断的日期
     * @param destDate
     *            目标日期
     * @return
     */
    public static boolean after(Date date, Date destDate) {
        checkNotNull(date);
        checkNotNull(destDate);
        return date.after(destDate);
    }

    /** 等于 */
    public static boolean equal(Date date, Date destDate) {
        checkNotNull(date);
        checkNotNull(destDate);
        return date.equals(destDate);
    }
    
    /** 小于 */
    public static boolean less(Date date, Date destDate) {
        return before(date, destDate);
    }

    /** 大于 */
    public static boolean great(Date date, Date destDate) {
        return after(date, destDate);
    }

    /** 小于等于 */
    public static boolean lessOrEqual(Date date, Date destDate) {
        return !after(date, destDate);
    }

    /** 大于等于 */
    public static boolean greatOrEqual(Date date, Date destDate) {
        return !before(date, destDate);
    }
    
    /**
     * 判断日期是否在目标日期范围内（包含边界）
     * 
     * @param date
     *            判断日期
     * @param destDateLeft
     *            目标日期起
     * @param destDateRight
     *            目标日期止
     * @return
     */
    public static boolean in(Date date, Date destDateLeft, Date destDateRight) {
        checkState(!after(destDateLeft, destDateRight));
        return !before(date, destDateLeft) && !after(date, destDateRight);
    }

    /**
     * 判断日期范围是否在目标日期范围内（包含边界）
     * 
     * @param dateLeft
     *            判断日期起
     * @param dateRight
     *            判断日期止
     * @param destDateLeft
     *            目标日期起
     * @param destDateRight
     *            目标日期止
     * @return
     */
    public static boolean in(Date dateLeft, Date dateRight, Date destDateLeft, Date destDateRight) {
        checkState(!after(dateLeft, dateRight));
        return in(dateLeft, destDateLeft, destDateRight) && in(dateRight, destDateLeft, destDateRight);
    }

    // =============== 增、减、截取日期 ===============

    /**
     * 获取当前日期所在月份月初第一天.
     *
     * @return
     */
    public static Date getFirstDayOfMonth() {
        return getFirstDayOfMonth(now());
    }

    /**
     * 获取指定日期所在月份月初第一天
     *
     * @param date
     *            指定日期
     * @return
     */
    public static Date getFirstDayOfMonth(Date date) {
        return getFirstDayOfMonth(date, 0);
    }

    /**
     * 获取当前日期增减N月之后，所在月份月初第一天
     * 
     * @param months
     *            调整月份
     * @return
     */
    public static Date getFirstDayOfMonth(int months) {
        return getFirstDayOfMonth(now(), months);
    }

    /**
     * 获取指定日期增减N月之后，所在月份月初第一天
     * 
     * @param date
     *            指定日期
     * @param months
     *            调整月份
     * @return
     */
    public static Date getFirstDayOfMonth(Date date, int months) {
        checkNotNull(date);
        return TimeKit.toLocalDate(date).plusMonths(months).dayOfMonth().withMinimumValue().toDate();
    }

    /**
     * 获取当前日期所在月份月末最后一天
     *
     * @return
     */
    public static Date getLastDayOfMonth() {
        return getLastDayOfMonth(now());
    }

    /**
     * 获取指定日期所在月份月末最后一天
     * 
     * @param date
     *            指定日期
     * @return
     */
    public static Date getLastDayOfMonth(Date date) {
        return getLastDayOfMonth(date, 0);
    }

    /**
     * 获取当前日期增减N月之后，所在月份月末最后一天
     * 
     * @param months
     *            调整月份
     * @return
     */
    public static Date getLastDayOfMonth(int months) {
        return getLastDayOfMonth(now(), months);
    }

    /**
     * 获取指定日期增减N月之后，所在月份月末最后一天
     * 
     * @param date
     *            指定日期
     * @param months
     *            调整月份
     * @return
     */
    public static Date getLastDayOfMonth(Date date, int months) {
        checkNotNull(date);
        return TimeKit.toLocalDate(date).plusMonths(months).dayOfMonth().withMaximumValue().toDate();
    }

    /**
     * 获取指定日期所在季度最后一天
     */
    public static Date getLastDayOfSession(Date date) {
        checkNotNull(date);
        LocalDate localDate = TimeKit.toLocalDate(date);
        int month = localDate.getMonthOfYear() + 2;
        return localDate.withMonthOfYear(month - month % 3).dayOfMonth().withMaximumValue().toDate();
    }

    /**
     * 获取当前日期所在年度第一天
     * 
     * @return
     */
    public static Date getFirstDayOfYear() {
        return getFirstDayOfYear(now());
    }

    /**
     * 获取指定日期所在年度第一天
     * 
     * @param date
     * @return
     */
    public static Date getFirstDayOfYear(Date date) {
        return getFirstDayOfYear(date, 0);
    }

    /**
     * 获取当前日期增减N年之后，所在年度第一天
     * 
     * @param date
     * @param years
     * @return
     */
    public static Date getFirstDayOfYear(int years) {
        return getFirstDayOfYear(now(), years);
    }

    /**
     * 获取指定日期增减N年之后，所在年度第一天
     * 
     * @param date
     * @param years
     * @return
     */
    public static Date getFirstDayOfYear(Date date, int years) {
        checkNotNull(date);
        return TimeKit.toLocalDate(date).plusYears(years).dayOfYear().withMinimumValue().toDate();
    }

    /**
     * 获取当前日期所在年度最后一天
     * 
     * @return
     */
    public static Date getLastDayOfYear() {
        return getLastDayOfYear(now());
    }

    /**
     * 获取指定日期所在年度最后一天
     * 
     * @param date
     * @return
     */
    public static Date getLastDayOfYear(Date date) {
        return getLastDayOfYear(date, 0);
    }

    /**
     * 获取当前日期增减N年之后，所在年度最后一天
     * 
     * @param date
     * @return
     */
    public static Date getLastDayOfYear(int years) {
        return getLastDayOfYear(now(), years);
    }

    /**
     * 获取指定日期增减N年之后，所在年度最后一天
     * 
     * @param date
     * @return
     */
    public static Date getLastDayOfYear(Date date, int years) {
        checkNotNull(date);
        return TimeKit.toLocalDate(date).plusYears(years).dayOfYear().withMaximumValue().toDate();
    }

    /**
     * 返回指定日期的上一月
     * 
     * @param date
     *            指定日期
     * @return
     */
    public static Date prevMonth(Date date) {
        return addMonths(date, -1);
    }

    /**
     * 返回指定日期的下一月
     * 
     * @param date
     *            指定日期
     * @return
     */
    public static Date nextMonth(Date date) {
        return addMonths(date, 1);
    }

    /**
     * 将指定日期增加n月
     * 
     * @param date
     * @param months
     * @return
     */
    public static Date addMonths(Date date, int months) {
        checkNotNull(date);
        return TimeKit.toLocalDateTime(date).plusMonths(months).toDate();
    }

    /**
     * 返回指定日期的上一天
     * 
     * @param date
     * @return
     */
    public static Date prevDay(Date date) {
        return addDays(date, -1);
    }

    /**
     * 返回指定日期的下一天
     * 
     * @param date
     * @return
     */
    public static Date nextDay(Date date) {
        return addDays(date, 1);
    }

    /**
     * 将指定日期增加N天
     * 
     * @param date
     * @param days
     * @return
     */
    public static Date addDays(Date date, int days) {
        checkNotNull(date);
        return TimeKit.toLocalDateTime(date).plusDays(days).toDate();
    }

    /**
     * 指定日期的上一个小时
     * 
     * @param date
     * @param days
     * @return
     */
    public static Date prevHours(Date date) {
        return addHours(date, -1);
    }

    /**
     * <p>描述: 将指定日期增加N小时
     * <p>日期: 2018年7月26日 下午4:53:52
     * <p>作者: zhengxm
     * @param date
     * @param hours
     * @return
     */
    public static Date addHours(Date date, int hours) {
        checkNotNull(date);
        return TimeKit.toLocalDateTime(date).plusHours(hours).toDate();
    }
    
    public static Date truncYear(Date date) {
        return getFirstDayOfYear(date);
    }

    public static Date truncMonth(Date date) {
        return getFirstDayOfMonth(date);
    }

    public static Date truncDay(Date date) {
        checkNotNull(date);
        return TimeKit.toLocalDate(date).toDate();
    }

    // =============== 日期计算 ===============

    // =============== 适配业务 ===============

    /**
     * 获取当前系统日期(字符串)
     */
    public static String now(String pattern) {
        return toText(now(), pattern);
    }

    /**
     * 返回当前日期所在月份第一天，返回指定格式对应字符串
     * 
     * @param pattern
     * @return
     */
    public static String getFirstDayOfMonth(String pattern) {
        return getFirstDayOfMonth(now(), pattern);
    }

    /**
     * 返回指定日期所在月份第一天，返回指定格式对应字符串
     * 
     * @param pattern
     * @return
     */
    public static String getFirstDayOfMonth(Date date, String pattern) {
        return toText(getFirstDayOfMonth(date), pattern);
    }

    /**
     * 返回指定日期所在月份第一天，返回指定格式对应字符串
     * 
     * @param pattern
     * @return
     */
    public static String getFirstDayOfMonth(String text, String pattern) {
        return getFirstDayOfMonth(toDate(text, pattern), pattern);
    }

    /**
     * 获取当前日期增减N月之后，所在月份月初第一天
     * 
     * @param months
     *            调整月份
     * @return
     */
    public static String getFirstDayOfMonth(int months, String pattern) {
        return getFirstDayOfMonth(now(), months, pattern);
    }

    /**
     * 获取指定日期增减N月之后，所在月份月初第一天
     * 
     * @param date
     *            指定日期
     * @param months
     *            调整月份
     * @return
     */
    public static String getFirstDayOfMonth(Date date, int months, String pattern) {
        return toText(getFirstDayOfMonth(date, months), pattern);
    }

    /**
     * 获取指定日期增减N月之后，所在月份月初第一天
     * 
     * @param date
     *            指定日期
     * @param months
     *            调整月份
     * @return
     */
    public static String getFirstDayOfMonth(String text, int months, String pattern) {
        return getFirstDayOfMonth(toDate(text, pattern), months, pattern);
    }

    /**
     * 返回当前日期所在月份最后一天，返回指定格式对应字符串
     * 
     * @param pattern
     * @return
     */
    public static String getLastDayOfMonth(String pattern) {
        return getLastDayOfMonth(now(), pattern);
    }

    /**
     * 返回指定日期所在月份最后一天，返回指定格式对应字符串
     * 
     * @param pattern
     * @return
     */
    public static String getLastDayOfMonth(Date date, String pattern) {
        return toText(getLastDayOfMonth(date), pattern);
    }

    /**
     * 返回指定日期所在月份最后一天，返回指定格式对应字符串
     * 
     * @param pattern
     * @return
     */
    public static String getLastDayOfMonth(String text, String pattern) {
        return getLastDayOfMonth(toDate(text, pattern), pattern);
    }

    /**
     * 获取当前日期增减N月之后，所在月份月末最后一天
     * 
     * @param months
     *            调整月份
     * @return
     */
    public static String getLastDayOfMonth(int months, String pattern) {
        return toText(getLastDayOfMonth(months), pattern);
    }

    /**
     * 获取指定日期增减N月之后，所在月份月末最后一天
     * 
     * @param date
     *            指定日期
     * @param months
     *            调整月份
     * @return
     */
    public static String getLastDayOfMonth(Date date, int months, String pattern) {
        return toText(getLastDayOfMonth(date, months), pattern);
    }

    /**
     * 获取指定日期增减N月之后，所在月份月末最后一天
     * 
     * @param date
     *            指定日期
     * @param months
     *            调整月份
     * @return
     */
    public static String getLastDayOfMonth(String text, int months, String pattern) {
        return getLastDayOfMonth(toDate(text, pattern), months, pattern);
    }

    public static String getFirstDayOfYear(String pattern) {
        return getFirstDayOfYear(now(), pattern);
    }

    public static String getFirstDayOfYear(Date date, String pattern) {
        return toText(getFirstDayOfYear(date), pattern);
    }

    public static String getFirstDayOfYear(String text, String pattern) {
        return getFirstDayOfYear(toDate(text, pattern), pattern);
    }

    public static String getFirstDayOfYear(int months, String pattern) {
        return getFirstDayOfYear(now(), months, pattern);
    }

    public static String getFirstDayOfYear(Date date, int months, String pattern) {
        return toText(getFirstDayOfYear(date, months), pattern);
    }

    public static String getFirstDayOfYear(String text, int months, String pattern) {
        return getFirstDayOfYear(toDate(text, pattern), months, pattern);
    }

    /**<p>描述: 将指定日期增加n月
     * <p>日期: 2018年8月21日 下午1:29:17
     * <p>作者: lull
     * @param text
     * @param mouths
     * @param pattern
     * @return
     */
    public static String addMonths(Date date, int mouths, String pattern) {
        return toText(addMonths(date, mouths), pattern);
    }

    /**<p>描述: 将指定日期增加n月
     * <p>日期: 2018年8月21日 下午1:32:55
     * <p>作者: lull
     * @param text
     * @param mouths
     * @param pattern
     * @return
     */
    public static String addMonths(String text, int mouths, String pattern) {
        return addMonths(toDate(text, pattern), mouths, pattern);
    }

    /**
     * 返回指定日期的上一天，返回指定格式对应字符串
     * 
     * @param pattern
     * @return
     */
    public static String prevDay(Date date, String pattern) {
        return toText(prevDay(date), pattern);
    }

    /**
     * 返回指定日期的上一天，返回指定格式对应字符串
     * 
     * @param pattern
     * @return
     */
    public static String prevDay(String text, String pattern) {
        return prevDay(toDate(text, pattern), pattern);
    }

    /**
     * 返回指定日期的下一天，返回指定格式对应字符串
     * 
     * @param pattern
     * @return
     */
    public static String nextDay(Date date, String pattern) {
        return toText(nextDay(date), pattern);
    }

    /**
     * 返回指定日期的下一天，返回指定格式对应字符串
     * 
     * @param pattern
     * @return
     */
    public static String nextDay(String text, String pattern) {
        return nextDay(toDate(text, pattern), pattern);
    }

    /**
     * 将指定日期增加N天，返回指定格式对应字符串
     * 
     * @param pattern
     * @return
     */
    public static String addDays(Date date, int days, String pattern) {
        return toText(addDays(date, days), pattern);
    }

    /**
     * 将指定日期增加N天，返回指定格式对应字符串
     * 
     * @param pattern
     * @return
     */
    public static String addDays(String text, int days, String pattern) {
        return addDays(toDate(text, pattern), days, pattern);
    }

    /** 
     * @Description: 获取N小时前日期时间字符串 
     */
    public static String addHours(Date date, int hours, String pattern) {
        return toText(addHours(date, hours), pattern);
    }
   
}
