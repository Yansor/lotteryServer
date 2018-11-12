package javautils.date;

import java.util.List;
import java.util.ArrayList;

public class DateRangeUtil
{
    public static void main(final String[] args) {
        final String[] days = listDate("2017-05-04", "2017-05-10");
        String[] array;
        for (int length = (array = days).length, i = 0; i < length; ++i) {
            final String string = array[i];
            System.out.println(string);
        }
    }
    
    public static String[] listDate(final String sDate, final String eDate) {
        final Moment sMoment = new Moment().fromDate(sDate);
        final Moment eMoment = new Moment().fromDate(eDate);
        final List<String> list = new ArrayList<String>();
        if (sMoment.le(eMoment)) {
            list.add(sMoment.toSimpleDate());
            for (int days = eMoment.difference(sMoment, "day"), i = 0; i < days; ++i) {
                list.add(sMoment.add(1, "days").toSimpleDate());
            }
        }
        final String[] array = new String[list.size()];
        for (int i = 0; i < list.size(); ++i) {
            array[i] = list.get(i);
        }
        return array;
    }
    
    public static String[] listDateExceptLastDay(final String sDate, final String eDate) {
        final Moment sMoment = new Moment().fromDate(sDate);
        final Moment eMoment = new Moment().fromDate(eDate);
        final List<String> list = new ArrayList<String>();
        if (sMoment.le(eMoment)) {
            list.add(sMoment.toSimpleDate());
            for (int days = eMoment.difference(sMoment, "day"), i = 0; i < days - 1; ++i) {
                list.add(sMoment.add(1, "days").toSimpleDate());
            }
        }
        final String[] array = new String[list.size()];
        for (int i = 0; i < list.size(); ++i) {
            array[i] = list.get(i);
        }
        return array;
    }
}
