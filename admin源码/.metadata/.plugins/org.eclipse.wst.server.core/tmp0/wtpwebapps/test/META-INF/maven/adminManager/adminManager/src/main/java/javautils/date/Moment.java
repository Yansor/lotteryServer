package javautils.date;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;
import java.util.GregorianCalendar;

public class Moment
{
    public GregorianCalendar gc;
    
    static {
        final TimeZone china = TimeZone.getTimeZone("Asia/Shanghai");
        TimeZone.setDefault(china);
    }
    
    public static void main(final String[] args) {
        final Moment moment = new Moment();
        System.out.println(moment.difference(new Moment().fromDate("2015-01-18"), "hour"));
    }
    
    public Moment() {
        (this.gc = new GregorianCalendar()).setFirstDayOfWeek(2);
    }
    
    public Moment(final GregorianCalendar gc) {
        this.gc = gc;
    }
    
    public Moment fromDate(final String date) {
        try {
            final DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            final Date d = format.parse(date);
            this.gc.setTime(d);
        }
        catch (Exception ex) {}
        return this;
    }
    
    public Moment fromTime(final String time) {
        try {
            final DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            final Date d = format.parse(time);
            this.gc.setTime(d);
        }
        catch (Exception ex) {}
        return this;
    }
    
    public String toSimpleTime() {
        return String.valueOf(this.year()) + "-" + this.months() + "-" + this.days() + " " + this.hours() + ":" + this.minutes() + ":" + this.seconds();
    }
    
    public String toFullTime() {
        return String.valueOf(this.toSimpleTime()) + "." + this.milliseconds();
    }
    
    public String toSimpleDate() {
        return String.valueOf(this.year()) + "-" + this.months() + "-" + this.days();
    }
    
    public Date toDate() {
        return this.gc.getTime();
    }
    
    public String format(final String format) {
        return new SimpleDateFormat(format).format(this.toDate());
    }
    
    public int year() {
        return this.gc.get(1);
    }
    
    public Moment year(final int year) {
        this.gc.set(1, year);
        return this;
    }
    
    public int month() {
        return this.gc.get(2) + 1;
    }
    
    public String months() {
        return String.format("%02d", this.month());
    }
    
    public Moment month(final int month) {
        if (month > 0 && month <= 12) {
            this.gc.set(2, month - 1);
        }
        return this;
    }
    
    public Moment month(final String month) {
        this.month(Integer.parseInt(month));
        return this;
    }
    
    public int day() {
        return this.gc.get(5);
    }
    
    public String days() {
        return String.format("%02d", this.day());
    }
    
    public Moment day(final int day) {
        if (day > 0 && day <= 31) {
            this.gc.set(5, day);
        }
        return this;
    }
    
    public Moment day(final String day) {
        this.day(Integer.parseInt(day));
        return this;
    }
    
    public int hour() {
        return this.gc.get(11);
    }
    
    public String hours() {
        return String.format("%02d", this.hour());
    }
    
    public Moment hour(final int hour) {
        if (hour >= 0 && hour < 24) {
            this.gc.set(11, hour);
        }
        return this;
    }
    
    public Moment hour(final String hour) {
        this.hour(Integer.parseInt(hour));
        return this;
    }
    
    public int minute() {
        return this.gc.get(12);
    }
    
    public String minutes() {
        return String.format("%02d", this.minute());
    }
    
    public Moment minute(final int minute) {
        if (minute >= 0 && minute < 60) {
            this.gc.set(12, minute);
        }
        return this;
    }
    
    public Moment minute(final String minute) {
        this.minute(Integer.parseInt(minute));
        return this;
    }
    
    public int second() {
        return this.gc.get(13);
    }
    
    public String seconds() {
        return String.format("%02d", this.second());
    }
    
    public Moment second(final int second) {
        if (second >= 0 && second < 60) {
            this.gc.set(13, second);
        }
        return this;
    }
    
    public Moment second(final String second) {
        this.second(Integer.parseInt(second));
        return this;
    }
    
