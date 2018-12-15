package lottery.domains.content.payment.utils;

import java.io.PrintStream;
import java.text.DecimalFormat;

public class MoneyFormat
{
  public static String FormatPay41(String money)
  {
    return moneyToYuanForPositive(money);
  }
  
  public static String fonmatDinpay(String money)
  {
    return moneyToYuanForPositive(money);
  }
  
  public static String fonmatMobao(String money)
  {
    return moneyToYuanForPositive(money);
  }
  
  public static String moneyToYuanForPositive(String money)
  {
    if (money == null) {
      return "0.00";
    }
    String fist = money.substring(0, money.indexOf("."));
    String cosp = money.substring(money.indexOf(".") + 1, money.length());
    if (money.contains("."))
    {
      if (cosp.length() >= 2)
      {
        StringBuffer bf = new StringBuffer(fist);
        String mt = cosp.substring(0, 2);
        return "." + mt;
      }
      StringBuffer bf = new StringBuffer(fist);
      bf.append(".").append(cosp).append("0");
      return bf.toString();
    }
    StringBuffer bf = new StringBuffer(money);
    bf.append(".00");
    return bf.toString();
  }
  
  public static String pasMoney(Double money)
  {
    DecimalFormat df = new DecimalFormat("#########0.00");
    return df.format(money);
  }
  
  public static long yuanToFenMoney(String yuan)
  {
    if ((yuan == null) || (yuan.length() <= 0)) {
      return 0L;
    }
    try
    {
      int pIdx = yuan.indexOf(".");
      int len = yuan.length();
      String fixed = yuan.replaceAll("\\.", "");
      if ((pIdx < 0) || (pIdx == len - 1)) {
        return Long.valueOf(fixed + "00").longValue();
      }
      if (pIdx == len - 2) {
        return Long.valueOf(fixed + "0").longValue();
      }
      return Long.valueOf(fixed.substring(0, pIdx + 2)).longValue();
    }
    catch (Exception e) {}
    return 0L;
  }
  
  public static void main(String[] args)
  {
    System.out.println(yuanToFenMoney("100.00"));
  }
}
