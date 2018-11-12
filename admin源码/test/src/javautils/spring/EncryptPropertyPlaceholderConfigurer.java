package javautils.spring;

import javautils.StringUtil;
import javautils.encrypt.DESUtil;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

public class EncryptPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer
{
    private static final String KEY = "#$ddw134R$#G#DSFW@#?!@#!@#$CCCREW1";
    private static final DESUtil DES_UTIL;
    
    static {
        DES_UTIL = DESUtil.getInstance();
    }
    
    protected String convertProperty(final String propertyName, final String propertyValue) {
        if (StringUtil.isNotNull(propertyValue) && propertyValue.endsWith("|e")) {
            final String tempValue = propertyValue.substring(0, propertyValue.length() - 2);
            final String decryptValue = EncryptPropertyPlaceholderConfigurer.DES_UTIL.decryptStr(tempValue, "#$ddw134R$#G#DSFW@#?!@#!@#$CCCREW1");
            return decryptValue;
        }
        return propertyValue;
    }
}
