package javautils.encrypt;

import java.io.PrintStream;
import java.util.HashSet;
import org.apache.commons.lang.StringUtils;

public class PasswordUtil
{
  private static HashSet<String> SIMPLE_DBPASSWORS = new HashSet();
  private static final String[] SIMPLE_DIGITS = { "A", "B", "C", "D", "E", "F", 
    "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "a", "b", "c", "d", "e", "f", 
    "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z" };
  
  static
  {
    SIMPLE_DBPASSWORS.add(generatePasswordByPlainString("admin"));
    SIMPLE_DBPASSWORS.add(generatePasswordByPlainString("admin1"));
    SIMPLE_DBPASSWORS.add(generatePasswordByPlainString("admin12"));
    SIMPLE_DBPASSWORS.add(generatePasswordByPlainString("admin123"));
    SIMPLE_DBPASSWORS.add(generatePasswordByPlainString("admin1234"));
    SIMPLE_DBPASSWORS.add(generatePasswordByPlainString("admin12345"));
    SIMPLE_DBPASSWORS.add(generatePasswordByPlainString("admin123456"));
    SIMPLE_DBPASSWORS.add(generatePasswordByPlainString("admin1234567"));
    SIMPLE_DBPASSWORS.add(generatePasswordByPlainString("admin12345678"));
    SIMPLE_DBPASSWORS.add(generatePasswordByPlainString("admin123456789"));
    SIMPLE_DBPASSWORS.add(generatePasswordByPlainString("admin1234567890"));
    SIMPLE_DBPASSWORS.add(generatePasswordByPlainString("admin0123456789"));
    SIMPLE_DBPASSWORS.add(generatePasswordByPlainString("12"));
    SIMPLE_DBPASSWORS.add(generatePasswordByPlainString("123"));
    SIMPLE_DBPASSWORS.add(generatePasswordByPlainString("1234"));
    SIMPLE_DBPASSWORS.add(generatePasswordByPlainString("12345"));
    SIMPLE_DBPASSWORS.add(generatePasswordByPlainString("123456"));
    SIMPLE_DBPASSWORS.add(generatePasswordByPlainString("1234567"));
    SIMPLE_DBPASSWORS.add(generatePasswordByPlainString("12345678"));
    SIMPLE_DBPASSWORS.add(generatePasswordByPlainString("123456789"));
    for (int i = 0; i < 10; i++) {
      SIMPLE_DBPASSWORS.add(generatePasswordByPlainString(StringUtils.repeat(i+"", 6)));
    }
    String[] arrayOfString;
    int j = (arrayOfString = SIMPLE_DIGITS).length;
    for (int i = 0; i < j; i++)
    {
      String simpleDigit = arrayOfString[i];
      SIMPLE_DBPASSWORS.add(generatePasswordByPlainString(StringUtils.repeat(simpleDigit, 6)));
      
      SIMPLE_DBPASSWORS.add(generatePasswordByPlainString(simpleDigit + "12345"));
      SIMPLE_DBPASSWORS.add(generatePasswordByPlainString(simpleDigit + "123456"));
      SIMPLE_DBPASSWORS.add(generatePasswordByPlainString(simpleDigit + "1234567"));
      SIMPLE_DBPASSWORS.add(generatePasswordByPlainString(simpleDigit + "12345678"));
      SIMPLE_DBPASSWORS.add(generatePasswordByPlainString(simpleDigit + "123456789"));
      
      SIMPLE_DBPASSWORS.add(generatePasswordByPlainString(StringUtils.repeat(simpleDigit, 2) + "1234"));
      SIMPLE_DBPASSWORS.add(generatePasswordByPlainString(StringUtils.repeat(simpleDigit, 2) + "12345"));
      SIMPLE_DBPASSWORS.add(generatePasswordByPlainString(StringUtils.repeat(simpleDigit, 2) + "123456"));
      SIMPLE_DBPASSWORS.add(generatePasswordByPlainString(StringUtils.repeat(simpleDigit, 2) + "1234567"));
      SIMPLE_DBPASSWORS.add(generatePasswordByPlainString(StringUtils.repeat(simpleDigit, 2) + "12345678"));
      SIMPLE_DBPASSWORS.add(generatePasswordByPlainString(StringUtils.repeat(simpleDigit, 2) + "123456789"));
      
      SIMPLE_DBPASSWORS.add(generatePasswordByPlainString(StringUtils.repeat(simpleDigit, 3) + "123"));
      SIMPLE_DBPASSWORS.add(generatePasswordByPlainString(StringUtils.repeat(simpleDigit, 3) + "1234"));
      SIMPLE_DBPASSWORS.add(generatePasswordByPlainString(StringUtils.repeat(simpleDigit, 3) + "12345"));
      SIMPLE_DBPASSWORS.add(generatePasswordByPlainString(StringUtils.repeat(simpleDigit, 3) + "123456"));
      SIMPLE_DBPASSWORS.add(generatePasswordByPlainString(StringUtils.repeat(simpleDigit, 3) + "1234567"));
      SIMPLE_DBPASSWORS.add(generatePasswordByPlainString(StringUtils.repeat(simpleDigit, 3) + "12345678"));
      SIMPLE_DBPASSWORS.add(generatePasswordByPlainString(StringUtils.repeat(simpleDigit, 3) + "123456789"));
      
      SIMPLE_DBPASSWORS.add(generatePasswordByPlainString(StringUtils.repeat(simpleDigit, 4) + "12"));
      SIMPLE_DBPASSWORS.add(generatePasswordByPlainString(StringUtils.repeat(simpleDigit, 4) + "123"));
      SIMPLE_DBPASSWORS.add(generatePasswordByPlainString(StringUtils.repeat(simpleDigit, 4) + "1234"));
      SIMPLE_DBPASSWORS.add(generatePasswordByPlainString(StringUtils.repeat(simpleDigit, 4) + "12345"));
      SIMPLE_DBPASSWORS.add(generatePasswordByPlainString(StringUtils.repeat(simpleDigit, 4) + "123456"));
      SIMPLE_DBPASSWORS.add(generatePasswordByPlainString(StringUtils.repeat(simpleDigit, 4) + "1234567"));
      SIMPLE_DBPASSWORS.add(generatePasswordByPlainString(StringUtils.repeat(simpleDigit, 4) + "12345678"));
      SIMPLE_DBPASSWORS.add(generatePasswordByPlainString(StringUtils.repeat(simpleDigit, 4) + "123456789"));
    }
  }
  
