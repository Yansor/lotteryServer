package javautils.encrypt;

public class PaymentChannelEncrypt
{
    private static final DESUtil DES;
    private static final String DES_KEY = "#f$ddw4aFF2Wfgaewdff#GR0(DSFW@#?!@23#!@#a";
    
    static {
        DES = DESUtil.getInstance();
    }
    
    public static void main(final String[] args) {
        final String encrypt = encrypt("c19449a5-ec38-45d2-adf4-b34d16017317");
        final String decrypt = decrypt(encrypt);
        System.out.println(encrypt);
        System.out.println(decrypt);
    }
    
    public static String encrypt(final String str) {
        try {
            return PaymentChannelEncrypt.DES.encryptStr(str, "#f$ddw4aFF2Wfgaewdff#GR0(DSFW@#?!@23#!@#a");
        }
        catch (Exception e) {
            System.out.println("加密充值通道时出错");
            e.printStackTrace();
            return null;
        }
    }
    
    public static String decrypt(final String str) {
        try {
            return PaymentChannelEncrypt.DES.decryptStr(str, "#f$ddw4aFF2Wfgaewdff#GR0(DSFW@#?!@23#!@#a");
        }
        catch (Exception e) {
            System.out.println("解密充值通道时出错");
            e.printStackTrace();
            return null;
        }
    }
}
