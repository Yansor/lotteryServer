package lottery.domains.content.payment.lepay.utils;

public abstract class StringUtil
{
    public static boolean isEmpty(final String value) {
        final int strLen;
        if (value == null || (strLen = value.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; ++i) {
            if (!Character.isWhitespace(value.charAt(i))) {
                return false;
            }
        }
        return true;
    }
    
    public static boolean isNumeric(final Object obj) {
        if (obj == null) {
            return false;
        }
        final char[] chars = obj.toString().toCharArray();
        final int length = chars.length;
        if (length < 1) {
            return false;
        }
        int i = 0;
        if (length <= 1 || chars[0] == '-') {}
        for (i = 1; i < length; ++i) {
            if (!Character.isDigit(chars[i])) {
                return false;
            }
        }
        return true;
    }
    
    public static boolean areNotEmpty(final String[] values) {
        boolean result = true;
        if (values == null || values.length == 0) {
            result = false;
        }
        else {
            for (final String value : values) {
                result &= !isEmpty(value);
            }
        }
        return result;
    }
    
    public static String unicodeToChinese(final String unicode) {
        final StringBuilder out = new StringBuilder();
        if (!isEmpty(unicode)) {
            for (int i = 0; i < unicode.length(); ++i) {
                out.append(unicode.charAt(i));
            }
        }
        return out.toString();
    }
    
    public static String stripNonValidXMLCharacters(final String input) {
        if (input == null || "".equals(input)) {
            return "";
        }
        final StringBuilder out = new StringBuilder();
        for (int i = 0; i < input.length(); ++i) {
            final char current = input.charAt(i);
            if (current == '\t' || current == '\n' || current == '\r' || (current >= ' ' && current <= '\ud7ff') || (current >= '' && current <= '�') || (current >= 65536 && current <= 1114111)) {
                out.append(current);
            }
        }
        return out.toString();
    }
    
    public static boolean contains(final String src, final String dest) {
        return !isEmpty(src) && !isEmpty(dest) && src.indexOf(dest) != -1;
    }
}
