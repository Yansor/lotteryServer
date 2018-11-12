package javautils.encrypt;

import org.apache.commons.lang.StringUtils;
import java.util.HashSet;

public class PasswordUtil
{
    private static HashSet<String> SIMPLE_DBPASSWORS;
    private static final String[] SIMPLE_DIGITS;
    
    static {
        PasswordUtil.SIMPLE_DBPASSWORS = new HashSet<String>();
        SIMPLE_DIGITS = new String[] { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z" };
        PasswordUtil.SIMPLE_DBPASSWORS.add(generatePasswordByPlainString("admin"));
        PasswordUtil.SIMPLE_DBPASSWORS.add(generatePasswordByPlainString("admin1"));
        PasswordUtil.SIMPLE_DBPASSWORS.add(generatePasswordByPlainString("admin12"));
        PasswordUtil.SIMPLE_DBPASSWORS.add(generatePasswordByPlainString("admin123"));
        PasswordUtil.SIMPLE_DBPASSWORS.add(generatePasswordByPlainString("admin1234"));
        PasswordUtil.SIMPLE_DBPASSWORS.add(generatePasswordByPlainString("admin12345"));
        PasswordUtil.SIMPLE_DBPASSWORS.add(generatePasswordByPlainString("admin123456"));
        PasswordUtil.SIMPLE_DBPASSWORS.add(generatePasswordByPlainString("admin1234567"));
        PasswordUtil.SIMPLE_DBPASSWORS.add(generatePasswordByPlainString("admin12345678"));
        PasswordUtil.SIMPLE_DBPASSWORS.add(generatePasswordByPlainString("admin123456789"));
        PasswordUtil.SIMPLE_DBPASSWORS.add(generatePasswordByPlainString("admin1234567890"));
        PasswordUtil.SIMPLE_DBPASSWORS.add(generatePasswordByPlainString("admin0123456789"));
        PasswordUtil.SIMPLE_DBPASSWORS.add(generatePasswordByPlainString("12"));
        PasswordUtil.SIMPLE_DBPASSWORS.add(generatePasswordByPlainString("123"));
        PasswordUtil.SIMPLE_DBPASSWORS.add(generatePasswordByPlainString("1234"));
        PasswordUtil.SIMPLE_DBPASSWORS.add(generatePasswordByPlainString("12345"));
        PasswordUtil.SIMPLE_DBPASSWORS.add(generatePasswordByPlainString("123456"));
        PasswordUtil.SIMPLE_DBPASSWORS.add(generatePasswordByPlainString("1234567"));
        PasswordUtil.SIMPLE_DBPASSWORS.add(generatePasswordByPlainString("12345678"));
        PasswordUtil.SIMPLE_DBPASSWORS.add(generatePasswordByPlainString("123456789"));
        for (int i = 0; i < 10; ++i) {
            PasswordUtil.SIMPLE_DBPASSWORS.add(generatePasswordByPlainString(StringUtils.repeat(new StringBuilder(String.valueOf(i)).toString(), 6)));
        }
        String[] simple_DIGITS;
        for (int length = (simple_DIGITS = PasswordUtil.SIMPLE_DIGITS).length, j = 0; j < length; ++j) {
            final String simpleDigit = simple_DIGITS[j];
            PasswordUtil.SIMPLE_DBPASSWORS.add(generatePasswordByPlainString(StringUtils.repeat(simpleDigit, 6)));
            PasswordUtil.SIMPLE_DBPASSWORS.add(generatePasswordByPlainString(String.valueOf(simpleDigit) + "12345"));
            PasswordUtil.SIMPLE_DBPASSWORS.add(generatePasswordByPlainString(String.valueOf(simpleDigit) + "123456"));
            PasswordUtil.SIMPLE_DBPASSWORS.add(generatePasswordByPlainString(String.valueOf(simpleDigit) + "1234567"));
            PasswordUtil.SIMPLE_DBPASSWORS.add(generatePasswordByPlainString(String.valueOf(simpleDigit) + "12345678"));
            PasswordUtil.SIMPLE_DBPASSWORS.add(generatePasswordByPlainString(String.valueOf(simpleDigit) + "123456789"));
            PasswordUtil.SIMPLE_DBPASSWORS.add(generatePasswordByPlainString(String.valueOf(StringUtils.repeat(simpleDigit, 2)) + "1234"));
            PasswordUtil.SIMPLE_DBPASSWORS.add(generatePasswordByPlainString(String.valueOf(StringUtils.repeat(simpleDigit, 2)) + "12345"));
            PasswordUtil.SIMPLE_DBPASSWORS.add(generatePasswordByPlainString(String.valueOf(StringUtils.repeat(simpleDigit, 2)) + "123456"));
            PasswordUtil.SIMPLE_DBPASSWORS.add(generatePasswordByPlainString(String.valueOf(StringUtils.repeat(simpleDigit, 2)) + "1234567"));
            PasswordUtil.SIMPLE_DBPASSWORS.add(generatePasswordByPlainString(String.valueOf(StringUtils.repeat(simpleDigit, 2)) + "12345678"));
            PasswordUtil.SIMPLE_DBPASSWORS.add(generatePasswordByPlainString(String.valueOf(StringUtils.repeat(simpleDigit, 2)) + "123456789"));
            PasswordUtil.SIMPLE_DBPASSWORS.add(generatePasswordByPlainString(String.valueOf(StringUtils.repeat(simpleDigit, 3)) + "123"));
            PasswordUtil.SIMPLE_DBPASSWORS.add(generatePasswordByPlainString(String.valueOf(StringUtils.repeat(simpleDigit, 3)) + "1234"));
            PasswordUtil.SIMPLE_DBPASSWORS.add(generatePasswordByPlainString(String.valueOf(StringUtils.repeat(simpleDigit, 3)) + "12345"));
            PasswordUtil.SIMPLE_DBPASSWORS.add(generatePasswordByPlainString(String.valueOf(StringUtils.repeat(simpleDigit, 3)) + "123456"));
            PasswordUtil.SIMPLE_DBPASSWORS.add(generatePasswordByPlainString(String.valueOf(StringUtils.repeat(simpleDigit, 3)) + "1234567"));
            PasswordUtil.SIMPLE_DBPASSWORS.add(generatePasswordByPlainString(String.valueOf(StringUtils.repeat(simpleDigit, 3)) + "12345678"));
            PasswordUtil.SIMPLE_DBPASSWORS.add(generatePasswordByPlainString(String.valueOf(StringUtils.repeat(simpleDigit, 3)) + "123456789"));
            PasswordUtil.SIMPLE_DBPASSWORS.add(generatePasswordByPlainString(String.valueOf(StringUtils.repeat(simpleDigit, 4)) + "12"));
            PasswordUtil.SIMPLE_DBPASSWORS.add(generatePasswordByPlainString(String.valueOf(StringUtils.repeat(simpleDigit, 4)) + "123"));
            PasswordUtil.SIMPLE_DBPASSWORS.add(generatePasswordByPlainString(String.valueOf(StringUtils.repeat(simpleDigit, 4)) + "1234"));
            PasswordUtil.SIMPLE_DBPASSWORS.add(generatePasswordByPlainString(String.valueOf(StringUtils.repeat(simpleDigit, 4)) + "12345"));
            PasswordUtil.SIMPLE_DBPASSWORS.add(generatePasswordByPlainString(String.valueOf(StringUtils.repeat(simpleDigit, 4)) + "123456"));
            PasswordUtil.SIMPLE_DBPASSWORS.add(generatePasswordByPlainString(String.valueOf(StringUtils.repeat(simpleDigit, 4)) + "1234567"));
            PasswordUtil.SIMPLE_DBPASSWORS.add(generatePasswordByPlainString(String.valueOf(StringUtils.repeat(simpleDigit, 4)) + "12345678"));
            PasswordUtil.SIMPLE_DBPASSWORS.add(generatePasswordByPlainString(String.valueOf(StringUtils.repeat(simpleDigit, 4)) + "123456789"));
        }
    }
    
    public static boolean isSimplePasswordForGenerate(final String password) {
        final String dbPassword = generatePasswordByMD5(password);
        return isSimplePassword(dbPassword);
    }
    
    public static boolean isSimplePassword(final String dbPassword) {
        return PasswordUtil.SIMPLE_DBPASSWORS.contains(dbPassword);
    }
    
    public static String generatePasswordByMD5(final String md5String) {
        return MD5Util.getMD5Code(md5String).toUpperCase();
    }
    
    public static String generatePasswordByPlainString(final String plainString) {
        String password = MD5Util.getMD5Code(plainString).toUpperCase();
        password = MD5Util.getMD5Code(password).toUpperCase();
        password = MD5Util.getMD5Code(password).toUpperCase();
        return password;
    }
    
    public static boolean validatePassword(final String dbPassword, final String token, final String md5String) {
        final String dbPasswordWithToken = MD5Util.getMD5Code(String.valueOf(dbPassword) + token).toUpperCase();
        return md5String.equals(dbPasswordWithToken);
    }
    
    public static boolean validateSamePassword(final String dbPassword, final String md5String) {
        final String inputPassword = generatePasswordByMD5(md5String);
        return dbPassword.equals(inputPassword);
    }
    
    public static void main(final String[] args) {
        String password = "123456";
        password = generatePasswordByPlainString(password);
        System.out.println(password);
    }
}
