package lottery.domains.content.payment.tgf.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil
{
  public static final String dtLong = "yyyyMMddHHmmss";
  public static final String simple = "yyyy-MM-dd HH:mm:ss";
  public static final String dtShort = "yyyyMMdd";
  
  public static String getOrderNum()
  {
    Date date = new Date();
    DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
    return df.format(date);
  }
  
  public static String getDateFormatter()
  {
    Date date = new Date();
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    return df.format(date);
  }
  
  public static String getDate()
  {
    Date date = new Date();
    DateFormat df = new SimpleDateFormat("yyyyMMdd");
    return df.format(date);
  }
}
