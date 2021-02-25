package com.benben.global.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by liuchengqiang on 2018/10/25.
 */
public class DateUtils {

    private static final int MIN_HOUR = 0;

    private static final int MIN_MINUTE = 0;

    private static final int MIN_SECOND = 0;

    private static final int MIN_NANOSECOND = 0;

    private static final int MAX_MINUTE = 59;

    private static final int MAX_SECOND = 59;

    private static final String ISO_DATE = "yyyy-MM-dd'T'HH:mm:ssX";

    private static final String UTC_TIMEZONE = "UTC";

    /**
     * get the hours between 2 dates
     *
     * @param startDate start date
     * @param endDate end date
     * @return gap hours
     */
    public static int getHoursBetween2Dates(Date startDate, Date endDate) {
        ZonedDateTime startDateTime = ZonedDateTime.ofInstant(startDate.toInstant(),
                ZoneId.of(UTC_TIMEZONE));
        ZonedDateTime endDateTime = ZonedDateTime.ofInstant(endDate.toInstant(),
                ZoneId.of(UTC_TIMEZONE));
        Long duration = Duration.between(startDateTime, endDateTime).toHours();
        return duration.intValue();
    }

    /**
     * convert to date type from string in iso format
     *
     * @param isoDate the string date
     * @return date
     * @throws IllegalArgumentException
     * @throws ParseException
     */
    public static Date getDateFromIsoFormat(String isoDate)
            throws IllegalArgumentException, ParseException {
        DateFormat df = new SimpleDateFormat(ISO_DATE);
        df.setLenient(false);
        return df.parse(isoDate);
    }

    /**
     * convert to string from date in iso format
     *
     * @param date the given date
     * @return the date in string format
     */
    public static String getDateInISOFormat(Date date) {
        DateFormat df = new SimpleDateFormat(ISO_DATE);
        df.setTimeZone(TimeZone.getTimeZone(UTC_TIMEZONE));
        return df.format(date);
    }

    /**
     * get the first day of next month
     *
     * @param targetTime
     * @param timeZone
     * @return
     */
    public static Date getFirstDayOfNextMonth(Date targetTime, String timeZone) {
        ZonedDateTime lastRedeemDateTime = ZonedDateTime
                .ofInstant(targetTime.toInstant(), TimeZone.getTimeZone(timeZone).toZoneId());
        ZonedDateTime firstDayOfNexMonth = lastRedeemDateTime
                .with(TemporalAdjusters.firstDayOfNextMonth())
                .withHour(MIN_HOUR)
                .withMinute(MIN_MINUTE)
                .withSecond(MIN_SECOND)
                .withNano(MIN_NANOSECOND);
        return Date.from(firstDayOfNexMonth.toInstant());
    }

    /**
     * get the first day of current month
     *
     * @param time
     * @param currencyTimeZone
     * @return
     */
    public static Date getFirstDayOfCurrentMonth(Date time, String currencyTimeZone) {
        // e.g)2016-09-03 12:22:00+0900 JST
        ZonedDateTime dateTime = ZonedDateTime
                .ofInstant(time.toInstant(), TimeZone.getTimeZone(currencyTimeZone).toZoneId());
        // e.g)2016-09-01 00:00:00+0900 JST
        ZonedDateTime firstDayOfCurrentMonthInTimeZone = dateTime
                .with(TemporalAdjusters.firstDayOfMonth())
                .withHour(MIN_HOUR)
                .withMinute(MIN_MINUTE)
                .withSecond(MIN_SECOND)
                .withNano(MIN_NANOSECOND);

        // e.g)2016-09-01 00:00:00
        String firstDayOfCurrentMonthStr = firstDayOfCurrentMonthInTimeZone.format(DateTimeFormatter.ofPattern(ISO_DATE));
        SimpleDateFormat sdfUtc = new SimpleDateFormat(ISO_DATE);
        sdfUtc.setTimeZone(TimeZone.getTimeZone(UTC_TIMEZONE));

        Date firstDayOfCurrentMonthInUTC;
        try {
            // e.g)2016-09-01 00:00:00+0000 UTC
            firstDayOfCurrentMonthInUTC = sdfUtc.parse(firstDayOfCurrentMonthStr);
        } catch (ParseException e) {
            return null;
        }
        return firstDayOfCurrentMonthInUTC;

    }

