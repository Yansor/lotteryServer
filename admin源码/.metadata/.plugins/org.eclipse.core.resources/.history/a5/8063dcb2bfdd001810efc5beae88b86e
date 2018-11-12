package admin.tools;

import java.io.IOException;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;

public class StringUtils
{
    public static boolean isEmpty(final String input) {
        if (input == null || "".equals(input.trim())) {
            return true;
        }
        for (int i = 0; i < input.length(); ++i) {
            final char c = input.charAt(i);
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                return false;
            }
        }
        return true;
    }
    
    public static int toInt(final String str, final int defValue) {
        try {
            return Integer.parseInt(str);
        }
        catch (Exception ex) {
            return defValue;
        }
    }
    
    public static Double toDouble(final String str, final Double defValue) {
        try {
            return Double.parseDouble(str);
        }
        catch (Exception ex) {
            return defValue;
        }
    }
    
    public static int toInt(final Object obj) {
        if (obj == null) {
            return 0;
        }
        return toInt(obj.toString(), 0);
    }
    
    public static long toLong(final String obj) {
        try {
            return Long.parseLong(obj);
        }
        catch (Exception ex) {
            return 0L;
        }
    }
    
    public static boolean toBool(final String b) {
        try {
            return Boolean.parseBoolean(b);
        }
        catch (Exception ex) {
            return false;
        }
    }
    
    public static String toConvertString(InputStream is) {
        final StringBuffer res = new StringBuffer();
        final InputStreamReader isr = new InputStreamReader(is);
        BufferedReader read = new BufferedReader(isr);
        try {
            for (String line = read.readLine(); line != null; line = read.readLine()) {
                res.append(line);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (isr != null) {
                    isr.close();
                    isr.close();
                }
                if (read != null) {
                    read.close();
                    read = null;
                }
                if (is != null) {
                    is.close();
                    is = null;
                }
            }
            catch (IOException ex) {}
        }
        try {
            if (isr != null) {
                isr.close();
                isr.close();
            }
            if (read != null) {
                read.close();
                read = null;
            }
            if (is != null) {
                is.close();
                is = null;
            }
        }
        catch (IOException ex2) {}
        return res.toString();
    }
    
    public static int getSubStrCount(final String str, final String key) {
        int count = 0;
        for (int index = 0; (index = str.indexOf(key, index)) != -1; index += key.length(), ++count) {}
        return count;
    }
    
    public static String reverse(final String str) {
        final String reverseStr = new StringBuffer(str).reverse().toString();
        return reverseStr;
    }
}
