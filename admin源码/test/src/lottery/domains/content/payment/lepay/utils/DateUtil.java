package lottery.domains.content.payment.lepay.utils;

import java.text.ParsePosition;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;

public class DateUtil
{
    private static final ThreadLocal<SimpleDateFormat> threadLocal;
    private static final Object object;
    
    static {
        threadLocal = new ThreadLocal<SimpleDateFormat>();
        object = new Object();
    }
    
    private static SimpleDateFormat getDateFormat(final String pattern) throws RuntimeException {
        SimpleDateFormat dateFormat = DateUtil.threadLocal.get();
        if (dateFormat == null) {
            synchronized (DateUtil.object) {
                if (dateFormat == null) {
                    dateFormat = new SimpleDateFormat(pattern);
                    dateFormat.setLenient(false);
                    DateUtil.threadLocal.set(dateFormat);
                }
            }
            // monitorexit(DateUtil.object)
        }
        dateFormat.applyPattern(pattern);
        return dateFormat;
    }
    
    private static int getInteger(final Date date, final int dateType) {
        int num = 0;
        final Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
            num = calendar.get(dateType);
        }
        return num;
    }
    
    private static String addInteger(final String date, final int dateType, final int amount) {
        String dateString = null;
        final DateStyle dateStyle = getDateStyle(date);
        if (dateStyle != null) {
            Date myDate = StringToDate(date, dateStyle);
            myDate = addInteger(myDate, dateType, amount);
            dateString = DateToString(myDate, dateStyle);
        }
        return dateString;
    }
    
    private static Date addInteger(final Date date, final int dateType, final int amount) {
        Date myDate = null;
        if (date != null) {
            final Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(dateType, amount);
            myDate = calendar.getTime();
        }
        return myDate;
    }
    
    private static Date getAccurateDate(final List<Long> timestamps) {
        Date date = null;
        long timestamp = 0L;
        final Map map = new HashMap();
        final List absoluteValues = new ArrayList();
        if (timestamps != null && timestamps.size() > 0) {
            if (timestamps.size() > 1) {
                for (int i = 0; i < timestamps.size(); ++i) {
                    for (int j = i + 1; j < timestamps.size(); ++j) {
                        final long absoluteValue = Math.abs(timestamps.get(i) - timestamps.get(j));
                        absoluteValues.add(absoluteValue);
                        final long[] timestampTmp = { timestamps.get(i), timestamps.get(j) };
                        map.put(absoluteValue, timestampTmp);
                    }
                }
                long minAbsoluteValue = -1L;
                if (!absoluteValues.isEmpty()) {
                    minAbsoluteValue = (long) absoluteValues.get(0);
                    for (int k = 1; k < absoluteValues.size(); ++k) {
                        if (minAbsoluteValue > (long)absoluteValues.get(k)) {
                            minAbsoluteValue = (long)absoluteValues.get(k);
                        }
                    }
                }
                if (minAbsoluteValue != -1L) {
                    final long[] timestampsLastTmp = (long[])map.get(minAbsoluteValue);
                    final long dateOne = timestampsLastTmp[0];
                    final long dateTwo = timestampsLastTmp[1];
                    if (absoluteValues.size() > 1) {
                        timestamp = ((Math.abs(dateOne) > Math.abs(dateTwo)) ? dateOne : dateTwo);
                    }
                }
            }
            else {
                timestamp = timestamps.get(0);
            }
        }
        if (timestamp != 0L) {
            date = new Date(timestamp);
        }
        return date;
    }
    
    public static boolean isDate(final String date) {
        boolean isDate = false;
        if (date != null && getDateStyle(date) != null) {
            isDate = true;
        }
        return isDate;
    }
    
    public static DateStyle getDateStyle(final String date) {
        DateStyle dateStyle = null;
        final Map map = new HashMap();
        final List timestamps = new ArrayList();
        DateStyle[] values;
        for (int length = (values = DateStyle.values()).length, i = 0; i < length; ++i) {
            final DateStyle style = values[i];
            if (!style.isShowOnly()) {
                Date dateTmp = null;
                if (date != null) {
                    try {
                        final ParsePosition pos = new ParsePosition(0);
                        dateTmp = getDateFormat(style.getValue()).parse(date, pos);
                        if (pos.getIndex() != date.length()) {
                            dateTmp = null;
                        }
                    }
                    catch (Exception ex) {}
                }
                if (dateTmp != null) {
                    timestamps.add(dateTmp.getTime());
                    map.put(dateTmp.getTime(), style);
                }
            }
        }
        final Date accurateDate = getAccurateDate(timestamps);
        if (accurateDate != null) {
            dateStyle = (DateStyle) map.get(accurateDate.getTime());
        }
        return dateStyle;
    }
    
    public static Date StringToDate(final String date) {
        final DateStyle dateStyle = getDateStyle(date);
        return StringToDate(date, dateStyle);
    }
    
    public static Date StringToDate(final String date, final String pattern) {
        Date myDate = null;
        if (date != null) {
            try {
                myDate = getDateFormat(pattern).parse(date);
            }
            catch (Exception ex) {}
        }
        return myDate;
    }
    
    public static Date StringToDate(final String date, final DateStyle dateStyle) {
        Date myDate = null;
        if (dateStyle != null) {
            myDate = StringToDate(date, dateStyle.getValue());
        }
        return myDate;
    }
    
    public static String DateToString(final Date date, final String pattern) {
        String dateString = null;
        if (date != null) {
            try {
                dateString = getDateFormat(pattern).format(date);
            }
            catch (Exception ex) {}
        }
        return dateString;
    }
    
    public static String DateToString(final Date date, final DateStyle dateStyle) {
        String dateString = null;
        if (dateStyle != null) {
            dateString = DateToString(date, dateStyle.getValue());
        }
        return dateString;
    }
    
    public static String StringToString(final String date, final String newPattern) {
        final DateStyle oldDateStyle = getDateStyle(date);
        return StringToString(date, oldDateStyle, newPattern);
    }
    
    public static String StringToString(final String date, final DateStyle newDateStyle) {
        final DateStyle oldDateStyle = getDateStyle(date);
        return StringToString(date, oldDateStyle, newDateStyle);
    }
    
    public static String StringToString(final String date, final String olddPattern, final String newPattern) {
        return DateToString(StringToDate(date, olddPattern), newPattern);
    }
    
    public static String StringToString(final String date, final DateStyle olddDteStyle, final String newParttern) {
        String dateString = null;
        if (olddDteStyle != null) {
            dateString = StringToString(date, olddDteStyle.getValue(), newParttern);
        }
        return dateString;
    }
    
    public static String StringToString(final String date, final String olddPattern, final DateStyle newDateStyle) {
        String dateString = null;
        if (newDateStyle != null) {
            dateString = StringToString(date, olddPattern, newDateStyle.getValue());
        }
        return dateString;
    }
    
    public static String StringToString(final String date, final DateStyle olddDteStyle, final DateStyle newDateStyle) {
        String dateString = null;
        if (olddDteStyle != null && newDateStyle != null) {
            dateString = StringToString(date, olddDteStyle.getValue(), newDateStyle.getValue());
        }
        return dateString;
    }
    
    public static String addYear(final String date, final int yearAmount) {
        return addInteger(date, 1, yearAmount);
    }
    
    public static Date addYear(final Date date, final int yearAmount) {
        return addInteger(date, 1, yearAmount);
    }
    
    public static String addMonth(final String date, final int monthAmount) {
        return addInteger(date, 2, monthAmount);
    }
    
    public static Date addMonth(final Date date, final int monthAmount) {
        return addInteger(date, 2, monthAmount);
    }
    
    public static String addDay(final String date, final int dayAmount) {
        return addInteger(date, 5, dayAmount);
    }
    
    public static Date addDay(final Date date, final int dayAmount) {
        return addInteger(date, 5, dayAmount);
    }
    
    public static String addHour(final String date, final int hourAmount) {
        return addInteger(date, 11, hourAmount);
    }
    
    public static Date addHour(final Date date, final int hourAmount) {
        return addInteger(date, 11, hourAmount);
    }
    
    public static String addMinute(final String date, final int minuteAmount) {
        return addInteger(date, 12, minuteAmount);
    }
    
    public static Date addMinute(final Date date, final int minuteAmount) {
        return addInteger(date, 12, minuteAmount);
    }
    
    public static String addSecond(final String date, final int secondAmount) {
        return addInteger(date, 13, secondAmount);
    }
    
    public static Date addSecond(final Date date, final int secondAmount) {
        return addInteger(date, 13, secondAmount);
    }
    
    public static int getYear(final String date) {
        return getYear(StringToDate(date));
    }
    
    public static int getYear(final Date date) {
        return getInteger(date, 1);
    }
    
    public static int getMonth(final String date) {
        return getMonth(StringToDate(date));
    }
    
    public static int getMonth(final Date date) {
        return getInteger(date, 2) + 1;
    }
    
    public static int getDay(final String date) {
        return getDay(StringToDate(date));
    }
    
    public static int getDay(final Date date) {
        return getInteger(date, 5);
    }
    
    public static int getHour(final String date) {
        return getHour(StringToDate(date));
    }
    
    public static int getHour(final Date date) {
        return getInteger(date, 11);
    }
    
    public static int getMinute(final String date) {
        return getMinute(StringToDate(date));
    }
    
    public static int getMinute(final Date date) {
        return getInteger(date, 12);
    }
    
    public static int getSecond(final String date) {
        return getSecond(StringToDate(date));
    }
    
    public static int getSecond(final Date date) {
        return getInteger(date, 13);
    }
    
    public static String getDate(final String date) {
        return StringToString(date, DateStyle.YYYY_MM_DD);
    }
    
    public static String getDate(final Date date) {
        return DateToString(date, DateStyle.YYYY_MM_DD);
    }
    
    public static String getTime(final String date) {
        return StringToString(date, DateStyle.HH_MM_SS);
    }
    
    public static String getTime(final Date date) {
        return DateToString(date, DateStyle.HH_MM_SS);
    }
    
    public static String getDateTime(final String date) {
        return StringToString(date, DateStyle.YYYY_MM_DD_HH_MM_SS);
    }
    
    public static String getDateTime(final Date date) {
        return DateToString(date, DateStyle.YYYY_MM_DD_HH_MM_SS);
    }
    
    public static Week getWeek(final String date) {
        Week week = null;
        final DateStyle dateStyle = getDateStyle(date);
        if (dateStyle != null) {
            final Date myDate = StringToDate(date, dateStyle);
            week = getWeek(myDate);
        }
        return week;
    }
    
    public static Week getWeek(final Date date) {
        Week week = null;
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        final int weekNumber = calendar.get(7) - 1;
        switch (weekNumber) {
            case 0: {
                week = Week.SUNDAY;
                break;
            }
            case 1: {
                week = Week.MONDAY;
                break;
            }
            case 2: {
                week = Week.TUESDAY;
                break;
            }
            case 3: {
                week = Week.WEDNESDAY;
                break;
            }
            case 4: {
                week = Week.THURSDAY;
                break;
            }
            case 5: {
                week = Week.FRIDAY;
                break;
            }
            case 6: {
                week = Week.SATURDAY;
                break;
            }
        }
        return week;
    }
    
    public static int getIntervalDays(final String date, final String otherDate) {
        return getIntervalDays(StringToDate(date), StringToDate(otherDate));
    }
    
    public static int getIntervalDays(final Date date, final Date otherDate) {
        int num = -1;
        final Date dateTmp = StringToDate(getDate(date), DateStyle.YYYY_MM_DD);
        final Date otherDateTmp = StringToDate(getDate(otherDate), DateStyle.YYYY_MM_DD);
        if (dateTmp != null && otherDateTmp != null) {
            final long time = Math.abs(dateTmp.getTime() - otherDateTmp.getTime());
            num = (int)(time / 86400000L);
        }
        return num;
    }
    
    public static String getAge(final Date date, final Date otherDate) {
        final int dis = getIntervalDays(new Date(), otherDate);
        final int year = dis / 365;
        final int month = dis % 365 / 30;
        final int day = dis % 365 % 31;
        final String age = String.valueOf((year > 0) ? new StringBuilder(String.valueOf(year)).append("��").toString() : "") + ((month > 0) ? (String.valueOf(month) + "����") : "") + day + "��";
        return age;
    }
    
    public static int getStartTimeOfOneDay(final long time) {
        final Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time);
        cal.set(11, 0);
        cal.set(12, 0);
        cal.set(13, 0);
        return (int)(cal.getTimeInMillis() / 1000L);
    }
    
    public static String getCurrentDate(final String dateFormat) {
        return new SimpleDateFormat(dateFormat).format(new Date());
    }
    
    public static String getMsgId() {
        final int ran = getRandom(10);
        final String msgId = String.valueOf(getCurrentDate("yyyyMMddHHmmss")) + "-" + ran;
        return msgId;
    }
    
    public static int getRandom(final int length) {
        int num = 1;
        double random = Math.random();
        if (random < 0.1) {
            random += 0.1;
        }
        for (int i = 0; i < length; ++i) {
            num *= 10;
        }
        return (int)(random * num);
    }
}