    /**
     * get the last minute and last second of the given time
     *
     * @param time
     * @return
     */
    public static Date getLastMinuteAndLastSec(Date time) {
        // e.g) 2017-01-01 00:12:13.123
        ZonedDateTime dateTime = ZonedDateTime.ofInstant(time.toInstant(),
                ZoneId.of(UTC_TIMEZONE));
        // e.g) 2017-01-01 00:59:59.000
        ZonedDateTime lastMinutsAndLastSec = dateTime.withMinute(MAX_MINUTE).withSecond(MAX_SECOND)
                .withNano(MIN_NANOSECOND);
        return Date.from(lastMinutsAndLastSec.toInstant());
    }

    /**
     * set the last minute and last second of the given time
     *
     * @param time
     * @return
     */
    public static Date setMinuteAndSecondFiftyNine(Date time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        calendar.set(Calendar.MINUTE, MAX_MINUTE);
        calendar.set(Calendar.SECOND, MAX_SECOND);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * get n milli seconds before the give time
     *
     * @param milliSeconds
     * @param time
     * @return
     */
    public static Date getNMilliSecondsBefore(Integer milliSeconds, Date time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        calendar.add(Calendar.MILLISECOND, -1 * milliSeconds);
        return calendar.getTime();
    }

    /**
     * get n seconds after the give time
     *
     * @param seconds
     * @param time
     * @return
     */
    public static Date getNSecondsAfter(Integer seconds, Date time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        calendar.add(Calendar.MILLISECOND, seconds);
        return calendar.getTime();
    }

    /**
     * get n seconds before the give time
     *
     * @param seconds
     * @param time
     * @return
     */
    public static Date getNSecondsBefore(Integer seconds, Date time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        calendar.add(Calendar.SECOND, -1 * seconds);
        return calendar.getTime();
    }

    /**
     * get n milli seconds after the give time
     *
     * @param milliSeconds
     * @param time
     * @return
     */
    public static Date getNMilliSecondsAfter(Integer milliSeconds, Date time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        calendar.add(Calendar.MILLISECOND, milliSeconds);
        return calendar.getTime();
    }

    /**
     * get n minutes before the give time
     *
     * @param minutes
     * @param time
     * @return
     */
    public static Date getNMinutesBefore(Integer minutes, Date time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        calendar.add(Calendar.MINUTE, -1 * minutes);
        return calendar.getTime();
    }

    /**
     * get n minutes after the give time
     *
     * @param minutes
     * @param time
     * @return
     */
    public static Date getNMinutesAfter(Integer minutes, Date time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        calendar.add(Calendar.MINUTE, minutes);
        return calendar.getTime();
    }

    /**
     * get n hours before the given time
     *
     * @param nHours
     * @param time
     * @return
     */
    public static Date getNHoursBefore(Integer nHours, Date time) {
        // e.g) nHours:3  time:2017-01-01 11:12:13
        ZonedDateTime dateTime = ZonedDateTime.ofInstant(time.toInstant(),
                ZoneId.of(UTC_TIMEZONE));

        // e.g) 2017-01-01 14:12:13
        ZonedDateTime nHoursAfter = dateTime.minusHours(nHours);
        return Date.from(nHoursAfter.toInstant());
    }

    /**
     * get n hours after the given time
     *
     * @param nHours
     * @param time
     * @return
     */
    public static Date getNHoursAfter(Integer nHours, Date time) {
        // e.g) nHours:3  time:2017-01-01 11:12:13
        ZonedDateTime dateTime = ZonedDateTime.ofInstant(time.toInstant(),
                ZoneId.of(UTC_TIMEZONE));

        // e.g) 2017-01-01 14:12:13
        ZonedDateTime nHoursAfter = dateTime.plusHours(nHours);
        return Date.from(nHoursAfter.toInstant());
    }

    /**
     * get n days before the give time
     *
     * @param days
     * @param time
     * @return
     */
    public static Date getNDaysBefore(Integer days, Date time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        calendar.add(Calendar.DATE, -1 * days);
        return calendar.getTime();
    }

    /**
     * get n days after the give time
     *
     * @param days
     * @param time
     * @return
     */
    public static Date getNDaysAfter(Integer days, Date time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        calendar.add(Calendar.DATE, days);
        return calendar.getTime();
    }

    /**
     * get n years before the give time
     *
     * @param years
     * @param time
     * @return
     */
    public static Date getNYearsBefore(Integer years, Date time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        calendar.add(Calendar.YEAR, -1 * years);
        return calendar.getTime();
    }

    /**
     * get n years after the give time
     *
     * @param years
     * @param time
     * @return
     */
    public static Date getNYearsAfter(Integer years, Date time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        calendar.add(Calendar.YEAR, years);
        return calendar.getTime();
    }

}
