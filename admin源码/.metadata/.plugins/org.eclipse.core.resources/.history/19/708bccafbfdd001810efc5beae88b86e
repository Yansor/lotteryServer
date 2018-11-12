package javautils.encrypt;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.RandomStringUtils;
import javautils.date.Moment;

public class TokenUtil
{
    public static String generateDisposableToken() {
        String tokenStr = String.valueOf(new Moment().format("yyyyMMddHHmmss")) + RandomStringUtils.random(8, true, true);
        tokenStr = DigestUtils.md5Hex(tokenStr).toUpperCase();
        return tokenStr;
    }
}
