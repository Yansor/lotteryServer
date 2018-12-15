package lottery.domains.content.biz.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javautils.date.Moment;
import javautils.math.MathUtil;
import lottery.domains.content.biz.GameBetsService;
import lottery.domains.content.biz.UserBetsService;
import lottery.domains.content.biz.UserWithdrawLimitService;
import lottery.domains.content.dao.UserWithdrawLimitDao;
import lottery.domains.content.entity.UserWithdrawLimit;
import lottery.domains.content.vo.user.UserVO;
import lottery.domains.content.vo.user.UserWithdrawLimitVO;
import lottery.domains.pool.LotteryDataFactory;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserWithdrawLimitServiceImpl
  implements UserWithdrawLimitService
{
  @Autowired
  private UserWithdrawLimitDao uWithdrawLimitDao;
  @Autowired
  private LotteryDataFactory dataFactory;
  @Autowired
  private GameBetsService gameBetsService;
  @Autowired
  private UserBetsService uBetsService;
  
  public boolean add(int userId, double amount, String time, int type, int subType, double percent)
  {
    if (percent > 0.0D)
    {
      UserWithdrawLimit entity = new UserWithdrawLimit();
      entity.setRechargeMoney(amount);
      entity.setRechargeTime(time);
      entity.setUserId(userId);
      entity.setProportion(percent);
      entity.setConsumptionRequirements(MathUtil.multiply(amount, percent));
      entity.setType(type);
      entity.setSubType(subType);
      return this.uWithdrawLimitDao.add(entity);
    }
    return true;
  }
  
  public boolean delByUserId(int userId)
  {
    return this.uWithdrawLimitDao.delByUserId(userId);
  }
  
  public UserWithdrawLimit getByUserId(int userId)
  {
    return this.uWithdrawLimitDao.getByUserId(userId);
  }
  
  public Map<String, Object> getUserWithdrawLimits(int userId, String time)
  {
    UserVO user = this.dataFactory.getUser(userId);
    String username = user == null ? "未知" : user.getUsername();
    
    List<UserWithdrawLimit> records = this.uWithdrawLimitDao.getUserWithdrawLimits(userId, time);
    
    List<UserWithdrawLimit> entities = new ArrayList();
    for (UserWithdrawLimit record : records) {
      if ((StringUtils.isNotEmpty(record.getRechargeTime())) && (record.getRechargeMoney() > 0.0D)) {
        entities.add(record);
      }
    }
    List<UserWithdrawLimit> rechargeEntities = new ArrayList();
    for (UserWithdrawLimit entity : entities) {
      if (entity.getConsumptionRequirements() > 0.0D) {
        rechargeEntities.add(entity);
      }
    }
    int listSize = rechargeEntities.size();
    
    List<UserWithdrawLimitVO> rechargeVOs = new ArrayList();
    for (int i = 0; i < listSize; i++)
    {
      UserWithdrawLimit entity = (UserWithdrawLimit)rechargeEntities.get(i);
      double needConsumption = entity.getConsumptionRequirements();
      
      double remainConsumption = 0.0D;
      double totalBilling = 0.0D;
      if (needConsumption < 0.0D)
      {
        remainConsumption = needConsumption;
      }
      else if (needConsumption > 0.0D)
      {
        String rechargeSTime = entity.getRechargeTime();
        String rechargeETime;
//        String rechargeETime;
        if (listSize > i + 1) {
          rechargeETime = ((UserWithdrawLimit)rechargeEntities.get(i + 1)).getRechargeTime();
        } else {
          rechargeETime = new Moment().toSimpleTime();
        }
        double[] consumptions = getConsumptions(userId, needConsumption, rechargeSTime, rechargeETime);
        remainConsumption = consumptions[0];
        totalBilling = consumptions[1];
      }
      UserWithdrawLimitVO userWithdrawLimitVO = new UserWithdrawLimitVO(entity, username, totalBilling, remainConsumption);
      ((List)rechargeVOs).add(userWithdrawLimitVO);
    }
    UserWithdrawLimitVO last;
    if (((List)rechargeVOs).size() > 1) {
      for (int i = 0; i < ((List)rechargeVOs).size(); i++)
      {
        last = (UserWithdrawLimitVO)((List)rechargeVOs).get(i);
        double lastBilling = last.getTotalBilling();
        if (lastBilling > 0.0D)
        {
          for (int j = 0; j < i; j++)
          {
            UserWithdrawLimitVO limitVO = (UserWithdrawLimitVO)((List)rechargeVOs).get(j);
            double remainConsumption = limitVO.getRemainConsumption();
            if (remainConsumption > 0.0D)
            {
              double giveBilling = remainConsumption > lastBilling ? lastBilling : remainConsumption;
              remainConsumption = MathUtil.subtract(remainConsumption, giveBilling);
              limitVO.setTotalBilling(MathUtil.add(limitVO.getTotalBilling(), giveBilling));
              limitVO.setRemainConsumption(remainConsumption);
              lastBilling = MathUtil.subtract(lastBilling, giveBilling);
            }
          }
          last.setTotalBilling(lastBilling);
          double lastRemainConsumption = MathUtil.subtract(last.getBean().getConsumptionRequirements(), lastBilling);
          if (lastRemainConsumption < 0.0D) {
            lastRemainConsumption = 0.0D;
          }
          last.setRemainConsumption(lastRemainConsumption);
        }
      }
    }
    for (UserWithdrawLimit entity : entities) {
      if (entity.getConsumptionRequirements() < 0.0D)
      {
        UserWithdrawLimitVO userWithdrawLimitVO = new UserWithdrawLimitVO(entity, username, 0.0D, entity.getConsumptionRequirements());
        ((List)rechargeVOs).add(userWithdrawLimitVO);
      }
    }
//    Collections.sort((List)rechargeVOs, new UserWithdrawLimitServiceImpl.1(this));
    
    double totalRemainConsumption = 0.0D;
    for (UserWithdrawLimitVO userWithdrawLimitVO : rechargeVOs) {
      totalRemainConsumption = MathUtil.add(totalRemainConsumption, userWithdrawLimitVO.getRemainConsumption());
    }
    if (totalRemainConsumption < 0.0D) {
      totalRemainConsumption = 0.0D;
    }
    Map<String, Object> map = new HashMap();
    map.put("list", rechargeVOs);
    map.put("totalRemainConsumption", Double.valueOf(totalRemainConsumption));
    return map;
  }
  
  private UserWithdrawLimitVO getLastRecharge(List<UserWithdrawLimitVO> vos)
  {
    for (int i = vos.size() - 1; i >= 0; i--) {
      if (((UserWithdrawLimitVO)vos.get(i)).getBean().getType() != 5) {
        return (UserWithdrawLimitVO)vos.get(i);
      }
    }
    return null;
  }
  
  private double[] getConsumptions(int userId, double needConsumption, String sTime, String eTime)
  {
    double remainConsumption = needConsumption;
    
    double lotteryBilling = this.uBetsService.getBillingOrder(userId, sTime, eTime);
    if (lotteryBilling < 0.0D) {
      lotteryBilling = 0.0D;
    }
    remainConsumption = MathUtil.subtract(remainConsumption, lotteryBilling);
    if (remainConsumption < 0.0D) {
      remainConsumption = 0.0D;
    }
    double gameBilling = this.gameBetsService.getBillingOrder(userId, sTime, eTime);
    if (gameBilling < 0.0D) {
      gameBilling = 0.0D;
    }
    remainConsumption = MathUtil.subtract(remainConsumption, gameBilling);
    if (remainConsumption < 0.0D) {
      remainConsumption = 0.0D;
    }
    double totalBilling = MathUtil.add(lotteryBilling, gameBilling);
    
    return new double[] { remainConsumption, totalBilling };
  }
}
