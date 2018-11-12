package javautils.image;

import com.google.zxing.common.BitMatrix;
import java.io.IOException;
import sun.misc.BASE64Encoder;
import java.io.OutputStream;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import java.io.ByteArrayOutputStream;
import java.util.Map;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.EncodeHintType;
import java.util.HashMap;

public class ImageUtil
{
    public static String encodeQR(final String signature, final int height, final int width) {
        ByteArrayOutputStream outputStream = null;
        try {
            final Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
            hints.put(EncodeHintType.MARGIN, 1);
            final QRCodeWriter writer = new QRCodeWriter();
            final BitMatrix bitMatrix = writer.encode(signature, BarcodeFormat.QR_CODE, height, width, (Map)hints);
            outputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "png", (OutputStream)outputStream);
            final BASE64Encoder encoder = new BASE64Encoder();
            return "data:image/png;base64," + encoder.encode(outputStream.toByteArray());
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                }
                catch (IOException e2) {
                    e2.printStackTrace();
                }
            }
        }
    }
    
    private static BitMatrix deleteWhite(final BitMatrix matrix) {
        final int[] rec = matrix.getEnclosingRectangle();
        final int resWidth = rec[2] + 1;
        final int resHeight = rec[3] + 1;
        final BitMatrix resMatrix = new BitMatrix(resWidth, resHeight);
        resMatrix.clear();
        for (int i = 0; i < resWidth; ++i) {
            for (int j = 0; j < resHeight; ++j) {
                if (matrix.get(i + rec[0], j + rec[1])) {
                    resMatrix.set(i, j);
                }
            }
        }
        return resMatrix;
    }
    
    public static void main(final String[] args) {
        for (int i = 0; i < 100; ++i) {
            final long start = System.currentTimeMillis();
            encodeQR("http://static.hs.com/static/images/m_01.png", 200, 200);
            final long spent = System.currentTimeMillis() - start;
            System.out.println("耗时" + spent);
        }
    }
}
