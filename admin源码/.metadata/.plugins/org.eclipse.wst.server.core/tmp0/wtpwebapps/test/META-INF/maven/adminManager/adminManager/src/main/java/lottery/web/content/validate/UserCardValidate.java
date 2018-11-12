package lottery.web.content.validate;

import javautils.StringUtil;
import admin.web.WebJSONObject;
import org.springframework.stereotype.Component;

@Component
public class UserCardValidate
{
    public boolean required(final WebJSONObject json, final Integer bankId, final String cardName, final String cardId) {
        if (bankId == null) {
            json.set(2, "2-1011");
            return false;
        }
        if (!StringUtil.isNotNull(cardName)) {
            json.set(2, "2-1012");
            return false;
        }
        if (!StringUtil.isNotNull(cardId)) {
            json.set(2, "2-1013");
            return false;
        }
        return true;
    }
    
    public boolean checkCardId(final String cardId) {
        if (!StringUtil.isNotNull(cardId) || !cardId.matches("\\d+")) {
            return false;
        }
        if (cardId.length() < 10) {
            return false;
        }
        final String nonCardId = cardId.substring(0, cardId.length() - 1);
        final char code = cardId.charAt(cardId.length() - 1);
        final char[] chs = nonCardId.trim().toCharArray();
        int luhmSum = 0;
        for (int i = chs.length - 1, j = 0; i >= 0; --i, ++j) {
            int k = chs[i] - '0';
            if (j % 2 == 0) {
                k *= 2;
                k = k / 10 + k % 10;
            }
            luhmSum += k;
        }
        final char bit = (luhmSum % 10 == 0) ? '0' : ((char)(10 - luhmSum % 10 + 48));
        return bit == code;
    }
}
