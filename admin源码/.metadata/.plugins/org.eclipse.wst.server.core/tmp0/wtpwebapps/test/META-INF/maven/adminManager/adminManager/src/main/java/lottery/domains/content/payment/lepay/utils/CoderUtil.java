package lottery.domains.content.payment.lepay.utils;

import org.apache.commons.codec.binary.Base64;

public class CoderUtil
{
    public static byte[] decryptBASE64(final String key) {
        final byte[] b = key.getBytes();
        final Base64 base64 = new Base64();
        return base64.decode(b);
    }
    
    public static String encryptBASE64(final byte[] key) throws Exception {
        final Base64 base64 = new Base64();
        final byte[] b = base64.encode(key);
        final String s = new String(b);
        return s;
    }
}
