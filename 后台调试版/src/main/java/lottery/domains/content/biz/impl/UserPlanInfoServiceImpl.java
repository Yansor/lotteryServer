package lottery.domains.content.biz.impl;

import lottery.domains.content.biz.UserPlanInfoService;
import lottery.domains.content.dao.UserPlanInfoDao;
import lottery.domains.content.entity.UserPlanInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserPlanInfoServiceImpl
  implements UserPlanInfoService
{
  @Autowired
  private UserPlanInfoDao uPlanInfoDao;
  
  public UserPlanInfo get(int userId)
  {
    UserPlanInfo bean = this.uPlanInfoDao.get(userId);
    if (bean == null)
    {
      int level = 0;
      int planCount = 0;
      int prizeCount = 0;
      double totalMoney = 0.0D;
      double totalPrize = 0.0D;
      int status = 0;
      bean = new UserPlanInfo(userId, level, planCount, prizeCount, totalMoney, totalPrize, status);
      this.uPlanInfoDao.add(bean);
    }
    return bean;
  }
}
