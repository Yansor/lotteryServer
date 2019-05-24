package lottery.domains.content.api.pt;

import java.util.HashMap;

public final class PTCode
{
  private static final HashMap<String, String> CODE_MAP = new HashMap();
  public static final String DEFAULT_ERROR_CODE = "2-7004";
  
  static
  {
    CODE_MAP.put("19", "2-7000");
    CODE_MAP.put("41", "2-7001");
    CODE_MAP.put("109", "2-7001");
    CODE_MAP.put("44", "2-7002");
    CODE_MAP.put("49", "2-7003");
    CODE_MAP.put("71", "2-7004");
    CODE_MAP.put("97", "2-7009");
    CODE_MAP.put("98", "2-7005");
    CODE_MAP.put("302", "2-7006");
  }
  
  public static String transErrorCode(String code)
  {
    String errorCode = (String)CODE_MAP.get(code);
    if (errorCode == null) {
      return "2-7004";
    }
    return errorCode;
  }
}
