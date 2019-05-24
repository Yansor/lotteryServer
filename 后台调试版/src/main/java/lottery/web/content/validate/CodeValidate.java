package lottery.web.content.validate;

import admin.web.WebJSONObject;
import javautils.StringUtil;
import lottery.domains.content.entity.Lottery;
import lottery.domains.pool.LotteryDataFactory;
import lottery.domains.utils.lottery.open.LotteryOpenUtil;
import lottery.domains.utils.lottery.open.OpenTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CodeValidate
{
  @Autowired
  private LotteryOpenUtil lotteryOpenUtil;
  @Autowired
  private LotteryDataFactory lotteryDataFactory;
  
  public boolean validateExpect(WebJSONObject json, String lottery, String expect)
  {
    boolean isTrueExpect = false;
    Lottery lotteryBean = this.lotteryDataFactory.getLottery(lottery);
    if (lotteryBean != null)
    {
      OpenTime bean = this.lotteryOpenUtil.getCurrOpenTime(lotteryBean.getId());
      if (bean.getExpect().compareTo(expect) > 0) {
        isTrueExpect = true;
      }
    }
    if (!isTrueExpect) {
      json.set(Integer.valueOf(2), "2-2101");
    }
    return isTrueExpect;
  }
  
  public boolean validateCode(WebJSONObject json, String lottery, String code)
  {
    boolean isTrueCode = false;
    Lottery lotteryBean = this.lotteryDataFactory.getLottery(lottery);
    if (lotteryBean != null) {
      switch (lotteryBean.getType())
      {
      case 1: 
        if (isSsc(code)) {
          isTrueCode = true;
        }
        break;
      case 2: 
        if (is11x5(code)) {
          isTrueCode = true;
        }
        break;
      case 3: 
        if (isK3(code)) {
          isTrueCode = true;
        }
        break;
      case 4: 
        if (is3d(code)) {
          isTrueCode = true;
        }
        break;
      case 5: 
        if (isBjkl8(code)) {
          isTrueCode = true;
        }
        break;
      case 6: 
        if (isBjpk10(code)) {
          isTrueCode = true;
        }
        break;
      case 7: 
        if (isSsc(code)) {
          isTrueCode = true;
        }
        break;
      }
    }
    if (!isTrueCode) {
      json.set(Integer.valueOf(2), "2-2100");
    }
    return isTrueCode;
  }
  
  public boolean isSsc(String s)
  {
    if (!StringUtil.isNotNull(s)) {
      return false;
    }
    String[] codes = s.split(",");
    if (codes.length != 5) {
      return false;
    }
    String[] arrayOfString1;
    int j = (arrayOfString1 = codes).length;
    for (int i = 0; i < j; i++)
    {
      String tmpS = arrayOfString1[i];
      if (!StringUtil.isInteger(tmpS)) {
        return false;
      }
      if (tmpS.length() != 1) {
        return false;
      }
      int tmpC = Integer.parseInt(tmpS);
      if ((tmpC < 0) || (tmpC > 9)) {
        return false;
      }
    }
    return true;
  }
  
  public boolean is11x5(String s)
  {
    if (!StringUtil.isNotNull(s)) {
      return false;
    }
    String[] codes = s.split(",");
    if (codes.length != 5) {
      return false;
    }
    String[] arrayOfString1;
    int j = (arrayOfString1 = codes).length;
    for (int i = 0; i < j; i++)
    {
      String tmpS = arrayOfString1[i];
      if (!StringUtil.isInteger(tmpS)) {
        return false;
      }
      if (tmpS.length() != 2) {
        return false;
      }
      int tmpC = Integer.parseInt(tmpS);
      if ((tmpC < 1) || (tmpC > 11)) {
        return false;
      }
    }
    return true;
  }
  
  public boolean isK3(String s)
  {
    if (!StringUtil.isNotNull(s)) {
      return false;
    }
    String[] codes = s.split(",");
    if (codes.length != 3) {
      return false;
    }
    String[] arrayOfString1;
    int j = (arrayOfString1 = codes).length;
    for (int i = 0; i < j; i++)
    {
      String tmpS = arrayOfString1[i];
      if (!StringUtil.isInteger(tmpS)) {
        return false;
      }
      if (tmpS.length() != 1) {
        return false;
      }
      int tmpC = Integer.parseInt(tmpS);
      if ((tmpC < 1) || (tmpC > 6)) {
        return false;
      }
    }
    return true;
  }
  
  public boolean is3d(String s)
  {
    if (!StringUtil.isNotNull(s)) {
      return false;
    }
    String[] codes = s.split(",");
    if (codes.length != 3) {
      return false;
    }
    String[] arrayOfString1;
    int j = (arrayOfString1 = codes).length;
    for (int i = 0; i < j; i++)
    {
      String tmpS = arrayOfString1[i];
      if (!StringUtil.isInteger(tmpS)) {
        return false;
      }
      if (tmpS.length() != 1) {
        return false;
      }
      int tmpC = Integer.parseInt(tmpS);
      if ((tmpC < 0) || (tmpC > 9)) {
        return false;
      }
    }
    return true;
  }
  
  public boolean isBjkl8(String s)
  {
    if (!StringUtil.isNotNull(s)) {
      return false;
    }
    String[] codes = s.split(",");
    if (codes.length != 20) {
      return false;
    }
    String[] arrayOfString1;
    int j = (arrayOfString1 = codes).length;
    for (int i = 0; i < j; i++)
    {
      String tmpS = arrayOfString1[i];
      if (!StringUtil.isInteger(tmpS)) {
        return false;
      }
      if (tmpS.length() != 2) {
        return false;
      }
      int tmpC = Integer.parseInt(tmpS);
      if ((tmpC < 1) || (tmpC > 80)) {
        return false;
      }
    }
    return true;
  }
  
  public boolean isBjpk10(String s)
  {
    if (!StringUtil.isNotNull(s)) {
      return false;
    }
    String[] codes = s.split(",");
    if (codes.length != 10) {
      return false;
    }
    String[] arrayOfString1;
    int j = (arrayOfString1 = codes).length;
    for (int i = 0; i < j; i++)
    {
      String tmpS = arrayOfString1[i];
      if (!StringUtil.isInteger(tmpS)) {
        return false;
      }
      if (tmpS.length() != 2) {
        return false;
      }
      int tmpC = Integer.parseInt(tmpS);
      if ((tmpC < 1) || (tmpC > 10)) {
        return false;
      }
    }
    return true;
  }
}
