package javautils.lzma;

import SevenZip.Compression.LZMA.Decoder;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class LZMAJsUtil
{
  public static byte[] hex2byte(String s)
  {
    int len = s.length();
    byte[] data = new byte[len / 2];
    for (int i = 0; i < len; i += 2) {
      data[(i / 2)] = 
        ((byte)((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16)));
    }
    return data;
  }
  
  public static String decompress(String hexString)
  {
    try
    {
      byte[] data = hex2byte(hexString);
      ByteArrayInputStream input = new ByteArrayInputStream(data);
      ByteArrayOutputStream output = new ByteArrayOutputStream();
      decode(input, output);
      byte[] result = output.toByteArray();
      if ((result != null) && (result.length > 0)) {
        return new String(result);
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return null;
  }
  
  public static void decode(InputStream input, OutputStream output)
    throws IOException
  {
    byte[] properties = new byte[5];
    for (int i = 0; i < properties.length; i++)
    {
      int r = input.read();
      if (r == -1) {
        throw new IOException("truncated input");
      }
      properties[i] = ((byte)r);
    }
    Decoder decoder = new Decoder();
    if (!decoder.SetDecoderProperties(properties)) {
      throw new IOException("corrupted input");
    }
    long expectedLength = -1L;
    for (int i = 0; i < 64; i += 8)
    {
      int r = input.read();
      if (r == -1) {
        throw new IOException("truncated input");
      }
      expectedLength |= r << i;
    }
    decoder.Code(input, output, expectedLength);
  }
  
  public static void main(String[] args) {}
}
