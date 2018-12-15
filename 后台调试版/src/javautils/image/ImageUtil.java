package javautils.image;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import sun.misc.BASE64Encoder;

public class ImageUtil
{
  public static String encodeQR(String signature, int height, int width)
  {
    ByteArrayOutputStream outputStream = null;
    try
    {
      Map<EncodeHintType, Object> hints = new HashMap();
      hints.put(EncodeHintType.MARGIN, Integer.valueOf(1));
      
      QRCodeWriter writer = new QRCodeWriter();
      BitMatrix bitMatrix = writer.encode(signature, BarcodeFormat.QR_CODE, height, width, hints);
      
      outputStream = new ByteArrayOutputStream();
      MatrixToImageWriter.writeToStream(bitMatrix, "png", outputStream);
      
      BASE64Encoder encoder = new BASE64Encoder();
      return "data:image/png;base64," + encoder.encode(outputStream.toByteArray());
    }
    catch (Exception e)
    {
      e.printStackTrace();
      return null;
    }
    finally
    {
      if (outputStream != null) {
        try
        {
          outputStream.close();
        }
        catch (IOException e)
        {
          e.printStackTrace();
        }
      }
    }
  }
  
  private static BitMatrix deleteWhite(BitMatrix matrix)
  {
    int[] rec = matrix.getEnclosingRectangle();
    int resWidth = rec[2] + 1;
    int resHeight = rec[3] + 1;
    
    BitMatrix resMatrix = new BitMatrix(resWidth, resHeight);
    resMatrix.clear();
    for (int i = 0; i < resWidth; i++) {
      for (int j = 0; j < resHeight; j++) {
        if (matrix.get(i + rec[0], j + rec[1])) {
          resMatrix.set(i, j);
        }
      }
    }
    return resMatrix;
  }
  
  public static void main(String[] args)
  {
    for (int i = 0; i < 100; i++)
    {
      long start = System.currentTimeMillis();
      
      encodeQR("http://static.hs.com/static/images/m_01.png", 200, 200);
      
      long spent = System.currentTimeMillis() - start;
      
      System.out.println("耗时" + spent);
    }
  }
}
