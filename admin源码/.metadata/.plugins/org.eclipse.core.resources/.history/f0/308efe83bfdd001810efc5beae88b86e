package lottery.domains.content.payment.lepay.utils;

public enum Week
{
    MONDAY("MONDAY", 0, "����һ", "Monday", "Mon.", 1), 
    TUESDAY("TUESDAY", 1, "���ڶ�", "Tuesday", "Tues.", 2), 
    WEDNESDAY("WEDNESDAY", 2, "������", "Wednesday", "Wed.", 3), 
    THURSDAY("THURSDAY", 3, "������", "Thursday", "Thur.", 4), 
    FRIDAY("FRIDAY", 4, "������", "Friday", "Fri.", 5), 
    SATURDAY("SATURDAY", 5, "������", "Saturday", "Sat.", 6), 
    SUNDAY("SUNDAY", 6, "������", "Sunday", "Sun.", 7);
    
    String name_cn;
    String name_en;
    String name_enShort;
    int number;
    
    private Week(final String s, final int n, final String name_cn, final String name_en, final String name_enShort, final int number) {
        this.name_cn = name_cn;
        this.name_en = name_en;
        this.name_enShort = name_enShort;
        this.number = number;
    }
    
    public String getChineseName() {
        return this.name_cn;
    }
    
    public String getName() {
        return this.name_en;
    }
    
    public String getShortName() {
        return this.name_enShort;
    }
    
    public int getNumber() {
        return this.number;
    }
}
