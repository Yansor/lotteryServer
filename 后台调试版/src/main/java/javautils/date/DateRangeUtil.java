package javautils.date;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class DateRangeUtil
{
  public static void main(String[] args)
  {
    String[] days = listDate("2017-05-04", "2017-05-10");
    String[] arrayOfString1;
    int j = (arrayOfString1 = days).length;
    for (int i = 0; i < j; i++)
    {
      String string = arrayOfString1[i];
      System.out.println(string);
    }
  }
  
  public static String[] listDate(String sDate, String eDate)
  {
    Moment sMoment = new Moment().fromDate(sDate);
    Moment eMoment = new Moment().fromDate(eDate);
    List<String> list = new ArrayList();
    if (sMoment.le(eMoment))
    {
      list.add(sMoment.toSimpleDate());
      int days = eMoment.difference(sMoment, "day");
      for (int i = 0; i < days; i++) {
        list.add(sMoment.add(1, "days").toSimpleDate());
      }
    }
    String[] array = new String[list.size()];
    for (int i = 0; i < list.size(); i++) {
      array[i] = ((String)list.get(i));
    }
    return array;
  }
  
  public static String[] listDateExceptLastDay(String sDate, String eDate)
  {
    Moment sMoment = new Moment().fromDate(sDate);
    Moment eMoment = new Moment().fromDate(eDate);
    List<String> list = new ArrayList();
    if (sMoment.le(eMoment))
    {
      list.add(sMoment.toSimpleDate());
      int days = eMoment.difference(sMoment, "day");
      for (int i = 0; i < days - 1; i++) {
        list.add(sMoment.add(1, "days").toSimpleDate());
      }
    }
    String[] array = new String[list.size()];
    for (int i = 0; i < list.size(); i++) {
      array[i] = ((String)list.get(i));
    }
    return array;
  }
}