    public int millisecond() {
        return this.gc.get(14);
    }
    
    public String milliseconds() {
        return String.format("%03d", this.millisecond());
    }
    
    public Moment millisecond(final int millisecond) {
        if (millisecond >= 0 && millisecond < 1000) {
            this.gc.set(14, millisecond);
        }
        return this;
    }
    
    public Moment millisecond(final String millisecond) {
        this.millisecond(Integer.parseInt(millisecond));
        return this;
    }
    
    public int weekOfMonth() {
        return this.gc.get(4);
    }
    
    public int weekOfYear() {
        return this.gc.get(3);
    }
    
    public int dayOfWeek() {
        final int field = this.gc.get(7);
        switch (field) {
            case 1: {
                return 7;
            }
            case 2: {
                return 1;
            }
            case 3: {
                return 2;
            }
            case 4: {
                return 3;
            }
            case 5: {
                return 4;
            }
            case 6: {
                return 5;
            }
            case 7: {
                return 6;
            }
            default: {
                return this.gc.getFirstDayOfWeek();
            }
        }
    }
    
    public Moment dayOfWeek(final int week) {
        switch (week) {
            case 7: {
                this.gc.set(7, 1);
                break;
            }
            case 1: {
                this.gc.set(7, 2);
                break;
            }
            case 2: {
                this.gc.set(7, 3);
                break;
            }
            case 3: {
                this.gc.set(7, 4);
                break;
            }
            case 4: {
                this.gc.set(7, 5);
                break;
            }
            case 5: {
                this.gc.set(7, 6);
                break;
            }
            case 6: {
                this.gc.set(7, 7);
                break;
            }
        }
        return this;
    }
    
    public int dayOfYear() {
        return this.gc.get(6);
    }
    
    public Moment dayOfYear(final int day) {
        this.gc.set(6, day);
        return this;
    }
    
    public int quarter() {
        final int month = this.month();
        switch (month) {
            case 1:
            case 2:
            case 3: {
                return 1;
            }
            case 4:
            case 5:
            case 6: {
                return 2;
            }
            case 7:
            case 8:
            case 9: {
                return 3;
            }
            case 10:
            case 11:
            case 12: {
                return 4;
            }
            default: {
                return 0;
            }
        }
    }
    
    public int get(final String key) {
        switch (key.hashCode()) {
            case -1074026988: {
                if (!key.equals("minute")) {
                    return 0;
                }
                return this.minute();
            }
            case -906279820: {
                if (!key.equals("second")) {
                    return 0;
                }
                return this.second();
            }
            case 99228: {
                if (!key.equals("day")) {
                    return 0;
                }
                break;
            }
            case 3076014: {
                if (!key.equals("date")) {
                    return 0;
                }
                break;
            }
            case 3208676: {
                if (!key.equals("hour")) {
                    return 0;
                }
                return this.hour();
            }
            case 3704893: {
                if (!key.equals("year")) {
                    return 0;
                }
                return this.year();
            }
            case 104080000: {
                if (!key.equals("month")) {
                    return 0;
                }
                return this.month();
            }
            case 1942410881: {
                if (!key.equals("millisecond")) {
                    return 0;
                }
                return this.millisecond();
            }
        }
        return this.day();
    }
    
    public Moment set(final String key, final int value) {
        switch (key.hashCode()) {
            case -1074026988: {
                if (!key.equals("minute")) {
                    return this;
                }
                this.minute(value);
                return this;
            }
            case -906279820: {
                if (!key.equals("second")) {
                    return this;
                }
                this.second(value);
                return this;
            }
            case 99228: {
                if (!key.equals("day")) {
                    return this;
                }
                break;
            }
            case 3076014: {
                if (!key.equals("date")) {
                    return this;
                }
                break;
            }
            case 3208676: {
                if (!key.equals("hour")) {
                    return this;
                }
                this.hour(value);
                return this;
            }
            case 3704893: {
                if (!key.equals("year")) {
                    return this;
                }
                this.year(value);
                return this;
            }
            case 104080000: {
                if (!key.equals("month")) {
                    return this;
                }
                this.month(value);
                return this;
            }
            case 1942410881: {
                if (!key.equals("millisecond")) {
                    return this;
                }
                this.millisecond(value);
                return this;
            }
        }
        this.day(value);
        return this;
    }
    
