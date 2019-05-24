package javautils.encrypt;

import javautils.date.Moment;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.RandomStringUtils;

public class TokenUtil
{
  public static String generateDisposableToken()
  {
    String tokenStr = new Moment().format("yyyyMMddHHmmss") + RandomStringUtils.random(8, true, true);
    tokenStr = DigestUtils.md5Hex(tokenStr).toUpperCase();
    return tokenStr;
  }
}
