package javautils.date;

import java.util.Calendar;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil
{
    public static String getCurrentTime() {
        final GregorianCalendar g = new GregorianCalendar();
        return dateToString(g);
    }
    
    public static String getCurrentDate() {
        final GregorianCalendar g = new GregorianCalendar();
        return dateToStringSim(g);
    }
    
    public static String getYesterday() {
        final GregorianCalendar g = new GregorianCalendar();
        g.set(5, g.get(5) - 1);
        return dateToStringSim(g);
    }
    
    public static String getMonthFirstDate(final Date date) {
        final GregorianCalendar g = new GregorianCalendar();
        g.setTime(date);
        g.set(5, 1);
        return dateToStringSim(g);
    }
    
    public static String getMonthLastDate(final Date date) {
        final GregorianCalendar g = new GregorianCalendar();
        g.setTime(date);
        g.set(5, 1);
        return dateToStringSim(g);
    }
    
    public static String getTomorrow() {
        final GregorianCalendar g = new GregorianCalendar();
        g.set(5, g.get(5) + 1);
        return dateToStringSim(g);
    }
    
    public static String getTime(final long ms) {
        final GregorianCalendar g = new GregorianCalendar();
        g.setTimeInMillis(ms);
        return dateToString(g);
    }
    
    private static GregorianCalendar getCalendarByTime(final String time, final String format) {
        GregorianCalendar g = new GregorianCalendar();
        try {
            g.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time));
        }
        catch (ParseException e) {
            g = null;
        }
        return g;
    }
    
    private static String calcDate(GregorianCalendar g, final int seconds) {
        if (g == null) {
            g = new GregorianCalendar();
        }
        g.add(13, seconds);
        return dateToString(g);
    }
    
    public static String dateToString(final Date date) {
        try {
            final DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            final String s = formatter.format(date);
            return s;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static Date stringToDate(final String date) {
        try {
            final DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            final Date d = formatter.parse(date);
            return d;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static Date stringToDate(final String date, final String format) {
        try {
            final DateFormat formatter = new SimpleDateFormat(format);
            final Date d = formatter.parse(date);
            return d;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static long calcDate(final String subDate, final String minDate) {
        final long lSubDate = getCalendarByTime(subDate, "yyyy-MM-dd HH:mm:ss").getTimeInMillis();
        final long lMinDate = getCalendarByTime(minDate, "yyyy-MM-dd HH:mm:ss").getTimeInMillis();
        return lSubDate - lMinDate;
    }
    
    public static int calcDays(final Date date1, final Date date2) {
        final Calendar date1Calendar = Calendar.getInstance();
        date1Calendar.setTime(date1);
        final Calendar date2Calendar = Calendar.getInstance();
        date2Calendar.setTime(date2);
        final int day1 = date1Calendar.get(6);
        final int day2 = date2Calendar.get(6);
        return day1 - day2;
    }
    
    public static String calcDateByTime(final String time, final int seconds) {
        final GregorianCalendar g = getCalendarByTime(time, "yyyy-MM-dd HH:mm:ss");
        return calcDate(g, seconds);
    }
    
    public static String calcNewDay(final String date, final int days) {
        final GregorianCalendar g = new GregorianCalendar();
        g.setTimeInMillis(formatTime(date, "yyyy-MM-dd"));
        g.set(5, g.get(5) + days);
        return dateToStringSim(g);
    }
    
    public static String calcNextDay(final String date) {
        final GregorianCalendar g = new GregorianCalendar();
        g.setTimeInMillis(formatTime(date, "yyyy-MM-dd"));
        g.set(5, g.get(5) + 1);
        return dateToStringSim(g);
    }
    
    public static String calcLastDay(final String date) {
        final GregorianCalendar g = new GregorianCalendar();
        g.setTimeInMillis(formatTime(date, "yyyy-MM-dd"));
        g.set(5, g.get(5) - 1);
        return dateToStringSim(g);
    }
    
    public static String calcLastMonth(final String date) {
        final GregorianCalendar g = new GregorianCalendar();
        g.setTimeInMillis(formatTime(date, "yyyy-MM-dd"));
        g.set(2, g.get(2) - 1);
        return dateToStringSim(g);
    }
    
    private static Calendar getDateOfMonth(final Calendar date, final int num, final boolean flag) {
        final Calendar lastDate = (Calendar)date.clone();
        if (flag) {
            lastDate.add(2, num);
        }
        else {
            lastDate.add(2, -num);
        }
        return lastDate;
    }
    
    private static Calendar getDateOfLastMonth(final String dateStr, final int num, final boolean flag) {
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            final Date date = sdf.parse(dateStr);
            final Calendar c = Calendar.getInstance();
            c.setTime(date);
            return getDateOfMonth(c, num, flag);
        }
        catch (Exception e) {
            throw new IllegalArgumentException("Invalid date format(yyyyMMdd): " + dateStr);
        }
    }
    
    public static String getSameDateOfLastMonth(final String date, final int num, final boolean flag) {
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        final String lastDate = sdf.format(getDateOfLastMonth(date, num, flag).getTime());
        return lastDate;
    }
    
    private static String dateToString(final GregorianCalendar g) {
        final String year = String.valueOf(g.get(1));
        final String month = String.format("%02d", g.get(2) + 1);
        final String day = String.format("%02d", g.get(5));
        final String hours = String.format("%02d", g.get(11));
        final String minutes = String.format("%02d", g.get(12));
        final String seconds = String.format("%02d", g.get(13));
        return String.valueOf(year) + "-" + month + "-" + day + " " + hours + ":" + minutes + ":" + seconds;
    }
    
    public static long stringToLong(final String time, final String format) {
        return stringToDate(time, format).getTime();
    }
    
    public static String dateToStringSim(final GregorianCalendar g) {
        final String year = String.valueOf(g.get(1));
        final String month = String.format("%02d", g.get(2) + 1);
        final String day = String.format("%02d", g.get(5));
        return String.valueOf(year) + "-" + month + "-" + day;
    }
    
    public static String getCurTimeStr() {
        final GregorianCalendar g = new GregorianCalendar();
        final String year = String.valueOf(g.get(1));
        final String month = String.format("%02d", g.get(2) + 1);
        final String day = String.format("%02d", g.get(5));
        final String hours = String.format("%02d", g.get(11));
        final String minutes = String.format("%02d", g.get(12));
        final String seconds = String.format("%02d", g.get(13));
        return String.valueOf(year) + month + day + hours + minutes + seconds;
    }
    
    public static String formatTime(final String time, final String oldFormat, final String newFormat) {
        return new SimpleDateFormat(newFormat).format(stringToDate(time, oldFormat));
    }
    
    public static String formatTime(final Date date, final String format) {
        return new SimpleDateFormat(format).format(date);
    }
    
    public static String formatTime(final long time, final String format) {
        return formatTime(getTime(time), "yyyy-MM-dd HH:mm:ss", format);
    }
    
    public static long formatTime(final String time, final String format) {
        return stringToDate(time, format).getTime();
    }
    
    public static String dateForm(final String date, final String config) {
        if ("MM/dd/yyyy".equals(config)) {
            final String[] dateStrs = date.split("/");
            return String.valueOf(dateStrs[2]) + "-" + dateStrs[0] + "-" + dateStrs[1];
        }
        if ("MM-dd-yyyy".equals(config)) {
            final String[] dateStrs = date.split("-");
            return String.valueOf(dateStrs[2]) + "-" + dateStrs[0] + "-" + dateStrs[1];
        }
        return null;
    }
    
    public static String[] getDateArray(final String beginDate, final String endDate) {
        final Calendar beginCal = Calendar.getInstance();
        final Calendar endCal = Calendar.getInstance();
        final Date begin = parseDate(beginDate, "yyyy-MM-dd");
        final Date end = parseDate(endDate, "yyyy-MM-dd");
        beginCal.setTime(begin);
        endCal.setTime(end);
        final long between_days = (endCal.getTimeInMillis() - beginCal.getTimeInMillis()) / 86400000L;
        final int size = Integer.parseInt(String.valueOf(between_days)) + 1;
        if (size < 1) {
            return null;
        }
        final String[] dateArray = new String[size];
        for (int i = 0; i < size; ++i) {
            if (i > 0) {
                beginCal.add(5, 1);
            }
            final Date begintDate = beginCal.getTime();
            final String dateStr = new SimpleDateFormat("yyyy-MM-dd").format(begintDate);
            dateArray[i] = dateStr;
        }
        return dateArray;
    }
    
    public static Date parseDate(final String strDate, final String format) {
        Date date = null;
        try {
            final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
            date = simpleDateFormat.parse(strDate);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return date;
    }
    
    public static int getYear(final String time) {
        return Integer.parseInt(time.substring(0, 4));
    }
    
    public static int getMonth(final String time) {
        return Integer.parseInt(time.substring(5, 7));
    }
    
    public static int getDay(final String time) {
        return Integer.parseInt(time.substring(8, 10));
    }
    
    public static int getHours(final String time) {
        return Integer.parseInt(time.substring(11, 13));
    }
    
    public static int getMinutes(final String time) {
        return Integer.parseInt(time.substring(14, 16));
    }
    
    public static int getSeconds(final String time) {
        return Integer.parseInt(time.substring(17));
    }
    
    public static int getYear() {
        final GregorianCalendar g = new GregorianCalendar();
        return g.get(1);
    }
    
    public static int getMonth() {
        final GregorianCalendar g = new GregorianCalendar();
        return g.get(2) + 1;
    }
    
    public static int getDay() {
        final GregorianCalendar g = new GregorianCalendar();
        return g.get(5);
    }
    
    public static int getHours() {
        final GregorianCalendar g = new GregorianCalendar();
        return g.get(11);
    }
    
    public static int getMinutes() {
        final GregorianCalendar g = new GregorianCalendar();
        return g.get(12);
    }
    
    public static int getSeconds() {
        final GregorianCalendar g = new GregorianCalendar();
        return g.get(13);
    }
    
    public static void main(final String[] args) {
    }
}