    public long millis() {
        return this.gc.getTimeInMillis();
    }
    
    public static Moment max(final Moment... moment) {
        Moment max = null;
        if (moment != null && moment.length > 0) {
            for (final Moment tmp : moment) {
                if (max == null) {
                    max = tmp;
                }
                if (max.millis() < tmp.millis()) {
                    max = tmp;
                }
            }
        }
        return max;
    }
    
    public static Moment min(final Moment... moment) {
        Moment min = null;
        if (moment != null && moment.length > 0) {
            for (final Moment tmp : moment) {
                if (min == null) {
                    min = tmp;
                }
                if (min.millis() > tmp.millis()) {
                    min = tmp;
                }
            }
        }
        return min;
    }
    
    public Moment add(final int amount, final String key) {
        Label_0495: {
            Label_0482: {
                Label_0469: {
                    Label_0456: {
                        Label_0444: {
                            Label_0432: {
                                Label_0420: {
                                    Label_0406: {
                                        switch (key.hashCode()) {
                                            case -1281313977: {
                                                if (!key.equals("quarters")) {
                                                    return this;
                                                }
                                                break Label_0406;
                                            }
                                            case -1068487181: {
                                                if (!key.equals("months")) {
                                                    return this;
                                                }
                                                break Label_0420;
                                            }
                                            case 77: {
                                                if (!key.equals("M")) {
                                                    return this;
                                                }
                                                break Label_0420;
                                            }
                                            case 81: {
                                                if (!key.equals("Q")) {
                                                    return this;
                                                }
                                                break Label_0406;
                                            }
                                            case 100: {
                                                if (!key.equals("d")) {
                                                    return this;
                                                }
                                                break Label_0444;
                                            }
                                            case 104: {
                                                if (!key.equals("h")) {
                                                    return this;
                                                }
                                                break Label_0456;
                                            }
                                            case 109: {
                                                if (!key.equals("m")) {
                                                    return this;
                                                }
                                                break Label_0469;
                                            }
                                            case 115: {
                                                if (!key.equals("s")) {
                                                    return this;
                                                }
                                                break Label_0482;
                                            }
                                            case 119: {
                                                if (!key.equals("w")) {
                                                    return this;
                                                }
                                                break Label_0432;
                                            }
                                            case 121: {
                                                if (!key.equals("y")) {
                                                    return this;
                                                }
                                                break;
                                            }
                                            case 3494: {
                                                if (!key.equals("ms")) {
                                                    return this;
                                                }
                                                break Label_0495;
                                            }
                                            case 3076183: {
                                                if (!key.equals("days")) {
                                                    return this;
                                                }
                                                break Label_0444;
                                            }
                                            case 85195282: {
                                                if (!key.equals("milliseconds")) {
                                                    return this;
                                                }
                                                break Label_0495;
                                            }
                                            case 99469071: {
                                                if (!key.equals("hours")) {
                                                    return this;
                                                }
                                                break Label_0456;
                                            }
                                            case 113008383: {
                                                if (!key.equals("weeks")) {
                                                    return this;
                                                }
                                                break Label_0432;
                                            }
                                            case 114851798: {
                                                if (!key.equals("years")) {
                                                    return this;
                                                }
                                                break;
                                            }
                                            case 1064901855: {
                                                if (!key.equals("minutes")) {
                                                    return this;
                                                }
                                                break Label_0469;
                                            }
                                            case 1970096767: {
                                                if (!key.equals("seconds")) {
                                                    return this;
                                                }
                                                break Label_0482;
                                            }
                                        }
                                        this.gc.add(1, amount);
                                        return this;
                                    }
                                    this.gc.add(2, amount * 3);
                                    return this;
                                }
                                this.gc.add(2, amount);
                                return this;
                            }
                            this.gc.add(3, amount);
                            return this;
                        }
                        this.gc.add(5, amount);
                        return this;
                    }
                    this.gc.add(11, amount);
                    return this;
                }
                this.gc.add(12, amount);
                return this;
            }
            this.gc.add(13, amount);
            return this;
        }
        this.gc.add(14, amount);
        return this;
    }
    
