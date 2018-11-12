package javautils.image;

import java.awt.Font;
import java.awt.Color;
import javax.servlet.ServletOutputStream;
import java.io.OutputStream;
import java.awt.image.RenderedImage;
import javax.imageio.ImageIO;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.util.Random;

public class ImageCodeUtil
{
    static Random r;
    static int width;
    static int height;
    static int line;
    static int length;
    static int fontSize;
    static char[] ch;
    
    static {
        ImageCodeUtil.r = new Random();
        ImageCodeUtil.width = 250;
        ImageCodeUtil.height = 100;
        ImageCodeUtil.line = 80;
        ImageCodeUtil.length = 4;
        ImageCodeUtil.fontSize = 84;
        ImageCodeUtil.ch = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
    }
    
    public static void generate(final String key, final HttpServletRequest request, final HttpServletResponse response) {
        final BufferedImage image = new BufferedImage(ImageCodeUtil.width, ImageCodeUtil.height, 4);
        final Graphics g = image.getGraphics();
        g.fillRect(0, 0, ImageCodeUtil.width, ImageCodeUtil.height);
        g.setFont(font());
        g.setColor(color(110, 133));
        for (int i = 0; i <= ImageCodeUtil.line; ++i) {
            drawLine(g);
        }
        final StringBuffer sb = new StringBuffer();
        for (int j = 0; j < ImageCodeUtil.length; ++j) {
            sb.append(drawString(g, j * 60 + 10, 80));
        }
        g.dispose();
        request.getSession().setAttribute(key, (Object)sb.toString());
        write(image, response);
    }
    
    static void write(final BufferedImage image, final HttpServletResponse response) {
        response.setContentType("image/png");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expire", 0L);
        try {
            final ServletOutputStream out = response.getOutputStream();
            ImageIO.write(image, "PNG", (OutputStream)response.getOutputStream());
            out.flush();
            out.close();
        }
        catch (Exception ex) {}
    }
    
    static String drawString(final Graphics g, final int x, final int y) {
        final Random r = new Random();
        g.setFont(font());
        final int red = r.nextInt(101);
        final int green = r.nextInt(111);
        final int blue = r.nextInt(121);
        g.setColor(new Color(red, green, blue));
        final String s = string();
        g.drawString(s, x, y);
        return s;
    }
    
    static void drawLine(final Graphics g) {
        final int x = ImageCodeUtil.r.nextInt(ImageCodeUtil.width);
        final int y = ImageCodeUtil.r.nextInt(ImageCodeUtil.height);
        final int xl = ImageCodeUtil.r.nextInt(13);
        final int yl = ImageCodeUtil.r.nextInt(15);
        g.drawLine(x, y, x + xl, y + yl);
    }
    
    static Font font() {
        return new Font("Arial", 1, 84);
    }
    
    static Color color(int fc, int bc) {
        if (fc > 255) {
            fc = 255;
        }
        if (bc > 255) {
            bc = 255;
        }
        final int red = fc + ImageCodeUtil.r.nextInt(bc - fc - 16);
        final int green = fc + ImageCodeUtil.r.nextInt(bc - fc - 14);
        final int blue = fc + ImageCodeUtil.r.nextInt(bc - fc - 18);
        return new Color(red, green, blue);
    }
    
    static String string() {
        final int index = ImageCodeUtil.r.nextInt(ImageCodeUtil.ch.length);
        return String.valueOf(ImageCodeUtil.ch[index]);
    }
}
