package javautils.list;

import java.util.Iterator;
import java.util.List;
import javautils.StringUtil;

public class ListUtil
{
    public static int[] transObjectToInt(final Object[] o) {
        final int[] t = new int[o.length];
        for (int i = 0; i < o.length; ++i) {
            if (o[i] instanceof Integer) {
                t[i] = (int)o[i];
            }
            if (o[i] instanceof String) {
                final String s = (String)o[i];
                if (StringUtil.isIntegerString(s)) {
                    t[i] = Integer.parseInt(s);
                }
            }
        }
        return t;
    }
    
    public static String transListToString(final List<?> list) {
        final StringBuffer sb = new StringBuffer();
        for (final Object obj : list) {
            sb.append(String.valueOf(String.valueOf(obj)) + ", ");
        }
        if (list.size() > 0) {
            return sb.substring(0, sb.length() - 2);
        }
        return sb.toString();
    }
}
