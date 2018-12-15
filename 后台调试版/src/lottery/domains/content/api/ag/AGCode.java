package lottery.domains.content.api.ag;

import java.util.HashMap;

public final class AGCode
{
  private static final HashMap<String, String> CODE_MAP = new HashMap();
  public static final String DEFAULT_ERROR_CODE = "2-8000";
  
  static
  {
    CODE_MAP.put("key_error", "2-8000");
    CODE_MAP.put("network_error", "2-8001");
    CODE_MAP.put("account_add_fail", "2-8002");
    CODE_MAP.put("error", "2-8006");
    CODE_MAP.put("account_not_exist", "2-8003");
    CODE_MAP.put("duplicate_transfer", "2-8004");
    CODE_MAP.put("not_enough_credit", "2-8005");
  }
  
  public static String transErrorCode(String code)
  {
    String errorCode = (String)CODE_MAP.get(code);
    if (errorCode == null) {
      return null;
    }
    return errorCode;
  }
}
