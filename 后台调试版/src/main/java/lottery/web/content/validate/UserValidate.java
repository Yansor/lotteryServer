package lottery.web.content.validate;

import admin.web.WebJSONObject;
import java.util.List;
import javautils.regex.RegexUtil;
import lottery.domains.content.dao.UserDao;
import lottery.domains.content.entity.User;
import lottery.domains.content.vo.config.CodeConfig;
import lottery.domains.content.vo.user.SysCodeRangeVO;
import lottery.domains.pool.LotteryDataFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserValidate
{
  @Autowired
  private UserDao uDao;
  @Autowired
  private LotteryDataFactory lotteryDataFactory;
  
  public boolean testUserPoint(WebJSONObject json, User uBean, double locatePoint)
  {
    if (locatePoint <= uBean.getLocatePoint())
    {
      json.set(Integer.valueOf(2), "2-2014");
      return false;
    }
    CodeConfig config = this.lotteryDataFactory.getCodeConfig();
    if (locatePoint > config.getSysLp())
    {
      json.set(Integer.valueOf(2), "2-9");
      return false;
    }
    if (uBean.getUpid() != 0)
    {
      User upperUser = this.uDao.getById(uBean.getUpid());
      if (upperUser != null)
      {
        boolean trueCode = locatePoint < upperUser.getLocatePoint();
        if (upperUser.getAllowEqualCode() == 1) {
          trueCode = locatePoint <= upperUser.getLocatePoint();
        }
        if (!trueCode)
        {
          json.set(Integer.valueOf(2), "2-2015");
          return false;
        }
      }
    }
    return true;
  }
  
  public boolean testUsername(WebJSONObject json, String username)
  {
    String patrn = "^[a-zA-Z]{1}([a-zA-Z0-9]){5,11}$";
    if (!RegexUtil.isMatcher(username, patrn))
    {
      json.set(Integer.valueOf(2), "2-2029");
      return false;
    }
    return true;
  }
  
  public boolean testNewUserPoint(WebJSONObject json, double locatePoint)
  {
    CodeConfig config = this.lotteryDataFactory.getCodeConfig();
    if ((locatePoint < 0.0D) || (locatePoint > config.getSysLp()))
    {
      json.set(Integer.valueOf(2), "2-9");
      return false;
    }
    return true;
  }
  
  public boolean testNewUserPoint(WebJSONObject json, User upperUser, double locatePoint)
  {
    CodeConfig config = this.lotteryDataFactory.getCodeConfig();
    if ((locatePoint < 0.0D) || (locatePoint > config.getSysLp()))
    {
      json.set(Integer.valueOf(2), "2-9");
      return false;
    }
    if ((locatePoint > config.getSysLp()) || (locatePoint < 0.0D))
    {
      json.set(Integer.valueOf(2), "2-2026");
      return false;
    }
    boolean trueCode = locatePoint < upperUser.getLocatePoint();
    if (upperUser.getAllowEqualCode() == 1) {
      trueCode = locatePoint <= upperUser.getLocatePoint();
    }
    if (!trueCode)
    {
      json.set(Integer.valueOf(2), "2-2015");
      return false;
    }
    return true;
  }
  
  public SysCodeRangeVO loadEditPoint(User uBean)
  {
    CodeConfig config = this.lotteryDataFactory.getCodeConfig();
    double testMinPoint = 0.0D;
    double testMaxPoint = config.getSysLp();
    if (uBean.getUpid() != 0)
    {
      User upperUser = this.uDao.getById(uBean.getUpid());
      if (upperUser != null)
      {
        testMaxPoint = upperUser.getLocatePoint();
        if (upperUser.getAllowEqualCode() != 1) {
          testMaxPoint = upperUser.getLocatePoint() - 0.1D;
        }
      }
    }
    List<User> uDirectList = this.uDao.getUserDirectLower(uBean.getId());
    for (User tmpBean : uDirectList) {
      if (tmpBean.getLocatePoint() > testMinPoint)
      {
        testMinPoint = tmpBean.getLocatePoint();
        if (uBean.getAllowEqualCode() != 1) {
          testMinPoint += 0.1D;
        }
      }
    }
    SysCodeRangeVO result = new SysCodeRangeVO();
    result.setMinPoint(testMinPoint);
    result.setMaxPoint(testMaxPoint);
    return result;
  }
}
