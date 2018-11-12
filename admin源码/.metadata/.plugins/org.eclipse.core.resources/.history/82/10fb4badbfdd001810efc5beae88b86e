package lottery.domains.content.api.pt;

import java.util.HashMap;

public final class PTCode
{
    private static final HashMap<String, String> CODE_MAP;
    public static final String DEFAULT_ERROR_CODE = "2-7004";
    
    static {
        (CODE_MAP = new HashMap<String, String>()).put("19", "2-7000");
        PTCode.CODE_MAP.put("41", "2-7001");
        PTCode.CODE_MAP.put("109", "2-7001");
        PTCode.CODE_MAP.put("44", "2-7002");
        PTCode.CODE_MAP.put("49", "2-7003");
        PTCode.CODE_MAP.put("71", "2-7004");
        PTCode.CODE_MAP.put("97", "2-7009");
        PTCode.CODE_MAP.put("98", "2-7005");
        PTCode.CODE_MAP.put("302", "2-7006");
    }
    
    private PTCode() {
    }
    
    public static String transErrorCode(final String code) {
        final String errorCode = PTCode.CODE_MAP.get(code);
        if (errorCode == null) {
            return "2-7004";
        }
        return errorCode;
    }
}
