package com.liushimin.miniUI.util;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

import java.util.Date;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * 日期工具类（JodaTime）.
 * 
 * @author shiwp
 * @see Joda-time
 */
public class TimeKit {

    private TimeKit() {

    }

    /**
     * Date ==> LocalDate
     * 
     * @param date
     * @return
     */
    public static LocalDate toLocalDate(Date date) {
        return LocalDate.fromDateFields(toDateIfNecessary(date));
    }

    /**
     * Date ==> LocalDateTime
     * 
     * @param date
     * @return
     */
    public static LocalDateTime toLocalDateTime(Date date) {
        return LocalDateTime.fromDateFields(toDateIfNecessary(date));
    }

    /**
     * 如果是java.util.Date的子类（如java.sql包），就转成java.util.Date
     * 
     * @param date
     * @return
     */
    private static Date toDateIfNecessary(Date date) {
        checkNotNull(date);
        if (Date.class.equals(date.getClass())) {
            return date;
        }
        return new Date(date.getTime());
    }

}







