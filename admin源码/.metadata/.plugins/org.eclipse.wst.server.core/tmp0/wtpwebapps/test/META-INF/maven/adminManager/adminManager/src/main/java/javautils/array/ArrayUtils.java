package javautils.array;

import java.util.Set;
import java.util.HashSet;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Collection;
import org.apache.commons.lang.StringUtils;
import java.util.ArrayList;
import javautils.StringUtil;
import java.util.List;

public class ArrayUtils
{
    public static String transInIds(final int[] ids) {
        final StringBuffer sb = new StringBuffer();
        for (int i = 0, j = ids.length; i < j; ++i) {
            sb.append(ids[i]);
            if (i < j - 1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }
    
    public static String transInIds(final Integer[] ids) {
        final StringBuffer sb = new StringBuffer();
        for (int i = 0, j = ids.length; i < j; ++i) {
            sb.append((int)ids[i]);
            if (i < j - 1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }
    
    public static String transInIds(final List<Integer> ids) {
        final StringBuffer sb = new StringBuffer();
        for (int i = 0, j = ids.size(); i < j; ++i) {
            sb.append((int)ids.get(i));
            if (i < j - 1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }
    
    public static String transInsertIds(final int[] ids) {
        final StringBuffer sb = new StringBuffer();
        for (int i = 0, j = ids.length; i < j; ++i) {
            sb.append("[" + ids[i] + "]");
            if (i < j - 1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }
    
    public static int[] transGetIds(final String ids) {
        final String[] tmp = ids.replaceAll("\\[|\\]", "").split(",");
        final int[] arr = new int[tmp.length];
        for (int i = 0; i < arr.length; ++i) {
            arr[i] = Integer.parseInt(tmp[i]);
        }
        return arr;
    }
    
    public static String deleteInsertIds(final String ids, final int id, final boolean isAll) {
        if (StringUtil.isNotNull(ids) && ids.indexOf("[" + id + "]") != -1) {
            final String[] tmp = ids.replaceAll("\\[|\\]", "").split(",");
            final List<Integer> list = new ArrayList<Integer>();
            for (int i = 0; i < tmp.length; ++i) {
                if (id != Integer.parseInt(tmp[i])) {
                    list.add(Integer.parseInt(tmp[i]));
                }
                else if (isAll) {
                    break;
                }
            }
            final int[] arr = new int[list.size()];
            for (int j = 0; j < list.size(); ++j) {
                arr[j] = list.get(j);
            }
            return transInsertIds(arr);
        }
        return ids;
    }
    
    public static String addId(String ids, final int id) {
        if (StringUtils.isEmpty(ids)) {
            return "[" + id + "]";
        }
        if (ids.indexOf("[" + id + "]") <= -1) {
            ids = String.valueOf(ids) + ",[" + id + "]";
        }
        return ids;
    }
    
    public static String toString(final List<Integer> list) {
        final StringBuffer sb = new StringBuffer();
        for (int i = 0, j = list.size(); i < j; ++i) {
            sb.append(list.get(i));
            if (i < j - 1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }
    
    public static String toStringWithQuote(final Collection<String> sets) {
        final StringBuffer sb = new StringBuffer();
        final Iterator<String> iterator = sets.iterator();
        while (iterator.hasNext()) {
            final String next = iterator.next();
            sb.append("'").append(next).append("'");
            if (iterator.hasNext()) {
                sb.append(",");
            }
        }
        return sb.toString();
    }
    
    public static boolean hasRepeat(final String[] arr) {
        final Set<String> set = new HashSet<String>(Arrays.asList(arr));
        return arr.length != set.size();
    }
    
    public static void main(final String[] args) {
        final String ids = "[2],[209],[72],[1]";
        System.out.println(deleteInsertIds(ids, 72, false));
    }
}