  public static boolean isSimplePasswordForGenerate(String password)
  {
    String dbPassword = generatePasswordByMD5(password);
    return isSimplePassword(dbPassword);
  }
  
  public static boolean isSimplePassword(String dbPassword)
  {
    return SIMPLE_DBPASSWORS.contains(dbPassword);
  }
  
  public static String generatePasswordByMD5(String md5String)
  {
    return MD5Util.getMD5Code(md5String).toUpperCase();
  }
  
  public static String generatePasswordByPlainString(String plainString)
  {
    String password = MD5Util.getMD5Code(plainString).toUpperCase();
    
    password = MD5Util.getMD5Code(password).toUpperCase();
    
    password = MD5Util.getMD5Code(password).toUpperCase();
    
    return password;
  }
  
  public static boolean validatePassword(String dbPassword, String token, String md5String)
  {
    String dbPasswordWithToken = MD5Util.getMD5Code(dbPassword + token).toUpperCase();
    
    return md5String.equals(dbPasswordWithToken);
  }
  
  public static boolean validateSamePassword(String dbPassword, String md5String)
  {
    String inputPassword = generatePasswordByMD5(md5String);
    
    return dbPassword.equals(inputPassword);
  }
  
  public static void main(String[] args)
  {
    String password = "123456";
    password = generatePasswordByPlainString(password);
    System.out.println(password);
  }
}
