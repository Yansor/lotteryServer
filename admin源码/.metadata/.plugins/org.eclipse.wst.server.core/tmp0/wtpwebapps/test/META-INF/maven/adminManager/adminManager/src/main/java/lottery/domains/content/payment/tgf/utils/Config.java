package lottery.domains.content.payment.tgf.utils;

public class Config
{
    public static final String SIGN_TYPE = "MD5";
    public static String PFX_FILE;
    public static String CERT_FILE;
    public static String PASSWD;
    public static final String KEY = "";
    public static final String MERCHANT_ID = "";
    public static final String MERCHANT_NOTIFY_URL = "";
    public static final String GATEWAY_URL = "";
    public static final String API_VERSION = "1.0.0.0";
    public static final String APINAME_PAY = "TRADE.B2C";
    public static final String APINAME_SCANPAY = "TRADE.SCANPAY";
    public static final String APINAME_H5PAY = "TRADE.H5PAY";
    public static final String APINAME_QUICKPAY_APPLY = "TRADE.QUICKPAY.APPLY";
    public static final String APINAME_QUERY = "TRADE.QUERY";
    public static final String APINAME_REFUND = "TRADE.REFUND";
    public static final String APINAME_SETTLE = "TRADE.SETTLE";
    public static final String APINAME_SETTLE_QUERY = "TRADE.SETTLE.QUERY";
    public static final String APINAME_NOTIFY = "TRADE.NOTIFY";
    public static final String APINAME_BALANCE_QUERY = "BALANCE.QUERY";
    public static final String APINAME_QUICKPAY_CONFIRM = "TRADE.QUICKPAY.CONFIRM";
    
    static {
        Config.PFX_FILE = "c:/temp/test/mzh.pfx";
        Config.CERT_FILE = "c:/temp/test/epay.cer";
        Config.PASSWD = "mzh";
    }
}