    public Moment subtract(final int amount, final String key) {
        return this.add(-amount, key);
    }
    
    public Moment startOf(final String key) {
        final Moment moment = new Moment((GregorianCalendar)this.gc.clone());
        switch (key.hashCode()) {
            case -1074026988: {
                if (!key.equals("minute")) {
                    return moment;
                }
                break;
            }
            case -906279820: {
                if (!key.equals("second")) {
                    return moment;
                }
                moment.millisecond(0);
                return moment;
            }
            case 99228: {
                if (!key.equals("day")) {
                    return moment;
                }
                moment.hour(0).minute(0).second(0).millisecond(0);
                return moment;
            }
            case 3208676: {
                if (!key.equals("hour")) {
                    return moment;
                }
                moment.minute(0).second(0).millisecond(0);
                break;
            }
            case 3645428: {
                if (!key.equals("week")) {
                    return moment;
                }
                moment.dayOfWeek(1).hour(0).minute(0).second(0).millisecond(0);
                return moment;
            }
            case 3704893: {
                if (!key.equals("year")) {
                    return moment;
                }
                moment.dayOfYear(1).hour(0).minute(0).second(0).millisecond(0);
                return moment;
            }
            case 104080000: {
                if (!key.equals("month")) {
                    return moment;
                }
                moment.day(1).hour(0).minute(0).second(0).millisecond(0);
                return moment;
            }
        }
        moment.second(0).millisecond(0);
        return moment;
    }
    
    public Moment endOf(final String key) {
        final Moment moment = new Moment();
        switch (key) {
            case "minute": {
                return moment.startOf(key).second(59).millisecond(999);
            }
            case "second": {
                return moment.startOf(key).millisecond(999);
            }
            case "day": {
                return moment.startOf(key).hour(23).minute(59).second(59).millisecond(999);
            }
            case "hour": {
                return moment.startOf(key).minute(59).second(59).millisecond(999);
            }
            case "week": {
                return moment.startOf(key).add(1, "weeks").subtract(1, "days").hour(23).minute(59).second(59).millisecond(999);
            }
            case "year": {
                return moment.startOf(key).add(1, "years").subtract(1, "days").hour(23).minute(59).second(59).millisecond(999);
            }
            case "month": {
                return moment.startOf(key).add(1, "months").subtract(1, "days").hour(23).minute(59).second(59).millisecond(999);
            }
            default:
                break;
        }
        return moment;
    }
    
    public int difference(final Moment moment, final String key) {
        switch (key) {
            case "minute": {
                return (int)((this.millis() - moment.millis()) / 60000L);
            }
            case "second": {
                return (int)((this.millis() - moment.millis()) / 1000L);
            }
            case "day": {
                return (int)((this.millis() - moment.millis()) / 86400000L);
            }
            case "hour": {
                return (int)((this.millis() - moment.millis()) / 3600000L);
            }
            case "year": {
                return this.year() - moment.year();
            }
            case "month": {
                return this.difference(moment, "year") * 12 + (this.month() - moment.month());
            }
            default:
                break;
        }
        return 0;
    }
    
    public boolean gt(final Moment moment) {
        return this.millis() > moment.millis();
    }
    
    public boolean lt(final Moment moment) {
        return this.millis() < moment.millis();
    }
    
    public boolean ge(final Moment moment) {
        return this.millis() >= moment.millis();
    }
    
    public boolean le(final Moment moment) {
        return this.millis() <= moment.millis();
    }
    
    public boolean between(final Moment sMoment, final Moment eMoment) {
        return this.ge(sMoment) && this.le(eMoment);
    }
    
    public Moment fromDate(final Date date) {
        this.gc.setTime(date);
        return this;
    }
}
