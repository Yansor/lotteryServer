package lottery.domains.content.api.ag;

import java.util.HashMap;

public final class AGCode
{
    private static final HashMap<String, String> CODE_MAP;
    public static final String DEFAULT_ERROR_CODE = "2-8000";
    
    static {
        (CODE_MAP = new HashMap<String, String>()).put("key_error", "2-8000");
        AGCode.CODE_MAP.put("network_error", "2-8001");
        AGCode.CODE_MAP.put("account_add_fail", "2-8002");
        AGCode.CODE_MAP.put("error", "2-8006");
        AGCode.CODE_MAP.put("account_not_exist", "2-8003");
        AGCode.CODE_MAP.put("duplicate_transfer", "2-8004");
        AGCode.CODE_MAP.put("not_enough_credit", "2-8005");
    }
    
    private AGCode() {
    }
    
    public static String transErrorCode(final String code) {
        final String errorCode = AGCode.CODE_MAP.get(code);
        if (errorCode == null) {
            return null;
        }
        return errorCode;
    }
}
