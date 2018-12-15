package lottery.web.content.utils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javautils.array.ArrayUtils;
import javautils.math.MathUtil;
import lottery.domains.content.dao.UserCodeQuotaDao;
import lottery.domains.content.dao.UserDao;
import lottery.domains.content.entity.User;
import lottery.domains.content.vo.config.CodeConfig;
import lottery.domains.content.vo.user.UserCodeQuotaVO;
import lottery.domains.content.vo.user.UserCodeRangeVO;
import lottery.domains.content.vo.user.UserVO;
import lottery.domains.pool.LotteryDataFactory;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserCodePointUtil
{
  @Autowired
  private UserDao uDao;
  @Autowired
  private UserCodeQuotaDao uCodeQuotaDao;
  @Autowired
  private LotteryDataFactory dataFactory;
  
  public int getUserCode(double locatePoint)
  {
    CodeConfig config = this.dataFactory.getCodeConfig();
    return config.getSysCode() - (int)(MathUtil.subtract(config.getSysLp(), locatePoint) * 20.0D);
  }
  
  public double getLocatePoint(int code)
  {
    CodeConfig config = this.dataFactory.getCodeConfig();
    return config.getSysLp() - (config.getSysCode() - code) / 20.0D;
  }
  
  public double getNotLocatePoint(int code)
  {
    CodeConfig config = this.dataFactory.getCodeConfig();
    double result = config.getSysNlp() - (config.getSysCode() - code) / 20.0D;
    return result < 0.0D ? 0.0D : result;
  }
  
  public double getNotLocatePoint(double locatePoint)
  {
    return getNotLocatePoint(getUserCode(locatePoint));
  }
  
  public List<UserCodeQuotaVO> listTotalQuota(User uBean)
  {
    return new ArrayList();
  }
  
  public List<UserCodeQuotaVO> listSurplusQuota(User uBean)
  {
    return new ArrayList();
  }
  
  public UserCodeRangeVO getUserCodeRange(User uBean, List<UserCodeQuotaVO> surplusList)
  {
    return null;
  }
  
  public List<String> getUserLevels(String username)
  {
    if (StringUtils.isEmpty(username)) {
      return new ArrayList();
    }
    List<String> userLevels = new LinkedList();
    
    User user = this.uDao.getByUsername(username);
    if (user == null) {
      return userLevels;
    }
    if (StringUtils.isEmpty(user.getUpids())) {
      return userLevels;
    }
    int[] upids = ArrayUtils.transGetIds(user.getUpids());
    for (int i = upids.length - 1; i >= 0; i--)
    {
      UserVO upUser = this.dataFactory.getUser(upids[i]);
      if (upUser != null) {
        userLevels.add(upUser.getUsername());
      }
    }
    userLevels.add(username);
    
    return userLevels;
  }
  
  public boolean isLevel1Proxy(User uBean)
  {
    if (uBean.getType() == 1) {
      if (uBean.getUpid() == 72) {
        return true;
      }
    }
    return false;
  }
  
  public boolean isLevel2Proxy(User uBean)
  {
    if ((uBean.getType() == 1) && 
      (uBean.getUpid() != 0) && 
      (uBean.getUpid() != 72))
    {
      User upBean = this.uDao.getById(uBean.getUpid());
      if (isLevel1Proxy(upBean)) {
        return true;
      }
    }
    return false;
  }
  
  public boolean isLevel2ZhaoShangProxy(User uBean)
  {
    if ((uBean.getType() == 1) && 
      (uBean.getCode() == this.dataFactory.getCodeConfig().getSysCode()) && 
      (uBean.getUpid() != 0) && 
      (uBean.getUpid() != 72) && 
      (uBean.getIsCjZhaoShang() == 0))
    {
      User upBean = this.uDao.getById(uBean.getUpid());
      if (isLevel1Proxy(upBean)) {
        return true;
      }
    }
    return false;
  }
  
  public boolean isLevel2CJZhaoShangProxy(User uBean)
  {
    if ((uBean.getType() == 1) && 
      (uBean.getCode() == this.dataFactory.getCodeConfig().getSysCode()) && 
      (uBean.getUpid() != 0) && 
      (uBean.getUpid() != 72) && 
      (uBean.getIsCjZhaoShang() == 1))
    {
      User upBean = this.uDao.getById(uBean.getUpid());
      if (isLevel1Proxy(upBean)) {
        return true;
      }
    }
    return false;
  }
  
  public boolean isLevel3Proxy(User uBean)
  {
    if ((uBean.getType() == 1) && 
      (uBean.getUpid() != 0) && 
      (uBean.getUpid() != 72))
    {
      User upBean = this.uDao.getById(uBean.getUpid());
      if (isLevel2Proxy(upBean)) {
        return true;
      }
    }
    return false;
  }
}
