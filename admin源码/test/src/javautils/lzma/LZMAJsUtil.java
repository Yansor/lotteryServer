package javautils.lzma;

import SevenZip.Compression.LZMA.Decoder;
import java.io.IOException;
import java.io.OutputStream;
import java.io.InputStream;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;

public class LZMAJsUtil
{
    public static byte[] hex2byte(final String s) {
        final int len = s.length();
        final byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte)((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }
    
    public static String decompress(final String hexString) {
        try {
            final byte[] data = hex2byte(hexString);
            final ByteArrayInputStream input = new ByteArrayInputStream(data);
            final ByteArrayOutputStream output = new ByteArrayOutputStream();
            decode(input, output);
            final byte[] result = output.toByteArray();
            if (result != null && result.length > 0) {
                return new String(result);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static void decode(final InputStream input, final OutputStream output) throws IOException {
        final byte[] properties = new byte[5];
        for (int i = 0; i < properties.length; ++i) {
            final int r = input.read();
            if (r == -1) {
                throw new IOException("truncated input");
            }
            properties[i] = (byte)r;
        }
        final Decoder decoder = new Decoder();
        if (!decoder.SetDecoderProperties(properties)) {
            throw new IOException("corrupted input");
        }
        long expectedLength = -1L;
        for (int j = 0; j < 64; j += 8) {
            final int r2 = input.read();
            if (r2 == -1) {
                throw new IOException("truncated input");
            }
            expectedLength |= (long)r2 << j;
        }
        decoder.Code(input, output, expectedLength);
    }
    
    public static void main(final String[] args) {
    }
}
