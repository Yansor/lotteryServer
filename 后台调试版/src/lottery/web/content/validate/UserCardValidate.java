package lottery.web.content.validate;

import admin.web.WebJSONObject;
import javautils.StringUtil;
import org.springframework.stereotype.Component;

@Component
public class UserCardValidate
{
  public boolean required(WebJSONObject json, Integer bankId, String cardName, String cardId)
  {
    if (bankId == null)
    {
      json.set(Integer.valueOf(2), "2-1011");
      return false;
    }
    if (!StringUtil.isNotNull(cardName))
    {
      json.set(Integer.valueOf(2), "2-1012");
      return false;
    }
    if (!StringUtil.isNotNull(cardId))
    {
      json.set(Integer.valueOf(2), "2-1013");
      return false;
    }
    return true;
  }
  
  public boolean checkCardId(String cardId)
  {
    if ((StringUtil.isNotNull(cardId)) && (cardId.matches("\\d+")))
    {
      if (cardId.length() < 10) {
        return false;
      }
      String nonCardId = cardId.substring(0, cardId.length() - 1);
      char code = cardId.charAt(cardId.length() - 1);
      char[] chs = nonCardId.trim().toCharArray();
      int luhmSum = 0;
      int i = chs.length - 1;
      for (int j = 0; i >= 0; j++)
      {
        int k = chs[i] - '0';
        if (j % 2 == 0)
        {
          k *= 2;
          k = k / 10 + k % 10;
        }
        luhmSum += k;i--;
      }
      char bit = luhmSum % 10 == 0 ? '0' : (char)(10 - luhmSum % 10 + 48);
      return bit == code;
    }
    return false;
  }
}
