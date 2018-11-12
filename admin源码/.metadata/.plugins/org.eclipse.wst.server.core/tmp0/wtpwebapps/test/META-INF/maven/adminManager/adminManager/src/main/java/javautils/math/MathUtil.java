package javautils.math;

import java.math.RoundingMode;
import java.math.BigDecimal;

public class MathUtil
{
    public static double add(final double a, final double b) {
        final BigDecimal b2 = new BigDecimal(Double.toString(a));
        final BigDecimal b3 = new BigDecimal(Double.toString(b));
        return b2.add(b3).doubleValue();
    }
    
    public static double subtract(final double a, final double b) {
        final BigDecimal b2 = new BigDecimal(Double.toString(a));
        final BigDecimal b3 = new BigDecimal(Double.toString(b));
        return b2.subtract(b3).doubleValue();
    }
    
    public static double multiply(final double a, final double b) {
        final BigDecimal b2 = new BigDecimal(Double.toString(a));
        final BigDecimal b3 = new BigDecimal(Double.toString(b));
        return b2.multiply(b3).doubleValue();
    }
    
    public static double divide(final double v1, final double v2, final int scale) {
        final BigDecimal b1 = new BigDecimal(Double.toString(v1));
        final BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2, scale, 5).doubleValue();
    }
    
    public static void main(final String[] args) {
        System.out.println(doubleToStringDown(1.4, 1));
    }
    
    public static double decimalFormat(final BigDecimal bd, final int point) {
        return bd.setScale(point, 5).doubleValue();
    }
    
    public static double doubleFormat(final double d, final int point) {
        try {
            final BigDecimal bd = new BigDecimal(d);
            return bd.setScale(point, 5).doubleValue();
        }
        catch (Exception e) {
            e.printStackTrace();
            return d;
        }
    }
    
    public static String doubleToString(final double d, final int point) {
        final BigDecimal bd = new BigDecimal(d);
        return bd.setScale(point, 5).toString();
    }
    
    public static String doubleToStringDown(final double d, final int point) {
        final BigDecimal bd = new BigDecimal(d);
        return bd.setScale(point, RoundingMode.DOWN).toString();
    }
    
    public static float floatFormat(final float f, final int point) {
        try {
            final BigDecimal bd = new BigDecimal(f);
            return bd.setScale(point, 5).floatValue();
        }
        catch (Exception e) {
            e.printStackTrace();
            return f;
        }
    }
}
