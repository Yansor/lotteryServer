package lottery.domains.content.biz.impl;

import java.util.ArrayList;
import java.util.List;
import javautils.StringUtil;
import javautils.date.Moment;
import javautils.jdbc.PageList;
import lottery.domains.content.biz.ActivityRechargeService;
import lottery.domains.content.biz.UserBillService;
import lottery.domains.content.biz.UserSysMessageService;
import lottery.domains.content.biz.UserWithdrawLimitService;
import lottery.domains.content.dao.ActivityRechargeBillDao;
import lottery.domains.content.dao.UserDao;
import lottery.domains.content.entity.ActivityRechargeBill;
import lottery.domains.content.entity.User;
import lottery.domains.content.vo.activity.ActivityRechargeBillVO;
import lottery.domains.content.vo.config.WithdrawConfig;
import lottery.domains.pool.LotteryDataFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActivityRechargeServiceImpl
  implements ActivityRechargeService
{
  @Autowired
  private UserDao uDao;
  @Autowired
  private ActivityRechargeBillDao aRechargeBillDao;
  @Autowired
  private UserBillService uBillService;
  @Autowired
  private UserSysMessageService uSysMessageService;
  @Autowired
  private UserWithdrawLimitService uWithdrawLimitService;
  @Autowired
  private LotteryDataFactory lotteryDataFactory;
  
  public PageList search(String username, String date, String keyword, Integer status, int start, int limit)
  {
    List<Criterion> criterions = new ArrayList();
    List<Order> orders = new ArrayList();
    boolean isSearch = true;
    if (StringUtil.isNotNull(username))
    {
      User user = this.uDao.getByUsername(username);
      if (user != null) {
        criterions.add(Restrictions.eq("userId", Integer.valueOf(user.getId())));
      } else {
        isSearch = false;
      }
    }
    if (StringUtil.isNotNull(date)) {
      criterions.add(Restrictions.like("time", date, MatchMode.ANYWHERE));
    }
    if (StringUtil.isNotNull(keyword))
    {
      Disjunction disjunction = Restrictions.or(new Criterion[0]);
      disjunction.add(Restrictions.eq("ip", keyword));
      criterions.add(disjunction);
    }
    if (status != null) {
      criterions.add(Restrictions.eq("status", status));
    }
    orders.add(Order.desc("id"));
    if (isSearch)
    {
      List<ActivityRechargeBillVO> list = new ArrayList();
      PageList pList = this.aRechargeBillDao.find(criterions, orders, start, limit);
      for (Object tmpBean : pList.getList()) {
        list.add(new ActivityRechargeBillVO((ActivityRechargeBill)tmpBean, this.lotteryDataFactory));
      }
      pList.setList(list);
      return pList;
    }
    return null;
  }
  
  public boolean agree(int id)
  {
    ActivityRechargeBill entity = this.aRechargeBillDao.getById(id);
    if ((entity != null) && 
      (entity.getStatus() == 0))
    {
      String thisTime = new Moment().toSimpleTime();
      entity.setStatus(1);
      entity.setTime(thisTime);
      boolean aFlag = this.aRechargeBillDao.update(entity);
      if (aFlag)
      {
        User uBean = this.uDao.getById(entity.getUserId());
        boolean uFlag = this.uDao.updateLotteryMoney(uBean.getId(), entity.getMoney());
        if (uFlag)
        {
          int type = 3;
          int subType = 2;
          double percent = this.lotteryDataFactory.getWithdrawConfig().getSystemConsumptionPercent();
          this.uWithdrawLimitService.add(uBean.getId(), entity.getMoney(), thisTime, type, subType, percent);
          
          String remarks = "开业大酬宾。";
          int refType = 1;
          this.uBillService.addActivityBill(uBean, 2, entity.getMoney(), refType, remarks);
          this.uSysMessageService.addActivityRecharge(uBean.getId(), entity.getMoney());
        }
        return uFlag;
      }
    }
    return false;
  }
  
  public boolean refuse(int id)
  {
    ActivityRechargeBill entity = this.aRechargeBillDao.getById(id);
    if ((entity != null) && 
      (entity.getStatus() == 0))
    {
      entity.setStatus(-1);
      return this.aRechargeBillDao.update(entity);
    }
    return false;
  }
  
  public boolean check(int id)
  {
    ActivityRechargeBill entity = this.aRechargeBillDao.getById(id);
    if (entity != null)
    {
      String date = new Moment().fromTime(entity.getPayTime()).toSimpleDate();
      List<ActivityRechargeBill> list = this.aRechargeBillDao.get(entity.getIp(), date);
      if ((list != null) && (list.size() > 1)) {
        return true;
      }
    }
    return false;
  }
}
