package lottery.domains.content.payment.lepay.utils;

public enum DateStyle
{
    YYYY_MM("YYYY_MM", 0, "yyyy-MM", false), 
    YYYY_MM_DD("YYYY_MM_DD", 1, "yyyy-MM-dd", false), 
    YYYYMMDD("YYYYMMDD", 2, "yyyyMMdd", false), 
    YYYY_MM_DD_HH_MM("YYYY_MM_DD_HH_MM", 3, "yyyy-MM-dd HH:mm", false), 
    YYYY_MM_DD_HH_MM_SS("YYYY_MM_DD_HH_MM_SS", 4, "yyyy-MM-dd HH:mm:ss", false), 
    YYYY_MM_EN("YYYY_MM_EN", 5, "yyyy/MM", false), 
    YYYY_MM_DD_EN("YYYY_MM_DD_EN", 6, "yyyy/MM/dd", false), 
    YYYY_MM_DD_HH_MM_EN("YYYY_MM_DD_HH_MM_EN", 7, "yyyy/MM/dd HH:mm", false), 
    YYYY_MM_DD_HH_MM_SS_EN("YYYY_MM_DD_HH_MM_SS_EN", 8, "yyyy/MM/dd HH:mm:ss", false), 
    YYYY_MM_CN("YYYY_MM_CN", 9, "yyyy��MM��", false), 
    YYYY_MM_DD_CN("YYYY_MM_DD_CN", 10, "yyyy��MM��dd��", false), 
    YYYY_MM_DD_HH_MM_CN("YYYY_MM_DD_HH_MM_CN", 11, "yyyy��MM��dd�� HH:mm", false), 
    YYYY_MM_DD_HH_MM_SS_CN("YYYY_MM_DD_HH_MM_SS_CN", 12, "yyyy��MM��dd�� HH:mm:ss", false), 
    HH_MM("HH_MM", 13, "HH:mm", true), 
    HH_MM_SS("HH_MM_SS", 14, "HH:mm:ss", true), 
    MM_DD("MM_DD", 15, "MM-dd", true), 
    MM_DD_HH_MM("MM_DD_HH_MM", 16, "MM-dd HH:mm", true), 
    MM_DD_HH_MM_SS("MM_DD_HH_MM_SS", 17, "MM-dd HH:mm:ss", true), 
    MM_DD_EN("MM_DD_EN", 18, "MM/dd", true), 
    MM_DD_HH_MM_EN("MM_DD_HH_MM_EN", 19, "MM/dd HH:mm", true), 
    MM_DD_HH_MM_SS_EN("MM_DD_HH_MM_SS_EN", 20, "MM/dd HH:mm:ss", true), 
    MM_DD_CN("MM_DD_CN", 21, "MM��dd��", true), 
    MM_DD_HH_MM_CN("MM_DD_HH_MM_CN", 22, "MM��dd�� HH:mm", true), 
    MM_DD_HH_MM_SS_CN("MM_DD_HH_MM_SS_CN", 23, "MM��dd�� HH:mm:ss", true);
    
    private String value;
    private boolean isShowOnly;
    
    private DateStyle(final String s, final int n, final String value, final boolean isShowOnly) {
        this.value = value;
        this.isShowOnly = isShowOnly;
    }
    
    public String getValue() {
        return this.value;
    }
    
    public boolean isShowOnly() {
        return this.isShowOnly;
    }
}
