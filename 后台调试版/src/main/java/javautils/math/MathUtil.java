package javautils.math;

import java.io.PrintStream;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class MathUtil
{
  public static double add(double a, double b)
  {
    BigDecimal b1 = new BigDecimal(Double.toString(a));
    BigDecimal b2 = new BigDecimal(Double.toString(b));
    return b1.add(b2).doubleValue();
  }
  
  public static double subtract(double a, double b)
  {
    BigDecimal b1 = new BigDecimal(Double.toString(a));
    BigDecimal b2 = new BigDecimal(Double.toString(b));
    return b1.subtract(b2).doubleValue();
  }
  
  public static double multiply(double a, double b)
  {
    BigDecimal b1 = new BigDecimal(Double.toString(a));
    BigDecimal b2 = new BigDecimal(Double.toString(b));
    return b1.multiply(b2).doubleValue();
  }
  
  public static double divide(double v1, double v2, int scale)
  {
    BigDecimal b1 = new BigDecimal(Double.toString(v1));
    BigDecimal b2 = new BigDecimal(Double.toString(v2));
    return b1.divide(b2, scale, 5).doubleValue();
  }
  
  public static void main(String[] args)
  {
    System.out.println(doubleToStringDown(1.4D, 1));
  }
  
  public static double decimalFormat(BigDecimal bd, int point)
  {
    return bd.setScale(point, 5).doubleValue();
  }
  
  public static double doubleFormat(double d, int point)
  {
    try
    {
      BigDecimal bd = new BigDecimal(d);
      return bd.setScale(point, 5).doubleValue();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return d;
  }
  
  public static String doubleToString(double d, int point)
  {
    BigDecimal bd = new BigDecimal(d);
    return bd.setScale(point, 5).toString();
  }
  
  public static String doubleToStringDown(double d, int point)
  {
    BigDecimal bd = new BigDecimal(d);
    return bd.setScale(point, RoundingMode.DOWN).toString();
  }
  
  public static float floatFormat(float f, int point)
  {
    try
    {
      BigDecimal bd = new BigDecimal(f);
      return bd.setScale(point, 5).floatValue();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return f;
  }
}
