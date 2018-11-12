package lottery.domains.content.payment.utils;

import java.text.DecimalFormat;

public class MoneyFormat
{
    public static String FormatPay41(final String money) {
        return moneyToYuanForPositive(money);
    }
    
    public static String fonmatDinpay(final String money) {
        return moneyToYuanForPositive(money);
    }
    
    public static String fonmatMobao(final String money) {
        return moneyToYuanForPositive(money);
    }
    
    public static String moneyToYuanForPositive(final String money) {
        if (money == null) {
            return "0.00";
        }
        final String fist = money.substring(0, money.indexOf("."));
        final String cosp = money.substring(money.indexOf(".") + 1, money.length());
        if (!money.contains(".")) {
            final StringBuffer bf = new StringBuffer(money);
            bf.append(".00");
            return bf.toString();
        }
        if (cosp.length() >= 2) {
            final StringBuffer bf = new StringBuffer(fist);
            final String mt = cosp.substring(0, 2);
            return bf.append(".").append(mt).toString();
        }
        final StringBuffer bf = new StringBuffer(fist);
        bf.append(".").append(cosp).append("0");
        return bf.toString();
    }
    
    public static String pasMoney(final Double money) {
        final DecimalFormat df = new DecimalFormat("#########0.00");
        return df.format(money);
    }
    
    public static long yuanToFenMoney(final String yuan) {
        if (yuan == null || yuan.length() <= 0) {
            return 0L;
        }
        try {
            final int pIdx = yuan.indexOf(".");
            final int len = yuan.length();
            final String fixed = yuan.replaceAll("\\.", "");
            if (pIdx < 0 || pIdx == len - 1) {
                return Long.valueOf(String.valueOf(fixed) + "00");
            }
            if (pIdx == len - 2) {
                return Long.valueOf(String.valueOf(fixed) + "0");
            }
            return Long.valueOf(fixed.substring(0, pIdx + 2));
        }
        catch (Exception e) {
            return 0L;
        }
    }
    
    public static void main(final String[] args) {
        System.out.println(yuanToFenMoney("100.00"));
    }
}
